package com.mopub.volley;

import android.content.Intent;

public class AuthFailureError extends VolleyError {
    private Intent mResolutionIntent;

    public AuthFailureError(Intent intent) {
        this.mResolutionIntent = intent;
    }

    public AuthFailureError(NetworkResponse response) {
        super(response);
    }

    public AuthFailureError(String message) {
        super(message);
    }

    public AuthFailureError(String message, Exception reason) {
        super(message, reason);
    }

    public Intent getResolutionIntent() {
        return this.mResolutionIntent;
    }

    public String getMessage() {
        if (this.mResolutionIntent != null) {
            return "User needs to (re)enter credentials.";
        }
        return super.getMessage();
    }
}
