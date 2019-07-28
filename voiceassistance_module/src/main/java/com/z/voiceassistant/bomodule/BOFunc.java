package com.z.voiceassistant.bomodule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.z.voiceassistant.constants.BaiDConstant;
import com.z.voiceassistant.constants.BoConstant;
import com.z.voiceassistant.constants.BoHttpConstant;
import com.z.voiceassistant.constants.URLConstants;
import com.z.voiceassistant.listener.IDataCallBack;
import com.z.voiceassistant.models.BoBackBean;
import com.z.voiceassistant.models.BoGetResultBean;
import com.z.voiceassistant.models.BoGetRoundIdBean;
import com.z.voiceassistant.models.BoUploadBean;
import com.z.voiceassistant.utils.Logg;
import com.google.gson.Gson;
import com.z.tinyapp.network.okhttp.GsonObjectCallback;
import com.z.tinyapp.network.okhttp.OkHttp3Utils;
import com.z.tinyapp.utils.logs.sLog;
import com.zcnet.voiceinteractionmodule.IBoDataCallBack;
import com.zcnet.voiceinteractionmodule.voiceAPI;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import znlp.com.Tools;

/**
 * Created by GongDongdong on 2018/8/8
 */

public class BOFunc extends BroadcastReceiver implements IBoDataCallBack {

    private static final String TAG = "sunBoFunc";

    private int bot_id = 12;
    private String mSessionId = "";
    //    private String mAccessToken;
    private String mFrontId;

    private String mFlowId;

    private static IDataCallBack mCallBack;

//    private List<BoBackBean> mList = new ArrayList<>();

    //每一次请求是否推送回来 True代表已经推送回来 False代表还未收到消息
    private static volatile Map<String, Boolean> mIsNeedRequestList = new HashMap<>();

    private voiceAPI mBoApi;


    public BOFunc(IDataCallBack callBack) {

//        new AccessTokenThread().start();

        mCallBack = callBack;

        mBoApi = new voiceAPI(this);

    }

    public BOFunc(){

    }

    /**
     * 语音交互
     */
    public void voiceResultCheckWithBO(Bundle data) {

        String asrContent = data.getString(BaiDConstant.ASR_RESULT_FINAL);

        String frontInfo = String.format(
                Locale.CHINA, "{\"btConnected\":%1$d,\"contactBookSync\":%2$d}",
                data.getInt(BoConstant.MESSAGE_BLUETOOTH_STATUS),
                data.getInt(BoConstant.MESSAGE_CONTACTS_STATUS));

        final BoUploadBean uploadBean = new BoUploadBean();
        asrContent = Tools.normalizeNumber(asrContent, 0);
        uploadBean.setContent(asrContent);
        uploadBean.setAccessToken("TFNHS0o4V0g5SzEwMDAwMDUxNTMzNzc5MDY5Mzc1");
        uploadBean.setBotId(bot_id);
        uploadBean.setSessionId(mSessionId);
        uploadBean.setFrontInfo(frontInfo);

        Logg.e(TAG, "upload data ->" + new Gson().toJson(uploadBean));
        try {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mBoApi.addAIProcess(new Gson().toJson(uploadBean));
                    } catch (Exception e) {
                        sLog.i(TAG, "run: voiceResultCheckWithBO ->",e);
                    }
                }
            }).start();

        } catch (Exception e) {
            sLog.i(TAG, "voiceResultCheckWithBO: ", e);
        }
