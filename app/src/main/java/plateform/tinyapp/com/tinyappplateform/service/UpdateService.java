package plateform.tinyapp.com.tinyappplateform.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tinyapp.network.BaseJsonBean;
import com.tinyapp.network.httputil.HttpConstant;
import com.tinyapp.network.okhttp.GsonObjectCallback;
import com.tinyapp.network.okhttp.OkHttp3Utils;
import com.tinyapp.tinyappplateform.bean.AppListBean;
import com.tinyapp.tinyappplateform.weex.WeexManager;
import com.tinyapp.utils.common.JsonUtil;
import com.tinyapp.utils.common.PrefernceUtil;

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
            for (AppListBean bean: WeexManager.getInstance().weexList.appList){
                object1.put(new JSONObject().put("guid",bean.guid).put("appIndex",index++));
            }
            object.put("appInfoList",object1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttp3Utils.doPostJsonObject(HttpConstant.UPDATEPOS, object.toString(), new GsonObjectCallback<BaseJsonBean>() {
            @Override
            public void onSuccess(BaseJsonBean baseJsonBean) {
                Log.i("zf","onSuccess_update");
                PrefernceUtil.putString("config_weex", JsonUtil.toJSONString(WeexManager.getInstance().weexList));
                UpdateService.this.stopSelf();
            }

            @Override
            public void onFailed(String code, String msg) {
                Log.i("zf","onFailed_update:"+msg);
                UpdateService.this.stopSelf();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }
}
