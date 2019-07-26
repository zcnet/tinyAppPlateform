package com.z.tinyapp.network;

/**
 * Created by zhengfei on 2018/8/13.
 */

public interface UpLoadCallBack {
    void onStart();

    void onError(String code, String failureMsg);

    void onSuccess(String str);

}
