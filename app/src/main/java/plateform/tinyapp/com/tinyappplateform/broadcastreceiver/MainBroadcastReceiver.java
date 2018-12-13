package plateform.tinyapp.com.tinyappplateform.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by zhengfei on 2018/8/7.
 */

public class MainBroadcastReceiver extends BroadcastReceiver {
    ICallback callback;
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()){
            //用户退出登录
            case BroadCastConstant.ACCOUNTLOGOUT_ACTION:
                System.exit(0);
                break;

            //应用有更新
            case BroadCastConstant.PUSH_ACTION:
                String message_body = intent.getStringExtra("message");
                if(callback!=null){
                    callback.updataBack("id","sersion");
                }
                break;

            case BroadCastConstant.MAINCARDDOWN:
                if(callback!=null){
                    String giud=intent.getStringExtra("weexid");
                    Boolean isSuccess=intent.getBooleanExtra("success",false);
                    callback.cardDown(giud,isSuccess);
                }
                break;
            case BroadCastConstant.EDITCARDDOWN:
                if(callback!=null){
                    String giud=intent.getStringExtra("weexid");
                    Boolean isSuccess=intent.getBooleanExtra("success",false);
                    callback.cardDown(giud, isSuccess);
                }
                break;
            case BroadCastConstant.SENDNOFIT:
                if(callback!=null){
                    String giud=intent.getStringExtra("weexid");
                    callback.nofit(giud);
                }
                break;
            case BroadCastConstant.MAINMAINDOWN:
                if(callback!=null){
                    String giud = intent.getStringExtra("weexid");
                    callback.mainDown(giud);
                }
                break;
        }
    }
    public interface ICallback{
        void updataBack(String weexGuid, String version);
        void cardDown(String giud, Boolean isSuccess);
        void nofit(String weexId);
        void mainDown(String giud);
    }
    public void setCallback(ICallback callback){
        this.callback=callback;
    }
}
