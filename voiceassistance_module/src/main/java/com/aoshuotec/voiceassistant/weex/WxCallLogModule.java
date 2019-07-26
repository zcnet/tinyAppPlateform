package com.aoshuotec.voiceassistant.weex;

import android.os.Bundle;

import com.aoshuotec.voiceassistant.constants.WxConstant;
import com.aoshuotec.voiceassistant.listener.IDataCallBack;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

/**
 * Created by sun on 2018/10/17
 * <p>
 * Weex注册名为callLogUtil
 */

public class WxCallLogModule extends WXModule {
    //logcat |grep -iE "RunTime|sun"

    private static JSCallback sCallBack;

    private static IDataCallBack mCallBack;

    //添加Manager回调
    public static void setCallBack(IDataCallBack callBack){
        mCallBack = callBack;
    }

    public static JSCallback getJsCallBack() {
        return sCallBack;
    }

    /**
     * 查找通话记录
     */
    @JSMethod(uiThread = false)
    public static void findCallRecord(String recordType, JSCallback callback) {
        sCallBack = callback;
        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_CALL_LOG);
        bundle.putString(WxConstant.MESSAGE_CALL_LOG_TYPE, recordType);
        if(mCallBack!=null){
            mCallBack.getWxCallLogDataBack(bundle);
        }
    }

}
