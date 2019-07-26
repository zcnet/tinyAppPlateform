package com.aoshuotec.voiceassistant;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import com.aoshuotec.voiceassistant.baidusdkmodule.VoiceSDKExt;
import com.aoshuotec.voiceassistant.bomodule.BOFunc;
import com.aoshuotec.voiceassistant.constants.BaiDConstant;
import com.aoshuotec.voiceassistant.constants.BoConstant;
import com.aoshuotec.voiceassistant.constants.FloatWindowConstant;
import com.aoshuotec.voiceassistant.constants.JNIConstant;
import com.aoshuotec.voiceassistant.constants.LocationConstant;
import com.aoshuotec.voiceassistant.constants.ManagerConstant;
import com.aoshuotec.voiceassistant.constants.TTsConstant;
import com.aoshuotec.voiceassistant.constants.WxConstant;
import com.aoshuotec.voiceassistant.listener.IDataCallBack;
import com.aoshuotec.voiceassistant.models.BoBackBean;
import com.aoshuotec.voiceassistant.models.TTSParamBean;
import com.aoshuotec.voiceassistant.models.ThirdUploadMissingBean;
import com.aoshuotec.voiceassistant.models.WxAddWxBean;
import com.aoshuotec.voiceassistant.models.WxRemoveWxBean;
import com.aoshuotec.voiceassistant.models.WxUploadBean;
import com.aoshuotec.voiceassistant.myservices.FloatWindowFunc;
import com.aoshuotec.voiceassistant.ttsmodule.TTSFunc;
import com.aoshuotec.voiceassistant.utils.CallLogUploadUtils;
import com.aoshuotec.voiceassistant.utils.LocationUtils;
import com.aoshuotec.voiceassistant.utils.Logg;
import com.aoshuotec.voiceassistant.weex.WxCallLogModule;
import com.aoshuotec.voiceassistant.weex.WxCommonModule;
import com.aoshuotec.voiceassistant.weex.WxContactsModule;
import com.contacts.CallLogUtils;
import com.contacts.Contact;
import com.contacts.PinYinService;
import com.google.gson.Gson;
import com.z.tinyapp.userinfo.car.idl.BluetoothRelatedFunc;
import com.z.tinyapp.utils.common.TextUtil;
import com.z.tinyapp.utils.logs.sLog;
import com.zcnet.voiceinteractionmodule.SessionInstance;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import MBluetoothService.ContactListInfo;
import MBluetoothService.Proxy.IBluetooth;
import MBluetoothService.RecentCallInfo;
import MCommon.EResult;
import TBoxService.Proxy.ITBoxService;
import tinyrpc.RpcError;

/**
 * Created by sun on 2018/10/15
 */
public class FunctionManager extends Service implements IDataCallBack {

    private static final String TAG = "sunFuncMgr";

    //BO模块
    private BOFunc mBoFunc;
    //TTS模块
    private TTSFunc mTTsFunc;
    //百度录音模块
    private VoiceSDKExt mBaiDFunc;
    //拼音模块
    private PinYinService.MBinder mPinYinBinder;
    //悬浮窗模块
    private FloatWindowFunc mFwFunc;
    //蓝牙对应功能模块
    private BluetoothRelatedFunc mBlueToothFunc;
    //蓝牙状态 off为未连接 on为已连接
    private int mBlueToothStatus = 0;
    //通讯录状态 off为更新前 on为更新后
    private int mContactsStatus = 0;
    //流程进度标志
    private int mFlowTag = ManagerConstant.FUNC_MANAGER_ORIGIN;
    //百度语音识别失误次数
    private int mBaiDFailedCount = -1;
    //是否正在退出
    private boolean isExiting = false;
    //JNI停止录音后需要执行动作的状态
    private int mJniStopState = -1;
    //是否正在播放TTS
    private boolean mIsPlayingTTS = false;
    //错误文字列表
    private List<BoBackBean.ErrorTextListBean.TextListBeanX> mErrorList = new ArrayList<>();
    //是否有操作(录音/TTS) 如果有则不退出
    private boolean mIsNeedExit = false;
    //蓝牙对应PhoneId
    private int mPhoneId = -1;
    //经度
    private String mLongitude = "121.11";
    //纬度
    private String mLatitude = "31.222";


