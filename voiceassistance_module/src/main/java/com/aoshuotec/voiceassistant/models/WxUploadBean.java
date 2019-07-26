package com.aoshuotec.voiceassistant.models;

/**
 * Created by sun on 2018/12/5
 */
public class WxUploadBean {

    /**
     * id : 123461231
     * intent : cover
     * method : 0
     * data : 已为您拨打xxxx
     */

    private String id;
    private String intent;
    private String method;
    private String data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}