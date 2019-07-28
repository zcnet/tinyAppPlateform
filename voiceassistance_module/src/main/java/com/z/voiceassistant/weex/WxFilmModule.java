package com.z.voiceassistant.weex;

import android.content.Intent;

import com.z.voiceassistant.application.MApplication;
import com.z.tinyapp.utils.common.TextUtil;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

/**
 * Created by sun on 2019/3/20
 */
public class WxFilmModule extends WXModule {

    /**
     * 发送广播
     */
    @JSMethod(uiThread = false)
    public void sendBroadCast(String cmd, String extra) {
        Intent intent = new Intent();
        intent.setAction("com.baidu.duerosauto.app_controler.callback");
        intent.putExtra("cmd", cmd);
        intent.putExtra("extra", extra);
        MApplication.getContext().sendBroadcast(intent);
    }

    /**
     * 打开电影APP主页
     */
    @JSMethod(uiThread = false)
    public void openFilmHome(String filmId) {
        Intent intent = new Intent("com.baidu.iov.dueros.film.action.FILMHOME");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!TextUtil.isEmpty(filmId)) {
            intent.putExtra("movieId", Integer.valueOf(filmId));
        }
        intent.putExtra("needTts", true);
        MApplication.getContext().startActivity(intent);
    }

}