    @Override
    public void onCreate() {
        super.onCreate();
        //initGateway
        JniGateway.setCallBack(this);
        JniGateway.JniInitial();

        //initLocation
        LocationUtils.getsInstance().register(this, this);
        //initContacts
        mBlueToothFunc = new BluetoothRelatedFunc();
        mBlueToothFunc.setIGetPhoneIdBack(new BluetoothRelatedFunc.IGetPhoneIdBack() {
            @Override
            public void onGetPhoneId(int id) {
                mPhoneId = id;
            }
        });
        //设置蓝牙状态回调
        mBlueToothFunc.setIBluetoothStatusBack(new BluetoothRelatedFunc.IBluetoothStatusBack() {//蓝牙状态监听

            @Override
            public void callSuccess() {//蓝牙连接
                mBlueToothStatus = 1;
                mBlueToothFunc.flushContactsMap();
                mBlueToothFunc.flushCallLogMap();
                Logg.e(TAG, "funcManager onCreate : mBlueToothStatus = on ");
            }

            @Override
            public void callFailed() {//蓝牙断开
                mBlueToothStatus = 0;
                mContactsStatus = 0;
                Logg.e(TAG, "funcManager onCreate : mBlueToothStatus = off ");
            }
        });
        //设置获取联系人回调
        mBlueToothFunc.setIContactsBack(new BluetoothRelatedFunc.IContactsBack() {
            @Override
            public void backMap(final Map<Integer, ContactListInfo> map) {
                sLog.i(null, "setIContactsBack");
                //sLog.printCallStatck();
                //联系人列表获取成功
                if (map == null || map.isEmpty()) {
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PinYinService.onUpdateContacts(map);
                        mContactsStatus = 1;
                    }
                }).start();
                /*new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        PinYinService.onUpdateContacts(map);
                        mContactsStatus = 1;
                    }
                });*/
                sLog.i(TAG, "funcManager onCreate : mContactsStatus = on ");
            }
        });
        //设置获取通话记录回调
        mBlueToothFunc.setICallLogBack(new BluetoothRelatedFunc.ICallLogBack() {
            @Override
            public void onCallLogUpdate(Map<Integer, RecentCallInfo> map) {
                sLog.i(TAG, "onCallLogUpdate: map == null ->" + (map == null));
                if (map != null) {
                    CallLogUtils.addMap(map);
                }
            }
        });

        mBlueToothFunc.flushContactsMap();
        mBlueToothFunc.flushBluetoothStatus();
        mBlueToothFunc.flushCallLogMap();
    }

    /**
     * 初始化各个模块
     */
    private void init() {
        //initBoFunc
        mBoFunc = new BOFunc(this);
        //initBDFunc
        mBaiDFunc = new VoiceSDKExt(this);
        //initTTsFunc
        mTTsFunc = new TTSFunc(this);
        //initFloatWindowFunc
        mFwFunc = new FloatWindowFunc(FunctionManager.this, this);
        //initWeexFunc
        WxContactsModule.setCallBack(this);
        WxCallLogModule.setCallBack(this);
        WxCommonModule.setCallBack(this);
        //bindPinYinService
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(this, PinYinService.class));
        intent.setPackage(this.getPackageName());
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mPinYinBinder = (PinYinService.MBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mPinYinBinder = null;
            }
        }, Context.BIND_AUTO_CREATE);
        Logg.e(TAG, "init over");
    }

    /**
     * 开始整个流程
     */
    private void origin(Bundle bundle) {
        switch (mFlowTag) {
            //初始化后
            case ManagerConstant.FUNC_MANAGER_ORIGIN:
                mFwFunc.showFloatWindow();
                break;
            //百度识别后
            case ManagerConstant.FUNC_MANAGER_BAI_D:
                mBoFunc.voiceResultCheckWithBO(bundle);
                break;
        }
    }

    /**
     * 从百度模块传来的消息
     */
    @Override
    public void getBaiDDataBack(Bundle bundle) {
        //如果为正在退出，只接受Jni方向传来的消息
        if (isExiting) {
            Logg.e("sun", "-----isExiting----getBaiDDataBack-");
            return;
        }
        onBaiDFunc(bundle);
    }

    /**
     * 从拼音模块传来的消息
     */
    @Override
    public void getPinYinDataBack(Bundle bundle) {
        //如果为正在退出，只接受Jni方向传来的消息
        if (isExiting) {
            Logg.e("sun", "-----isExiting----getPinYinDataBack-");
            return;
        }
        onPinYinFunc(bundle);
    }

    /**
     * 从TTS模块传来的消息
     */
    @Override
    public void getTTSDataBack(Bundle bundle) {
        //如果为正在退出，只接受Jni方向传来的消息
        if (isExiting) {
            Logg.e("sun", "-----isExiting----getTTSDataBack-");
            return;
        }
        onTTsFunc(bundle);
    }

    /**
     * 从GPS模块传来的消息
     */
    @Override
    public void getLocationDataBack(Bundle bundle) {
        Logg.i(TAG, "getLocationDataBack: ");
        mLongitude =
                new DecimalFormat("0.00").format(bundle.getDouble(LocationConstant.MESSAGE_LONGITUDE));
        mLatitude =
                new DecimalFormat("0.00").format(bundle.getDouble(LocationConstant.MESSAGE_LATITUDE));
    }

    /**
     * 从JNI模块传来的消息
     */
    @Override
    public void getJniDataBack(Bundle bundle) {
        onJniFunc(bundle);
    }

    /**
     * 从BO模块传来的消息
     */
    @Override
    public void getBoDataBack(final Bundle bundle) {
        //如果为正在退出，只接受Jni方向传来的消息
        if (isExiting) {
            Logg.e("sun", "-----isExiting----getBoDataBack-");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                onBoFunc(bundle);
            }
        }).start();
    }

    /**
     * 从Wx通用模块传来的消息
     */
    @Override
    public void getWxCommonDataBack(Bundle bundle) {
        //如果为正在退出，只接受Jni方向传来的消息
        if (isExiting) {
            Logg.e("sun", "-----isExiting----getWxCommonDataBack-");
            return;
        }
        onWxCommonFunc(bundle);
    }

    /**
     * 从Wx联系人模块传来的消息
     */
    @Override
    public void getWxContactDataBack(Bundle bundle) {
        //如果为正在退出，只接受Jni方向传来的消息
        if (isExiting) {
            Logg.e("sun", "-----isExiting----getWxContactDataBack-");
            return;
        }
        onWxContactsFunc(bundle);
    }

    /**
     * 从Wx通话记录模块传来的消息
     */
    @Override
    public void getWxCallLogDataBack(Bundle bundle) {
        //如果为正在退出，只接受Jni方向传来的消息
        if (isExiting) {
            Logg.e("sun", "-----isExiting----getWxCallLogDataBack-");
            return;
        }
        onWxCallLogFunc(bundle);
    }

    /**
     * 从悬浮窗模块传来的消息
     */
    @Override
    public void getFwDataBack(Bundle bundle) {
        //如果为正在退出，只接受Jni方向传来的消息
        if (isExiting) {
            Logg.e("sun", "-----isExiting----getFwDataBack-");
            return;
        }
        onFloatWindowFunc(bundle);
    }

    /**
     * 百度识别模块回调
     */
    private void onBaiDFunc(Bundle bundle) {
        int resultCode = bundle.getInt(BaiDConstant.RESULT_CODE);

        mJniStopState = JNIConstant.TODO_WAIT_RECOGNIZE;

        switch (resultCode) {
            case BaiDConstant.SPEECH_RECOGNIZED_SUCCESS:

                //如果在识别流程中 则不退出
                mIsNeedExit = false;

                //更新悬浮窗下方动画
                mFwFunc.updateVoiceWaveView(bundle.getInt(BaiDConstant.VOLUME_NUMBER));

                String result;

                //识别中
                result = bundle.getString(BaiDConstant.ASR_RESULT_MIDDLE);
                if (!TextUtil.isEmpty(result)) {
                    Logg.e(TAG, "loading--inner->" + result);
                    mFwFunc.middleRecResultFinal(result);
                    mFwFunc.startBusyMarkAnimation();
                    mBaiDFailedCount = -1;
                }

                //识别结束
                result = bundle.getString(BaiDConstant.ASR_RESULT_FINAL, "");

                if (TextUtil.isEmpty(result)) {
                    return;
                }

                //移除下方悬浮窗波浪线
                mFwFunc.updateVoiceWaveView(0);

                //记录User说的话
                mFwFunc.setUserSay(result);
                mFwFunc.showUserSay();

                mFwFunc.middleRecResultFinal(result);

                mBaiDFailedCount = -1;

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Logg.e(TAG, "loading--over->" + result);

                //暂停录音
                mBaiDFunc.stopRecognize();
                //刷新Jni停止录音后接下来的动作
                mJniStopState = JNIConstant.TODO_WAIT_RECOGNIZE;

                //百度识别后转到Bo模块
                mFlowTag = ManagerConstant.FUNC_MANAGER_BAI_D;

                //蓝牙与通讯录状态
                bundle.putInt(BoConstant.MESSAGE_BLUETOOTH_STATUS, mBlueToothStatus);
                bundle.putInt(BoConstant.MESSAGE_CONTACTS_STATUS, mContactsStatus);

                //经纬度
                bundle.putString(LocationConstant.MESSAGE_LATITUDE, mLatitude);
                bundle.putString(LocationConstant.MESSAGE_LONGITUDE, mLongitude);

                origin(bundle);

                return;
            case BaiDConstant.VAD_DETECT_NO_SPEECH:
                mBaiDFunc.stopRecognize();
                mBaiDFunc.startRecognize();
                break;
            case BaiDConstant.ASR_ENGINE_BUSY:
            case BaiDConstant.NO_RECOGNIZE_RESULT:
            case BaiDConstant.DOWNLOAD_NETWORK_READ_FAIL:
            case BaiDConstant.SERVER_PARAM_ERROR:
                mJniStopState = JNIConstant.TODO_PLAY_RECOGNIZE_FAILED_TTS;
                mBaiDFunc.stopRecognize();
                mFwFunc.hideBusyMark();
                mFwFunc.showExitHelpButton();
                mBaiDFailedCount++;

                playErrorList();
                break;
        }
    }

    /**
     * Bo模块回调
     */
    private void onBoFunc(Bundle bundle) {

        BoBackBean bean = null;

        switch (bundle.getInt(BoConstant.RESULT_CODE)) {
            case BoConstant.REQUEST_SUCCESS:
                bean = (BoBackBean) bundle.getSerializable(BoConstant.BO_JAVA_BEAN);
                break;
            case BoConstant.REQUEST_FAILED:
                mBaiDFailedCount++;

                mFwFunc.hideBusyMark();

                playErrorList();
                return;
        }

        if (bean == null) {
            return;
        }

        if (bean.getErrorTextList() != null && bean.getErrorTextList().size() != 0) {
            mErrorList.clear();
            mErrorList.addAll(bean.getErrorTextList().get(0).getTextList());
        }

        List<BoBackBean.VaActionListBean> list = bean.getVaActionList();
        BoBackBean.VaActionListBean actionBean;

        mFwFunc.showExitHelpButton();
        mFwFunc.hideBusyMark();
        mFwFunc.middleRecResultFinal("");

        Collections.sort(list, new Comparator<BoBackBean.VaActionListBean>() {
            @Override
            public int compare(BoBackBean.VaActionListBean lhs, BoBackBean.VaActionListBean rhs) {

//                if(lhs.getDoActionNow()==1){
//                    return 1;
//                }

                return lhs.getPriority() - rhs.getPriority();
            }
        });

        //需要播报的TTS文言
        String playStr = "";
        //是否需要录音
        boolean needRecord = false;
        //TTS播报优先级
        int ttsPriority = 0;
        //REC播报优先级
        int recPriority = 0;
        //当前动作优先级
        int actionPriority = 0;
        //TTS播放文言的配置参数
        TTSParamBean paramBean = new TTSParamBean();

        for (int i = 0; i < list.size(); i++) {
            actionBean = list.get(i);
            switch (actionBean.getName()) {
                case "display:显示文字":

                    if (actionPriority != actionBean.getPriority()) {
                        if (actionBean.getInterval() != 0) {
                            try {
                                Thread.sleep(actionBean.getInterval());
                            } catch (InterruptedException e) {
                                Logg.e(TAG, "display thread sleep error ", e);
                            }
                        }
                    }

                    if (actionBean.getTextList().size() != 0) {
                        mFwFunc.showTtsPlay(actionBean.getTextList().get(0).getText());
                    }
                    break;
                case "play:播放文字":
                    if (actionPriority != actionBean.getPriority()) {
                        if (actionBean.getInterval() != 0) {
                            try {
                                Thread.sleep(actionBean.getInterval());
                            } catch (InterruptedException e) {
                                Logg.e(TAG, "display thread sleep error ", e);
                            }
                        }
                    }

                    ttsPriority = actionBean.getPriority();

                    if (actionBean.getTextList().size() != 0) {
                        playStr = actionBean.getTextList().get(0).getText();
                    }
                    break;
                case "rec:录制音频":
                    paramBean.setPauseTime(actionBean.getInterval());
                    recPriority = actionBean.getPriority();
                    needRecord = true;
                    break;
                case "execaction:执行业务动作":
                    if (actionPriority != actionBean.getPriority()) {
                        if (actionBean.getInterval() != 0) {
                            try {
                                Thread.sleep(actionBean.getInterval());
                            } catch (InterruptedException e) {
                                Logg.e(TAG, "display thread sleep error ", e);
                            }
                        }
                    }
                    break;
                case "exit:退出":


                    if (actionBean.getInterval() == 0) {
                        // interval为0，语音助手直接退出
                        bundle = new Bundle();
                        bundle.putString(FloatWindowConstant.MESSAGE_WHAT, FloatWindowConstant.MESSAGE_EXIT);
                        getFwDataBack(bundle);
                        return;
                    } else {
                        //interval不为0, 需要有两个动作：
                        //①延时退出：如果用在指定范围内，不做任何操作。VA语音助手退出。如果操作就不退出。
                        //②如果重新录音，跟BO语音交互的回话session重置，不传已有的session。
                        mBoFunc.clearCookies();
                        new Thread(new ExitRunnable()).start();
                    }
                    break;
            }
        }
        //如果需要录音
        if (needRecord) {
            if (recPriority >= ttsPriority) {
                //如果录音优先级不小于TTS播报优先级 则立刻开始录音
                mBaiDFunc.startRecognize();
                paramBean.setNeedRecord(false);
                mTTsFunc.play(playStr, paramBean);
            } else {
                //如果录音优先级小于TTS播报优先级 则在TTS播完之后才开始录音
                paramBean.setNeedRecord(true);
                mTTsFunc.play(playStr, paramBean);
            }
        } else {
            //如果不需要录音 则只播报TTS文言
            paramBean.setPauseTime(0);
            paramBean.setNeedRecord(false);
            mTTsFunc.play(playStr, paramBean);
        }

        Map<String, Object> map = new HashMap<>();

        List<BoBackBean.BuActionListBean> buActionListBeans = bean.getBuActionList();
        BoBackBean.BuActionListBean buActionListBean;
        if (buActionListBeans != null) {
            for (int i = 0; i < buActionListBeans.size(); i++) {
                buActionListBean = buActionListBeans.get(i);

                switch (buActionListBean.getAppName()) {
                    case "callCard":
                        map.put(FloatWindowConstant.MODULE_DISPLAY_TYPE, buActionListBean.getDisplayType());
                        map.put(FloatWindowConstant.MODULE_KEY_LIST_BO_CALL_BACK, buActionListBean.getContent());
                        mFwFunc.addWxView(FloatWindowConstant.MODULE_CALL, map, buActionListBean.getContent());
                        break;
                    case "showContacts":
                        map.put(FloatWindowConstant.MODULE_DISPLAY_TYPE, buActionListBean.getDisplayType());
                        map.put(FloatWindowConstant.MODULE_KEY_LIST_BO_CALL_BACK, buActionListBean.getContent());
                        mFwFunc.addWxView(FloatWindowConstant.MODULE_LIST, map, buActionListBean.getContent());
                        break;
                }
            }
        }
    }

    /**
     * JNI模块回调
     */
    private void onJniFunc(Bundle bundle) {
        switch (bundle.getString(JNIConstant.MESSAGE_WHAT, "")) {
            case JNIConstant.MESSAGE_INIT_OVER:
                init();
                break;
            case JNIConstant.MESSAGE_VR:
                isExiting = false;

                JniGateway.JniVaSessionStart();

                if (mFwFunc.getStatus() != 0) {
                    mBaiDFunc.startRecognize();
                    return;
                }

                mFwFunc.showFloatWindow();
                Logg.e(TAG, "onJniFunc : VR ");
                break;
            case JNIConstant.MESSAGE_STOP_STREAMING:
                Logg.e(TAG, "onJniFunc : STOP STREAMING " + mJniStopState);
                switch (mJniStopState) {
                    case JNIConstant.TODO_WAIT_RECOGNIZE:
                        break;
                    case JNIConstant.TODO_PLAY_RECOGNIZE_FAILED_TTS:
                        mBaiDFunc.stopRecognize();
                        mBaiDFunc.startRecognize();
                        break;
                    case JNIConstant.TODO_RE_RECOGNIZE:
                        mFwFunc.clearTtsSay();
                        mFwFunc.clearTvBottom();
                        mBaiDFunc.stopRecognize();
                        mBaiDFunc.startRecognize();
                        break;
                    case JNIConstant.TODO_EXIT:
                        //退出悬浮窗
                        new Handler(getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                mBoFunc.clearCookies();
                                JniGateway.JniVaSessionStop();
                                mFwFunc.removeFloatWindow();
                            }
                        });
                        break;
                }
                mJniStopState = -1;
                break;
        }
    }

    /**
     * 拼音模块回调
     */
    @SuppressWarnings("unused")
    private void onPinYinFunc(Bundle bundle) {
    }

    /**
     * TTS模块回调
     */
    private void onTTsFunc(Bundle bundle) {
        switch (bundle.getString(TTsConstant.MESSAGE_WHAT, "")) {
            case TTsConstant.MESSAGE_TTS_FINISH_INIT:

                Logg.i(TAG, "onTTsFunc: tts finish");
                mFwFunc.hideTtsIcon();
                mFwFunc.clearTtsSay();
                mIsPlayingTTS = false;

                if (mBaiDFailedCount >= 2) {
                    mBaiDFailedCount = -1;
                    bundle = new Bundle();
                    bundle.putString(FloatWindowConstant.MESSAGE_WHAT, FloatWindowConstant.MESSAGE_EXIT);
                    getFwDataBack(bundle);
                    return;
                }

                if (bundle.getBoolean(TTsConstant.MESSAGE_TTS_FRESHEN)) {
                    mFwFunc.hideBusyMark();
                    mFwFunc.clearTvBottom();
                    mBaiDFunc.startRecognize();
                }
                break;
            case TTsConstant.MESSAGE_TTS_PLAYING:
                mIsPlayingTTS = true;
                mFwFunc.hideBusyMark();
                break;
            case TTsConstant.MESSAGE_TTS_FINISH_BO:
                mFwFunc.hideTtsIcon();
                mFwFunc.clearTtsSay();
                mFwFunc.hideBusyMark();
                mFwFunc.clearTvBottom();
                mIsPlayingTTS = false;

                if (bundle.getBoolean(TTsConstant.MESSAGE_TTS_FRESHEN)) {
                    mBaiDFunc.startRecognize();
                }
                break;
        }
    }

    /**
     * 悬浮窗模块回调
     */
    private void onFloatWindowFunc(Bundle bundle) {
        switch (bundle.getString(FloatWindowConstant.MESSAGE_WHAT, "")) {
            case FloatWindowConstant.MESSAGE_EXIT:

                if (mIsPlayingTTS) {
                    return;
                }

                SessionInstance.getInstance().release();

                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        //刷新Jni停止录音后接下来的动作
                        mJniStopState = JNIConstant.TODO_EXIT;
                        isExiting = true;
                        mBlueToothFunc.clearEvent();
                        mBaiDFunc.stopRecognize();
                        JniGateway.StopStreaming();
                    }
                });
                break;
            case FloatWindowConstant.MESSAGE_INIT:
                isExiting = false;
                mTTsFunc.playInit();
                mFwFunc.middleRecResultFinal("你好啊");
                mFwFunc.showTtsIcon();
                break;
        }
    }

    /**
     * Weex联系人模块回调
     */
    private void onWxContactsFunc(Bundle bundle) {
        String name;
        List<Contact> list;
        String num;
        switch (bundle.getString(WxConstant.MESSAGE_WHAT, "")) {
            case WxConstant.MESSAGE_SEARCH:
                name = bundle.getString(WxConstant.MESSAGE_SEARCH_DATA, "");
                if (mPinYinBinder == null) {
                    Logg.i(TAG, "onWxContactsFunc: pinyin binder null");
                    return;
                }
                list = mPinYinBinder.searchContacts(name);

                WxContactsModule.getJsCallBack().invokeAndKeepAlive(new Gson().toJson(list));
                break;
            case WxConstant.MESSAGE_SEARCH_WITH_FLAG:
                name = bundle.getString(WxConstant.MESSAGE_SEARCH_DATA, "");
                String flag = bundle.getString(WxConstant.MESSAGE_SEARCH_DATA_FLAG);
                if (mPinYinBinder == null) {
                    Logg.i(TAG, "onWxContactsFunc: pinyin binder null");
                    return;
                }

                if (TextUtil.isEmpty(name)) {
                    Logg.i(TAG, "onWxContactsFunc onCompareNameAndFlag name null");
                    return;
                }

                list = mPinYinBinder.searchContacts(name, flag);
                if (WxContactsModule.getJsCallBack() != null) {
                    WxContactsModule.getJsCallBack().invokeAndKeepAlive(new Gson().toJson(list));
                    //WxContactsModule.getJsCallBack().invokeAndKeepAlive(new JSONArray(contacts));
                }
                break;
            case WxConstant.MESSAGE_CALL_BT_PHONE:
                num = bundle.getString(WxConstant.MESSAGE_CALL_PHONE_NUMBER, "");

                //退出悬浮窗
                bundle = new Bundle();
                bundle.putString(FloatWindowConstant.MESSAGE_WHAT, FloatWindowConstant.MESSAGE_EXIT);
                getFwDataBack(bundle);

                if (mPinYinBinder != null) {
                    mPinYinBinder.callPhone(this, num);
                }
                break;
            case WxConstant.MESSAGE_CALL_ON_STAR_PHONE:
                num = bundle.getString(WxConstant.MESSAGE_CALL_PHONE_NUMBER, "");

                bundle = new Bundle();
                bundle.putString(FloatWindowConstant.MESSAGE_WHAT, FloatWindowConstant.MESSAGE_EXIT);
                getFwDataBack(bundle);

                try {
                    ITBoxService.Inst().CallPersonalNumber(num, "");
                } catch (RpcError rpcError) {
                    Logg.e(TAG, "onWxContactsFunc: MESSAGE_CALL_ON_STAR_PHONE", rpcError);
                }
                break;
            case WxConstant.MESSAGE_STOP_CALL_BT_PHONE:
                try {
                    IBluetooth.Inst().HangupPhone(mPhoneId, new EResult.Ref(EResult.ESuccess));
                } catch (RpcError rpcError) {
                    Logg.e(TAG, "onWxContactsFunc: HangupPhone", rpcError);
                }
                break;
            case WxConstant.MESSAGE_STOP_CALL_ON_STAR_PHONE:
                try {
                    ITBoxService.Inst().HangupPersonalCall();
                } catch (RpcError rpcError) {
                    Logg.e(TAG, "onWxContactsFunc: HangupPersonalCall", rpcError);
                }

                break;
            case WxConstant.MESSAGE_REDIAL_BT_PHONE:

                //退出悬浮窗
                bundle = new Bundle();
                bundle.putString(FloatWindowConstant.MESSAGE_WHAT, FloatWindowConstant.MESSAGE_EXIT);
                getFwDataBack(bundle);

                try {
                    IBluetooth.Inst().Redial(mPhoneId, new EResult.Ref(EResult.ESuccess));
                } catch (RpcError rpcError) {
                    Logg.e(TAG, "onWxContactsFunc: Redial", rpcError);
                }
                break;
            case WxConstant.MESSAGE_REDIAL_ON_STAR_PHONE:
                //退出悬浮窗
                bundle = new Bundle();
                bundle.putString(FloatWindowConstant.MESSAGE_WHAT, FloatWindowConstant.MESSAGE_EXIT);
                getFwDataBack(bundle);

                try {
                    ITBoxService.Inst().RedialPersonalCall();
                } catch (RpcError rpcError) {
                    Logg.e(TAG, "onWxContactsFunc: RedialPersonalCall", rpcError);
                }
                break;
            case WxConstant.MESSAGE_SEARCH_UPLOAD:
                mBoFunc.uploadContacts(bundle.getString(WxConstant.MESSAGE_SEARCH_DATA));
                break;
        }
    }

    /**
     * Weex通用模块回调
     */
    private void onWxCommonFunc(Bundle bundle) {
        String str = bundle.getString(WxConstant.MESSAGE_WX_COMMON_METHOD_DATA, "");
        switch (bundle.getString(WxConstant.MESSAGE_WHAT, "")) {
            case WxConstant.MESSAGE_WX_COMMON_METHOD_UPLOAD:

                if (TextUtil.isEmpty(str)) {
                    Logg.e(TAG, "MESSAGE_WX_COMMON_METHOD_UPLOAD - str null !!!");
                    return;
                }

                mBaiDFunc.stopRecognize();

                WxUploadBean bean = new Gson().fromJson(str, WxUploadBean.class);
                String data = bean.getData();

                bundle = new Bundle();
                bundle.putInt(BoConstant.MESSAGE_BLUETOOTH_STATUS, mBlueToothStatus);
                bundle.putInt(BoConstant.MESSAGE_CONTACTS_STATUS, mContactsStatus);

                bundle.putString(LocationConstant.MESSAGE_LATITUDE, mLatitude);
                bundle.putString(LocationConstant.MESSAGE_LONGITUDE, mLongitude);

                bundle.putString(BaiDConstant.ASR_RESULT_FINAL, data);
                mBoFunc.voiceResultCheckWithBO(bundle);

                break;
            case WxConstant.MESSAGE_WX_COMMON_METHOD_ADD_WX:
                if (TextUtil.isEmpty(str)) {
                    Logg.e(TAG, "MESSAGE_WX_COMMON_METHOD_ADD - str null !!!");
                    return;
                }
                mFwFunc.addOtherWx(new Gson().fromJson(str, WxAddWxBean.class));
                break;
            case WxConstant.MESSAGE_WX_COMMON_METHOD_REMOVE_WX:
                if (TextUtil.isEmpty(str)) {
                    Logg.e(TAG, "MESSAGE_WX_COMMON_METHOD_REMOVE_Wx - str null !!!");
                    return;
                }
                mFwFunc.removeWx(new Gson().fromJson(str, WxRemoveWxBean.class));
                break;
            case WxConstant.MESSAGE_WX_COMMON_PLAY_TTS:
                String temp = bundle.getString(WxConstant.MESSAGE_WX_COMMON_PLAY_TTS_DATA);
                TTSParamBean ttsParamBean = new TTSParamBean();
                ttsParamBean.setPauseTime(0);
                ttsParamBean.setNeedRecord(false);
                mTTsFunc.play(temp, ttsParamBean);
                break;
            case WxConstant.MESSAGE_WX_COMMON_START_RECORD:
                mBaiDFunc.startRecognize();
                break;
            case WxConstant.MESSAGE_WX_COMMON_EXIT:
                bundle = new Bundle();
                bundle.putString(FloatWindowConstant.MESSAGE_WHAT, FloatWindowConstant.MESSAGE_EXIT);
                getFwDataBack(bundle);
                break;
        }
    }

    /**
     * Weex通话记录模块回调
     */
    private void onWxCallLogFunc(Bundle bundle) {
        switch (bundle.getString(WxConstant.MESSAGE_WHAT, "")) {
            case WxConstant.MESSAGE_CALL_LOG:
                //通讯录Type
                int mCallLogType = -1;
                String type = bundle.getString(WxConstant.MESSAGE_CALL_LOG_TYPE, "");
                switch (type) {
                    case WxConstant.MESSAGE_CALL_LOG_TYPE_CALLING:
                        mCallLogType = 2;
                        break;
                    case WxConstant.MESSAGE_CALL_LOG_TYPE_CALLED:
                        mCallLogType = 1;
                        break;
                    case WxConstant.MESSAGE_CALL_LOG_TYPE_MISSING:
                        mCallLogType = 3;
                        break;
                }
                if (WxCallLogModule.getJsCallBack() != null) {
                    Map<Integer, RecentCallInfo> recentCallInfoMap = CallLogUtils.filterCallLogType(mCallLogType);
                    ThirdUploadMissingBean wxMissBean = CallLogUploadUtils.changeToThirdMissingBean(recentCallInfoMap);

                    WxCallLogModule
                            .getJsCallBack()
                            .invokeAndKeepAlive(
                                    new Gson()
                                            .toJson(
                                                    wxMissBean
                                            )
                            );
                }
                break;
            case WxConstant.MESSAGE_CALL_LOG_UPLOAD_MISS:
                mBoFunc.uploadMissingCall(bundle.getString(WxConstant.MESSAGE_CALL_LOG_DATA));
                break;
            case WxConstant.MESSAGE_CALL_LOG_UPLOAD_OUT:
                mBoFunc.uploadOutCall(bundle.getString(WxConstant.MESSAGE_CALL_LOG_DATA));
                break;
        }
    }

    /**
     * TTS播放未识别文本
     */
    private void playErrorList() {

        TTSParamBean ttsParamBean = new TTSParamBean();

        if (mBaiDFailedCount >= 2) {
            ttsParamBean.setNeedFinish(true);
            ttsParamBean.setNeedRecord(true);
            ttsParamBean.setPauseTime(0);
            if (mErrorList != null && mErrorList.size() - 1 > mBaiDFailedCount) {
                mTTsFunc.play(mErrorList.get(mBaiDFailedCount).getText(), ttsParamBean);
                mFwFunc.showTtsPlay(mErrorList.get(mBaiDFailedCount).getText());
            }
            mTTsFunc.play("对不起，我还是没有听清，我即将退出！", ttsParamBean);
            mFwFunc.showTtsPlay("对不起，我还是没有听清，我即将退出！");
            return;
        }
        ttsParamBean.setNeedFinish(false);
        ttsParamBean.setNeedRecord(true);
        ttsParamBean.setPauseTime(0);

        mTTsFunc.play("对不起，我没有听清！", ttsParamBean);
        mFwFunc.showTtsPlay("对不起，我没有过听清！");
    }

    /**
     * 延时判断 是否要退出
     */
    private class ExitRunnable implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (mIsNeedExit) {
                Bundle bundle = new Bundle();
                bundle.putString(FloatWindowConstant.MESSAGE_WHAT, FloatWindowConstant.MESSAGE_EXIT);
                getFwDataBack(bundle);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MBinder();
    }

    private class MBinder extends Binder {
    }
}
