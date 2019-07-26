package com.tinyapp.tinyappplateform.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.z.tinyapp.network.DownLoadCallBack;
import com.z.tinyapp.network.okhttp.OkHttp3Utils;
import com.tinyapp.tinyappplateform.weexapps.AppDirs;
import com.tinyapp.tinyappplateform.weexapps.IDownloadCB;
import com.z.tinyapp.utils.common.AsscetsUtil;
import com.z.tinyapp.utils.common.FileUtil;
import com.z.tinyapp.utils.logs.sLog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AppDownloadService extends Service {
    private static String URL =
            "http://47.97.73.53";
            //"http://192.168.0.104:8080";
    private static Map<Integer, IDownloadCB>  sCBMap= new HashMap<>();

    public static void putCBMap(int hashCode, IDownloadCB dcb) {
        sCBMap.put(hashCode, dcb);
    }
    public static void removeCBMap(int hashCode){
        sCBMap.remove(hashCode);
    }
    public static IDownloadCB getCB(int hashCode){
        return sCBMap.get(hashCode);
    }

    private List<String> downList;

    boolean checkSign(String appName, String packagePath){
        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        downList = new ArrayList<>();
        sLog.i(null, "AppDownloadService: onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sLog.i(null, "AppDownloadService: onStartCommand");
        if (null != intent) {
            final String appName = intent.getStringExtra("appName");
            final String packagePath = intent.getStringExtra("packagePath");
            String url = URL + intent.getStringExtra("url");
            final int cbCode = intent.getIntExtra("cbCode", 0);
            final AppDirs ads = new AppDirs(this, appName);
            final String savePath = ads.getPackageDir(packagePath);

            if (!downList.contains(packagePath + "@" + appName)){
                final IDownloadCB cb = getCB(cbCode);
                OkHttp3Utils.download(url, savePath + "/"+packagePath+"_w.zip", new DownLoadCallBack(){

                    @Override
                    public void onStart() {
                        downList.add(packagePath + "@" + appName);
                    }

                    @Override
                    public void onError(String code, String failureMsg) {

                    }

                    @Override
                    public void onLoadProgress(int progress) {

                    }

                    @Override
                    public void onSuccess() {
                        try {
                            AsscetsUtil.UnZipFolder(savePath + "/"+packagePath+"_w.zip", ads.getRootDir());
                            //if(checkSign(appName, packagePath)){
                                //AsscetsUtil.UnZipFolder(savePath + "/"+packagePath+"/"+appName+".zip", ads.getRootDir());
                                FileUtil.deleteFile(new File(savePath + "/"+packagePath+"_w.zip"));
                                //FileUtil.deleteDir(new File(savePath + "/"+packagePath));
                            //}
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(null != getCB(cbCode))
                            getCB(cbCode).onSuccess("finish");
                        removeCBMap(cbCode);
                        downList.remove(packagePath + "@" + appName);
                    }

                    @Override
                    public void onWarn() {

                    }
                });
            } else {
                if(null != getCB(cbCode))
                    getCB(cbCode).onSuccess("Downloading");
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
