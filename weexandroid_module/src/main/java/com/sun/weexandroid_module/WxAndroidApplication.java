package com.sun.weexandroid_module;

import android.app.Application;

import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

/**
 * Created by sun on 2018/8/6
 * <p>
 * <p>
 * 用于初始化及注册组件
 */
public class WxAndroidApplication {

    private static final String TAG = "sunHy_WxAndroidApplication";

    public static void initWx(final Application application) {
        try {
            InitConfig config = new InitConfig.Builder()
                    .setHttpAdapter(new WxHttpAdapter())
                    .setImgAdapter(new WxImageAdapter())
                    .build();
            WXSDKEngine.registerModule("wx_android_http_module", WxHttpAdapter.class);
            WXSDKEngine.registerModule("nav", WxNavModule.class);
            WXSDKEngine.registerModule("weexapps", WxAppsModule.class);
            WXSDKEngine.initialize(application, config);
        } catch (WXException e) {
            Loger.showE(TAG, "WxModuleApplication  onCreate exception", e);
        }
    }


}
