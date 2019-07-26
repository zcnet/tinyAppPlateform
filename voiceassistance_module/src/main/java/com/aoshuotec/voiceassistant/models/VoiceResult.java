package com.aoshuotec.voiceassistant.models;

/**
 * Created by GongDongdong on 2018/8/29.
 */

public class VoiceResult {
    private String resultType;
    private String resultString;

    public VoiceResult(){
        resultType = "";
        resultString = "";
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }
}
