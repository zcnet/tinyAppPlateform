package com.aoshuotec.voiceassistant.constants;

/**
 * Created by sun on 2018/10/16
 */
public class BoConstant {

    private static final int BASE_NUMBER = 1;

    //Bo请求结果状态码
    public static final String RESULT_CODE = "RESULT_CODE";

    //网络请求成功
    public static final int REQUEST_SUCCESS = BASE_NUMBER+BASE_NUMBER;

    //网络请求失败
    public static final int REQUEST_FAILED = BASE_NUMBER+REQUEST_SUCCESS;

    //网络请求后转为的JavaBean
    public static final String BO_JAVA_BEAN  = "BO_JAVA_BEAN";

    //蓝牙状态
    public static final String MESSAGE_BLUETOOTH_STATUS = "MESSAGE_BLUETOOTH_STATUS";

    //通讯录状态
    public static final String MESSAGE_CONTACTS_STATUS = "MESSAGE_CONTACTS_STATUS";

    //IT后台返回结果中代表成功请求的Value
    public static final String VALUE_TRUE_MESSAGE = "0";

}
