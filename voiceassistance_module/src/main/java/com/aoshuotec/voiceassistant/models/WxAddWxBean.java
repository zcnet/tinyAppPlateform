package com.aoshuotec.voiceassistant.models;

/**
 * Created by sun on 2018/12/12
 */

public class WxAddWxBean {

    /**
     * action : addWx
     * path : /sdcard/111.js
     * text : 正在为您搜索联系人
     * data : 此处为传给下一个Weex的数据(Json形式)
     * type : 1
     */

    private int type;
    private String action;
    private String path;
    private String text;
    private String data;
    private String name;


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPath() {
        return path;
    }
    public String getName(){
        return name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