//        OkHttp3Utils.doPostJsonObject(HttpConstant.ASRBOREC, new Gson().toJson(uploadBean)
//                , new GsonObjectCallback<BoGetRoundIdBean>() {
//                    @Override
//                    public void onSuccess(BoGetRoundIdBean baseJsonBean) {
//
//                        Logg.e(TAG, "onFirstUpload Success" + JSON.toJSONString(baseJsonBean));
//
//                        if (!baseJsonBean.getCode().equals("S00")) {
//                            return;
//                        }
//
//                        mIsNeedRequestList.put(baseJsonBean.getFrontSessionRoundId(), false);
//
//                        try {
//                            TimeUnit.SECONDS.sleep(2);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        if (mIsNeedRequestList.get(baseJsonBean.getFrontSessionRoundId()) == null) {
//                            return;
//                        }
//
//                        if (mIsNeedRequestList.get(baseJsonBean.getFrontSessionRoundId())) {
//                            mIsNeedRequestList.remove(baseJsonBean.getFrontSessionRoundId());
//                            return;
//                        } else {
//                            mIsNeedRequestList.remove(baseJsonBean.getFrontSessionRoundId());
//                        }
//
//                        mFrontId = baseJsonBean.getFrontSessionRoundId();
//
//                        BoGetResultBean boGetResultBean = new BoGetResultBean();
//                        boGetResultBean.setAccessToken("TFNHS0o4V0g5SzEwMDAwMDUxNTMzNzc5MDY5Mzc1");
//                        boGetResultBean.setFrontSessionRoundId(mFrontId);
//
//                        OkHttp3Utils.doPostJsonString(URLConstants.URL_GET_RESULT, new Gson().toJson(boGetResultBean), new GsonObjectCallback<BoBackBean>() {
//                            @Override
//                            public void onSuccess(BoBackBean baseJsonBean) {
//
////                                bot_id = String.valueOf(baseJsonBean.getBotId());
//                                Bundle bundle = new Bundle();
//                                mSessionId = baseJsonBean.getSessionId();
//                                Logg.e(TAG, "URL_GET_RESULT SUCCESS ->" + JSON.toJSONString(baseJsonBean));
//                                bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_SUCCESS);
//                                bundle.putSerializable(BoConstant.BO_JAVA_BEAN, baseJsonBean);
//                                mCallBack.getBoDataBack(bundle);
//                            }
//
//                            @Override
//                            public void onFailed(String code, String msg) {
//                                Logg.e(TAG, "URL_GET_RESULT  FAILED  ->" + "  code:" + code + "  msg:" + msg);
//                                Bundle bundle = new Bundle();
//                                bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_FAILED);
//                                mCallBack.getBoDataBack(bundle);
//                            }
//                        });
//
//                    }
//
//                    @Override
//                    public void onFailed(String code, String msg) {
//                        Logg.e(TAG, "URL_GET_RESULT  FAILED  ->" + "  code:" + code + "  msg:" + msg);
//                        Bundle bundle = new Bundle();
//                        bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_FAILED);
//                        mCallBack.getBoDataBack(bundle);
//
////                        // TODO: 2018/12/28 测试数据
////                        Bundle bundle = new Bundle();
////                        switch (i) {
////                            case 0:
////                                bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_SUCCESS);
////                                bundle.putSerializable(BoConstant.BO_JAVA_BEAN, mList.get(0));
////                                mCallBack.getBoDataBack(bundle);
////                                i++;
////                                break;
////                            case 1:
////                                bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_SUCCESS);
////                                bundle.putSerializable(BoConstant.BO_JAVA_BEAN, mList.get(1));
////                                mCallBack.getBoDataBack(bundle);
////                                i++;
////                                break;
////                            case 2:
////                                break;
////                            case 3:
////                                bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_SUCCESS);
////                                bundle.putSerializable(BoConstant.BO_JAVA_BEAN, mList.get(2));
////                                mCallBack.getBoDataBack(bundle);
////                                i++;
////                                break;
////                            case 4:
////                                break;
////                        }
//                    }
//                });
    }

