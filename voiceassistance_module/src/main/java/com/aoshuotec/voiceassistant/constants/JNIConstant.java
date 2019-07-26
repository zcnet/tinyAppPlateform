package com.aoshuotec.voiceassistant.constants;

/**
 * Created by sun on 2018/10/17
 */

public class JNIConstant {

    private static final int BASE_NUM = 1;

    //意图
    public static final String MESSAGE_WHAT = "MESSAGE_WHAT";

    //初始化完成
    public static final String MESSAGE_INIT_OVER = "MESSAGE_INIT_OVER";

    //唤醒
    public static final String MESSAGE_VR = "MESSAGE_VR";

    //停止录音
    public static final String MESSAGE_STOP_STREAMING = "MESSAGE_STOP_STREAMING";

    //JniStopStreaming接下来的动作
    //等待网络识别结果(百度语音识别成功后)
    public static final int TODO_WAIT_RECOGNIZE = BASE_NUM + BASE_NUM;
    //播放没有听清的语音(百度语音识别失败 错误为引擎忙)
    public static final int TODO_PLAY_RECOGNIZE_FAILED_TTS = TODO_WAIT_RECOGNIZE + BASE_NUM;
    //直接重新开始录音(百度语音识别失败 错误为一般错误 可忽略)
    public static final int TODO_RE_RECOGNIZE = TODO_PLAY_RECOGNIZE_FAILED_TTS + BASE_NUM;
    //退出悬浮窗
    public static final int TODO_EXIT = TODO_RE_RECOGNIZE + BASE_NUM;
    //退出悬浮窗
    public static final int TODO_WX_EXIT = TODO_RE_RECOGNIZE + TODO_EXIT;

    //JNISessionStop
    public static final String TODO_WX_SESSION_STOP = "TODO_WX_SESSION_STOP";

    //移除下方音量波动
    public static final String TODO_WX_REMOVE_VOICE = "TODO_WX_REMOVE_VOICE";
}
