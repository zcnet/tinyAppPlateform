package com.tinyapp.tinyappplateform.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.z.tinyapp.network.BaseJsonBean;
import com.z.tinyapp.network.httputil.HttpConstant;
import com.z.tinyapp.network.okhttp.GsonObjectCallback;
import com.z.tinyapp.network.okhttp.OkHttp3Utils;
import com.z.tinyapp.utils.logs.sLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhengfei on 2018/8/23.
 */

public class UpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JSONObject object=null;
        try {
            object=new JSONObject();
            object.put("accessToken","dmVoaWNsZTAwMDIyMTUyNzY1NDAxMTI3MA");
            int index=0;
            JSONArray object1=new JSONArray();
            object.put("appInfoList",object1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttp3Utils.doPostJsonObject(HttpConstant.UPDATEPOS, object.toString(), new GsonObjectCallback<BaseJsonBean>() {
            @Override
            public void onSuccess(BaseJsonBean baseJsonBean) {
                sLog.i("zf","onSuccess_update");
            }

            @Override
            public void onFailed(String code, String msg) {
                sLog.i("zf","onFailed_update:"+msg);
                UpdateService.this.stopSelf();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }
}