//    private int i = 0;

    /**
     * 上传联系人至第三方
     */
    public void uploadContacts(String data) {

        Logg.e(TAG, "upload4Third data ->" + data);

        OkHttp3Utils.doPostJsonString(BoHttpConstant.URL_THIRD_CONTACTS, data
                , new GsonObjectCallback<BoGetRoundIdBean>() {
                    @Override
                    public void onSuccess(BoGetRoundIdBean baseJsonBean) {
                        sLog.e(TAG, "OkHttp3Utils searchByName-->" + JSON.toJSONString(baseJsonBean));

                        if (!baseJsonBean.getCode().equals("S00")) {
                            Bundle bundle = new Bundle();
                            bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_FAILED);
                            mCallBack.getBoDataBack(bundle);
                            return;
                        }

                        mIsNeedRequestList.put(baseJsonBean.getFrontSessionRoundId(), false);

                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (mIsNeedRequestList.get(baseJsonBean.getFrontSessionRoundId()) == null) {
                            return;
                        }

                        if (mIsNeedRequestList.get(baseJsonBean.getFrontSessionRoundId())) {
                            mIsNeedRequestList.remove(baseJsonBean.getFrontSessionRoundId());
                            return;
                        } else {
                            mIsNeedRequestList.remove(baseJsonBean.getFrontSessionRoundId());
                        }

                        BoGetResultBean boGetResultBean = new BoGetResultBean();
                        boGetResultBean.setAccessToken("TFNHS0o4V0g5SzEwMDAwMDUxNTMzNzc5MDY5Mzc1");
                        boGetResultBean.setFrontSessionRoundId(mFrontId);

                        Logg.e(TAG, "upload4Third URL_GET_RESULT ->" + new Gson().toJson(boGetResultBean));
                        OkHttp3Utils.doPostJsonString(URLConstants.URL_GET_RESULT, new Gson().toJson(boGetResultBean), new GsonObjectCallback<BoBackBean>() {
                            @Override
                            public void onSuccess(BoBackBean baseJsonBean) {

//                                bot_id = String.valueOf(baseJsonBean.getBotId());
                                mSessionId = baseJsonBean.getSessionId();
                                Logg.e(TAG, "URL_GET_RESULT upload4Third SUCCESS ->" + JSON.toJSONString(baseJsonBean));
                                Bundle bundle = new Bundle();
                                bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_SUCCESS);
                                bundle.putSerializable(BoConstant.BO_JAVA_BEAN, baseJsonBean);
                                mCallBack.getBoDataBack(bundle);
                            }

                            @Override
                            public void onFailed(String code, String msg) {
                                Logg.e(TAG, "URL_GET_RESULT  upload4Third FAILED  ->" + "  code:" + code + "  msg:" + msg);
                                Bundle bundle = new Bundle();
                                bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_FAILED);
                                mCallBack.getBoDataBack(bundle);
                            }
                        });
                    }

                    @Override
                    public void onFailed(String code, String msg) {
                        sLog.e(TAG, "OkHttp3Utils searchByName-----" + code + msg);
                        Bundle bundle = new Bundle();
                        bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_FAILED);
                        mCallBack.getBoDataBack(bundle);

                        // TODO: 2018/12/28 测试数据
