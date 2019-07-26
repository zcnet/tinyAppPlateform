package com.zcnet.voice_callback_lib;

import android.content.Context;

import java.lang.reflect.Method;

public class GeneralContext {

    private static int bot = 0;

    public static int getBot() {
        return bot;
    }

    public static void setBot(int i) {
        bot = i;
    }

    public static Context getContext() {
        Context ctx = null;
        try {
            Class<?> ActivityThread = Class.forName("android.app.ActivityThread");
            Method method = ActivityThread.getMethod("currentActivityThread");
            Object currentActivityThread = method.invoke(ActivityThread);
            Method method2 = currentActivityThread.getClass().getMethod("getApplication");
            ctx = (Context) method2.invoke(currentActivityThread);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ctx;
    }

//        return context;}

//    public static Context getContext() {
//        Context ctx = null;
//        try {
//            Class<?> ActivityThread = Class.forName("android.app.ActivityThread");
//            Method method = ActivityThread.getMethod("currentActivityThread");
//            Object currentActivityThread = method.invoke(ActivityThread);
//            Method method2 = currentActivityThread.getClass().getMethod("getApplication");
//            ctx = (Context) method2.invoke(currentActivityThread);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ctx;
//    }
}
