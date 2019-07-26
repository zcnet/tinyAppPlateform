package com.tinyapp.tinyappplateform.bean;

import java.util.List;

public class UserBean {
    String name;
    List<String> appList;

    public UserBean(){
        name = "__default";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAppList() {
        return appList;
    }

    public void setAppList(List<String> appList) {
        this.appList = appList;
    }
}
