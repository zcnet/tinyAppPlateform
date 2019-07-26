package com.zcnet.voice_callback_lib;

import com.zcnet.voice_callback_lib.voice_data.VABaseOnCompleteReqPostData;
import com.zcnet.voice_callback_lib.voice_data.VABaseOnResultReqPostData;

public interface IVoiceCallback2BO {
    void onResult(VABaseOnResultReqPostData reqData);
    void onComplete(VABaseOnCompleteReqPostData vaBaseOnCompleteReqPostData,IVoiceDataCallBack callBack);
    void onPickuplistResult(String pickuplist);
}