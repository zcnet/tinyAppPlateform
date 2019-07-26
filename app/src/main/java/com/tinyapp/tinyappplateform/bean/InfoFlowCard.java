package com.tinyapp.tinyappplateform.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfoFlowCard {
    public String name;// "app1_info1",
    @SerializedName(value = "friendlyName", alternate = {"friendlyname"})
    public String friendlyName;
    public String guid;//"xxxxxxxxxxxxxxxxxxxx",
    public String version;//"0.0.0.2",
    @SerializedName(value = "showtype", alternate = {"showType"})
    public String showtype;
    public String path;//"/weex_app1/WeexInfoFlowCard/index.js",
    @SerializedName(value = "closestrategy", alternate = {"closeStrategy"})
    public List<String> closestrategy;//[
    @SerializedName(value = "overtime", alternate = {"overTime"})
    public int overtime;//"1"
    @SerializedName(value = "hasbigcard", alternate = {"hasBigCard"})
    public String hasbigcard;//1,0
}
