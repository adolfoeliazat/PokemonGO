package com.upsight.android.analytics.internal;

import dagger.internal.Factory;
import rx.Scheduler;

public final class AnalyticsSchedulersModule_ProvideSendingExecutorFactory implements Factory<Scheduler> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final AnalyticsSchedulersModule module;

    static {
        $assertionsDisabled = !AnalyticsSchedulersModule_ProvideSendingExecutorFactory.class.desiredAssertionStatus();
    }

    public AnalyticsSchedulersModule_ProvideSendingExecutorFactory(AnalyticsSchedulersModule module) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            return;
        }
        throw new AssertionError();
    }

    public Scheduler get() {
        Scheduler provided = this.module.provideSendingExecutor();
        if (provided != null) {
            return provided;
        }
        throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<Scheduler> create(AnalyticsSchedulersModule module) {
        return new AnalyticsSchedulersModule_ProvideSendingExecutorFactory(module);
    }
}
