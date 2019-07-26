package com.zcnet.voice_callback_lib;

import com.google.gson.Gson;
import com.z.tinyapp.utils.logs.sLog;
import com.zcnet.voice_callback_lib.va_framework.VAFramework;
import com.zcnet.voice_callback_lib.voice_data.VABaseIntent;
import com.zcnet.voice_callback_lib.voice_data.VABaseIntentSlot;
import com.zcnet.voice_callback_lib.voice_data.VABaseOnCompleteReqPostData;
import com.zcnet.voice_callback_lib.voice_data.VABaseOnResultReqPostData;
import com.zcnet.voice_callback_lib.voice_data.VABaseVoiceData;
import com.zcnet.voice_callback_lib.voice_data.VAIntent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VoiceCallback2BO implements IVoiceCallback2BO {
    private static final VoiceCallback2BO ourInstance = new VoiceCallback2BO();

    public static VoiceCallback2BO getInstance() {
        return ourInstance;
    }

    private VoiceCallback2BO() {
    }

    private static final String LOG_TAG = "_VA_VOICECALLBACK2BO";

    private IVoiceDataCallBack mCallBack;

    private IVoiceDataCallBack mCallBackPickup;
    private IVoiceDataCallBack mCallBackPickuplist;

    public void setPickuplistCallback(IVoiceDataCallBack callBack){
        mCallBackPickuplist = callBack;
    }

    public void onCompletePickuplist(String pickup_res, IVoiceDataCallBack callBack) {
        mCallBackPickup = callBack;
        try {
            JSONObject ress = new JSONObject(pickup_res);
            VAFramework.getInstance().getVoiceData().setPickupInfo(ress);
            VAFramework.getInstance().getVoiceData().threadNotify();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 该接口作为业务侧的入口，接收从 BO 侧发送过来的意图。
    // 使用 VABaseOnCompleteReqPostData 中的数据解析模块解析 postData，生成 VABaseVoiceData。
    // 参数：VABaseOnCompleteReqPostData postData
    @Override
    public void onComplete(VABaseOnCompleteReqPostData postData,IVoiceDataCallBack callBack) {
        sLog.i(LOG_TAG, "onComplete: postData: " + new Gson().toJson(postData));
        mCallBack = callBack;
        // 如果当前 VoiceData 为空，即没有会话，则将数据直接置于新的 VoiceData 中。
        // 如果当前有会话在处理，分两种情况：
        // 1. 线程等待
        //      1. 新的意图在期望意图中，将数据置于旧的 VoiceData 中；唤醒线程。
        //      2. 没有新意图或新意图不在期望意图中，将数据置于新的 VoiceData 中。
        // 2. 线程运行或其他状态：停止现有的线程置空会话，将数据置于新的 VoiceData 中。
        if (VAFramework.getInstance().getIsVoiceDataEmpty() == false) {
            if(VAFramework.getInstance().getVoiceData()!=null){
                if (VAFramework.getInstance().getVoiceData().threadState()) {
                    // 只判断新意图的第一个，因为通常 postData 只有一个新意图。
                    if (VAFramework.getInstance().getVoiceData().isExpected(postData.getCurrIntent().get(0).getName())) {
                        VAFramework.getInstance().getVoiceData().setOnCompleteReqPostData(postData);
                        List<VAIntent> vaIntents = getVaIntentFromOnCompleteReqPostData(postData);
                        VAFramework.getInstance().getVoiceData().setVaIntents(vaIntents);
                        try {
                            JSONObject jsonObject = new JSONObject(postData.getFrontInfo());
                            VAFramework.getInstance().getVoiceData().setJsonObject(jsonObject);
                        } catch (Exception e) {
                            sLog.i(LOG_TAG, "onComplete: Exception: " + e);
                        }
                        VAFramework.getInstance().getVoiceData().setIsNewIntent(true);
                        VAFramework.getInstance().getVoiceData().threadNotify();
                    } else {
                        VAFramework.getInstance().getVoiceData().threadInterrupt();
                        VABaseVoiceData vaBaseVoiceData = new VABaseVoiceData();
                        vaBaseVoiceData.setOnCompleteReqPostData(postData);
                        List<VAIntent> vaIntents = getVaIntentFromOnCompleteReqPostData(postData);
                        vaBaseVoiceData.setVaIntents(vaIntents);
                        try {
                            JSONObject jsonObject = new JSONObject(postData.getFrontInfo());
                            VAFramework.getInstance().getVoiceData().setJsonObject(jsonObject);
                        } catch (Exception e) {
                            sLog.i(LOG_TAG, "onComplete: Exception: " + e);
                        }
                        VAFramework.getInstance().setVoiceData(vaBaseVoiceData);
                        VAFramework.getInstance().runVoiceData();
                    }
                } else {
                    VAFramework.getInstance().getVoiceData().threadInterrupt();
                    VABaseVoiceData vaBaseVoiceData = new VABaseVoiceData();
                    vaBaseVoiceData.setOnCompleteReqPostData(postData);
                    List<VAIntent> vaIntents = getVaIntentFromOnCompleteReqPostData(postData);
                    vaBaseVoiceData.setVaIntents(vaIntents);
                    try {
                        JSONObject jsonObject = new JSONObject(postData.getFrontInfo());
                        VAFramework.getInstance().getVoiceData().setJsonObject(jsonObject);
                    } catch (Exception e) {
                        sLog.i(LOG_TAG, "onComplete: Exception: " + e);
                    }
                    VAFramework.getInstance().setVoiceData(vaBaseVoiceData);
                    VAFramework.getInstance().runVoiceData();
                }
            }

        } else {
            VABaseVoiceData vaBaseVoiceData = new VABaseVoiceData();
            vaBaseVoiceData.setOnCompleteReqPostData(postData);
            List<VAIntent> vaIntents = getVaIntentFromOnCompleteReqPostData(postData);
            vaBaseVoiceData.setVaIntents(vaIntents);

            VAFramework.getInstance().setVoiceData(vaBaseVoiceData);
            try {
                JSONObject jsonObject = new JSONObject(postData.getFrontInfo());
                VAFramework.getInstance().getVoiceData().setJsonObject(jsonObject);
            } catch (Exception e) {
                sLog.i(LOG_TAG, "onComplete: Exception: " + e);
            }
            VAFramework.getInstance().setIsVoiceDataEmpty(false);
            VAFramework.getInstance().runVoiceData();

        }
    }

    // 发送处理结果到 BO。
    @Override
    public void onResult(VABaseOnResultReqPostData onResultPostData) {
        sLog.i(LOG_TAG, "onResult: onResultPostData: " + onResultPostData.toString());
        mCallBack.onSuccess(new Gson().toJson(onResultPostData));
    }

    public void onPickuplistResult(String pickuplist){
        if (null != mCallBackPickuplist)
            mCallBackPickuplist.onSuccess(pickuplist);
    }
    // 以 VABaseOnCompleteReqPostData 为蓝本，生成 VAIntent List。
    private List<VAIntent> getVaIntentFromOnCompleteReqPostData(VABaseOnCompleteReqPostData postData) {
        sLog.i(LOG_TAG, "getVaIntentFromOnCompleteReqPostData: postData: " + postData);
        List<VAIntent> vaIntents = new ArrayList<VAIntent>();

        if (!Utilities.isNull(postData.getCurrIntent())) {
            List<VABaseIntent> vaBaseIntents  = postData.getCurrIntent();
            for (VABaseIntent vaBaseIntent : vaBaseIntents) {
                VAIntent vaIntent = new VAIntent();
                vaIntent.setUserId(-1L);
                vaIntent.setCreateTime(System.currentTimeMillis());
                // resetAiSession 会根据流程来定义。
                vaIntent.setFrontSessionId(postData.getFrontSessionRoundId());
                vaIntent.setDialogStatus(1);    // 我也不知道这里要写什么。
                vaIntent.setIntentName(vaBaseIntent.getName());
                List<VABaseIntentSlot> vaBaseIntentSlots = vaBaseIntent.getSlots();
                vaIntent.setVaSlots(vaBaseIntentSlots);
                vaIntents.add(vaIntent);
            }
        } else {
            VAIntent vaIntent = new VAIntent();
            vaIntent.setUserId(-1L);
            vaIntent.setCreateTime(System.currentTimeMillis());
            vaIntent.setResetAiSession(true);
            vaIntent.setFrontSessionId("");
            vaIntent.setFrontSessionRoundId("");
            vaIntent.setDialogStatus(1);    // 我也不知道这里要写什么。
            vaIntent.setIntentName("");

            List<VABaseIntentSlot> vaSlotList = new ArrayList<VABaseIntentSlot>();
            VABaseIntentSlot slot = new VABaseIntentSlot();
            slot.setName("");
            slot.setOriginalWord("");
            slot.setNormalizedWord("");
            slot.setTransformerWord("");
            slot.setRequiredSlot("");
            slot.setResetWhenIntentRecognized("");
            slot.setClarify("");
            vaSlotList.add(slot);
            vaIntent.setVaSlots(vaSlotList);

            vaIntents.add(vaIntent);
        }
        return vaIntents;
    }
}