//                        Bundle bundle = new Bundle();
//                        bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_SUCCESS);
//                        bundle.putSerializable(BoConstant.BO_JAVA_BEAN, mList.get(3));
//                        mCallBack.getBoDataBack(bundle);
//                        i = 0;
                    }
                });
    }


    /**
     * 上传未接来电至第三方
     */
    public void uploadMissingCall(String data) {

        Logg.e(TAG, "uploadMissingCall data ->" + data);

        OkHttp3Utils.doPostJsonString(BoHttpConstant.URL_THIRD_MISSING_CALL, data
                , new GsonObjectCallback<BoGetRoundIdBean>() {
                    @Override
                    public void onSuccess(BoGetRoundIdBean baseJsonBean) {
                        sLog.e(TAG, "OkHttp3Utils searchByName-->" + JSON.toJSONString(baseJsonBean));

                        if (!baseJsonBean.getCode().equals("S00")) {
                            Bundle bundle = new Bundle();
                            bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_FAILED);
                            mCallBack.getBoDataBack(bundle);
                            return;
                        }

                        mIsNeedRequestList.put(baseJsonBean.getFrontSessionRoundId(), false);

                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (mIsNeedRequestList.get(baseJsonBean.getFrontSessionRoundId()) == null) {
                            return;
                        }

                        if (mIsNeedRequestList.get(baseJsonBean.getFrontSessionRoundId())) {
                            mIsNeedRequestList.remove(baseJsonBean.getFrontSessionRoundId());
                            return;
                        } else {
                            mIsNeedRequestList.remove(baseJsonBean.getFrontSessionRoundId());
                        }

                        BoGetResultBean boGetResultBean = new BoGetResultBean();
                        boGetResultBean.setAccessToken("TFNHS0o4V0g5SzEwMDAwMDUxNTMzNzc5MDY5Mzc1");
                        boGetResultBean.setFrontSessionRoundId(mFrontId);

                        Logg.e(TAG, "uploadMissingCall URL_GET_RESULT ->" + new Gson().toJson(boGetResultBean));
                        OkHttp3Utils.doPostJsonString(URLConstants.URL_GET_RESULT, new Gson().toJson(boGetResultBean), new GsonObjectCallback<BoBackBean>() {
                            @Override
                            public void onSuccess(BoBackBean baseJsonBean) {

//                                bot_id = String.valueOf(baseJsonBean.getBotId());
                                mSessionId = baseJsonBean.getSessionId();
                                Logg.e(TAG, "URL_GET_RESULT SUCCESS ->" + JSON.toJSONString(baseJsonBean));
                                Bundle bundle = new Bundle();
                                bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_SUCCESS);
                                bundle.putSerializable(BoConstant.BO_JAVA_BEAN, baseJsonBean);
                                mCallBack.getBoDataBack(bundle);
                            }

                            @Override
                            public void onFailed(String code, String msg) {
                                Logg.e(TAG, "URL_GET_RESULT  FAILED  ->" + "  code:" + code + "  msg:" + msg);
                                Bundle bundle = new Bundle();
                                bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_FAILED);
                                mCallBack.getBoDataBack(bundle);
                            }
                        });
                    }

                    @Override
                    public void onFailed(String code, String msg) {
                        sLog.e(TAG, "uploadMissingCall searchByName-----" + code + msg);
                        Bundle bundle = new Bundle();
                        bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_FAILED);
                        mCallBack.getBoDataBack(bundle);
                    }
                });
    }

    /**
     * 上传拨出电话至第三方
     */
    public void uploadOutCall(String data) {

        Logg.e(TAG, "uploadOutCall data ->" + data);

        OkHttp3Utils.doPostJsonString(BoHttpConstant.URL_THIRD_OUT_CALL, data
                , new GsonObjectCallback<BoGetRoundIdBean>() {
                    @Override
                    public void onSuccess(BoGetRoundIdBean baseJsonBean) {
                        sLog.e(TAG, "OkHttp3Utils searchByName-->" + JSON.toJSONString(baseJsonBean));

                        if (!baseJsonBean.getCode().equals("S00")) {
                            Bundle bundle = new Bundle();
                            bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_FAILED);
                            mCallBack.getBoDataBack(bundle);
                            return;
                        }

                        mIsNeedRequestList.put(baseJsonBean.getFrontSessionRoundId(), false);

                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (mIsNeedRequestList.get(baseJsonBean.getFrontSessionRoundId()) == null) {
                            return;
                        }

                        if (mIsNeedRequestList.get(baseJsonBean.getFrontSessionRoundId())) {
                            mIsNeedRequestList.remove(baseJsonBean.getFrontSessionRoundId());
                            return;
                        } else {
                            mIsNeedRequestList.remove(baseJsonBean.getFrontSessionRoundId());
                        }

                        BoGetResultBean boGetResultBean = new BoGetResultBean();
                        boGetResultBean.setAccessToken("TFNHS0o4V0g5SzEwMDAwMDUxNTMzNzc5MDY5Mzc1");
                        boGetResultBean.setFrontSessionRoundId(mFrontId);

                        Logg.e(TAG, "uploadOutCall URL_GET_RESULT ->" + new Gson().toJson(boGetResultBean));
                        OkHttp3Utils.doPostJsonString(URLConstants.URL_GET_RESULT, new Gson().toJson(boGetResultBean), new GsonObjectCallback<BoBackBean>() {
                            @Override
                            public void onSuccess(BoBackBean baseJsonBean) {

//                                bot_id = String.valueOf(baseJsonBean.getBotId());
                                mSessionId = baseJsonBean.getSessionId();
                                Logg.e(TAG, "URL_GET_RESULT SUCCESS ->" + JSON.toJSONString(baseJsonBean));
                                Bundle bundle = new Bundle();
                                bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_SUCCESS);
                                bundle.putSerializable(BoConstant.BO_JAVA_BEAN, baseJsonBean);
                                mCallBack.getBoDataBack(bundle);
                            }

                            @Override
                            public void onFailed(String code, String msg) {
                                Logg.e(TAG, "URL_GET_RESULT  FAILED  ->" + "  code:" + code + "  msg:" + msg);
                                Bundle bundle = new Bundle();
                                bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_FAILED);
                                mCallBack.getBoDataBack(bundle);
                            }
                        });
                    }

                    @Override
                    public void onFailed(String code, String msg) {
                        sLog.e(TAG, "uploadOutCall searchByName-----" + code + msg);
                        Bundle bundle = new Bundle();
                        bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_FAILED);
                        mCallBack.getBoDataBack(bundle);
                    }
                });
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        sLog.e(TAG, "onReceive: ----->" + intent.getStringExtra("message"));
        String content = intent.getStringExtra("message");
        JSONObject object = JSON.parseObject(content).getJSONObject("content");
        BoBackBean bean = JSON.parseObject(object.toJSONString(), BoBackBean.class);

        if (bean != null) {

            Logg.e(TAG, "------2---list size-------" + mIsNeedRequestList.size());

            // TODO: 2018/12/9 推送相关
//            if (mIsNeedRequestList.get(bean.getFrontSessionRoundId()) == null) {
//                return;
//            }
//
//            if (mIsNeedRequestList.get(bean.getFrontSessionRoundId())) {
//                mIsNeedRequestList.remove(bean.getFrontSessionRoundId());
//                return;
//            } else {
//                mIsNeedRequestList.remove(bean.getFrontSessionRoundId());
//            }

            Bundle bundle = new Bundle();
            bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_SUCCESS);
            bundle.putSerializable(BoConstant.BO_JAVA_BEAN, bean);
            mCallBack.getBoDataBack(bundle);
        }

    }

    public void clearCookies() {
        mSessionId = null;
        mFrontId = null;
//        i = 0;
    }

    @Override
    public void onDataBack(String data) {

        BoBackBean baseJsonBean = new Gson().fromJson(data, BoBackBean.class);

        //bot_id = String.valueOf(baseJsonBean.getBotId());
        Bundle bundle = new Bundle();
        mSessionId = baseJsonBean.getSessionId();
        Logg.e(TAG, "URL_GET_RESULT SUCCESS ->" + JSON.toJSONString(baseJsonBean));
        bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_SUCCESS);
        bundle.putSerializable(BoConstant.BO_JAVA_BEAN, baseJsonBean);
        mCallBack.getBoDataBack(bundle);
    }

    @Override
    public void onFailed() {
        Bundle bundle = new Bundle();
        bundle.putInt(BoConstant.RESULT_CODE, BoConstant.REQUEST_FAILED);
        bundle.putSerializable(BoConstant.BO_JAVA_BEAN, null);
        mCallBack.getBoDataBack(bundle);
    }

    //    class AccessTokenThread extends Thread {
