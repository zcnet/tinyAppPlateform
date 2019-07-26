package com.aoshuotec.voiceassistant.models;

/**
 * Created by GongDongdong on 2018/8/14.
 */

public class BOResult {
    private int type = 1;    // 1 : normal  >1 : special js type
    private String result;

    public BOResult(String result) {
        this.result = result;
        type = 1;
    }

    public BOResult(String result, int i) {
        this.result = result;
        type = i;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
