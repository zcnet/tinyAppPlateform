package com.tinyapp.tinyappplateform.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppInfoBean{
    public String name;// "weex_app1",
    @SerializedName(value = "friendlyname", alternate = {"friendlyName"})
    public String friendlyname;//"保养",
    @SerializedName(value = "appicon", alternate = {"appIcon"})
    public AppIcon appicon;//
    @SerializedName(value = "infoicon", alternate = {"infoIcon"})
    public String infoicon;// "http://www.zeninte.com/zeninte/app/icon/app1.jpg",
    public String version;// "0.0.0.2",
    public String intro;//"查看保养信息",
    @SerializedName(value = "platformInfo", alternate = {"platforminfo"})
    PlatformInfo platformInfo;//
    @SerializedName(value = "applevel", alternate = {"appLevel"})
    public String applevel;//"system",
    @SerializedName(value = "apptype", alternate = {"appType"})
    public String apptype;//"Other",
    @SerializedName(value = "apppath", alternate = {"appPath"})
    public String apppath;//"/weex_app1/WeexMain/index.js",
    public String guid;// "49204EB5-2BD9-498C-A9F1-2F4C3F63576241",
    @SerializedName(value = "appiconauthor", alternate = {"appIconAuthor"})
    public String appiconauthor;//"home",
    @SerializedName(value = "appdepdency", alternate = {"appDepdency"})
    public List<Appdepdency> appdepdency;//[
    @SerializedName(value = "appcard", alternate = {"appCard"})
    public AppCard appcard;//
    @SerializedName(value = "infoflowcard", alternate = {"infoFlowCard"})
    public List<InfoFlowCard> infoflowcard;//[
    @SerializedName(value = "vrcard", alternate = {"vrCard"})
    public List<VRCard> vrcard;//[

}
