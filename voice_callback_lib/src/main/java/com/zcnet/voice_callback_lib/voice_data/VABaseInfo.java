package com.zcnet.voice_callback_lib.voice_data;

public class VABaseInfo {
    private String appVer;      // 版本号。
    private String companyId;

    public String getAppVer() {return appVer;}
    public void setAppVer(String appVer) {this.appVer = appVer;}

    public String getCompanyId() {return companyId;}
    public void setCompanyId(String companyId) {this.companyId = companyId;}

    public String toString() {
        return "VABaseInfo [appVer=" + appVer + ", companyId=" + companyId + "]";
    }
}
