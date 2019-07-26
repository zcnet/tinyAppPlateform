package com.z.tinyapp.network.okhttp;

import android.os.Handler;

import com.google.gson.Gson;
import com.z.tinyapp.network.BaseJsonBean;
import com.z.tinyapp.network.HttpEngine;
import com.z.tinyapp.network.NetCode;
import com.z.tinyapp.userinfo.user.BindUserManager;
import com.z.tinyapp.utils.logs.sLog;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
/**
 * Created by zhengfei on 2018/8/13.
 */

public abstract class GsonObjectCallback<T extends BaseJsonBean> implements Callback {
    private Handler handler = OkHttp3Utils.getInstance().getHandler();
    //主线程处理
    public abstract void onSuccess(T t);
    //主线程处理
    public abstract void onFailed(String code, String msg);
    //请求失败
    @Override
    public void onFailure(final Call call, final IOException e) {
        if(handler!=null){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onFailed(NetCode.NETERROR, e.toString());
                }
            });
        }
    }

    //请求json 并直接返回泛型的对象 主线程处理
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try{
            String json = response.body().string();
            sLog.e(HttpEngine.TAG,"x json:"+json);
            Class<T> cls = null;
            Class clz = this.getClass();
            ParameterizedType type = (ParameterizedType) clz.getGenericSuperclass();
            Type[] types = type.getActualTypeArguments();
            cls = (Class<T>) types[0];
            Gson gson = new Gson();
            final T t = gson.fromJson(json, cls);
            if(t.code.equals("S00")){
                if(handler!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onSuccess(t);
                        }
                    });
                }
            }else{
                sLog.e(HttpEngine.TAG,"code:"+t.code+"    message:"+t.message);
                if(handler!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onFailed(t.code,t.message);
                        }
                    });
                }
                if(t.code.equals("E204")){
                    BindUserManager.getInstenst().setIsFlushTocken(true).bindService();
                }
            }
        }catch (Exception e){
            sLog.e(HttpEngine.TAG,e.toString());
            if(handler!=null){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onFailed(NetCode.CLASSFORMERROR,"类型转换异常");
                    }
                });
            }
        }
    }
}
