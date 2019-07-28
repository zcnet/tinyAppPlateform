package com.z.voiceassistant.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.z.voiceassistant.weex.WxCallLogModule;
import com.z.voiceassistant.weex.WxCarControlModule;
import com.z.voiceassistant.weex.WxCommonModule;
import com.z.voiceassistant.weex.WxContactsModule;
import com.z.voiceassistant.weex.WxFilmModule;
import com.z.voiceassistant.weex.WxMapComponent;
import com.z.voiceassistant.weex.WxMapModule;
import com.z.voiceassistant.weex.WxMediaModule;
import com.z.voiceassistant.weex.WxReminderModule;
import com.z.voiceassistant.weex.WxSourceModule;
import com.z.tinyapp.utils.logs.sLog;
import com.sun.weexandroid_module.WxHttpAdapter;
import com.sun.weexandroid_module.WxImageAdapter;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.zcnet.voiceinteractionmodule.InterActionApplication;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun on 2018/10/17
 */
public class MApplication extends Application {

    private static final String TAG = "sunMApplication";

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        init(this);
    }

    public static void initContext(Context context){
        mContext = context.getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }

    public static void init(final Application application) {
        MApplication.initWx(application);
        MApplication.initZnlp(application.getApplicationContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                MApplication.initFont(application);
            }
        }).start();
    }

    private static void initZnlp(Context ct) {
        InterActionApplication.init(ct);
    }

    public static void initWx(Application application) {
        try {
            sLog.i(null, "weex: initWx start");
            InitConfig config = new InitConfig.Builder()
                    .setImgAdapter(new WxImageAdapter())
                    .setHttpAdapter(new WxHttpAdapter())
                    .build();
            WXSDKEngine.registerModule("myContactUtil", WxContactsModule.class);
            WXSDKEngine.registerModule("myCallLogUtil", WxCallLogModule.class);
            WXSDKEngine.registerModule("wxCommonModule", WxCommonModule.class);
            WXSDKEngine.registerModule("wxMediaModule", WxMediaModule.class);
            WXSDKEngine.registerModule("wxSourceModule", WxSourceModule.class);
            WXSDKEngine.registerModule("wxCarControlModule", WxCarControlModule.class);
            WXSDKEngine.registerModule("wxReminderModule", WxReminderModule.class);
            WXSDKEngine.registerModule("wxFilmModule", WxFilmModule.class);
            WXSDKEngine.registerModule("wxMapModule", WxMapModule.class);
            WXSDKEngine.registerComponent("wxMapComponent", WxMapComponent.class);
            WXSDKEngine.initialize(application, config);
            sLog.i(null, "weex: initWx end");
        } catch (WXException e) {
            sLog.i(null, "weex: initWx error");
            e.printStackTrace();
        }
    }


    public static void initFont(Context context) {
        sLog.e("sunMApplication", "initFont: ---update fonts---");
        Typeface newTypeface = Typeface.createFromAsset(context.getAssets(), "PATAC_HeiTi_V5.0.ttf");
        try {
            Map<String, Typeface> newMap = new HashMap<>();
            newMap.put("SERIF", newTypeface);
            final Field staticField = Typeface.class.getDeclaredField("sSystemFontMap");
            staticField.setAccessible(true);
            staticField.set(null, newMap);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
