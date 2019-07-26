package com.z.tinyapp.network;

import android.content.Context;

import com.liulishuo.filedownloader.FileDownloader;
import com.z.tinyapp.network.okhttp.GsonObjectCallback;
import com.z.tinyapp.network.okhttp.OkHttp3Utils;
import com.z.tinyapp.network.pki2.PKI2Utils;

import java.io.File;
import java.util.Map;


public class HttpEngine {

    public static final String TAG = "net_travelgroup";

    private Context context;

    public void setContext(Context context) {
        this.context = context;
        FileDownloader.setup(context);
    }
    public HttpEngine() {
        OkHttp3Utils.getInstance().getHandler();
    }
    public HttpEngine(Context context) {
        this.context = context;
    }

    public  void post(String url, Map<String, String> param, GsonObjectCallback callback) {
        OkHttp3Utils.doPost(url,param, callback);
    }

    public void postJson(final String url, Map<String, Object> param, GsonObjectCallback callback) {
        OkHttp3Utils.doPostJson(url,param,callback);
    }
    public void postJsonObject(final String url, String param, GsonObjectCallback callback) {
        OkHttp3Utils.doPostJsonObject(url,param,callback);
    }

    public void get(String url, GsonObjectCallback callback) {
        OkHttp3Utils.doGet(url,callback);
    }

    public void download(String url,String saveDir,DownLoadCallBack callBack){
        OkHttp3Utils.download(url,saveDir,callBack);
    }

    public  void postKPI(String url, Map<String, String> param, ResponseCallBack callback) {
        PKI2Utils.getInstance().doPost(url,param, callback);
    }

    public void postJsonKPI(final String url, Map<String, String> param, ResponseCallBack callback) {
        PKI2Utils.getInstance().doPostJson(url,param,callback);
    }

    public void getKPI(String url, ResponseCallBack callback) {
        PKI2Utils.getInstance().doGet(url,null,callback);
    }

    public void uploadFile(String url,File file,Map<String,Object> param,GsonObjectCallback callback){
        OkHttp3Utils.uploadFile(url,file,param,callback);
    }
    public void downloadwithParam(final String url,String params, final String saveDir, final DownLoadCallBack callBack){
        OkHttp3Utils.downloadwithParam(url,params,saveDir,callBack);
    }
}
