package com.z.voiceassistant.ttsmodule;

import android.os.Bundle;

import com.z.voiceassistant.JniGateway;
import com.z.voiceassistant.constants.TTsConstant;
import com.z.voiceassistant.listener.IDataCallBack;
import com.z.voiceassistant.models.TTSParamBean;
import com.z.voiceassistant.utils.Logg;
import com.z.tinyapp.utils.common.TextUtil;

/**
 * Created by GongDongdong on 2018/8/8
 */
public class TTSFunc {

    private static final String TAG = "sunTTs";

    private IDataCallBack mCallBack;

    public TTSFunc(IDataCallBack callBack) {
        mCallBack = callBack;
    }

    /**
     * 初始进入时的播报
     */
    public void playInit() {
        Logg.e(TAG, "BONeed2Play : " + "你好啊");

        new Thread(new Runnable() {
            @Override
            public void run() {
                JniGateway.JniTtsPlay("你好啊");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Logg.e(TAG, "TTS Play Over");

                Bundle bundle2 = new Bundle();
                bundle2.putString(TTsConstant.MESSAGE_WHAT, TTsConstant.MESSAGE_TTS_FINISH_INIT);
                bundle2.putBoolean(TTsConstant.MESSAGE_TTS_FRESHEN, true);
                mCallBack.getTTSDataBack(bundle2);

            }
        }).start();


    }

    public void play(final String str, final TTSParamBean bean) {
        Logg.e(TAG, "BONeed2Play : " + str);

        new Thread(new Runnable() {
            @Override
            public void run() {

                if(TextUtil.isEmpty(str)){
                    return;
                }

                JniGateway.TtsCancel();

                JniGateway.JniTtsPlay(str);

                do {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }while (JniGateway.GetTtsStatus() != 1);

                if (bean.getPauseTime() != 0) {
                    try {
                        Thread.sleep(bean.getPauseTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Logg.e(TAG, "TTS Play Over");

                Bundle bundle2 = new Bundle();
                bundle2.putString(TTsConstant.MESSAGE_WHAT, TTsConstant.MESSAGE_TTS_FINISH_BO);
                bundle2.putBoolean(TTsConstant.MESSAGE_TTS_FRESHEN, bean.isNeedRecord());
                bundle2.putBoolean(TTsConstant.MESSAGE_TTS_NEED_FINISH, bean.isNeedFinish());
                mCallBack.getTTSDataBack(bundle2);

            }
        }).start();
    }

}
