package com.zcnet.voice_callback_lib.voice_data;

import com.z.tinyapp.utils.logs.sLog;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// VABaseVoiceData 是 VAFramework 里线程处理的数据存储类，主要包含以下部分：
// 1. VABaseOnCompleteReqPostData - 从 BO 侧传过来的数据；
// 2. VABaseOnResultReqPostData - 要传给 BO 侧的数据；
// 3. Thread - 线程对象，用于处理不同的意图；
// 4. ExpectedIntentList - 期望意图列表。
// 5. VAIntent - 由 VABaseOnCompleteReqPostData 中的 VABaseIntent 部分分离出来。
public class VABaseVoiceData implements Serializable {
    private static final String LOG_TAG = "_VA_VABASEVOICEDATA";
    private VABaseOnCompleteReqPostData onCompleteReqPostData;
    //private VABaseOnCompleteReqPostData onCompleteReqPostDataLatest;
    private List<VAIntent> vaIntents;
    private JSONObject jsonObject;
    private String phoneNumber;
    private boolean isNewIntent = false;
    private List<VAExpectedIntent> vaExpectedIntents = new ArrayList<>();
    private VABaseOnResultReqPostData onResultReqPostData = new VABaseOnResultReqPostData();
    // 线程运行需要 Runnable 对象，该对象由 VAFramework 提供。
    private Thread thread;

    private boolean isThreadWait = false;
    private JSONObject pickupInfo = null;

    public void setPickupInfo(JSONObject pickupInfo){
        this.pickupInfo = pickupInfo;
    }

    public JSONObject getPickupInfo(){
        JSONObject ret = pickupInfo;
        pickupInfo = null;
        return ret;
    }

    public VABaseOnCompleteReqPostData getOnCompleteReqPostData() {
        return onCompleteReqPostData;
    }

    public void setOnCompleteReqPostData(VABaseOnCompleteReqPostData postData) {
        this.onCompleteReqPostData = postData;
    }

    public List<VAIntent> getVaIntents() {
        return vaIntents;
    }

