package com.z.voiceassistant.baidusdkmodule;

import com.airiche.volume.AiVolume;
import com.z.voiceassistant.JniGateway;
import com.z.voiceassistant.listener.IDataCallBack;
import com.z.voiceassistant.utils.Logg;
import com.z.tinyapp.utils.logs.sLog;

import speech.StreamApi;

/**
 * Created by GongDongdong on 2018/8/8
 */

public class VoiceSDKExt {
    private static final String TAG = "sunVoiceSDKExt";

    private AsrDemo asrDemo;

    private IDataCallBack mCallBack;

    private boolean isOpen = false;
    private AiVolume mAiVolume;
    private boolean isExiting = false;

    public VoiceSDKExt(IDataCallBack callBack) {
        mCallBack = callBack;
        mAiVolume = new AiVolume();
        mAiVolume.init();
    }

    public void setExit(boolean a){
        isExiting = a;
    }

    public void startRecognize() {
        if (isOpen) {
            sLog.i(TAG, "startRecognize: already start");
            return;
        }

        isOpen = true;
        Logg.i(TAG, "bd startRecognize");

        StreamApi.ApiParam apiParam = new StreamApi.ApiParam();
        // Activate to get cuid. 先激活，拿到uuid, 用于后端身份识别。详情参见激活流程
        apiParam.cuid = "Get your cuid by activation";

        asrDemo = new AsrDemo(mCallBack, mAiVolume);
        asrDemo.setApiParam(apiParam);
        asrDemo.setAsrId(AsrDemo.buildAsrId("some-encoded-prefix-for-each-car"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                JniGateway.JniStartStreaming();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sLog.d("up_stream", "ThreadId:" + Thread.currentThread());
                try {
                    if(!isExiting){
                        asrDemo.upload();
                    }
                } catch (Exception e) {
                    sLog.e("sun", "run: ", e);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                sLog.d("down_stream", "ThreadId:" + Thread.currentThread());
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                    sLog.e("sun", "run2: ", e);
                }
                if(!isExiting){
                    asrDemo.download();
                }

            }
        }).start();
    }

    public void startNoListenRecognize() {

        isOpen = true;
        Logg.i(TAG, "bd startRecognize");

        StreamApi.ApiParam apiParam = new StreamApi.ApiParam();
        // Activate to get cuid. 先激活，拿到uuid, 用于后端身份识别。详情参见激活流程
        apiParam.cuid = "Get your cuid by activation";

        asrDemo = new AsrDemo(mCallBack, mAiVolume);
        asrDemo.setApiParam(apiParam);
        asrDemo.setAsrId(AsrDemo.buildAsrId("some-encoded-prefix-for-each-car"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                JniGateway.JniStartStreaming();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sLog.d("up_stream", "ThreadId:" + Thread.currentThread());
                try {
                    if(!isExiting){
                        asrDemo.upload();
                    }
                } catch (Exception e) {
                    sLog.e("sun", "run: ", e);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                sLog.d("down_stream", "ThreadId:" + Thread.currentThread());
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                    sLog.e("sun", "run2: ", e);
                }
                if(!isExiting){
                    asrDemo.download();
                }

            }
        }).start();
    }

    public void stopRecognize() {
        isOpen = false;
        Logg.i(TAG, "bd stopRecognize");
        if(asrDemo!=null){
            asrDemo.exit();
        }
    }

    public void cancelRecognize() {
        Logg.i(TAG, "bd cancelRecognize");
        stopRecognize();
    }
}
