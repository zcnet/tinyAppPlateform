package com.z.tinyapp.utils.logs;

import android.util.Log;

/**
 * Created by GongDongdong on 2018/7/19.
 */

public class xLog {
    private static int defautLogLevel = 1;
    public static void m(String tag, String msg){
        ModulesList modulesList = ModulesList.getInstance();
        LogSpec logSpec = modulesList.getLogSpec(tag);
        if(logSpec != null && logSpec.isDebug() && logSpec.getDefaultLevel() > defautLogLevel){
            switch (logSpec.getDefaultLevel()){
                case LogLevel.ASSERT:
                    sLog.e(tag, msg);
                    break;
                case LogLevel.ERROR:
                    sLog.e(tag, msg);
                    break;
                case LogLevel.WARN:
                    sLog.w(tag, msg);
                    break;
                case LogLevel.INFO:
                    sLog.i(tag, msg);
                    break;
                case LogLevel.DEBUG:
                    sLog.d(tag, msg);
                    break;
                case LogLevel.VERBOSE:
                    sLog.v(tag, msg);
                    break;
            }
        }
    }
}
