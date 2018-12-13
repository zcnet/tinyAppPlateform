package plateform.tinyapp.com.tinyappplateform.broadcastreceiver;

/**
 * Created by zhengfei on 2018/8/7.
 */

public class BroadCastConstant {
    //用户退出登录
    public static final String ACCOUNTLOGOUT_ACTION = "com.hmi.users.action.accountLogout";

    //获取消息推送的通知
    public static final String PUSH_ACTION="com.hmi.hmiservice.alpush";

    //下载weex卡片成功
    public static final String MAINCARDDOWN ="com.tinyapp.main.downcard";

    //Edit页面下载weex卡片成功
    public static final String EDITCARDDOWN ="com.tinyapp.edit.downcard";

    //主应用包下载完成通知
    public static final String MAINMAINDOWN ="com.tinyapp.main.downmain";

    //weex发送消息nofit
    public static final String SENDNOFIT="com.tinyapp.nofit";
}
