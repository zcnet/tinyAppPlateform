package com.tinyapp.tinyappplateform.weexapps;

import com.tinyapp.tinyappplateform.bean.AppInfoBean;

public class InfoFlowItem {
    private AppInfoBean appInfoBean;
    private int index;

    public AppInfoBean getAppInfoBean() {
        return appInfoBean;
    }

    public void setAppInfoBean(AppInfoBean appInfoBean) {
        this.appInfoBean = appInfoBean;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPath(){
       return appInfoBean.infoflowcard.get(index).path;
    }

    public String getFriendName() {
        return appInfoBean.infoflowcard.get(index).friendlyName;
    }
    public boolean isAllowBigCard(){
        if ("1".equals(appInfoBean.infoflowcard.get(index).hasbigcard)) {
            return true;
        } else {
            return false;
        }
    }
}
