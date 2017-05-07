package com.upsight.android.analytics.internal.session;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.upsight.android.Upsight;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.configuration.UpsightConfiguration;
import com.upsight.android.analytics.event.session.UpsightSessionPauseEvent;
import com.upsight.android.analytics.event.session.UpsightSessionResumeEvent;
import com.upsight.android.analytics.event.session.UpsightSessionStartEvent;
import com.upsight.android.analytics.internal.DispatcherService;
import com.upsight.android.analytics.internal.session.ApplicationStatus.State;
import com.upsight.android.analytics.internal.session.SessionInitializer.Type;
import com.upsight.android.analytics.provider.UpsightLocationTracker;
import com.upsight.android.analytics.provider.UpsightSessionContext;
import com.upsight.android.analytics.session.UpsightSessionCallbacks;
import com.upsight.android.analytics.session.UpsightSessionInfo;
import com.upsight.android.internal.util.PreferencesHelper;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.persistence.UpsightDataStore;
import com.upsight.android.persistence.annotation.Created;
import com.upsight.android.persistence.annotation.Updated;
import java.io.IOException;

public class SessionManagerImpl implements SessionManager {
    private static final String KEY_SESSION = "com.upsight.session_callbacks";
    private static final String LOG_TAG;
    private static final String PREFERENCES_KEY_JSON_CONFIG = "session_manager_json_config";
    private static final String PREFERENCES_KEY_LAST_KNOWN_SESSION_TIME = "last_known_session_time";
    private Context mAppContext;
    private Session mBackgroundSession;
    private final Clock mClock;
    private ConfigParser mConfigParser;
    private Config mCurrentConfig;
    private long mLastKnownSessionTs;
    private UpsightLogger mLogger;
    private Session mSession;
    private long mStopRequestedTs;
    private UpsightContext mUpsight;
    protected UpsightSessionCallbacks mUpsightSessionCallbacks;

    public static final class Config {
        public final long timeToNewSession;

        Config(long timeToNewSession) {
            this.timeToNewSession = timeToNewSession;
        }

        public boolean isValid() {
            return this.timeToNewSession > 0;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            if (((Config) o).timeToNewSession != this.timeToNewSession) {
                return false;
            }
            return true;
        }
    }

    static {
        LOG_TAG = SessionManagerImpl.class.getSimpleName();
    }

    SessionManagerImpl(Context appContext, UpsightContext upsight, UpsightDataStore dataStore, UpsightLogger logger, ConfigParser configParser, Clock clock) {
        this.mLogger = logger;
        this.mConfigParser = configParser;
        this.mAppContext = appContext;
        this.mUpsight = upsight;
        this.mClock = clock;
        this.mBackgroundSession = BackgroundSessionImpl.create(appContext, new BackgroundSessionInitializer());
        this.mLastKnownSessionTs = PreferencesHelper.getLong(appContext, PREFERENCES_KEY_LAST_KNOWN_SESSION_TIME, 0);
        this.mUpsightSessionCallbacks = loadSessionHook();
        dataStore.subscribe(this);
        applyConfiguration(fetchCurrentConfig());
    }

    public UpsightSessionInfo getLatestSessionInfo() {
        return SessionImpl.getLatestSessionInfo(this.mUpsight);
    }

    public Session getBackgroundSession() {
        this.mUpsight.startService(new Intent(this.mUpsight.getApplicationContext(), DispatcherService.class));
        return this.mBackgroundSession;
    }

    public synchronized Session getSession() {
        Session startSession;
        this.mUpsight.startService(new Intent(this.mUpsight.getApplicationContext(), DispatcherService.class));
        boolean isSessionNull = isSessionNull();
        boolean isExpired = isExpired();
        if (isSessionNull || isExpired) {
            startSession = startSession(isSessionNull, isExpired, new StandardSessionInitializer());
        } else {
            startSession = this.mSession;
        }
        return startSession;
    }

    public synchronized Session startSession(@NonNull SessionInitializer initializer) {
        this.mUpsight.startService(new Intent(this.mUpsight.getApplicationContext(), DispatcherService.class));
        return startSession(isSessionNull(), isExpired(), initializer);
    }

    public synchronized void stopSession() {
        Session currentSession = getSession();
        this.mStopRequestedTs = this.mClock.currentTimeSeconds();
        currentSession.updateDuration(this.mAppContext, this.mStopRequestedTs);
    }

    @Created
    public synchronized void onConfigurationCreated(UpsightConfiguration config) {
        if (SessionManager.CONFIGURATION_SUBTYPE.equals(config.getScope())) {
            applyConfiguration(config.getConfiguration());
        }
    }

    @Updated
    public synchronized void onApplicationStatusUpdated(ApplicationStatus statusEvent) {
        if (State.BACKGROUND.equals(statusEvent.getState())) {
            this.mLastKnownSessionTs = this.mClock.currentTimeSeconds();
            PreferencesHelper.putLong(this.mAppContext, PREFERENCES_KEY_LAST_KNOWN_SESSION_TIME, this.mLastKnownSessionTs);
            UpsightSessionPauseEvent.createBuilder().record(this.mUpsight);
        }
    }

