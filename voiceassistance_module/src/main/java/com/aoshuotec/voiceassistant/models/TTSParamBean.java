package com.aoshuotec.voiceassistant.models;

/**
 * Created by sun on 2018/12/26
 */

public class TTSParamBean {
    private boolean isNeedFinish = false;
    private int pauseTime = 0;
    private boolean isNeedRecord = false;

    public boolean isNeedFinish() {
        return isNeedFinish;
    }

    public void setNeedFinish(boolean needFinish) {
        isNeedFinish = needFinish;
    }

    public int getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(int pauseTime) {
        this.pauseTime = pauseTime;
    }

    public boolean isNeedRecord() {
        return isNeedRecord;
    }

    public void setNeedRecord(boolean needRecord) {
        isNeedRecord = needRecord;
    }
}
