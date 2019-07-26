package com.sun.weexandroid_module;

import android.app.Activity;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.Stack;


public class WxAppsModule extends WXModule {
    private static IWxApps sApps = null;
    private static Stack<Activity> sActivityStack = new Stack<>();
    private static String sCurAppName = null;
    private static void closeActivity(Activity a){
        a.finish();
    }
    public static void setCallback(IWxApps apps){
        sApps = apps;
    }
    @JSMethod(uiThread = false)
    public void senCmd(String cmd){
        if (null != sApps) {
            sApps.cmd(cmd);
        }
    }
    @JSMethod(uiThread = false)
    public void showBigCard(String appName, String name, final JSCallback callback){
        if (null != sApps) {
            sApps.showBigCard(appName, name, new IWxApps.Callback() {
                @Override
                public void invoke(Object o) {
                    callback.invokeAndKeepAlive(o);
                }
            });
        }

    }

    @JSMethod(uiThread = false)
    public void showInfoFlow(String appName, String name, final JSCallback callback){
        if (null != sApps) {
            sApps.showInfoFlow(appName, name, new IWxApps.Callback() {
                @Override
                public void invoke(Object o) {
                    callback.invokeAndKeepAlive(o);
                }
            });
        }

    }


    @JSMethod(uiThread = false)
    public void showSmallCard(String appName, String name, final JSCallback callback){
        if (null != sApps) {
            sApps.showSmallCard(appName, name, new IWxApps.Callback() {
                @Override
                public void invoke(Object o) {
                    callback.invokeAndKeepAlive(o);
                }
            });
        }

    }

    @JSMethod(uiThread = false)
    public void openMain(final String appName, final JSCallback callback){
        if (null != sApps) {
            if (appName.equals(sCurAppName)){
                callback.invokeAndKeepAlive(null);
                while (sActivityStack.size()>1){
                    Activity aa = sActivityStack.pop();
                    closeActivity(aa);
                }
            } else {
                sApps.openMain(appName, new IWxApps.CallbackForMain() {
                    @Override
                    public void invoke(Activity a, Object o) {
                        callback.invokeAndKeepAlive(o);

                        Activity aa = null;
                        while((aa = sActivityStack.pop()) != null){
                            closeActivity(aa);
                        }
                        sActivityStack.push(a);
                        sCurAppName = appName;
                    }

                });
            }
        }
    }

    @JSMethod(uiThread = false)
    public void closeMain(final JSCallback callback){
        callback.invokeAndKeepAlive(null);
        while (sActivityStack.size()>1){
            Activity aa = sActivityStack.pop();
            closeActivity(aa);
        }
        sCurAppName = null;
    }

    @JSMethod(uiThread = false)
    public void pop(final JSCallback callback){
        Activity aa = sActivityStack.pop();
        if (aa != null)
            closeActivity(aa);
        if (sActivityStack.size() == 0)
            sCurAppName = null;
    }

    @JSMethod(uiThread = false)
    public void push(String path, final JSCallback callback){
        if (null != sApps && sCurAppName != null && sActivityStack.size() > 0){
            sApps.push(sCurAppName, path, new IWxApps.CallbackForMain() {
                @Override
                public void invoke(Activity a, Object o) {
                    callback.invokeAndKeepAlive(o);
                    sActivityStack.push(a);
                }
            });
        }
    }

}
