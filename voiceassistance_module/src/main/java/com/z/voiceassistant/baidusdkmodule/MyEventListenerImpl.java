package com.z.voiceassistant.baidusdkmodule;

import android.os.Bundle;

import com.z.voiceassistant.constants.BaiDConstant;
import com.z.voiceassistant.listener.IDataCallBack;
import com.z.voiceassistant.models.VoiceResult;
import com.z.voiceassistant.utils.Logg;
import com.baidu.speech.EventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by GongDongdong on 2018/8/31
 */
public class MyEventListenerImpl implements EventListener {

    private IDataCallBack mCallBack;

    public MyEventListenerImpl(IDataCallBack callBack) {
        mCallBack = callBack;
    }

    //todo this manager's main work handle the BaiduSDK event. Still need to complete
    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {

        if(mCallBack == null){
            return;
        }

        Bundle bundle = new Bundle();

        if ("asr.ready".equals(name)) {
            //todo when asr.ready what to do
            Logg.e("sunBaiduEvent", "asr.ready");
        }

        if ("asr.begin".equals(name)) {
            //todo when asr.begin what to do
            Logg.e("sunBaiduEvent", "asr.begin");
        }

        if ("asr.end".equals(name)) {
            //todo when asr.end what to do st1
            Logg.e("sunBaiduEvent", "asr.end");
        }

        if ("asr.finish".equals(name)) {
            //todo when asr.finish what to do st2
            //{"error":0,"origin_result":{"error":"Speech Recognize success.","sn":"cuid=24CC132D0DE3CD8440A03D4988A46797|0&sn=4dd0de84-551c-467b-9d32-4f24c6f95c37&nettype=1","err_no":0},"desc":"Speech Recognize success."}
            //{"origin_result":{"sn":"cuid=24CC132D0DE3CD8440A03D4988A46797|0&sn=4dd0de84-551c-467b-9d32-4f24c6f95c37&nettype=1","error":7,"desc":"No recognition result match","sub_error":7001},"error":7,"desc":"No recognition result match","sub_error":7001}
            //{"origin_result":{"sn":"cuid=24CC132D0DE3CD8440A03D4988A46797|0&sn=26876efa-3c3f-4c08-b0fd-464042b92167&nettype=1","error":3,"desc":"VAD detect no speech","sub_error":3101},"error":3,"desc":"VAD detect no speech","sub_error":3101}
            //{"origin_result":{"sn":"","error":2,"desc":"Download network read failed","sub_error":2005},"error":2,"desc":"Download network read failed","sub_error":2005}
            //{"origin_result":{"sn":"","error":4,"desc":"Server param error","sub_error":4001},"error":4,"desc":"Server param error","sub_error":4001}
            //{"origin_result":{"sn":"","error":8,"desc":"ASR Engine is busy","sub_error":8001},"error":8,"desc":"ASR Engine is busy","sub_error":8001}
            //{"origin_result":{"sn":"","error":3,"desc":"Recorder open failed","sub_error":3001},"error":3,"desc":"Recorder open failed","sub_error":3001}
            JSONObject asr_result;
            try {
                asr_result = new JSONObject(params);
                int errorNo = asr_result.getInt("error");
                switch (errorNo) {
                    case 7:  //"desc":"No recognition result match",
                        Logg.e("sunBaiDEventListener", "BaiDu -->  No recognition result match");
                        bundle.putInt(BaiDConstant.RESULT_CODE, BaiDConstant.NO_RECOGNIZE_RESULT);
                        break;
                    case 3:  //"desc":"VAD detect no speech",
                        //"desc":"Recorder open failed"
                        Logg.e("sunBaiDEventListener", "BaiDu -->  VAD detect no speech");
                        bundle.putInt(BaiDConstant.RESULT_CODE, BaiDConstant.VAD_DETECT_NO_SPEECH);
                        break;
                    case 2:  //"desc":"Download network read failed",
                        Logg.e("sunBaiDEventListener", "BaiDu -->  Download network read failed");
                        bundle.putInt(BaiDConstant.RESULT_CODE, BaiDConstant.DOWNLOAD_NETWORK_READ_FAIL);
                    case 4:  //"desc":"Server param error",
                        Logg.e("sunBaiDEventListener", "BaiDu -->  Server param error");
                        bundle.putInt(BaiDConstant.RESULT_CODE, BaiDConstant.SERVER_PARAM_ERROR);
                        break;
                    case 8: //"desc":"ASR Engine is busy"
                        Logg.e("sunBaiDEventListener", "BaiDu -->  ASR Engine is busy");
                        bundle.putInt(BaiDConstant.RESULT_CODE, BaiDConstant.ASR_ENGINE_BUSY);
                        break;
                    case 0:  //"desc":"Speech Recognize success."
                        Logg.e("sunBaiDEventListener", "BaiDu -->  ASR Success");
                        bundle.putInt(BaiDConstant.RESULT_CODE, BaiDConstant.SPEECH_RECOGNIZED_SUCCESS);
                        break;
                    default:
                        //todo the default error operation
                        break;
                }
            } catch (JSONException e) {
                Logg.e("sunBaiduEvent", "error\n", e);
            }
        }

        if ("asr.exit".equals(name)) {
            //todo when asr.exit what to do st3
        }

        if ("asr.volume".equals(name)) {
            try {
                JSONObject asr_volume = new JSONObject(params);
                int volume_percent = asr_volume.getInt("volume-percent");
                if (volume_percent <= 10) {
                    volume_percent = 10;
                } else if (volume_percent > 70) {
                    volume_percent = 100;
                } else {
                    volume_percent += 30;
                }
                bundle.putInt(BaiDConstant.RESULT_CODE, BaiDConstant.SPEECH_RECOGNIZED_SUCCESS);
                bundle.putInt(BaiDConstant.VOLUME_NUMBER, volume_percent);
            } catch (JSONException e) {
                Logg.e("sunBaiduEvent", "error\n", e);
            }
        }

        if ("asr.partial".equals(name) && params != null && !params.isEmpty()) {
            VoiceResult oneResult = getVoiceResult(params);

            if ("partial_result".equals(oneResult.getResultType())) {
                String content2Show = oneResult.getResultString();
                bundle.putInt(BaiDConstant.RESULT_CODE, BaiDConstant.SPEECH_RECOGNIZED_SUCCESS);
                bundle.putString(BaiDConstant.ASR_RESULT_MIDDLE, content2Show);
//                Logg.e("sunBaiduEvent", "partial_result   --->" + content2Show);
            }

            if ("final_result".equals(oneResult.getResultType())) {
                String asr_content = oneResult.getResultString();
                switch (asr_content){
                    case "拨打":
                        asr_content = "是";
                        break;
                    case "第一个":
                        asr_content = "第1个";
                        break;
                    case "第二个":
                        asr_content = "第2个";
                        break;
                    case "第三个":
                        asr_content = "第3个";
                        break;
                    case "第四个":
                        asr_content = "第4个";
                        break;
                }
                bundle.putInt(BaiDConstant.RESULT_CODE, BaiDConstant.SPEECH_RECOGNIZED_SUCCESS);
                bundle.putString(BaiDConstant.ASR_RESULT_FINAL, asr_content);
                Logg.e("sunBaiduEvent", "final_result   --->" + asr_content);
            }
        }

        mCallBack.getBaiDDataBack(bundle);
    }

    private VoiceResult getVoiceResult(String params) {
        VoiceResult voiceResult = new VoiceResult();
        JSONObject asr_res_json;
        try {
            asr_res_json = new JSONObject(params);
            String final_result_type = asr_res_json.getString("result_type");
            if ("partial_result".equals(final_result_type)) {
                String content2Show = operateTheString(asr_res_json.getString("results_recognition"));
                voiceResult.setResultType("partial_result");
                voiceResult.setResultString(content2Show);
            }

            if ("final_result".equals(final_result_type)) {
                String asr_content = asr_res_json.getString("best_result");
                voiceResult.setResultType("final_result");
                voiceResult.setResultString(asr_content);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return voiceResult;
    }

    private String operateTheString(String results_recognition) {
        Pattern p = Pattern.compile("(\\[[^\\]]*\\])");
        Matcher m = p.matcher(results_recognition);
        if (m.find()) {
            String middleRes = m.group().substring(1, m.group().length() - 1);
            Pattern p1 = Pattern.compile("\"(.*?)\"");
            Matcher m1 = p1.matcher(middleRes);
            if (m1.find()) {
                return m1.group().substring(1, m1.group().length() - 1);
            }
        }
        return "";
    }

}
