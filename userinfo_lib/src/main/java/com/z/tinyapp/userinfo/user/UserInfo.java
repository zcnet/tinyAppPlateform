package com.z.tinyapp.userinfo.user;

/**
 * Created by zhengfei on 2018/8/7.
 */

public class UserInfo {
    private static UserInfo userInfo=new UserInfo();
    private String accessToken = "";
    private String refreshToken = "";
    private String idmLoginName = "";
    private String deviceType = "";
    private String deviceCode = "";//vin码
    private int verCode;
    private String verName;
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getVerCode() {
        return verCode;
    }

    public void setVerCode(int verCode) {
        this.verCode = verCode;
    }

    public String getVerName() {
        return verName;
    }

    public void setVerName(String verName) {
        this.verName = verName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getIdmLoginName() {
        return idmLoginName;
    }

    public void setIdmLoginName(String idmLoginName) {
        this.idmLoginName = idmLoginName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }
    public UserInfo(){
    }
    public static UserInfo getInstance(){
        return userInfo;
    }
}
