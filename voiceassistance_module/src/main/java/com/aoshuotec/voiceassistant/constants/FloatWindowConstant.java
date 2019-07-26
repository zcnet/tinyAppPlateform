package com.aoshuotec.voiceassistant.constants;

/**
 * Created by sun on 2018/10/17
 */

public class FloatWindowConstant {

    //意图
    public static final String MESSAGE_WHAT = "MESSAGE_WHAT";

    //退出悬浮窗
    public static final String MESSAGE_EXIT = "MESSAGE_EXIT";

    //初始化完成
    public static final String MESSAGE_INIT = "MESSAGE_INIT";

    //模板标志
    public static final String MODULE_TAG = "BO_MODULE_TAG";

    //字符串模板
    public static final int MODULE_STRING = 0;
    //列表模板
    public static final int MODULE_LIST  = 1;
    //摁键模板
    public static final int MODULE_BUTTON = 2;
    //即将拨打电话的模板
    public static final int MODULE_CALL= 3;

    //模板显示方式
    public static final String MODULE_DISPLAY_TYPE = "MODULE_DISPLAY_TYPE";
    public static final int MODULE_DISPLAY_TYPE_FULL = 1;
    public static final int MODULE_DISPLAY_TYPE_HALF = 2;
    public static final int MODULE_DISPLAY_TYPE_NO = 0;



    //创建模板的Key
    //字符串模板 用于传输需要显示的字符串
    public static final String MODULE_KEY_STRING = "strcontent";
    //列表模板 用于传输需要显示的字符串
    public static final String MODULE_KEY_LIST_NAME = "name";
    public static final String MODULE_KEY_LIST_BO_CALL_BACK ="bocallback";
    //摁键模板
    public static final String MODULE_KEY_BUTTON_NAME ="name";
    public static final String MODULE_KEY_BUTTON_TEL ="tel";
    public static final String MODULE_KEY_BUTTON_PINDEX ="pindex";
    public static final String MODULE_KEY_BUTTON_TINDEX ="tindex";

}
