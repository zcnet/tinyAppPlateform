package com.tinyapp.tinyappplateform.broadcastreceiver;

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
            case BroadCastConstant.MAINMAINDOWN:
                if(callback!=null){
                    String giud = intent.getStringExtra("weexid");
                    callback.mainDown(giud);
                }
                break;
            case BroadCastConstant.INITWEEXSERVICEAPP:
                if (callback != null) {
                    callback.initWeexServiceApp();
                }
                break;
            case BroadCastConstant.EXEC_CMD:
                if (callback != null) {
                    callback.cmd(intent);
                }
                break;
        }
    }
    public interface ICallback{
        void updataBack(String weexGuid,String version);
        void cardDown(String giud, Boolean isSuccess);
        void nofit(String weexId);
        void mainDown(String giud);
        void initWeexServiceApp();
        void cmd(Intent cmd);
    }
    public void setCallback(ICallback callback){
        this.callback=callback;
    }
}
