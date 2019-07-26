package com.zcnet.voiceinteractionmodule.common;

public class BaseEntity {
    private String appVer;
    private String companyId;

    public BaseEntity() {
    }

    public String getAppVer() {
        return this.appVer;
    }

    public void setAppVer(String appVer) {
        this.appVer = appVer;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}

