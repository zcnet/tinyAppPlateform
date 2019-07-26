package com.z.tinyapp.network;

/**
 * Created by zhengfei on 2018/8/13.
 */

public interface DownLoadCallBack {
    void onStart();

    void onError(String code, String failureMsg);

    void onLoadProgress(int progress);

    void onSuccess();

    void onWarn();
}
