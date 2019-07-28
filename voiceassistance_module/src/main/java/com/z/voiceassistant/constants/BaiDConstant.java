package com.z.voiceassistant.constants;

/**
 * Created by sun on 2018/10/16
 * 百度语音模块所需常量
 */
public class BaiDConstant {



    //百度语音识别结果状态码
    public static final String RESULT_CODE = "RESULT_CODE";
    //百度语音音量变化
    public static final String VOLUME_NUMBER = "VOLUME_NUMBER";
    //百度语音识别中结果
    public static final String ASR_RESULT_MIDDLE = "ASR_RESULT_MIDDLE";
    //百度语音最终识别结果
    public static final String ASR_RESULT_FINAL = "ASR_RESULT_FINAL";


    //百度语音识别码基础数字
    private static final int BASE_CODE = 1;

    //百度语音无法识别
    public static final int NO_RECOGNIZE_RESULT = BASE_CODE;
    //vad检测无语音
    public static final int VAD_DETECT_NO_SPEECH = BASE_CODE + NO_RECOGNIZE_RESULT;
    //从网络获取识别结果失败
    public static final int DOWNLOAD_NETWORK_READ_FAIL = BASE_CODE + VAD_DETECT_NO_SPEECH;
    //服务器错误
    public static final int SERVER_PARAM_ERROR = BASE_CODE + DOWNLOAD_NETWORK_READ_FAIL;
    //语音识别繁忙
    public static final int ASR_ENGINE_BUSY = BASE_CODE + SERVER_PARAM_ERROR;
    //语音识别成功
    public static final int SPEECH_RECOGNIZED_SUCCESS = BASE_CODE + ASR_ENGINE_BUSY;
}
