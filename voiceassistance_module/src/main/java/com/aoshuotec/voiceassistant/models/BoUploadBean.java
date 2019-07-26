package com.aoshuotec.voiceassistant.models;

/**
 * Created by sun on 2018/12/19
 */

public class BoUploadBean {

    /**
     * accessToken : IhcjQdkKgbVsV6jRnhUcJOYcSX22LrnJwO1lAYYmsuaorZu5VPZeKq
     * botId : 1
     * sessionId : 1cc43160dc474915be44c00596239e521
     * content : 今天上海天气好吗
     * frontInfo : {"btConnected":1,"contactBookSync":1}
     */

    private String accessToken;
    private int botId;
    private String sessionId;
    private String content;
    private String frontInfo;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getBotId() {
        return botId;
    }

    public void setBotId(int botId) {
        this.botId = botId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrontInfo() {
        return frontInfo;
    }

    public void setFrontInfo(String frontInfo) {
        this.frontInfo = frontInfo;
    }
}
