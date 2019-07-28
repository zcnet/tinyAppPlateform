package com.z.voiceassistant.bomodule;

import com.z.voiceassistant.models.BoBackBean;

/**
 * Created by GongDongdong on 2018/8/9.
 */

public interface BOResultCallback {
    void onReceived(BoBackBean result, boolean b);
}
