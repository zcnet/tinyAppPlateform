package com.z.tinyapp.common.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.z.tinyapp.common.BuildConfig;

/**
 * Created by zhengfei on 2018/9/5.
 */

public class BaseApplication extends Application {
    private static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        if (BuildConfig.DEBUG) {
            //一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }
    public static BaseApplication getInstance(){
        return application;
    }
}
