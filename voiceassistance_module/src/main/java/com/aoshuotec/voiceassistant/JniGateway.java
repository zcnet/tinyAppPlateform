package com.aoshuotec.voiceassistant;

import android.os.Bundle;

import com.aoshuotec.voiceassistant.constants.FloatWindowConstant;
import com.aoshuotec.voiceassistant.constants.JNIConstant;
import com.aoshuotec.voiceassistant.listener.IDataCallBack;
import com.aoshuotec.voiceassistant.utils.Logg;
import com.aoshuotec.voiceassistant.weex.WxReminderModule;
import com.z.tinyapp.utils.logs.sLog;


public class JniGateway {

    private static final String TAG = "sunJniGateWay";

    private static final int REASON_HARD_KEY = 0;
    private static final int REASON_WAKE_UP = 1;
    private static final int REASON_REMINDER_ADD_FROM_HMI = 2;
    private static final int REASON_REMINDER_ADD_FROM_SMS = 3;
    private static final int SMS_REPLY_FROM_HMI = 4;

    private static IDataCallBack mCallBack;

    public static boolean sIsStartStreaming = false;

    static {
        System.loadLibrary("VoiceAssistant_Gateway");
    }

    public static native void Initial();

    public static native int InitVaMode();

    public static native void Terminate();

    public static native int StartStreaming();

    public static native int StopStreaming();

    public static native byte[] ReadStream(int size);

    public static native int GetStatus();

    public static native int TtsPlay(String tts_content);

    public static native int TtsCancel();

    public static native int GetTtsStatus();

    //not use
    public static native int ResetVaMode();

    //not use
    public static native int StopVaMode();

    public static native int VaSessionStart();

    public static native int VaSessionStop();

    public static native int CopyFile(String srcPath,String destPath,int bit);

    public static native int AddReminder(int reminderType, int year, int month, int day,
                                         int hour, int min, int sec, int repeat_type, String jniPcmPath);

    public static native int ShowReminder(int reminderType, int year, int month, int day,
                                          int hour, int min, int sec, int repeat_type);

    public static void setCallBack(IDataCallBack callBack) {
        mCallBack = callBack;
    }

    public static void JniInitial() {
        Logg.e(TAG, "initGateway: JniInitial  " + System.currentTimeMillis());
        Initial();
    }

    public static int JniVaSessionStart() {
        int i = VaSessionStart();
        Logg.e(TAG, "initGateway: JniVaSessionStart  reason->" + i + "  " + System.currentTimeMillis());
//        JniAddReminder(1,2019,5,15,8,0,0,0, "/var/log/asap/recorded_test_0.pcm");
//        JniCopyFile("aaa");
//        JniShowReminder(1,2019,4,15,8,0,0,0);
        //,"/var/log/asap/recorded_test_0.pcm"
        return i;
    }

    public static int JniVaSessionStop() {
        Logg.e(TAG, "initGateway: JniVaSessionStop  " + System.currentTimeMillis());
        return VaSessionStop();
    }

    public static int JniStartStreaming() {
        Logg.e(TAG, "initGateway: JniStartStreaming  " + System.currentTimeMillis());
        sIsStartStreaming = true;
        int i = StartStreaming();
        if(i==100){
            Bundle bundle = new Bundle();
            bundle.putString(JNIConstant.MESSAGE_WHAT,JNIConstant.TODO_WX_REMOVE_VOICE);
            mCallBack.getJniDataBack(bundle);
        }
        return i;
    }

    public static int JniStopStreaming() {
        Logg.e(TAG, "initGateway: JniStopStreaming  " + System.currentTimeMillis());
        sIsStartStreaming = false;
        return StopStreaming();
    }

    public static int JniTtsCancel() {
        Logg.e(TAG, "initGateway: JniTtsCancel  " + System.currentTimeMillis());
        return TtsCancel();
    }

    public static int JniTtsPlay(String str) {
        Logg.e(TAG, "initGateway: JniTtsPlay  " + System.currentTimeMillis());
        return TtsPlay(str);
    }

    public void VaActive(int reason) {
        Logg.e(TAG, "initGateway: VaActive  " + reason + "  " + System.currentTimeMillis());
        Bundle bundle;
        switch (reason) {
            case REASON_HARD_KEY:
                bundle = new Bundle();
                bundle.putString(JNIConstant.MESSAGE_WHAT, JNIConstant.MESSAGE_VR);
                if (mCallBack == null) {
                    sLog.i(TAG, "REASON_WAKE_UP: callBack null");
                    return;
                }
                mCallBack.getJniDataBack(bundle);
                break;
            case REASON_WAKE_UP:
                bundle = new Bundle();
                bundle.putString(JNIConstant.MESSAGE_WHAT, JNIConstant.MESSAGE_VR);
                if (mCallBack == null) {
                    sLog.i(TAG, "REASON_WAKE_UP: callBack null");
                    return;
                }
                mCallBack.getJniDataBack(bundle);
                break;
            case REASON_REMINDER_ADD_FROM_HMI:
                bundle = new Bundle();
                bundle.putString(JNIConstant.MESSAGE_WHAT, JNIConstant.MESSAGE_VR);
                if (mCallBack == null) {
                    sLog.i(TAG, "REASON_WAKE_UP: callBack null");
                    return;
                }
                mCallBack.getJniDataBack(bundle);
                break;
            case REASON_REMINDER_ADD_FROM_SMS:
                break;
            case SMS_REPLY_FROM_HMI:
                break;
        }
    }

