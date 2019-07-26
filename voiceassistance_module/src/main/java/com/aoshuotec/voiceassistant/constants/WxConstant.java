package com.aoshuotec.voiceassistant.constants;

/**
 * Created by sun on 2018/10/17
 */

public class WxConstant {

    //上传未接来电至第三方
    public static final String MESSAGE_CALL_LOG_UPLOAD_MISS = "MESSAGE_CALL_LOG_UPLOAD_MISS";
    //上传拨出来电至第三方
    public static final String MESSAGE_CALL_LOG_UPLOAD_OUT = "MESSAGE_CALL_LOG_UPLOAD_OUT";

    //通话记录中flowId
    public static final String MESSAGE_CALL_LOG_FLOW_ID = "MESSAGE_CALL_LOG_FLOW_ID";
    //通话记录中传递的数据
    public static final String MESSAGE_CALL_LOG_DATA = "MESSAGE_CALL_LOG_DATA";

    //上传通讯录至第三方的方法
    public static final String MESSAGE_SEARCH_UPLOAD = "MESSAGE_SEARCH_UPLOAD";


    //从Weex传入的意图
    public static final String MESSAGE_WX_COMMON_INTENT = "MESSAGE_WX_COMMON_INTENT";

    //从Weex传入的数据
    public static final String MESSAGE_WX_COMMON_DATA = "MESSAGE_WX_COMMON_DATA";

    //意图
    public static final String MESSAGE_WHAT = "MESSAGE_WHAT";

    //从通讯录检索的意图
    public static final String MESSAGE_SEARCH = "MESSAGE_SEARCH";

    //从通讯录检索的意图
    public static final String MESSAGE_SEARCH_WITH_FLAG = "MESSAGE_SEARCH_WITH_FLAG";

    //从通讯录检索的姓名
    public static final String MESSAGE_SEARCH_DATA = "MESSAGE_SEARCH_DATA";
    //从通讯录检索的姓名
    public static final String MESSAGE_SEARCH_DATA_FLAG = "MESSAGE_SEARCH_DATA_FLAG";


    //拨打蓝牙电话的意图
    public static final String MESSAGE_CALL_BT_PHONE ="MESSAGE_CALL_BT_PHONE";
    //拨打安吉星电话的意图
    public static final String MESSAGE_CALL_ON_STAR_PHONE ="MESSAGE_CALL_ON_STAR_PHONE";
    //拨打电话的意图点击的Item的电话号
    public static final String MESSAGE_CALL_PHONE_NUMBER ="MESSAGE_CALL_PHONE_NUMBER";

    //停止拨打蓝牙电话
    public static final String MESSAGE_STOP_CALL_BT_PHONE = "MESSAGE_STOP_CALL_BT_PHONE";
    //停止拨打安吉星电话
    public static final String MESSAGE_STOP_CALL_ON_STAR_PHONE = "MESSAGE_STOP_CALL_ON_STAR_PHONE";

    //重拨蓝牙电话
    public static final String MESSAGE_REDIAL_BT_PHONE ="MESSAGE_REDIAL_BT_PHONE";
    //重拨安吉星电话
    public static final String MESSAGE_REDIAL_ON_STAR_PHONE ="MESSAGE_REDIAL_ON_STAR_PHONE";

    //获取通话记录
    public static final String MESSAGE_CALL_LOG = "MESSAGE_CALL_LOG";
    //通话记录中Type
    public static final String MESSAGE_CALL_LOG_TYPE = "MESSAGE_CALL_LOG_TYPE";
    //通话记录Type中未接电话
    public static final String MESSAGE_CALL_LOG_TYPE_MISSING = "missed";
    //通话记录Type中呼入电话
    public static final String MESSAGE_CALL_LOG_TYPE_CALLED = "called";
    //通话记录Type中拨出电话
    public static final String MESSAGE_CALL_LOG_TYPE_CALLING = "calling";