//        @Override
//        public void run() {
//            while (true) {
//                Map<String, Object> map = new HashMap<>();
//                map.put("idmLoginName", "test2");
//                map.put("deviceType", "1");
//                map.put("deviceCode", "100005-100005-100007");
//                map.put("lon", "123.4");
//                map.put("lat", "41.8");
//                map.put("phoneNum", "13302580369");
//                OkHttp3Utils.doPostJson(HttpConstant.ACCESSTOKENGAIN, map
//                        , new GsonObjectCallback<TokenObj>() {
//                            @Override
//                            public void onSuccess(TokenObj baseJsonBean) {
//                                sLog.e("RunTime", "OkHttp3Utils onSuccess" + baseJsonBean.userList);
//                                if (baseJsonBean.userList != null && baseJsonBean.userList.size() > 0) {
//                                    mAccessToken = baseJsonBean.userList.get(0).accessToken;
//                                }
//                            }
//
//                            @Override
//                            public void onFailed(String code, String msg) {
//                                sLog.e(ConstV.TAG, "OkHttp3Utils TokenObj onFailed : " + code);
//                                sLog.e("RunTime", "OkHttp3Utils onSuccess" + code + msg);
//                            }
//                        });
//                try {
//                    Thread.sleep(1000 * 60 * 5);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

}
