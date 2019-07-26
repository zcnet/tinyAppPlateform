package com.aoshuotec.voiceassistant.constants;

/**
 * Created by sun on 2018/9/27
 */
public class URLConstants {

    private static String URL_BASE ="http://116.62.45.159:8080";

    //获取结果
    public static String URL_SEND_CHAT =URL_BASE+"/VoiceAPI/v1/dialog/result/get?appVer=1.0";

    //发送意图
    public static String URL_SEND_INTENT =URL_BASE+"/VoiceAPI/v1/intent/add";

    public static String URL_GET_RESULT =URL_BASE+"/VoiceAPI/v1/dialog/result/get?appVer=1.0";


}