    public void VaDeactive(int reason) {
        sLog.i(TAG, "VaDeactive: " + System.currentTimeMillis());
        Bundle bundle = new Bundle();
        bundle.putString(FloatWindowConstant.MESSAGE_WHAT, FloatWindowConstant.MESSAGE_EXIT);
        if (mCallBack == null) {
            sLog.i(TAG, "VaDeactive: callBack null");
            return;
        }
        mCallBack.getFwDataBack(bundle);
        sLog.i(TAG, "VaDeactive: Bundle Send " + System.currentTimeMillis());
    }

    /**
     * Jni回调
     */
    public void Connected() {
        Logg.e(TAG, "initGateway: Connected  " + System.currentTimeMillis());
        int a = JniGateway.InitVaMode();
        Logg.e(TAG, "initGateway: InitVaMode  " + a + System.currentTimeMillis());
        Bundle bundle = new Bundle();
        bundle.putString(JNIConstant.MESSAGE_WHAT, JNIConstant.MESSAGE_INIT_OVER);
        if (mCallBack == null) {
            sLog.i(TAG, "Connected: callBack null");
            return;
        }
        mCallBack.getJniDataBack(bundle);
    }

    public static void JniCopyFile(String srcPath,String destPath,int bit) {
        Logg.e(TAG, "initGateway: JniCopyFile  " + System.currentTimeMillis());
        int a = CopyFile(srcPath,destPath,bit);
        Logg.e(TAG, "initGateway: JniCopyFile  " + a + System.currentTimeMillis());
    }

    public static void JniAddReminder(int reminderType, int year, int month, int day,
                                      int hour, int min, int sec, int repeat_type, String jniPcmPath) {
        Logg.e(TAG, "initGateway: JniAddReminder  " + System.currentTimeMillis());
        int a = AddReminder(reminderType, year, month, day, hour, min, sec, repeat_type, jniPcmPath);
        Logg.e(TAG, "initGateway: JniAddReminder  " + a + System.currentTimeMillis());
    }

    public static void JniShowReminder(int reminderType, int year, int month, int day,
                                       int hour, int min, int sec, int repeat_type) {
        Logg.e(TAG, "initGateway: JniShowReminder  " + System.currentTimeMillis());
        int a = ShowReminder(reminderType, year, month, day, hour, min, sec, repeat_type);
        Logg.e(TAG, "initGateway: JniShowReminder  " + a + System.currentTimeMillis());
    }

    public void Disconnected() {
    }

    public void OnStartStreaming(int return_value) {

    }

    public void OnStopStreaming(int return_value) {
        Logg.e(TAG, "OnStopStreaming  return_value--->" + return_value);
        Bundle bundle = new Bundle();
        bundle.putString(JNIConstant.MESSAGE_WHAT, JNIConstant.MESSAGE_STOP_STREAMING);
        if (mCallBack == null) {
            sLog.i(TAG, "OnStopStreaming: callBack null");
            return;
        }
        mCallBack.getJniDataBack(bundle);
    }

    public void OnTtsPlay(int return_value) {

    }

    public void OnTtsCancel(int return_value) {

    }

    public void OnRestVaMode(int return_value) {

    }

    public void OnInitVaMode(int return_value) {

    }

    public void OnStopVaMode(int return_value) {

    }

    public void OnVaSessionStart(int return_value) {

    }

    public void OnVaSessionStop(int return_value) {
        Logg.i(TAG, "OnVaSessionStop: ");
        Bundle bundle = new Bundle();
        bundle.putString(JNIConstant.MESSAGE_WHAT, JNIConstant.TODO_WX_SESSION_STOP);
        if (mCallBack == null) {
            sLog.i(TAG, "OnVaSessionStop: callBack null");
            return;
        }
        mCallBack.getJniDataBack(bundle);
    }

    public void FetalError() {

    }

    public void OnCopyFile(int return_value) {
        Logg.i(TAG, "OnCopyFile: - > " + return_value);
        if (WxReminderModule.getCopyFileCallBack() != null) {
            WxReminderModule.getCopyFileCallBack().invokeAndKeepAlive(return_value);
        }
    }

    public void OnAddReminder(int return_value) {
        Logg.i(TAG, "OnAddReminder: - > " + return_value);
        if (WxReminderModule.getReminderCallBack() != null) {
            WxReminderModule.getReminderCallBack().invokeAndKeepAlive(return_value);
        }
    }

    public void OnShowReminder(int return_value) {
        Logg.i(TAG, "OnShowReminder: - > " + return_value);
    }
}
