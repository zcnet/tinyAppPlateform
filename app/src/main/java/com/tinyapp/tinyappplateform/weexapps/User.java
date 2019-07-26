package com.tinyapp.tinyappplateform.weexapps;

import com.tinyapp.tinyappplateform.bean.AppBean;
import com.tinyapp.tinyappplateform.bean.Card;
import com.tinyapp.tinyappplateform.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class User {
    String name;
    private List<AppBean> appBeanList = new ArrayList<>();
    private List<SmallCardItem> smallCardItemList = new ArrayList<>();
    User(){
        name = "__default";
    }
    public void fromUserBean(UserBean bean){
        name = bean.getName();
        appBeanList.clear();
        for (String appName: bean.getAppList()){
            AppBean app = AppListMgr.getInstance().find(appName);
            if (app != null)
                appBeanList.add(app);
        }
    }

    void cloneAppList(List<AppBean> l){
        appBeanList.clear();
        if (l == null) return;
        for(AppBean b: l){
            appBeanList.add(b);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AppBean> getAppBeanList() {
        return appBeanList;
    }
    public List<SmallCardItem> getSmallCardAppList(boolean reset){
        if (reset || smallCardItemList.size() == 0) {
            smallCardItemList.clear();
            for (AppBean b : getAppBeanList()) {
                if (b.appInfo.appcard.smallcard.size() > 0) {
                    for (Card c : b.appInfo.appcard.smallcard) {
                        if ("normal".equals(c.type)) {
                            smallCardItemList.add(new SmallCardItem(b, c));
                        }
                    }

                }
            }
        }
        return smallCardItemList;
    }

    public int getSmallCardAppListHeight(){
        int height = 0;
        for (SmallCardItem s: smallCardItemList) {
            height += (Integer.parseInt(s.card.height)+31);
        }
        return height;
    }


    public AppBean find(String name) {
        return AppListMgr.findIn(appBeanList, name);
    }

    public void setAppBeanList(List<AppBean> appBeanList) {
        this.appBeanList = appBeanList;
    }

    public boolean addSmallCard(String app, String name) {
        return true;
    }
}
