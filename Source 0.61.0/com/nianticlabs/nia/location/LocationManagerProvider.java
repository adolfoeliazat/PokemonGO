package com.nianticlabs.nia.location;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import com.nianticlabs.nia.contextservice.ContextService;
import com.nianticlabs.nia.contextservice.ServiceStatus;
import com.nianticlabs.nia.location.GpsProvider.GpsProviderListener;
import com.nianticlabs.nia.location.Provider.ProviderListener;
import java.util.ArrayList;
import java.util.List;

public class LocationManagerProvider implements GpsProvider {
    private static final boolean ENABLE_VERBOSE_LOGS = false;
    private static final String TAG = "LocationManagerProvider";
    private final Context context;
    private boolean firstLocationUpdate;
    private final Listener gpsStatusListener;
    private LocationListener listener;
    private LocationManager locationManager;
    private final String provider;
    private ProviderListener providerListener;
    private boolean running;
    private final float updateDistance;
    private final int updateTime;

    /* renamed from: com.nianticlabs.nia.location.LocationManagerProvider.1 */
    class C09571 implements LocationListener {
        C09571() {
        }

        public void onLocationChanged(Location location) {
            if (LocationManagerProvider.this.running) {
                LocationManagerProvider.this.updateLocation(location);
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
            LocationManagerProvider.this.updateStatus(ServiceStatus.RUNNING);
        }

        public void onProviderDisabled(String provider) {
            LocationManagerProvider.this.updateStatus(ServiceStatus.PERMISSION_DENIED);
        }
    }

    /* renamed from: com.nianticlabs.nia.location.LocationManagerProvider.2 */
    class C09582 implements Listener {
        C09582() {
        }

        private GpsSatellite[] getSatellites(GpsStatus gpsStatus) {
            List<GpsSatellite> list = new ArrayList();
            for (GpsSatellite sat : gpsStatus.getSatellites()) {
                list.add(sat);
            }
            return (GpsSatellite[]) list.toArray(new GpsSatellite[list.size()]);
        }

        public void onGpsStatusChanged(int event) {
            if (LocationManagerProvider.this.running) {
                GpsStatus status = LocationManagerProvider.this.locationManager.getGpsStatus(null);
                LocationManagerProvider.this.updateGpsStatus(status.getTimeToFirstFix(), getSatellites(status));
            }
        }
    }

    public LocationManagerProvider(Context context, String provider, int updateTime, float updateDistance) {
        this.running = ENABLE_VERBOSE_LOGS;
        this.firstLocationUpdate = ENABLE_VERBOSE_LOGS;
        this.listener = new C09571();
        this.gpsStatusListener = new C09582();
        this.providerListener = null;
        this.context = context;
        this.provider = provider;
        this.updateTime = updateTime;
        this.updateDistance = updateDistance;
    }

    public void onStart() {
        this.locationManager = (LocationManager) this.context.getSystemService("location");
    }

    public void onStop() {
        this.locationManager = null;
    }

    public void onPause() {
        if (this.running) {
            try {
                this.locationManager.removeUpdates(this.listener);
                this.running = ENABLE_VERBOSE_LOGS;
            } catch (SecurityException e) {
                Log.e(TAG, "Not allowed to access " + this.provider + " for updates", e);
            }
            updateStatus(ServiceStatus.STOPPED);
        }
    }

    public void onResume() {
        this.firstLocationUpdate = true;
        ServiceStatus statusFailed = ServiceStatus.FAILED;
        try {
            this.locationManager.requestLocationUpdates(this.provider, (long) this.updateTime, this.updateDistance, this.listener, ContextService.getServiceLooper());
            Log.d(TAG, "Location manager initialized");
            if (this.provider == "gps") {
                this.locationManager.addGpsStatusListener(this.gpsStatusListener);
            }
            this.running = true;
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Could not request " + this.provider + " updates", e);
        } catch (SecurityException e2) {
            Log.e(TAG, "Not allowed to access " + this.provider + " for updates", e2);
            statusFailed = ServiceStatus.PERMISSION_DENIED;
        }
        ServiceStatus statusFailedCapture = statusFailed;
        if (this.running) {
            updateStatus(ServiceStatus.INITIALIZED);
            try {
                updateLocation(this.locationManager.getLastKnownLocation(this.provider));
                return;
            } catch (SecurityException e3) {
                return;
            }
        }
        updateStatus(statusFailedCapture);
    }

    public void setListener(ProviderListener listener) {
        this.providerListener = listener;
    }

    private void updateStatus(ServiceStatus status) {
        ProviderListener listener = this.providerListener;
        if (listener != null) {
            listener.onProviderStatus(status);
        }
    }

    private void updateLocation(Location location) {
        ProviderListener listener = this.providerListener;
        if (listener != null) {
            if (this.firstLocationUpdate) {
                this.firstLocationUpdate = ENABLE_VERBOSE_LOGS;
                updateStatus(ServiceStatus.RUNNING);
            }
            listener.onProviderLocation(location);
        }
    }

    private void updateGpsStatus(int timeToFix, GpsSatellite[] satellites) {
        ProviderListener listener = this.providerListener;
        if (listener != null && (listener instanceof GpsProviderListener)) {
            ((GpsProviderListener) listener).onGpsStatusUpdate(timeToFix, satellites);
        }
    }
}
