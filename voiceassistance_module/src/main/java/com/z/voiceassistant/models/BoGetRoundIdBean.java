package com.z.voiceassistant.models;

import com.z.tinyapp.network.BaseJsonBean;

/**
 * Created by sun on 2018/10/11
 */

public class BoGetRoundIdBean extends BaseJsonBean{

    private String frontSessionRoundId;
    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setFrontSessionRoundId(String frontSessionRoundId) {
        this.frontSessionRoundId = frontSessionRoundId;
    }
    public String getFrontSessionRoundId() {
        return frontSessionRoundId;
    }

    @Override
    public String toString() {
        return "BoGetRoundIdBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", frontSessionRoundId='" + frontSessionRoundId + '\'' +
                ", count=" + count +
                '}';
    }
}
