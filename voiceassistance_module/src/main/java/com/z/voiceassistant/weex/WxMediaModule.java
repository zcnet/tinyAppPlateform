package com.z.voiceassistant.weex;

import com.z.voiceassistant.listener.IDataCallBack;
import com.z.voiceassistant.utils.Logg;
import com.z.tinyapp.utils.logs.sLog;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;


/**
 * Created by sun on 2018/11/2
 */

public class WxMediaModule extends WXModule {

    private static final String TAG = "sunWxMediaModule";
    private static IDataCallBack mCallBack;

    //添加Manager回调
    public static void setCallBack(IDataCallBack callBack) {
        mCallBack = callBack;
    }

    @JSMethod(uiThread = false)
    public void openFM() {
        sLog.i(TAG, "openFM: ");
//        Bundle bundle = new Bundle();
//        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_WX_MEDIA_OPEN_FM);
//        if (mCallBack != null) {
//            mCallBack.getWxMediaDataBack(bundle);
//        }

        try {
            ITunerService.Inst().ReqSwitchBandFreq(EStationType.STATIONTYPE_FM, 0);
        } catch (RpcError rpcError) {
            Logg.e(TAG, "onWxMediaFunc MESSAGE_WX_MEDIA_OPEN_FM -error-> ", rpcError);
        }
    }

    @JSMethod(uiThread = false)
    public void openAM() {
        sLog.i(TAG, "openAM: ");
//        Bundle bundle = new Bundle();
//        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_WX_MEDIA_OPEN_AM);
//        if (mCallBack != null) {
//            mCallBack.getWxMediaDataBack(bundle);
//        }

        try {
            ITunerService.Inst().ReqSwitchBandFreq(EStationType.STATIONTYPE_AM, 0);
        } catch (RpcError rpcError) {
            Logg.e(TAG, "onWxMediaFunc MESSAGE_WX_MEDIA_OPEN_AM -error-> ", rpcError);
        }
    }

    @JSMethod(uiThread = false)
    public void playFM(float f) {
        sLog.i(TAG, "playFM: f -> " + f);
//        Bundle bundle = new Bundle();
//        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_WX_MEDIA_PLAY_FM);
//        bundle.putFloat(WxConstant.MESSAGE_WX_COMMON_METHOD_DATA,f);
//        if (mCallBack != null) {
//            mCallBack.getWxMediaDataBack(bundle);
//        }

        try {
//            float f = bundle.getFloat(WxConstant.MESSAGE_WX_COMMON_METHOD_DATA);
//            Logg.i(TAG, "MESSAGE_WX_MEDIA_PLAY_FM FM -> " + f);
            ITunerService.Inst().ReqSwitchBandFreq(EStationType.STATIONTYPE_FM, (int) (f * 1000));
        } catch (RpcError rpcError) {
            Logg.e(TAG, "onWxMediaFunc MESSAGE_WX_MEDIA_PLAY_FM -error-> ", rpcError);
        }
    }

    @JSMethod(uiThread = false)
    public void playAM(int i) {
        sLog.i(TAG, "playAM: i -> " + i);
//        Bundle bundle = new Bundle();
//        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_WX_MEDIA_PLAY_AM);
//        bundle.putInt(WxConstant.MESSAGE_WX_COMMON_METHOD_DATA,i);
//        if (mCallBack != null) {
//            mCallBack.getWxMediaDataBack(bundle);
//        }

        try {
//            Logg.i(TAG, "MESSAGE_WX_MEDIA_PLAY_FM AM -> " + f);
            ITunerService.Inst().ReqSwitchBandFreq(EStationType.STATIONTYPE_AM, i);
        } catch (RpcError rpcError) {
            Logg.e(TAG, "onWxMediaFunc MESSAGE_WX_MEDIA_PLAY_AM -error-> ", rpcError);
        }
    }

}
