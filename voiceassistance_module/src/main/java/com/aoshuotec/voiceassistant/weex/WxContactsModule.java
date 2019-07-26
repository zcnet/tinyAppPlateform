package com.aoshuotec.voiceassistant.weex;

import android.os.Bundle;

import com.aoshuotec.voiceassistant.constants.WxConstant;
import com.aoshuotec.voiceassistant.listener.IDataCallBack;
import com.aoshuotec.voiceassistant.utils.Logg;
import com.z.tinyapp.utils.logs.sLog;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

/**
 * Created by sun on 2018/10/17
 */
@SuppressWarnings("unused")
public class WxContactsModule extends WXModule {
    //logcat |grep -iE "RunTime|sun"

    private static JSCallback sCallBack;

    private static IDataCallBack mCallBack;

    private static Bundle sBundle = null;


    //添加Manager回调
    public static void setCallBack(IDataCallBack callBack) {
        mCallBack = callBack;
    }

    public static JSCallback getJsCallBack() {
        return sCallBack;
    }

    interface IBundleGen {
        Bundle get();
    }
    private static void getWxContactDataBack(IBundleGen bundleGen) {
        synchronized (WxContactsModule.class){
            sBundle = bundleGen.get();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(7000);
                    synchronized (WxContactsModule.class){
                        if (sBundle != null){
                            mCallBack.getWxContactDataBack(sBundle);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    /**
     * 搜索通讯录
     */
    @JSMethod(uiThread = false)
    public static void searchContacts(String name, JSCallback callback) {
        sLog.e("sunWeexFunc", "searchContacts --->" + name);
        if (mCallBack == null) {
            Logg.e("sunWeexFunc", "searchContacts mCallBack null !!!!!");
            return;
        }

        sCallBack = callback;

        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_SEARCH);
        bundle.putString(WxConstant.MESSAGE_SEARCH_DATA, name);
        mCallBack.getWxContactDataBack(bundle);
    }

    /**
     * 搜索通讯录(带参数)
     */
    @JSMethod(uiThread = false)
    public static void searchContactsWithFlagment(String name, String flagment, JSCallback callBack) {
        sLog.e("sunWeexFunc", "searchContactsWithFlagment --->" + name);
        if (mCallBack == null) {
            Logg.e("sunWeexFunc", "searchContactsWithFlagment  mCallBack null !!!!!");
            return;
        }

        sCallBack = callBack;

        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_SEARCH_WITH_FLAG);
        bundle.putString(WxConstant.MESSAGE_SEARCH_DATA, name);
        bundle.putString(WxConstant.MESSAGE_SEARCH_DATA_FLAG, flagment);
        mCallBack.getWxContactDataBack(bundle);

    }


    /**
     * 拨打蓝牙电话
     */
    @JSMethod(uiThread = false)
    public static void startCallBTPhone(String num) {
        sLog.i("sunContactsModule", "startCallBTPhone: ");
        final String num1 = num;
        getWxContactDataBack(new IBundleGen() {
            @Override
            public Bundle get() {
                Bundle bundle = new Bundle();
                bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_CALL_BT_PHONE);
                bundle.putString(WxConstant.MESSAGE_CALL_PHONE_NUMBER, num1);
                return bundle;
            }
        });

        //mCallBack.getWxContactDataBack(bundle);

    }

    /**
     * 拨打安吉星电话
     */
    @JSMethod(uiThread = false)
    public static void startCallOnStarPhone(String num) {
        sLog.i("sunContactsModule", "startCallOnStarPhone: ");
        final String num1 = num;
        getWxContactDataBack(new IBundleGen() {
            @Override
            public Bundle get() {
                Bundle bundle = new Bundle();
                bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_CALL_ON_STAR_PHONE);
                bundle.putString(WxConstant.MESSAGE_CALL_PHONE_NUMBER, num1);
                return bundle;
            }
        });

        //mCallBack.getWxContactDataBack(bundle);


    }

    /**
     * 停止拨打蓝牙电话
     */
    @JSMethod(uiThread = false)
    public static void stopMakeBtCall() {
        getWxContactDataBack(new IBundleGen() {
            @Override
            public Bundle get() {
                Bundle bundle = new Bundle();
                bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_STOP_CALL_BT_PHONE);
                return bundle;
            }
        });

       // mCallBack.getWxContactDataBack(bundle);

    }

    /**
     * 停止拨打安吉星电话
     */
    @JSMethod(uiThread = false)
    public static void stopMakeOnStarCall() {
        getWxContactDataBack(new IBundleGen() {
            @Override
            public Bundle get() {
                Bundle bundle = new Bundle();
                bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_STOP_CALL_ON_STAR_PHONE);
                return bundle;
            }
        });

        //mCallBack.getWxContactDataBack(bundle);

    }

    /**
     * 重拨蓝牙电话
     */
    @JSMethod(uiThread = false)
    public static void redialBt() {
        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_REDIAL_BT_PHONE);
        mCallBack.getWxContactDataBack(bundle);

    }

    /**
     * 重拨安吉星电话
     */
    @JSMethod(uiThread = false)
    public static void redialOnStar() {
        Bundle bundle = new Bundle();
        bundle.putString(WxConstant.MESSAGE_WHAT, WxConstant.MESSAGE_REDIAL_ON_STAR_PHONE);
        mCallBack.getWxContactDataBack(bundle);

    }

    /**
     * 停止拨打
     */
    @JSMethod(uiThread = false)
    public static void stopCall() {
        sLog.i(null, "wxcontacts stopCall: ");
        if (sBundle != null ){
            if(WxConstant.MESSAGE_CALL_ON_STAR_PHONE.equals(sBundle.getString(WxConstant.MESSAGE_WHAT))) {
                stopMakeOnStarCall();
            } else if (WxConstant.MESSAGE_CALL_BT_PHONE.equals(sBundle.getString(WxConstant.MESSAGE_WHAT))) {
                stopMakeBtCall();
            }
        }
    }
}
