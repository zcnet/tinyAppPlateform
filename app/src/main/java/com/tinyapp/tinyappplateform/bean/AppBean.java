package com.tinyapp.tinyappplateform.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppBean {
    public int id;
    public String guid;
    public String name;// "weex_app1",
    @SerializedName(value = "friendlyName", alternate = {"friendlyname"})
    public String friendlyName;//保养",
    @SerializedName(value = "infoIcon", alternate = {"infoicon"})
    public String infoIcon;// "http://www.zeninte.com/zeninte/app/icon/app1.jpg",
    public String version;// "0.0.0.2",
    public String intro;// "查看保养信息",
    @SerializedName(value = "appConfig", alternate = {"appconfig"})
    public String appConfig;//"/weex_app1/WeexMain/config.json",
    @SerializedName(value = "appIcon", alternate = {"appicon"})
    public AppIcon appIcon;
    @SerializedName(value = "appCard", alternate = {"appcard"})
    public List<Card> appCard;
    @SerializedName(value = "appCardURL", alternate = {"appcardURL"})
    public String appCardURL;
    @SerializedName(value = "showPackageURL", alternate = {"showpackageURL"})
    public String showPackageURL;// "http://www.zeninte.com/zeninte/app/package/49204EB5-2BD9-498C-A9F1-2F4C3F63576228/weex_app1.zip",
    @SerializedName(value = "showInfoFlowCardsURL", alternate = {"showinfoflowcardsURL"})
    public String showInfoFlowCardsURL;// "http://www.zeninte.com/zeninte/app/package/49204EB5-2BD9-498C-A9F1-2F4C3F63576228/weex_infoflowapp1.zip",
    @SerializedName(value = "showVRCarURL", alternate = {"showvrcarURL"})
    public String showVRCarURL;// "http://www.zeninte.com/zeninte/app/package/49204EB5-2BD9-498C-A9F1-2F4C3F63576228/weex_app1_VR_card.zip",
    @SerializedName(value = "isAssociate", alternate = {"isassociate"})
    public int isAssociate;// 0,
    @SerializedName(value = "isInstall", alternate = {"isinstall"})
    public int isInstall;// 0,
    @SerializedName(value = "appLevel", alternate = {"applevel"})
    public String appLevel;//"system",
    @SerializedName(value = "appType", alternate = {"apptype"})
    public String appType;
    @SerializedName(value = "keyGuid", alternate = {"keyguid"})
    public String keyGuid;
    @SerializedName(value = "appdepdency", alternate = {"appDepdency"})
    public List<Appdepdency> appdepdency;//
    public boolean isInstalled;
    public transient AppInfoBean  appInfo;

    public void set(AppBean appBean) {
        id = appBean.id;
        guid = appBean.guid;
        friendlyName = appBean.friendlyName;
        appIcon = appBean.appIcon;
        version = appBean.version;
        intro = appBean.intro;
        infoIcon = appBean.infoIcon;
        appConfig = appBean.appConfig;
        appCard = appBean.appCard;
        appCardURL = appBean.appCardURL;
        showPackageURL = appBean.showPackageURL;
        showInfoFlowCardsURL = appBean.showInfoFlowCardsURL;
        showVRCarURL = appBean.showVRCarURL;
        isAssociate = appBean.isAssociate;
        isInstall = appBean.isInstall;
        appLevel = appBean.appLevel;
        appType = appBean.appType;
        keyGuid = appBean.keyGuid;
        appdepdency = appBean.appdepdency;
    }



    public boolean needInstall(){
        return !isInstalled;
    }
    public void setInstalled(boolean b) {
        isInstalled = b;

    }
}
