package com.z.tinyapp.userinfo.car.idl;

import android.content.Context;

import com.z.tinyapp.utils.logs.sLog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import MBluetoothService.Proxy.NewMessageReceivedEvent;
import MBluetoothService.TextInfo;
import tinyrpc.RpcError;

/**
 * 监听接收短信
 * Created by GongDongdong on 2018/1/16.
 */

public class MessageRelatedFunc {
    private static final String TAG = "tg_tag";
    private boolean isCaringUnReadMsg = false;
    NewMessageReceivedEvent newMessageRev = null;
    private Context mContext = null;


    public void registUnReadNotifitcation(){

        isCaringUnReadMsg = true;
        try {
            IDLBasicFuncSet.activeTheTRPCClinetMgr();
            newMessageRev = new NewMessageReceivedEvent(true){
                @Override
                public void OnNewMessageReceivedEventTriggered(TextInfo textInfo) {
                    //
                    sLog.e(TAG, "senderPhoneNumber : " + textInfo.senderPhoneNumber
                            +  "  textContent: " + textInfo.textContent + "  contactNameFirst :"
                            + textInfo.contactNameFirst + "   contactNameLast: "  +
                            textInfo.contactNameLast + "    recipientPhoneNumber: " + textInfo.recipientPhoneNumber);
//todo need check the message from
                    checkMessage();
                    String token = null;
                    token = getTokenFromContent(textInfo.textContent);
                    //TODO  加群
                }
            };
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
    }

    private void checkMessage() {

    }

    private String getTokenFromContent(String textContent) {
        if(textContent == null) return null;
        String tokenValue = null;
        sLog.e(TAG, "  textContent : " + textContent);
        if(textContent.startsWith("入群口令：")){
            sLog.e(TAG, "  textContent : startsWith : " + "入群口令：");
            String regEx = "[^0-9]";
            Pattern p = Pattern.compile(regEx);
            String aaa[] = textContent.split("。");
            Matcher m = p.matcher(aaa[0]);
            tokenValue = m.replaceAll("").trim();
        }

        if(textContent.startsWith("Group Joining Token：")){
            sLog.e(TAG, "  textContent : startsWith : " + "入群口令：");
            String regEx = "[^0-9]";
            Pattern p = Pattern.compile(regEx);
            String aaa[] = textContent.split(".");
            Matcher m = p.matcher(aaa[0]);
            tokenValue = m.replaceAll("").trim();
        }
//        if(textContent.startsWith(Constants.TEXT_PREFIX)) {
//            sLog.e("gddtest", "  textContent : startsWith : " + Constants.TEXT_PREFIX);
//            String regEx = "[^0-9]";
//            Pattern p = Pattern.compile(regEx);
//            Matcher m = p.matcher(textContent);
//            tokenValue = m.replaceAll("").trim();
//        }

        sLog.e(TAG, "  textContent : " + tokenValue);
        return tokenValue;
    }

    public void unRegistUnReadNotifitcation(){
        isCaringUnReadMsg = false;
        try {
            newMessageRev.EndListen();
            newMessageRev = null;
        } catch (RpcError rpcError) {
            rpcError.printStackTrace();
        }
    }
}
