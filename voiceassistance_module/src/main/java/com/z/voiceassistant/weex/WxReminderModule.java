package com.z.voiceassistant.weex;

import com.z.voiceassistant.JniGateway;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

/**
 * Created by sun on 2019/3/25
 */

public class WxReminderModule extends WXModule {

    private static JSCallback copyFileCallBack;
    private static JSCallback reminderCallBack;

    public static JSCallback getCopyFileCallBack(){
        return copyFileCallBack;
    }

    public static JSCallback getReminderCallBack(){
        return reminderCallBack;
    }

    @JSMethod(uiThread = false)
    public void copyFile(JSCallback callback){
        copyFileCallBack = callback;
        JniGateway.JniCopyFile("/var/log/asap/recorded_reminder.pcm","/var/log/asap/recorded_tmp.pcm",16000);
    }

    @JSMethod(uiThread = false)
    public void addReminder(int reminderType, int year, int month, int day,
                            int hour, int min, int sec, int repeat_type, String jniPcmPath,JSCallback callback){
        reminderCallBack = callback;

        JniGateway.JniCopyFile("/var/log/asap/recorded_tmp.pcm",jniPcmPath,22050);
        JniGateway.JniAddReminder(reminderType, year, month, day, hour, min, sec, repeat_type, jniPcmPath);
    }

}
