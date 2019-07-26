package com.zcnet.voice_callback_lib.voice_data;

public class VABaseEntity {
    private String appVer;      // 版本号。
    private String companyId;

    public VABaseEntity() {}

    public String getAppVer() {return appVer;}
    public void setAppVer(String appVer) {this.appVer = appVer;}

    // companyId
    public String getCompanyId() {return companyId;}
    public void setCompanyId(String companyId) {this.companyId = companyId;}
}