    private UpsightSessionCallbacks loadSessionHook() {
        try {
            Bundle bundle = this.mUpsight.getPackageManager().getApplicationInfo(this.mUpsight.getPackageName(), AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS).metaData;
            if (bundle != null && bundle.containsKey(KEY_SESSION)) {
                try {
                    Class<?> clazz = Class.forName(bundle.getString(KEY_SESSION));
                    if (UpsightSessionCallbacks.class.isAssignableFrom(clazz)) {
                        return (UpsightSessionCallbacks) clazz.newInstance();
                    }
                    throw new IllegalStateException(String.format("Class %s must implement %s!", new Object[]{clazz.getName(), UpsightSessionCallbacks.class.getName()}));
                } catch (ClassNotFoundException e) {
                    this.mLogger.m576e(Upsight.LOG_TAG, String.format("Unexpected error: Class: %s was not found.", new Object[]{session}), e);
                } catch (IllegalAccessException e2) {
                    this.mLogger.m576e(Upsight.LOG_TAG, String.format("Unexpected error: Class: %s does not have public access.", new Object[]{session}), e2);
                } catch (InstantiationException e3) {
                    this.mLogger.m576e(Upsight.LOG_TAG, String.format("Unexpected error: Class: %s could not be instantiated", new Object[]{session}), e3);
                }
            }
        } catch (NameNotFoundException e4) {
            this.mLogger.m576e(Upsight.LOG_TAG, "Unexpected error: Package name missing!?", e4);
        }
        return null;
    }

    private String fetchCurrentConfig() {
        return PreferencesHelper.getString(this.mAppContext, PREFERENCES_KEY_JSON_CONFIG, SessionManager.DEFAULT_CONFIGURATION);
    }

    private boolean applyConfiguration(String jsonConfig) {
        try {
            Config config = this.mConfigParser.parseConfig(jsonConfig);
            if (config == null || !config.isValid()) {
                this.mLogger.m582w(LOG_TAG, "New config is invalid", new Object[0]);
                return false;
            } else if (config.equals(this.mCurrentConfig)) {
                this.mLogger.m582w(LOG_TAG, "New config ignored because it is equal to current config", new Object[0]);
                return true;
            } else {
                PreferencesHelper.putString(this.mAppContext, PREFERENCES_KEY_JSON_CONFIG, jsonConfig);
                this.mCurrentConfig = config;
                return true;
            }
        } catch (IOException e) {
            this.mLogger.m576e(LOG_TAG, "Failed to apply new config", e);
            return false;
        }
    }

    private boolean isSessionNull() {
        return this.mSession == null;
    }

    private boolean isExpired() {
        return (this.mStopRequestedTs != 0 && this.mClock.currentTimeSeconds() - this.mStopRequestedTs > this.mCurrentConfig.timeToNewSession) || (this.mSession == null && this.mClock.currentTimeSeconds() - this.mLastKnownSessionTs > this.mCurrentConfig.timeToNewSession);
    }

    private Session startSession(boolean isSessionNull, boolean isExpired, SessionInitializer initializer) {
        if (!Upsight.isEnabled(this.mUpsight)) {
            return this.mBackgroundSession;
        }
        boolean fromPush = Type.PUSH.equals(initializer.getType());
        boolean sessionIsStopped = this.mStopRequestedTs != 0;
        this.mStopRequestedTs = 0;
        if (fromPush || isExpired) {
            UpsightLocationTracker.purge(this.mUpsight);
            if (this.mUpsightSessionCallbacks != null) {
                this.mUpsightSessionCallbacks.onStart(new UpsightSessionContext(this.mUpsight), SessionImpl.getLatestSessionInfo(this.mUpsight));
            }
            this.mSession = SessionImpl.incrementAndCreate(this.mAppContext, this.mClock, initializer);
            UpsightSessionStartEvent.createBuilder().record(this.mUpsight);
            if (this.mUpsightSessionCallbacks != null) {
                this.mUpsightSessionCallbacks.onStarted(this.mUpsight);
            }
        } else if (isSessionNull) {
            if (this.mUpsightSessionCallbacks != null) {
                this.mUpsightSessionCallbacks.onResume(new UpsightSessionContext(this.mUpsight), SessionImpl.getLatestSessionInfo(this.mUpsight));
            }
            this.mSession = SessionImpl.create(this.mAppContext, this.mClock, initializer);
            UpsightSessionResumeEvent.createBuilder().record(this.mUpsight);
            if (this.mUpsightSessionCallbacks != null) {
                this.mUpsightSessionCallbacks.onResumed(this.mUpsight);
            }
        } else if (sessionIsStopped) {
            if (this.mUpsightSessionCallbacks != null) {
                this.mUpsightSessionCallbacks.onResume(new UpsightSessionContext(this.mUpsight), SessionImpl.getLatestSessionInfo(this.mUpsight));
            }
            UpsightSessionResumeEvent.createBuilder().record(this.mUpsight);
            if (this.mUpsightSessionCallbacks != null) {
                this.mUpsightSessionCallbacks.onResumed(this.mUpsight);
            }
        }
        this.mLastKnownSessionTs = this.mClock.currentTimeSeconds();
        PreferencesHelper.putLong(this.mAppContext, PREFERENCES_KEY_LAST_KNOWN_SESSION_TIME, this.mLastKnownSessionTs);
        return this.mSession;
    }
}
