package com.zcnet.voiceinteractionmodule.usered;

public class APIResult {
    private String code = "";
    private String errmsg = "";
    private Object data = null;

    public APIResult() {
    }

    public APIResult(String code, String errmsg) {
        this.setCode(code);
        this.setErrmsg(errmsg);
    }

    public APIResult(String code, Object data) {
        this.setCode(code);
        this.setData(data);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrmsg() {
        return this.errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
