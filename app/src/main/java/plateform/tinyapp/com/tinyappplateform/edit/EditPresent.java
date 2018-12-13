package plateform.tinyapp.com.tinyappplateform.edit;

import android.content.Context;
import android.content.Intent;

import com.tinyapp.common.base.BasePresenter;
import com.tinyapp.network.BaseJsonBean;
import com.tinyapp.network.httputil.HttpConstant;
import com.tinyapp.network.httputil.NetUtils;
import com.tinyapp.network.okhttp.GsonObjectCallback;
import com.tinyapp.tinyappplateform.TapApplication;
import com.tinyapp.tinyappplateform.bean.AppListBean;
import com.tinyapp.tinyappplateform.bean.WeexBeanList;
import com.tinyapp.tinyappplateform.bean.WeexUpdateBean;
import com.tinyapp.tinyappplateform.service.DownLoadService;
import com.tinyapp.tinyappplateform.weex.WeexManager;
import com.tinyapp.utils.common.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhengfei on 2018/8/9.
 */

public class EditPresent extends BasePresenter<EditView> {

    private List<AppListBean> datas=new ArrayList<>();

    /**
     * 获取所有list
     */
    public void getWeexList(){
        datas.clear();
        datas.addAll(WeexManager.getInstance().weexList.appList);
        getWeexAssociate();
    }

    public List<AppListBean> getWeexDatas(){
        return datas;
    }
    /**
     * 获取未授权的list
     */
    private void  getWeexAssociate(){
        Map<String, Object> map = NetUtils.getBaseMap();
        map.put("assocation", "0");
        httpEngine.postJson(HttpConstant.GETWEEXLIST, map, new GsonObjectCallback<WeexBeanList>() {
            @Override
            public void onSuccess(WeexBeanList weexBeanList) {
                if(weexBeanList!=null&&weexBeanList.appList!=null) {
                    datas.addAll(weexBeanList.appList);
                }
                mvpView.showMainData();
            }

            @Override
            public void onFailed(String code, String msg) {
                ToastUtil.showNetErrorToast(TapApplication.getInstance());
                mvpView.showMainData();
            }
        });
    }

    /**
     * 应用授权
     * @param context
     * @param guid
     */
    public void associationGet(final Context context, final String guid){
        JSONObject object=null;
        try {
            JSONArray object1=new JSONArray();
            object1.put(new JSONObject().put("guid",guid));
            object=new JSONObject();
            object.put("accessToken","dmVoaWNsZTAwMDIyMTUyNzY1NDAxMTI3MA");
            object.put("appInfoList",object1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpEngine.postJsonObject(HttpConstant.USERASSOCIATION, object.toString(), new GsonObjectCallback<BaseJsonBean>() {
            @Override
            public void onSuccess(BaseJsonBean baseJsonBean) {
                for (AppListBean bean:datas) {
                    if(bean.guid.equals(guid)){
                        bean.isAssociate=1;
                        bean.status=4;
                        bean.isUsed=true;
                        WeexManager.weexManager.weexList.appList.add(bean);
                        Intent intent=new Intent(context, DownLoadService.class);
                        intent.putExtra(DownLoadService.INTENTTAG,DownLoadService.EDIT);
                        intent.putExtra("appBean",bean);
                        context.startService(intent);
                        break;
                    }
                }
            }

            @Override
            public void onFailed(String code, String msg) {
                mvpView.associationFild(guid);
            }
        });
    }

    public void getUpdate(final Context context, final AppListBean bean, final boolean isVersion){
        Map<String,Object> map= NetUtils.getBaseMap1();
        map.put("guid",bean.guid);
        if(isVersion){
            map.put("appVersion",bean.version);
        }
        map.put("keyGuid",bean.keyGuid);
        final String backGuid= UUID.randomUUID().toString();
        map.put("backGuid", backGuid);
        httpEngine.postJson(HttpConstant.GETMAINFILE, map, new GsonObjectCallback<WeexUpdateBean>() {
            @Override
            public void onSuccess(WeexUpdateBean baseJsonBean) {
                if(baseJsonBean.appList.size()>0){
                    Intent intent=new Intent(context,DownLoadService.class);
                    intent.putExtra("appBean",baseJsonBean.appList.get(0));
                    intent.putExtra("backGuid",backGuid);
                    if(isVersion){//更新
                        intent.putExtra(DownLoadService.INTENTTAG,DownLoadService.UPDATE);
                    }else{
                        intent.putExtra(DownLoadService.INTENTTAG,DownLoadService.MAIN);
                    }
                    context.startService(intent);
                }
            }

            @Override
            public void onFailed(String code, String msg) {
            }
        });
    }

}
