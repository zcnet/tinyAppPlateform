package com.tinyapp.tinyappplateform;

import android.content.ComponentName;
import android.content.Intent;

import com.z.voiceassistant.FunctionManager;
import com.z.voiceassistant.application.MApplication;
import com.z.tinyapp.common.base.BaseApplication;
import com.z.tinyapp.utils.common.PrefernceUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhengfei on 2018/8/9.
 */

public class TapApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        PrefernceUtil.initContext(this);
        MApplication.init(TapApplication.this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //MApplication.init(TapApplication.this);

                Intent intent = new Intent();
                intent.setComponent(new ComponentName(TapApplication.this, FunctionManager.class));
                intent.setPackage("com.z.voiceassistant");
                startService(intent);
                /*intent = new Intent(TapApplication.this, TestActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/


            }
        }).start();



    }

}
