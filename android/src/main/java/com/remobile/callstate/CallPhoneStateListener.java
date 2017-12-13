package com.remobile.callstate;

import android.telephony.PhoneStateListener;

public class CallPhoneStateListener extends PhoneStateListener {

    private PhoneCallStateUpdate callStatCallBack;

    public CallPhoneStateListener(PhoneCallStateUpdate callStatCallBack) {
        super();
        this.callStatCallBack = callStatCallBack;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        this.callStatCallBack.phoneCallStateUpdated(state, incomingNumber);
    }

    interface PhoneCallStateUpdate {
        void phoneCallStateUpdated(int state, String incomingNumber);
    }

}
