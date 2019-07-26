package com.sun.weexandroid_module;

import android.util.Log;

/**
 * Created by sun on 2018/8/7
 */

class Loger {

    private static final boolean isDebug = true;

    static void showI(String tag, String message, Exception e) {
        if (isDebug) {
            return;
        }
        Log.i(tag, "-" + message, e);
    }


    static void showI(String tag, String message) {
        if (isDebug) {
            return;
        }
        Log.i(tag, "-" + message);
    }


    static void showE(String tag, String message, Exception e) {
        if (isDebug) {
            return;
        }
        Log.e(tag, "-" + message, e);
    }

    static void showE(String tag, String message) {
        if (isDebug) {
            return;
        }
        Log.e(tag, "-" + message);
    }


}
