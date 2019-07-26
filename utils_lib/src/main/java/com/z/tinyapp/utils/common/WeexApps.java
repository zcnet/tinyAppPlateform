package com.z.tinyapp.utils.common;

public class WeexApps {
    public interface IAppListMgr {
        String findVRCardPath(String appName, String vrCardName);
    }
    private static IAppListMgr sAppListMgr = null;
    public static void setAppListMgr(IAppListMgr appListMgr){
        sAppListMgr = appListMgr;
    }
    public static IAppListMgr getAppListMgr(){
        return sAppListMgr;
    }
}
