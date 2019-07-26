package com.aoshuotec.voiceassistant.constants;

/**
 * Created by sun on 2018/12/27
 */

public class BoHttpConstant {

    //对应第三方的BaseURL
    private static final String URL_THIRD_BASE = "http://116.62.45.159:8080/";

    //版本
    private static final String URL_THIRD_VERSION = "?appVer=1.0";

    //上传联系人到第三方
    public static final String URL_THIRD_CONTACTS = URL_THIRD_BASE + "VoiceCallbackAPI/v1/make/call/phone/add" + URL_THIRD_VERSION;

    //上传未接来电到第三方
    public static final String URL_THIRD_MISSING_CALL = URL_THIRD_BASE + "VoiceCallbackAPI/v1/make/call/missing/list/add" + URL_THIRD_VERSION;

    //上传拨出电话到第三方
    public static final String URL_THIRD_OUT_CALL = URL_THIRD_BASE + "VoiceCallbackAPI/v1/make/call/out/list/add" + URL_THIRD_VERSION;

}