    //Weex通用方法
    //向后台传输数据
    public static final String MESSAGE_WX_COMMON_METHOD_UPLOAD = "MESSAGE_WX_COMMON_METHOD_UPLOAD";
    //添加Weex
    public static final String MESSAGE_WX_COMMON_METHOD_ADD_WX = "MESSAGE_WX_COMMON_METHOD_ADD_WX";
    //移除Weex
    public static final String MESSAGE_WX_COMMON_METHOD_REMOVE_WX = "MESSAGE_WX_COMMON_METHOD_REMOVE_WX";
    //播放TTS的意图
    public static final String MESSAGE_WX_COMMON_PLAY_TTS  = "MESSAGE_WX_COMMON_PLAY_TTS";
    //播放TTS传入的数据
    public static final String MESSAGE_WX_COMMON_PLAY_TTS_DATA  = "MESSAGE_WX_COMMON_PLAY_TTS_DATA";
    //开始录音的意图
    public static final String MESSAGE_WX_COMMON_START_RECORD  = "MESSAGE_WX_COMMON_START_RECORD";
    //停止录音的意图
    public static final String MESSAGE_WX_COMMON_STOP_RECORD  = "MESSAGE_WX_COMMON_STOP_RECORD";

    //传输的数据
    public static final String MESSAGE_WX_COMMON_METHOD_DATA = "MESSAGE_WX_COMMON_METHOD_DATA";
    //播放TTS时是否需要停止上一个播放的意图
    public static final String MESSAGE_WX_COMMON_PLAY_TTS_NEED_CANCEL  = "MESSAGE_WX_COMMON_PLAY_TTS_NEED_CANCEL";

    //Weex互相传数据
    public static final String MESSAGE_WX_SEND_2_WX = "MESSAGE_WX_SEND_2_WX";

    //退出当前页面
    public static final String MESSAGE_WX_COMMON_EXIT = "MESSAGE_WX_COMMON_EXIT";

    //退出当前Session
    public static final String MESSAGE_WX_COMMON_SESSION_EXIT = "MESSAGE_WX_COMMON_SESSION_EXIT";

    //搜索对应的错误文本列表
    public static final String MESSAGE_WX_COMMON_SEARCH = "MESSAGE_WX_COMMON_SEARCH";

    //搜索错误文本对应的BotId
    public static final String MESSAGE_WX_COMMON_BOT_ID ="MESSAGE_WX_COMMON_BOT_ID";
    //搜索错误文本对应List的Key
    public static final String MESSAGE_WX_COMMON_LIST_KEY ="MESSAGE_WX_COMMON_LIST_KEY";

    //添加对应Media的AIDL CallBack
    public static final String MESSAGE_WX_COMMON_CALLBACK = "MESSAGE_WX_COMMON_CALLBACK";
    //添加对应Media的AIDL CallBack的MediaName
    public static final String MESSAGE_WX_COMMON_CALLBACK_NAME = "MESSAGE_WX_COMMON_CALLBACK_NAME";
    //添加对应Media的AIDL CallBack的Action
    public static final String MESSAGE_WX_COMMON_CALLBACK_ACTION = "MESSAGE_WX_COMMON_CALLBACK_ACTION";
    //添加对应Media的AIDL CallBack的PkgName
    public static final String MESSAGE_WX_COMMON_CALLBACK_PACKAGE_NAME = "MESSAGE_WX_COMMON_CALLBACK_PACKAGE_NAME";

    //添加对应Media的AIDL CallBack
    public static final String MESSAGE_WX_COMMON_AIDL_PLAY = "MESSAGE_WX_COMMON_AIDL_PLAY";
    public static final String MESSAGE_WX_COMMON_AIDL_SEARCH = "MESSAGE_WX_COMMON_AIDL_SEARCH";
    public static final String MESSAGE_WX_COMMON_AIDL_GET_CURRENT = "MESSAGE_WX_COMMON_AIDL_GET_CURRENT";

    //添加打开FM的意图
    public static final String MESSAGE_WX_MEDIA_OPEN_FM = "MESSAGE_WX_MEDIA_OPEN_FM";

    //添加打开AM的意图
    public static final String MESSAGE_WX_MEDIA_OPEN_AM = "MESSAGE_WX_MEDIA_OPEN_AM";

    //播放具体频段FM的意图
    public static final String MESSAGE_WX_MEDIA_PLAY_FM = "MESSAGE_WX_MEDIA_PLAY_FM";

    //播放具体频段AM的意图
    public static final String MESSAGE_WX_MEDIA_PLAY_AM = "MESSAGE_WX_MEDIA_PLAY_AM";

    //打开Activity的意图
    public static final String MESSAGE_OPEN_ACTIVITY = "MESSAGE_OPEN_ACTIVITY";
    //打开Activity的包名
    public static final String MESSAGE_WX_ACTIVITY_PKG_NAME = "MESSAGE_WX_ACTIVITY_PKG_NAME";
    //打开Activity的类名
    public static final String MESSAGE_WX_ACTIVITY_CLASS_NAME = "MESSAGE_WX_ACTIVITY_CLASS_NAME";

}
