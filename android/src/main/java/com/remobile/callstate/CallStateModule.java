package com.remobile.callstate;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;


public class CallStateModule
        extends BaseModule
        implements Application.ActivityLifecycleCallbacks,
        CallPhoneStateListener.PhoneCallStateUpdate {

    private boolean wasAppInOffHook = false;
    private boolean wasAppInRinging = false;
    private ReactApplicationContext reactContext;
    private TelephonyManager telephonyManager;
    private CallPhoneStateListener callPhoneStateListener;

    public CallStateModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    public String getName() {
        return "CallState";
    }

    @ReactMethod
    public void startListener() {
        Log.i("----------------","startListener");
        telephonyManager = (TelephonyManager) this.reactContext.getSystemService(Context.TELEPHONY_SERVICE);
        callPhoneStateListener = new CallPhoneStateListener(this);
        telephonyManager.listen(callPhoneStateListener,CallPhoneStateListener.LISTEN_CALL_STATE);
    }

    @ReactMethod
    public void stopListener() {
        Log.i("----------------","stopListener");
        telephonyManager.listen(callPhoneStateListener,CallPhoneStateListener.LISTEN_NONE);
        telephonyManager = null;
        callPhoneStateListener = null;
    }

    // Activity Lifecycle Methods
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceType) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (wasAppInOffHook) {
            wasAppInOffHook = false;
            WritableMap params = Arguments.createMap();
            params.putString("state", "Disconnected");
            sendEvent("callStateUpdated", params);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public void phoneCallStateUpdated(int state, String phoneNumber) {
        WritableMap params = Arguments.createMap();

        Log.i("phoneCallStateUpdated",state+phoneNumber);
        switch (state) {
            //Hangup
            case TelephonyManager.CALL_STATE_IDLE:
                if(wasAppInRinging || wasAppInOffHook) {
                    params.putString("state", "Disconnected");
                }
                sendEvent("callStateUpdated", params);
                wasAppInRinging = false;
                wasAppInOffHook = false;
                // Device call state: No activity.
                break;
            //Outgoing
            case TelephonyManager.CALL_STATE_OFFHOOK:
                if (wasAppInOffHook || wasAppInRinging) {
                    params.putString("state", "Connected");
                } else {
                    params.putString("state", "Dialing");
                }
                sendEvent("callStateUpdated", params);
                //Device call state: Off-hook. At least one call exists that is dialing, active, or on hold, and no calls are ringing or waiting.
                wasAppInOffHook = true;

                break;
            //Incoming
            case TelephonyManager.CALL_STATE_RINGING:
                wasAppInRinging = true;
                params.putString("state", "Incoming");
                sendEvent("callStateUpdated", params);
                break;
        }
    }
}
