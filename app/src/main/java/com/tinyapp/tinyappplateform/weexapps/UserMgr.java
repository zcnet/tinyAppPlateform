package com.tinyapp.tinyappplateform.weexapps;

import com.tinyapp.tinyappplateform.bean.AppBean;

import java.util.ArrayList;
import java.util.List;

public class UserMgr {
    private List<User> userList = new ArrayList<>();
    private User default_user = new User();
    private User user = null;

    public void logout(){
        user = default_user;
    }

    public void init(){
        initDefault();
        initAccount();
        //updateApps();
    }


    public User getCurrentUser(){
        return user;
    }

    public void findRemotApp(String appName, IRemoteAppListCB cb) {
        AppListMgr.getInstance().getUserRemoteAppList("none", appName, cb);
    }

    public void installAppList(List<AppBean> appList, final IUpdateAndInstallCB cb) {
        boolean needUpdateInstall = false;
        for (AppBean app : appList){
            AppBean ab = AppListMgr.findIn(getCurrentUser().getAppBeanList(), app.name);
            if (null == ab) {
                ab = AppListMgr.findIn(AppListMgr.getInstance().getAppList(),app.name);
                if (null != ab) {
                    getCurrentUser().getAppBeanList().add(ab);
                } else {
                    app.setInstalled(false);
                    getCurrentUser().getAppBeanList().add(app);
                    AppListMgr.getInstance().getAppList().add(app);
                    needUpdateInstall = true;
                }
            }
        }
        if(needUpdateInstall){
            AppListMgr.getInstance().updateAndInstall(getCurrentUser().getAppBeanList(),  new IUpdateAndInstallCB(){
                @Override
                public void onSuccess() {
                    if (null != cb)
                        cb.onSuccess();
                    AppListMgr.getInstance().updateAndInstall(null); //全局列表安装
                }
            });
        } else {
            cb.onSuccess();
        }
    }

    private User find(String name){
        for (User u: userList) {
            if (name.equals(u.getName())){
                return u;
            }
        }
        return null;
    }

    private void initAccount(){
        User u = find(AccountUtil.getidmLoginName());
        if(u != null){
            user = u;
        } else {
            user = default_user;
        }

    }

    private void updateApps() {
        AppListMgr.getInstance().getUserRemoteAppList("associate", new IRemoteAppListCB() {
            @Override
            public void onSuccess(List<AppBean> appList){
                initAppList(appList);
            }
        });

    }

    private void initAppList(List<AppBean> remoteAppList){
        List<AppBean> newLocalAppList = new ArrayList<AppBean>();
        for (AppBean rab: remoteAppList) {
            AppBean lab = AppListMgr.getInstance().find(rab.name);
            rab.setInstalled(false);
            checkAppBean(newLocalAppList, lab, rab);
        }
        getCurrentUser().setAppBeanList(newLocalAppList);
        AppListMgr.getInstance().updateAndInstallForUser(newLocalAppList);
    }

    private void checkAppBean(List<AppBean> newLocalAppList, AppBean lab, AppBean rab){
        if (lab == null) {
            newLocalAppList.add(rab);
            AppListMgr.getInstance().addAppToList(rab);
        } else {
            if(!lab.version.equals(rab.version)) {
                lab.set(rab);
                lab.setInstalled(false);
            }
            newLocalAppList.add(lab);
        }
    }

    private void initDefault(){
        userList.add(default_user);
        default_user.cloneAppList(AppListMgr.getInstance().getAppList());
    }

    private UserMgr(){}
    private static UserMgr sInst = new UserMgr();
    public static UserMgr getInstance() {
        return sInst;
    }
}
