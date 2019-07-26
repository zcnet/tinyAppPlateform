package com.zcnet.voice_callback_lib.va_3rd_biz;

public class VA3rdBiz {
    private PhoneCall phoneCall = new PhoneCall();

    public void MAKE_CALL() throws Exception {
        phoneCall.makeCall();
    }

    public void STOP_CALL() throws Exception {
        phoneCall.stopCall();
    }

    public void PHONE_NUM_MODIFY() throws Exception {
        phoneCall.phoneNumModify();
    }

    public void CHECK_MISSING_CALLS() throws Exception {
        phoneCall.checkMissingCalls();
    }

    public void REDIAL_CALL() throws Exception {
        phoneCall.redialCall();
    }

    public void PHONE_NUM_CLEAR() throws Exception {
        phoneCall.phoneNumClear();
    }
}
