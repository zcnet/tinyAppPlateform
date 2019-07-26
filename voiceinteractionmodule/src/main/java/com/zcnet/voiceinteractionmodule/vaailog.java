package com.zcnet.voiceinteractionmodule;
//import com.z.tinyapp.utils.logs.xLog;
public class vaailog {
    private static int defautLogLevel = 1;
    public static void logm(String tag, String msg) {
        System.out.println(tag + "  " +msg);
//        xLog.m(tag, msg);
    }
    public static boolean setLogLevel(int ilevel)
    {
        defautLogLevel = ilevel;
        return true;
    }
}
