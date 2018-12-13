package plateform.tinyapp.com.tinyappplateform.main;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.SGM226AlertPopupAbs;

import com.tinyapp.common.base.BasePresenter;
import com.tinyapp.network.httputil.HttpConstant;
import com.tinyapp.network.httputil.NetUtils;
import com.tinyapp.network.okhttp.GsonObjectCallback;
import com.tinyapp.tinyappplateform.R;
import com.tinyapp.tinyappplateform.TapApplication;
import com.tinyapp.tinyappplateform.bean.AppListBean;
import com.tinyapp.tinyappplateform.bean.WeexBeanList;
import com.tinyapp.tinyappplateform.bean.WeexUpdateBean;
import com.tinyapp.tinyappplateform.service.DownLoadService;
import com.tinyapp.tinyappplateform.weex.WeexManager;
import com.tinyapp.utils.common.APKVersionCodeUtils;
import com.tinyapp.utils.common.AsscetsUtil;
import com.tinyapp.utils.common.FileUtil;
import com.tinyapp.utils.common.JsonUtil;
import com.tinyapp.utils.common.PrefernceUtil;
import com.tinyapp.utils.common.TextUtil;
import com.tinyapp.utils.common.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Created by zhengfei on 2018/7/20.
 */

public class MainPresent extends BasePresenter<MainView> {
    private List<AppListBean> datas = new ArrayList<>();

    public void initalDatas() {
        datas.clear();
        for (AppListBean item : WeexManager.getInstance().weexList.appList) {
            if (item.isUsed&&item.status!=4&&item.status!=7){
                datas.add(item);
            }
        }
    }

    public List<AppListBean> getWeexDatas() {
        return datas;
    }

    //获取已经授权weex列表
    public void getWeexList() {
        Map<String, Object> map = NetUtils.getBaseMap();
        map.put("assocation", "1");
        httpEngine.postJson(HttpConstant.GETWEEXLIST, map, new GsonObjectCallback<WeexBeanList>() {
            @Override
            public void onSuccess(WeexBeanList weexBeanList) {
                mvpView.showMianDate(weexBeanList);
            }

            @Override
            public void onFailed(String code, String msg) {
                ToastUtil.showNetErrorToast(TapApplication.getInstance());
            }
        });
    }

    public void showPopWindow(final Context context) {
        final SGM226AlertPopupAbs popupCheck = new SGM226AlertPopupAbs(context,
                SGM226AlertPopupAbs.DEFAULT_TYPE_NORMAL_POPUP);
        String messages = context.getString(R.string.whether_jump_to_user_login);
        String leftbtntxt = context.getString(R.string.btn_ok_text);
        popupCheck.setMessage(messages);
        popupCheck.setLeftButton(leftbtntxt, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("com.hmi.users.MainActivity");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                popupCheck.dismiss();
                System.exit(0);
            }
        });
        String rightbtntxt = context.getString(R.string.btn_cancel_text);
        popupCheck.setRightButton(rightbtntxt, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupCheck.dismiss();
                System.exit(0);
            }
        });
        popupCheck.setCloseOnAnyKey(false);
