package com.tinyapp.tinyappplateform.weexapps;

import com.tinyapp.tinyappplateform.bean.AppBean;

import java.util.List;

/**
 * IRemoteAppListCB
 */
interface IRemoteAppListCB {
    void onSuccess(List<AppBean> appList);
}
