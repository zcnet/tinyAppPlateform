package com.z.tinyapp.network;

import android.os.Handler;

import com.google.gson.Gson;
import com.netbirdtech.libcurl.CurlResult;
import com.z.tinyapp.network.pki2.PKI2Utils;
import com.z.tinyapp.utils.common.TextUtil;
import com.z.tinyapp.utils.logs.sLog;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by zhengfei on 2018/8/13.
 */

public abstract class ResponseCallBack<T extends BaseJsonBean> {
    public abstract void onStart();

    public abstract void onError(String code, String failureMsg);

    public abstract void onSuccess(T response);


    public void onResponse(final CurlResult result){
        try{
            Handler handler= PKI2Utils.getInstance().getmHandler();
            final int status=result.getStatus();
            if(status==0){//请求成功
                String binaryStr="";
                try {
                    binaryStr = new String(result.getBody(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if(TextUtil.isEmpty(binaryStr)){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onError(NetCode.DATASEMPTY,"获取数据为空");
                        }
                    });
                    return;
                }
                Class<T> cls = null;
                Class clz = this.getClass();
                ParameterizedType type = (ParameterizedType) clz.getGenericSuperclass();
                Type[] types = type.getActualTypeArguments();
                cls = (Class<T>) types[0];
                Gson gson = new Gson();
                final T t = gson.fromJson(binaryStr, cls);
                onSuccess(t);
            }else{//请求失败
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onError(status+"",result.getStatusLine());
                    }
                });
            }
        }catch (Exception e){
            sLog.e(HttpEngine.TAG,e.toString());
        }

    }
}
