package com.tinyapp.tinyappplateform.bean;

import com.google.gson.annotations.SerializedName;

public class Card{
    public String guid;//"xxxxxxxxxxxxxxxxxxxx",
    public String name;//"",
    @SerializedName(value = "friendlyName", alternate = {"friendlyname"})
    public String friendlyName;//"大卡片",
    public String version;//"0.0.0.2",
    public String width;
    @SerializedName(value = "height", alternate = {"hight"})
    public String height;
    public AppIcon icon;//
    public String path;//"/weex_app1/WeexCard/big.js"
    @SerializedName(value = "touchstart", alternate = {"touchStart"})
    public String touchstart;
    @SerializedName(value = "touchwidth", alternate = {"touchWidth"})
    public String touchwidth;
    public String type;//"system/normal"

}
