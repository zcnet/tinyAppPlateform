package com.z.voiceassistant.models;

/**
 * Created by sun on 2018/12/26
 */

public class BoGetResultBean {

    /**
     * accessToken : IhcjQdkKgbVsV6jRnhUcJOYcSX22LrnJwO1lAYYmsuaorZu5VPZeKq
     * frontSessionRoundId : 1cc43160dc474915be44c00596239e521-0001
     */

    private String accessToken;
    private String frontSessionRoundId;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getFrontSessionRoundId() {
        return frontSessionRoundId;
    }

    public void setFrontSessionRoundId(String frontSessionRoundId) {
        this.frontSessionRoundId = frontSessionRoundId;
    }
}
