package com.z.voiceassistant.listener;

import android.os.Bundle;

/**
 * Created by sun on 2018/10/15
 */
public interface IDataCallBack {
    void getJniDataBack(Bundle bundle);
    void getBoDataBack(Bundle bundle);
    void getWxCommonDataBack(Bundle bundle);
    void getWxContactDataBack(Bundle bundle);
    void getWxCallLogDataBack(Bundle bundle);
    void getFwDataBack(Bundle bundle);
    void getBaiDDataBack(Bundle bundle);
    void getPinYinDataBack(Bundle bundle);
    void getTTSDataBack(Bundle bundle);
    void getLocationDataBack(Bundle bundle);
}
