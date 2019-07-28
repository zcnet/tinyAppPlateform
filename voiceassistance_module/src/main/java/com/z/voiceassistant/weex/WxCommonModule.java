package com.z.voiceassistant.weex;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.z.voiceassistant.JniGateway;
import com.z.voiceassistant.application.MApplication;
import com.z.voiceassistant.constants.WxConstant;
import com.z.voiceassistant.listener.IDataCallBack;
import com.z.voiceassistant.models.WxAddWxBean;
import com.google.gson.Gson;
import com.z.tinyapp.utils.common.WeexApps;
import com.z.tinyapp.utils.logs.sLog;
import com.sun.weexandroid_module.WxRvUtils;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun on 2018/11/2
 */

public class WxCommonModule extends WXModule {

    private static final String TAG = "sunCommonModule";
    private static IDataCallBack mCallBack;

    private static JSCallback mJsCallBack;

    private static JSCallback mTTSCallBack;

    private static JSCallback mExitCallBack;

    private static Map<String, JSCallback> mMediaCallBack = new HashMap<>();

    //添加Manager回调
    public static void setCallBack(IDataCallBack callBack) {
        mCallBack = callBack;
    }

    public static JSCallback getJsCallBack() {
        return mJsCallBack;
    }

    public static JSCallback getTTSJsCallBack() {
        return mTTSCallBack;
    }

    public static JSCallback getMediaJsCallBack(String action, String pkgName) {
        return mMediaCallBack.get(action + pkgName);
    }

    public static JSCallback getExitCallBack() {
        return mExitCallBack;
    }

