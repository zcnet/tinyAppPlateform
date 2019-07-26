package com.z.tinyapp.utils.logs;

/**
 * Created by GongDongdong on 2018/7/19.
 */

public class LogSpec {
    private int defaultLevel = 2;
    private boolean isDebug = true;
    private String ModuleName = "";

    public int getDefaultLevel() {
        return defaultLevel;
    }

    public void setDefaultLevel(int defaultLevel) {
        this.defaultLevel = defaultLevel;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public String getModuleName() {
        return ModuleName;
    }

    public void setModuleName(String moduleName) {
        ModuleName = moduleName;
    }

    @Override
    public String toString() {
        return ModuleName + "  isDebug :" + isDebug + "   defaultLevel : " + defaultLevel;
    }
}
