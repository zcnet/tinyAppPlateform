package com.sun.weexandroid_module;

import android.app.Activity;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

public class WxNavModule  extends WXModule {
    private static IWxNav wxNav = null;
    public static void setNav(IWxNav nav) {
        wxNav = nav;
    }
    @JSMethod(uiThread = false)
    public void pop(JSCallback callback){
        ((Activity)mWXSDKInstance.getContext()).finish();
    }

    @JSMethod(uiThread = false)
    public void push(String path, JSCallback callback){
        if(null != wxNav) {
            wxNav.push(path);
        }
    }

}
