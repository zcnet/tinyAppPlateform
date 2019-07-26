package com.z.tinyapp.network.pki2;

import android.os.Handler;
import android.os.UserManager;
import android.util.Log;

import com.netbirdtech.libcurl.CurlHttp;
import com.netbirdtech.libcurl.CurlResult;
import com.z.tinyapp.network.BaseJsonBean;
import com.z.tinyapp.network.ResponseCallBack;
import com.z.tinyapp.utils.common.JsonUtil;
import com.z.tinyapp.utils.thred.ThreadPoolUtil;

import java.util.Map;


/**
 * Created by zhengfei on 2018/8/13.
 */

public  class PKI2Utils {
    private static final long DEFAULT_TIMEOUT = 20000;
    private static PKI2Utils pki2Utils = null;
    private static Handler mHandler = null;
    public static PKI2Utils getInstance(){
        if(pki2Utils==null){
            synchronized (PKI2Utils.class){
                if(pki2Utils==null){
                    pki2Utils=new PKI2Utils();
                }
            }
        }
        return pki2Utils;
    }
    public PKI2Utils(){
        if (mHandler == null) {
            mHandler = new Handler();
        }

    }

    public Handler getmHandler(){
        return mHandler;
    }
    private CurlHttp getCurrHttp(){
        CurlHttp curlHttp=new CurlHttp();
        curlHttp.setTimeoutMillis(DEFAULT_TIMEOUT);
        curlHttp.setConnectionTimeoutMillis(DEFAULT_TIMEOUT);
        return curlHttp;
    }
    public <T extends BaseJsonBean> void doGet(final String url, final Map<String,String> params, final ResponseCallBack<T> callBack){
        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                CurlHttp curlHttp= getCurrHttp();
                if(params!=null){
                    for (String key : params.keySet()) {
                        curlHttp.addParam(key, params.get(key));
                    }
                }
                CurlResult result=curlHttp.getUrl(url).setSSLContext().perform();
                callBack.onResponse(result);
            }
        });
    }
    public <T extends BaseJsonBean> void doPost(final String url, final Map<String,String> params, final ResponseCallBack<T> callBack){
        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                CurlHttp curlHttp= getCurrHttp();
                curlHttp.addHeader("Content-Type", "multipart/form-data");
                if(params!=null){
                    for (String key : params.keySet()) {
                        curlHttp.addParam(key, params.get(key));
                    }
                }
                CurlResult result=curlHttp.postUrl(url).setSSLContext().perform();
                callBack.onResponse(result);
            }
        });
    }
    public void doPostJson(final String url, final Map<String,String> params, final ResponseCallBack callBack){
        final byte[] jsonParams= JsonUtil.mapToByte(params);
        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                CurlHttp curlHttp= getCurrHttp();
                curlHttp.setBody("application/json",jsonParams);
                CurlResult result=curlHttp.postUrl(url).setSSLContext().perform();
                callBack.onResponse(result);
            }
        });
    }
}
