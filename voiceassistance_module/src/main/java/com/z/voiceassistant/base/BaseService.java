package com.z.voiceassistant.base;

import android.app.Service;
import android.content.Intent;

/**
 * Created by GongDongdong on 2018/8/30.
 */

public abstract class BaseService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
