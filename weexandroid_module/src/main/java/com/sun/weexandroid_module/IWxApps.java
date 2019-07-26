package com.sun.weexandroid_module;


import android.app.Activity;

public interface IWxApps {
    void cmd(String cmd);

    void showInfoFlow(String appName, String name, Callback callback);

    void showBigCard(String appName, String name, Callback callback);

    void showSmallCard(String appName, String name, Callback callback);

    void openMain(String appName, CallbackForMain callback);

    void push(String appName, String path, CallbackForMain callbackForMain);


    public interface Callback{
        void invoke(Object o);

    }
    public interface CallbackForMain{
        void invoke(Activity a, Object o);
    }

}
