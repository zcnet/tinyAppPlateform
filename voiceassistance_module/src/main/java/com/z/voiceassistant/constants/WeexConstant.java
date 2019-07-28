package com.z.voiceassistant.constants;

/**
 * Created by sun on 2018/10/17
 */

public class WeexConstant {

    //意图
    public static final String MESSAGE_WHAT = "MESSAGE_WHAT";

    //上传至Bo后台的意图
    public static final String MESSAGE_UPLOAD_THIRD = "MESSAGE_UPLOAD_BO";

    //上传至Bo后台的数据
    public static final String MESSAGE_UPLOAD_THIRD_DATA = "MESSAGE_UPLOAD_THIRD_DATA";

    //从通讯录检索的意图
    public static final String MESSAGE_SEARCH = "MESSAGE_SEARCH";

    //从通讯录检索的姓名
    public static final String MESSAGE_SEARCH_DATA = "MESSAGE_SEARCH_DATA";
    //从通讯录检索的姓名
    public static final String MESSAGE_SEARCH_FLOW_ID = "MESSAGE_SEARCH_FLOW_ID";

    //点击Item的意图
    public static final String MESSAGE_CLICK_ITEM ="MESSAGE_CLICK_ITEM";

    //具体点击Item的Index
    public static final String MESSAGE_CLICK_ITEM_INDEX ="MESSAGE_CLICK_ITEM_INDEX";


    //拨打蓝牙电话的意图
    public static final String MESSAGE_CALL_BT_PHONE ="MESSAGE_CALL_BT_PHONE";

    public static final String MESSAGE_CALL_ON_STAR_PHONE ="MESSAGE_CALL_ON_STAR_PHONE";
    //拨打电话的意图点击的Index
    public static final String MESSAGE_CALL_PHONE_INDEX ="MESSAGE_CALL_PHONE_INDEX";
    //拨打电话的意图点击的Item的电话号
    public static final String MESSAGE_CALL_PHONE_NUMBER ="MESSAGE_CALL_PHONE_NUMBER";
    //拨打电话的意图点击的是/否
    public static final String MESSAGE_CALL_PHONE_ISTRUE ="MESSAGE_CALL_PHONE_ISTRUE";

    //停止拨打蓝牙电话
    public static final String MESSAGE_STOP_CALL_BT_PHONE = "MESSAGE_STOP_CALL_BT_PHONE";

    public static final String MESSAGE_STOP_CALL_ON_STAR_PHONE = "MESSAGE_STOP_CALL_ON_STAR_PHONE";

    //重拨蓝牙电话
    public static final String MESSAGE_REDIA_BT_PHONE ="MESSAGE_REDIA_BT_PHONE";

    public static final String MESSAGE_REDIA_ON_STAR_PHONE ="MESSAGE_REDIA_ON_STAR_PHONE";

    //获取通话记录
    public static final String MESSAGE_CALL_LOG = "MESSAGE_CALL_LOG";
    //通话记录中Type
    public static final String MESSAGE_CALL_LOG_TYPE = "MESSAGE_CALL_LOG_TYPE";



    //Weex和Android端传递数据
    //Weex编号
    public static final String MESSAGE_WEEX_ID = "MESSAGE_WEEX_ID";
    //Weex传递的消息
    public static final String MESSAGE_WEEX_STRING = "MESSAGE_WEEX_STRING";
}