//        popupCheck.setTimeout(5000);
        popupCheck.show();
    }

    //初始化weex
    public void initalWeexData(final Context context) {
        if (!PrefernceUtil.getBoolean("isLoadWeex", false)) {
            boolean isSuccess = false;
            String sdPath = FileUtil.getFilePath(context);
            String filePath = sdPath + "/weexapps.zip";
            isSuccess = AsscetsUtil.copyFilesFromAssets(context, "weexapps.zip", filePath);
            if (isSuccess) {
                try {
                    AsscetsUtil.UnZipFolder(filePath, sdPath);
                    FileUtil.deleteFile(sdPath, "weexapps.zip");
                    PrefernceUtil.putBoolean("isLoadWeex", true);
                    initalWeex(context);
                } catch (Exception e) {
                    e.printStackTrace();
                    mvpView.initalWeexBack(false);
                }
            }
            mvpView.initalWeexBack(isSuccess);
        } else {
            initalWeex(context);
            mvpView.initalWeexBack(true);
        }
    }

    private void initalWeex(Context context) {
        String weexConfig = PrefernceUtil.getString("config_weex");
        if (TextUtils.isEmpty(weexConfig)) {
            weexConfig = context.getString(R.string.weexinitaltext);
            WeexManager.getInstance().weexList = JsonUtil.parseObject(weexConfig, WeexBeanList.class);
            PrefernceUtil.putString("config_weex", weexConfig);
        }else{
            WeexManager.getInstance().weexList = JsonUtil.parseObject(weexConfig, WeexBeanList.class);
            List<AppListBean> list=new ArrayList<>();
            int size=WeexManager.getInstance().weexList.appList.size();
            for (int i=0;i<size;i++){
                AppListBean bean=WeexManager.getInstance().weexList.appList.get(i);
                Log.i("zf2","status:"+bean.status);
                switch (bean.status){
                    case 0:
                        break;
                    case 1:
                        WeexManager.getInstance().weexList.appList.get(i).status=0;
                        break;
                    case 2:
                        String zipfilePath=FileUtil.getSrcFilePath(context,bean.name);
                        String srcfilePath=FileUtil.getAbsolutePath(context);
                        try {
                            AsscetsUtil.UnZipFolder(zipfilePath, srcfilePath);
                            FileUtil.deleteFile(new File(zipfilePath));
                            String fileJson= FileUtil.getDatafromFile(srcfilePath+"/"+bean.name+"/updateFile.json");
                            if(!TextUtil.isEmpty(fileJson)){
                                JSONObject object=new JSONObject(fileJson);
                                JSONArray array= (JSONArray) object.get("delete");
                                for (int j=0;j<array.length();j++) {
                                    String deleFilePath=srcfilePath+"/"+ array.get(j);
                                    FileUtil.deleteFile(new File(deleFilePath));
                                }
                            }
                            AppListBean bean1=JsonUtil.parseObject(PrefernceUtil.getString(bean.name), AppListBean.class);
                            if(bean1!=null){
                                WeexManager.getInstance().weexList.appList.remove(bean);
                                WeexManager.getInstance().weexList.appList.add(i,bean1);
                            }
                            WeexManager.getInstance().weexList.appList.get(i).status=0;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        FileUtil.deleteFolder(new File(FileUtil.getAbsolutePath(context)+"/"+bean.name));
                        list.add(bean);
                        break;
                    case 4:
                        list.add(bean);
                        break;
                    case 5:
                    case 6:
                        Log.i("zf","getUpdate:"+bean.name);
                        getUpdate(context,bean,false);
                        WeexManager.getInstance().weexList.appList.get(i).status=6;
                        break;
                    case 7:
                        bean.status=4;
                        Intent intent=new Intent(context, DownLoadService.class);
                        intent.putExtra(DownLoadService.INTENTTAG,DownLoadService.CARD);
                        intent.putExtra("appBean",bean);
                        context.startService(intent);
                        break;
                }
            }
            WeexManager.getInstance().weexList.appList.removeAll(list);
            WeexManager.getInstance().setToPrefernce();
        }
    }

    public void checkPackage(WeexBeanList weexBeanList,Context context) {
        int addSize=0;
        List<AppListBean> list = new ArrayList<>();
        if(weexBeanList.appList==null){
            weexBeanList.appList=new ArrayList<>();
        }
        //排序  更新  下载
        for (AppListBean bean : weexBeanList.appList) {
            boolean isFind = false;
            for (AppListBean bean1 : WeexManager.getInstance().weexList.appList) {
                if (bean.guid .equals(bean1.guid) ) {
                    bean.isUsed=bean1.isUsed;
                    if (APKVersionCodeUtils.compareVersion(bean1.version, bean.version) < 0) {
                        list.add(bean1);
                        bean.status = 1;//正在更新
                        getUpdate(context, bean1,true);
                        String str=JsonUtil.toJSONString(bean);
                        PrefernceUtil.putString(bean.name,str);
                    }else{
                        list.add(bean);
                    }
                    isFind = true;
                    break;
                }
            }
            if (!isFind) {
                bean.status = 4;//正在下载卡片包
                bean.isUsed=true;
                addSize++;
                list.add(bean);
                Intent intent=new Intent(context, DownLoadService.class);
                intent.putExtra(DownLoadService.INTENTTAG,DownLoadService.CARD);
                intent.putExtra("appBean",bean);
                context.startService(intent);
            }
        }
        //删除
        if (list.size()-addSize != WeexManager.getInstance().weexList.appList.size()) {
            for (AppListBean bean : WeexManager.getInstance().weexList.appList) {
                boolean isFind = false;
                for (AppListBean bean1 : weexBeanList.appList) {
                    if (bean.guid .equals(bean1.guid) ) {
                        isFind = true;
                        continue;
                    }
                }
                if (!isFind) {
                    bean.status = 3;//即将删除
                    list.add(bean);
                }
            }
        }
        WeexManager.getInstance().weexList.appList.clear();
        WeexManager.getInstance().weexList.appList.addAll(list);
        WeexManager.weexManager.setToPrefernce();
    }

    /**
     * 获取主信息文件(更新信息文件)
     */
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
                if(baseJsonBean.appList!=null&&baseJsonBean.appList.size()>0){
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