    //通过Android端向后台发送数据
    @JSMethod(uiThread = false)
    public void uploadData(String json) {
        sLog.i(TAG, "uploadData: ");
        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_WX_COMMON_METHOD_UPLOAD);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_METHOD_DATA, json);
        if (mCallBack != null) {
            mCallBack.getWxCommonDataBack(bundle);
        }
    }

    @JSMethod(uiThread = false)
    public void sendWxParam(String data) {
        sLog.i(TAG, "sendWxParam: data --> " + data);
        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_WX_SEND_2_WX);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_METHOD_DATA, data);
        if (mCallBack != null) {
            mCallBack.getWxCommonDataBack(bundle);
        }
    }

    @JSMethod(uiThread = false)
    public void addWx(String json) {
        sLog.i(TAG, "addWx: json=========>" + json);
        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_WX_COMMON_METHOD_ADD_WX);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_METHOD_DATA, json);
        if (mCallBack != null) {
            mCallBack.getWxCommonDataBack(bundle);
        }
    }

    @JSMethod(uiThread = false)
    public void addWxNotShow(String json) {
        sLog.i(TAG, "addWxNotShow: ");
        WxAddWxBean bean = new Gson().fromJson(json, WxAddWxBean.class);
        Map<String, Object> map = new HashMap<>();

        map.put("name", System.currentTimeMillis());
        map.put("data", bean.getData());

        String name = bean.getName();
        /*String path = bean.getPath();
        int idx = -1;
        idx = path.indexOf("weex.js");
        if (idx != -1){
            path = path.substring(0, idx);
        } else {
            idx = path.indexOf(".js");
            if (idx != -1){
                path = path.substring(0, idx);
            }
        }*/

        String tel_path = WeexApps.getAppListMgr().findVRCardPath("liteapp_voiceassistant_offline", name);

        WxRvUtils.getFloatWindowViewNoShowWithInstance(
                MApplication.getContext(),
                tel_path,
                map
        );
    }

    @JSMethod(uiThread = false)
    public void removeWx(String json) {
        sLog.i(TAG, "removeWx: ");
        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_WX_COMMON_METHOD_REMOVE_WX);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_METHOD_DATA, json);
        if (mCallBack != null) {
            mCallBack.getWxCommonDataBack(bundle);
        }
    }

    @JSMethod(uiThread = false)
    public void playTTS(String str, boolean needCancel) {
        sLog.i(TAG, "playTTS: --str-->" + str + "--needCancel-->" + needCancel);
        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_WX_COMMON_PLAY_TTS);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_PLAY_TTS_DATA, str);
        bundle.putBoolean(WxConstant.MESSAGE_WX_COMMON_PLAY_TTS_NEED_CANCEL, needCancel);
        if (mCallBack != null) {
            mCallBack.getWxCommonDataBack(bundle);
        }
    }

    //Weex需要知道TTS什么时候播放完成 ，所以先注册的一个接口
    @JSMethod(uiThread = false)
    public void onTTSFinish(JSCallback callback) {
        sLog.i(TAG, "onTtsFinish: register");
        mTTSCallBack = callback;
    }

    @JSMethod(uiThread = false)
    public void onSessionStop(JSCallback callback) {
        sLog.i(TAG, "onSessionStop:");
        SystemClock.sleep(200);
        mExitCallBack = callback;
        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_WX_COMMON_SESSION_EXIT);
        if (mCallBack != null) {
            mCallBack.getWxCommonDataBack(bundle);
        }
    }

    @JSMethod(uiThread = false)
    public void startRecord() {
        sLog.i(TAG, "startRecord: ");
        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_WX_COMMON_START_RECORD);
        if (mCallBack != null) {
            mCallBack.getWxCommonDataBack(bundle);
        }
    }

    @JSMethod(uiThread = false)
    public void stopRecord() {
        sLog.i(TAG, "stopRecord: ");
        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_WX_COMMON_STOP_RECORD);
        if (mCallBack != null) {
            mCallBack.getWxCommonDataBack(bundle);
        }
    }

    @JSMethod(uiThread = false)
    public void ttsCancel() {
        sLog.i(TAG, "ttsCancel: ");
        JniGateway.JniTtsCancel();
    }

    @JSMethod(uiThread = false)
    public void closePage() {
        sLog.i(TAG, "closePage:");
        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_WX_COMMON_EXIT);
        if (mCallBack != null) {
            mCallBack.getWxCommonDataBack(bundle);
        }
    }

    @JSMethod(uiThread = false)
    public void openActivity(String pkgName, String className) {
        sLog.i(TAG, "openActivity: --> " + pkgName + className);
        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_OPEN_ACTIVITY);
        bundle.putString(WxConstant.MESSAGE_WX_ACTIVITY_PKG_NAME, pkgName);
        bundle.putString(WxConstant.MESSAGE_WX_ACTIVITY_CLASS_NAME, className);
        if (mCallBack != null) {
            mCallBack.getWxCommonDataBack(bundle);
        }
    }

    @JSMethod(uiThread = false)
    public void searchTips(String botId, String listKey, JSCallback callback) {
        sLog.i(TAG, "searchErrorText: botId -> " + botId + "  listKey --> " + listKey);
        mJsCallBack = callback;
        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_WX_COMMON_SEARCH);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_BOT_ID, botId);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_LIST_KEY, listKey);
        if (mCallBack != null) {
            mCallBack.getWxCommonDataBack(bundle);
        }
    }

    @JSMethod(uiThread = false)
    public void addListener(String mediaInfo, String action, String pkgName, JSCallback callback) {
        sLog.i(TAG, "addListener: " + mediaInfo);
        mMediaCallBack.put(pkgName + action, callback);
        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_WX_COMMON_CALLBACK);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_CALLBACK_NAME, mediaInfo);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_CALLBACK_ACTION, action);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_CALLBACK_PACKAGE_NAME, pkgName);
        if (mCallBack != null) {
            mCallBack.getWxCommonDataBack(bundle);
        }
    }

    @JSMethod(uiThread = false)
    public void searchMusic(String mediaSearchInfo, String action, String pkgName, JSCallback callback) {
        sLog.i(TAG, "searchMusic: " + mediaSearchInfo + " act ->" + action + " pkgName -> " + pkgName);
        mMediaCallBack.put(action + pkgName, callback);
        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_WX_COMMON_AIDL_SEARCH);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_METHOD_DATA, mediaSearchInfo);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_CALLBACK_ACTION, action);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_CALLBACK_PACKAGE_NAME, pkgName);
        if (mCallBack != null) {
            mCallBack.getWxCommonDataBack(bundle);
        }
    }

    @JSMethod(uiThread = false)
    public void getCurrentMediaInfo(String getInfo, String action, String pkgName, JSCallback callback) {
        sLog.i(TAG, "getCurrentMediaInfo: " + getInfo);
        mMediaCallBack.put(action + pkgName, callback);
        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_WX_COMMON_AIDL_GET_CURRENT);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_METHOD_DATA, getInfo);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_CALLBACK_ACTION, action);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_CALLBACK_PACKAGE_NAME, pkgName);
        if (mCallBack != null) {
            mCallBack.getWxCommonDataBack(bundle);
        }
    }

    @JSMethod(uiThread = false)
    public void playMedia(String mediaPlayInfo, String action, String pkgName, JSCallback callback) {
        sLog.i(TAG, "playMedia: " + mediaPlayInfo);
        mMediaCallBack.put(action + pkgName, callback);
        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_WX_COMMON_AIDL_PLAY);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_METHOD_DATA, mediaPlayInfo);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_CALLBACK_ACTION, action);
        bundle.putString(WxConstant.MESSAGE_WX_COMMON_CALLBACK_PACKAGE_NAME, pkgName);
        if (mCallBack != null) {
            mCallBack.getWxCommonDataBack(bundle);
        }
    }

    @JSMethod(uiThread = false)
    public void sendCarBroadCast() {
        sLog.i(TAG, "sendCarBroadCast: ");
        Intent intent = new Intent();
        intent.setAction("com.baidu.iov.dueros.car2home.action.update_bind_device");
        MApplication.getContext().sendBroadcast(intent);
    }


}