    public void setVaIntents(List<VAIntent> vaIntents) {
        this.vaIntents = vaIntents;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public VABaseVoiceData(){
        sLog.i(null, "VABaseVoiceData: create");
    }

    public void setJsonObject(JSONObject jsonObject) {
        if (jsonObject==null)
            sLog.i(null, "VABaseVoiceData: setJsonObject: null");
        else
            sLog.i(null, "VABaseVoiceData: setJsonObject: ok");
        this.jsonObject = jsonObject;

        if (this.jsonObject==null)
            sLog.i(null, "VABaseVoiceData: this.setJsonObject: null");
        else
            sLog.i(null, "VABaseVoiceData: this.setJsonObject: ok");
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean getIsNewIntent() {
        return isNewIntent;
    }

    public void setIsNewIntent(boolean isNewIntent) {
        this.isNewIntent = isNewIntent;
    }

    public List<VAExpectedIntent> getVaExpectedIntents() {
        return vaExpectedIntents;
    }

    public void setVaExpectedIntents(List<VAExpectedIntent> vaExpectedIntents) {
        this.vaExpectedIntents = vaExpectedIntents;
    }

    // 判断意图是否在期望意图中。
    public boolean isExpected(String intentName) {
        sLog.i(LOG_TAG, "isExpected: intentName: " + intentName);
        for (VAExpectedIntent expectedIntent : this.vaExpectedIntents) {
            if (expectedIntent.getIntentName().equals(intentName)) {
                return true;
            }
        }
        return false;
    }

    // 添加期望意图。
    public void addExpectedIntent(VAExpectedIntent vaExpectedIntent) {
        this.vaExpectedIntents.add(vaExpectedIntent);
    }

    // 清除期望意图。
    public void clearExpectedIntent() {
        this.vaExpectedIntents.clear();
    }

    public VABaseOnResultReqPostData getOnResultReqPostData() {
        return onResultReqPostData;
    }

    public void setOnResultReqPostData(VABaseOnResultReqPostData postData) {
        this.onResultReqPostData = postData;
    }

    // START THREAD ZONE ///////////////////////////////////////////////////////////////////////////
    public Thread getThread() {
        return thread;
    }

    public void setThread(Runnable runnable) {
        sLog.i(LOG_TAG, "setThread.");
        this.thread = new Thread(runnable);
    }

    // 运行线程，运行前需要 setThread，将一个 runnable 对象给到线程。
    public void threadRun() {
        sLog.i(LOG_TAG, "runThread.");
        this.thread.run();
    }

    // 暂停线程。
    public void threadWait() {
        sLog.i(LOG_TAG, "isThreadWait.");
        try {
            isThreadWait = true;
            this.thread.wait();
        } catch (Exception e) {
            sLog.i(LOG_TAG, "isThreadWait: Exception: " + e);
        }
    }

    public void threadWait(long waitTime/* 单位： 毫秒 */) {
        sLog.i(LOG_TAG, "isThreadWait."+this.thread.getName());
        synchronized (thread) {
            try {
                isThreadWait = true;
                this.thread.wait(waitTime);
                sLog.i(LOG_TAG, "isThreadWait."+thread.getState().name());
            } catch (Exception e) {
                sLog.i(LOG_TAG, "isThreadWait: Exception: " , e);
            }
        }

    }

    // 继续运行线程。
    public void threadNotify() {
        sLog.i(LOG_TAG, "threadNotify.");
        synchronized (thread) {
            try {

                isThreadWait = false;
                this.thread.notify();
            } catch (Exception e) {
                sLog.i(LOG_TAG, "threadNotify: Exception: " + e);
            }
        }
    }

    // 结束线程。
    public void threadInterrupt() {
        sLog.i(LOG_TAG, "threadInterrupt.");
        try {
            this.thread.interrupt();
        } catch (Exception e) {
            sLog.i(LOG_TAG, "threadInterrupt: Exception: " + e);
        }
    }

    // 获取线程的状态。
    public boolean threadState() {
        sLog.i(LOG_TAG, "threadState."+this.thread.getName());
        return isThreadWait;
//        int threadStateCode = -1;
//        try {
//            Thread.State threadState = this.thread.getState();
//            switch (threadState) {
//                case NEW:
//                    threadStateCode = 0;    // NEW
//                    break;
//                case BLOCKED:
//                    threadStateCode = 1;   // BLOCKED
//                    break;
//                case WAITING:
//                    threadStateCode = 2;   // WAITING
//                    break;
//                case RUNNABLE:
//                    threadStateCode = 3;   // RUNNABLE
//                    break;
//                case TERMINATED:
//                    threadStateCode = 4;   // TERMINATED
//                    break;
//                case TIMED_WAITING:
//                    threadStateCode = 5;   // TIMED_WAITING
//                    break;
//                default:
//                    threadStateCode = -1;
//            }
//        } catch (Exception e) {
//            sLog.i(LOG_TAG, "threadStatus: Exception: " + e);
//        }
//        sLog.i(LOG_TAG, "threadState: threadStateCode: " + threadStateCode);
//        return threadStateCode;
    }

    // 判断线程是否中断。
    public boolean isThreadInterrupt() {
        sLog.i(LOG_TAG, "isThreadInterrupt.");
        boolean threadInterruptState = false;
        try {
            threadInterruptState = this.thread.isInterrupted();
        } catch (Exception e) {
            sLog.i(LOG_TAG, "isThreadInterrupt: Exception: " + e);
        }
        sLog.i(LOG_TAG, "isThreadInterrupt: threadInterruptState: " + threadInterruptState);
        return threadInterruptState;
    }
    // END THREAD ZONE /////////////////////////////////////////////////////////////////////////////

    // TODO: 2/19/2019 完善 toString。
    @Override
    public String toString() {
        return "VABaseVoiceData [onCompleteReqPostData= " + onCompleteReqPostData.toString()
                + "]";
    }
}