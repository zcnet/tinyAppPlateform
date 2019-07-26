package com.aoshuotec.voiceassistant.utils;

import com.z.tinyapp.utils.logs.sLog;

/**
 * Created by sun on 2018/10/16
 */

public class Logg {

    private static final boolean sIsDebug = true;

    public static void e(String tag, String value) {
        if (sIsDebug) {
            e(tag, value, null);
        }
    }

    public static void e(String tag, String value, Exception error) {
        if (sIsDebug) {
            sLog.e(tag, value, error);
        }
    }

    public static void i(String tag, String value) {
        if (sIsDebug) {
            sLog.e(tag, value);
        }
    }

}
