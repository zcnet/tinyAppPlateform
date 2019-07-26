package com.tinyapp.tinyappplateform.weexapps;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.google.gson.Gson;
import com.z.tinyapp.network.HttpEngine;
import com.z.tinyapp.network.httputil.HttpConstant;
import com.z.tinyapp.network.httputil.NetUtils;
import com.z.tinyapp.network.okhttp.GsonObjectCallback;
import com.tinyapp.tinyappplateform.bean.AppBean;
import com.tinyapp.tinyappplateform.bean.AppInfoBean;
import com.tinyapp.tinyappplateform.bean.Card;
import com.tinyapp.tinyappplateform.bean.InfoFlowCard;
import com.tinyapp.tinyappplateform.bean.RemoteAppListBean;
import com.tinyapp.tinyappplateform.bean.VRCard;
import com.tinyapp.tinyappplateform.bean.WeexUpdateBean;
import com.tinyapp.tinyappplateform.broadcastreceiver.BroadCastConstant;
import com.tinyapp.tinyappplateform.service.AppDownloadService;
import com.z.tinyapp.utils.common.FileUtil;
import com.z.tinyapp.utils.common.JsonUtil;
import com.z.tinyapp.utils.common.WeexApps;
import com.z.tinyapp.utils.logs.sLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class AppListMgr {

    private List<AppBean> appList;
    private Context context;
    private Handler handler = null;

    protected HttpEngine httpEngine = new HttpEngine();

    public Card findSmallCard(String appName, String smallCardName){
        if (null == appList)
            return null;
        for(AppBean ab: appList){
            if (appName.equals(ab.name)){
                if (null != ab.appInfo && null != ab.appInfo.appcard && null != ab.appInfo.appcard.smallcard){
                    int i = 0;
                    for (Card c: ab.appInfo.appcard.smallcard){
                        if (smallCardName.equals(c.name)){
                            if(null != c.path && c.path.length()>0){
                                return c;
                            }
                            break;
                        }
                        i ++;
                    }
                    break;
                }
            }
        }
        return null;
    }

    public Card findBigCard(String appName, String bigCardName) {
        if (null == appList)
            return null;
        for(AppBean ab: appList){
            if (appName.equals(ab.name)){
                if (null != ab.appInfo && null != ab.appInfo.appcard && null != ab.appInfo.appcard.bigcard){
                    int i = 0;
                    for (Card c: ab.appInfo.appcard.bigcard){
                        if (bigCardName.equals(c.name)){
                            if(null != c.path && c.path.length()>0){
                                return c;
                            }
                            break;
                        }
                        i ++;
                    }
                    break;
                }
            }
        }
        return null;
    }

    public InfoFlowItem findInfoFlow(String appName, String infoFlowCardName){
        if (null == appList)
            return null;
        for(AppBean ab: appList){
            if (appName.equals(ab.name)){
                if (null != ab.appInfo && null != ab.appInfo.infoflowcard){
                    int i = 0;
                    for (InfoFlowCard c: ab.appInfo.infoflowcard){
                        if (infoFlowCardName.equals(c.name)){
                            if(null != c.path && c.path.length()>0){
                                InfoFlowItem ifi = new InfoFlowItem();
                                ifi.setAppInfoBean(ab.appInfo);
                                ifi.setIndex(i);
                                return ifi;
                            }
                            break;
                        }
                        i ++;
                    }
                    break;
                }
            }
        }
        return null;
    }

    public InfoFlowItem findInfoFlow(String weexId){
        if (appList == null) return null;
        for(AppBean app: appList) {
            AppInfoBean ib = app.appInfo;
            if (null != ib && ib.infoflowcard != null) {
                int i = 0;
                for (InfoFlowCard ifc: ib.infoflowcard) {
                    if (weexId.equals(ifc.guid)){
                        InfoFlowItem ifi = new InfoFlowItem();
                        ifi.setAppInfoBean(ib);
                        ifi.setIndex(i);
                        return ifi;
                    }
                    i ++;
                }
            }
        }
        return null;
    }

    public List<AppBean> getAppList() {
        return appList;
    }

    public void setAppList(List<AppBean> appList) {
        this.appList = appList;
    }

    public void init(final Context context){
        this.context = context;
        fromFile(context, "/applist.json");

        WeexApps.setAppListMgr(new WeexApps.IAppListMgr() {
            @Override
            public String findVRCardPath(String appName, String vrCardName) {
                VRCard c = AppListMgr.this.findVRCard(appName, vrCardName);
                if (null == c)
                    return null;
                return new AppDirs(context, "__").getRootDir() +c.path;
            }
        });
    }

    private VRCard findVRCard(String appName, String vrCardName) {
        if (null == appList)
            return null;
        for(AppBean ab: appList){
            if (appName.equals(ab.name)){
                if (null != ab.appInfo && null != ab.appInfo.vrcard){
                    int i = 0;
                    for (VRCard c: ab.appInfo.vrcard){
                        if (vrCardName.equals(c.name)){
                            if(null != c.path && c.path.length()>0){
                                return c;
                            }
                            break;
                        }
                        i ++;
                    }
                    break;
                }
            }
        }
        return null;
    }

    public void fromFile(Context context, String pathname){
        this.appList = JsonUtil.parseList(Utils.getJson(context, pathname), AppBean.class);
        if (this.appList == null) {
            this.appList = new ArrayList<>();
        }
        genAppsInfo(context);
    }

    public AppBean find(String appName){
        return findIn(appList, appName);
    }

    public void getUserRemoteAppList(String associate, IRemoteAppListCB cb){
        getRemoteAppListImpl(associate, null, cb);
    }

    public void getUserRemoteAppList(String associate, String appName, IRemoteAppListCB cb){
        getRemoteAppListImpl(associate, appName, cb);
    }

    public void addAppToList(AppBean ab){
        getAppList().add(ab);
    }

    public void updateAndInstallForUser(List<AppBean> appList){
        updateAndInstall(appList, new IUpdateAndInstallCB(){
            @Override
            public void onSuccess() {
                updateAndInstall(null);
            }
        });
    }

    public void updateAndInstall(final IUpdateAndInstallCB cb){
        updateAndInstall(appList, new IUpdateAndInstallCB(){
            @Override
            public void onSuccess() {
                if (null != cb) {
                    cb.onSuccess();
                }
                Gson gson2=new Gson();
                String str=gson2.toJson(appList);
                try {
                    FileUtil.writeText(new AppDirs(context,"--").getRootDir(), "applist.json", str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                context.sendBroadcast(new Intent().setAction(BroadCastConstant.MAINCARDDOWN).putExtra("weexid", "xxx").putExtra("success",true));
            }
        });
    }

    public void updateAndInstall(List<AppBean> appList, final IUpdateAndInstallCB cb){
        if (null == appList || appList.size() == 0) {
            cb.onSuccess();
            return;
        }
        final Counter counter = new Counter(appList.size());
        for (AppBean b: appList) {
            if (b.needInstall()) {
                final AppBean bb = b;
                downloadAndInstall(b, new IUpdateAndInstallCB(){

                    @Override
                    public void onSuccess() {
                        bb.setInstalled(true);
                        counter.subsub();
                        if (counter.get() == 0 && null != cb){
                            cb.onSuccess();
                        }

                    }
                });
            }else {
                counter.subsub();
                if (counter.get() == 0 && null != cb){
                    cb.onSuccess();
                }
            }
        }
    }

    private void downloadAndInstall(AppBean app,final IUpdateAndInstallCB cb){
        new AppDirs(context, app.name).checkAppDirs();
        final Counter counter = new Counter(5);
        downloadInstallAppCards(app, new IUpdateAndInstallCB(){

            @Override
            public void onSuccess() {
                counter.subsub();
                if (counter.get()==1 && null != cb){
                    cb.onSuccess();
                }
            }
        });
        downloadInstallPackage(app, new IUpdateAndInstallCB(){

            @Override
            public void onSuccess() {
                counter.subsub();
                if (counter.get()==1 && null != cb){
                    cb.onSuccess();
                }
            }
        });
        downloadInstallInfoflowcards(app, new IUpdateAndInstallCB(){

            @Override
            public void onSuccess() {
                counter.subsub();
                if (counter.get()==1 && null != cb){
                    cb.onSuccess();
                }
            }
        });
        downloadInstallShowvrcar(app, new IUpdateAndInstallCB(){

            @Override
            public void onSuccess() {
                counter.subsub();
                if (counter.get()==1 && null != cb){
                    cb.onSuccess();
                }
            }
        });/**/
    }


    public static AppBean findIn(List<AppBean> appList, String name) {
        if (null == appList) return null;
        for (AppBean ab: appList){
            if (ab.name.equals(name)) {
                return ab;
            }
        }
        return null;
    }

    private void downloadInstallAppCards(AppBean app, final IUpdateAndInstallCB cb){
        String url = app!=null ? app.appCardURL:null;
        if (null == url) return;
        downloadInService(app.name, "WeexCard", url, new IDownloadCB() {
            @Override
            public void onSuccess(String status) {
                if ("Downloading".equals(status)){
                    sLog.i(null, "downloadInstallAppCards is downloading");
                } else {
                    sLog.i(null, "downloadInstallAppCards is finish");
                }
                if (null != cb)
                    cb.onSuccess();
            }
        });
    }

    private void downloadInstallPackage(AppBean app, final IUpdateAndInstallCB cb){
        String url = app.showPackageURL;
        downloadInService(app.name, "WeexMain", url, new IDownloadCB() {
            @Override
            public void onSuccess(String status) {
                if ("Downloading".equals(status)){
                    sLog.i(null, "downloadInstallPackage is downloading");
                } else {
                    sLog.i(null, "downloadInstallPackage is finish");
                }
                if (null != cb)
                    cb.onSuccess();
            }
        });
    }
    private void downloadInstallInfoflowcards(AppBean app, final IUpdateAndInstallCB cb){
        String url = app.showInfoFlowCardsURL;
        downloadInService(app.name, "WeexInfoFlowCard", url, new IDownloadCB() {
            @Override
            public void onSuccess(String status) {
                if ("Downloading".equals(status)){
                    sLog.i(null, "downloadInstallInfoflowcards is downloading");
                } else {
                    sLog.i(null, "downloadInstallInfoflowcards is finish");
                }
                if (null != cb)
                    cb.onSuccess();
            }
        });
    }
    private void downloadInstallShowvrcar(AppBean app, final IUpdateAndInstallCB cb){
        String url = app.showVRCarURL;
        downloadInService(app.name, "WeexVRCard", url, new IDownloadCB() {
            @Override
            public void onSuccess(String status) {
                if ("Downloading".equals(status)){
                    sLog.i(null, "downloadInstallShowvrcar is downloading");
                } else {
                    sLog.i(null, "downloadInstallShowvrcar is finish");
                }
                if (null != cb)
                    cb.onSuccess();
            }
        });
    }


    private void downloadInService(final String appName, final String packagePath, final String url, final IDownloadCB cb){
        //new android.os.Handler().post(
        new Thread(
                new Runnable() {
            @Override
            public void run() {
                AppDownloadService.putCBMap(cb.hashCode(), cb);
                sLog.i(null, "AppListMgr.init,start service");
                Intent intent=new Intent(context, AppDownloadService.class);
                intent.putExtra("appName", appName);
                intent.putExtra("packagePath", packagePath);
                intent.putExtra("url", url);
                intent.putExtra("cbCode", cb.hashCode());
                context.startService(intent);
            }
        }
        ).start();
        //);
    }

    private void genAppsInfo(Context context){
        if (this.appList == null) return;
        for (AppBean app:this.appList){
            app.setInstalled(true);
            sLog.i(null, "AndroidRuntime: genAppsInfo:"+ app.name);
            app.appInfo = (new Gson().fromJson(Utils.getJson(context, "/"+app.name+"/config.json"), AppInfoBean.class));
        }
    }

    /**
     * 获取主信息文件(更新信息文件)
     */
    public void getUpdateImpl(final AppBean bean, final boolean isVersion){
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
                    bean.showPackageURL = baseJsonBean.appList.get(0).showPackageURL;
                }
            }

            @Override
            public void onFailed(String code, String msg) {
            }
        });
    }

    private void getAllPackageURL(){
        if (null != appList && appList.size() > 0) {
            for (AppBean app : appList) {
                getUpdateImpl(app, true);
            }
        }
    }

    /**
     * 获取未授权的list
     */
    private void  getRemoteAppListImpl(String assocation, String appName, final IRemoteAppListCB cb){
        Map<String, Object> map = NetUtils.getBaseMap();
        if (null != assocation && !assocation.isEmpty()){
            switch (assocation) {
                case "yes":
                    map.put("assocation", "1");
                    break;
                case "no":
                    map.put("assocation", "0");
                    break;
                default:
                    break;
            }
        }

        if (null != appName && !appName.isEmpty())
            map.put("appName", appName);
        //map.put("accessToken", AccountUtil.getToken());
        httpEngine.postJson(HttpConstant.GETWEEXLIST, map, new GsonObjectCallback<RemoteAppListBean>() {

            @Override
            public void onSuccess(RemoteAppListBean remoteAppListBean) {
                //cb.onSuccess(remoteAppListBean.appList);
                cb.onSuccess(new ArrayList<AppBean>());
            }

            @Override
            public void onFailed(String code, String msg) {
                sLog.e(null, "GETWEEXLIST error:" + code +" msg:" + msg);
                if("513xx".equals(code)) {
                    String json = "[\n" +
                            "    {\n" +
                            "        \"id\": 54,\n" +
                            "        \"name\": \"weex_app1\",\n" +
                            "        \"friendlyName\": \"weex_app1\",\n" +
                            "        \"guid\": \"49204EB5-2BD9-498C-A9F1-2F4C3F63576240\",\n" +
                            "        \"version\": \"0.0.0.1\",\n" +
                            "        \"appIcon\": \"/zeninte/app/icon/app1.jpg\",\n" +
                            "        \"appLevel\": \"system\",\n" +
                            "        \"appType\": \"other\",\n" +
                            "        \"showInfoFlowCardsURL\": \"/zeninte/app/apps/app1/WeexInfoFlowCard/weex_app1.zip\",\n" +
                            "        \"showVRCarURL\": \"/zeninte/app/apps/app1/WeexVRCard/weex_app1.zip\",\n" +
                            "        \"keyGuid\": \"0000000000000001\",\n" +
                            "        \"visiable\": null,\n" +
                            "        \"isAssociate\": null,\n" +
                            "        \"intro\": \"weex_app1\",\n" +
                            "        \"appCard\": {\n" +
                            "            \"width\": 750,\n" +
                            "            \"url\": \"/zeninte/app/apps/app1/WeexCard/weex_app1.zip\"\n" +
                            "        }\n" +
                            "    },\n" +
                            "    {\n" +
                            "        \"id\": 55,\n" +
                            "        \"name\": \"weex_app2\",\n" +
                            "        \"friendlyName\": \"weex_app2\",\n" +
                            "        \"guid\": \"49204EB5-2BD9-498C-A9F1-2F4C3F63576241\",\n" +
                            "        \"version\": \"0.0.0.1\",\n" +
                            "        \"appIcon\": \"/zeninte/app/icon/app2.jpg\",\n" +
                            "        \"appLevel\": \"system\",\n" +
                            "        \"appType\": \"other\",\n" +
                            "        \"showInfoFlowCardsURL\": \"/zeninte/app/apps/app1/WeexInfoFlowCard/weex_app2.zip\",\n" +
                            "        \"showVRCarURL\": \"/zeninte/app/apps/app1/WeexVRCard/weex_app2.zip\",\n" +
                            "        \"keyGuid\": \"0000000000000001\",\n" +
                            "        \"visiable\": null,\n" +
                            "        \"isAssociate\": null,\n" +
                            "        \"intro\": \"weex_app2\",\n" +
                            "        \"appCard\": {\n" +
                            "            \"width\": 750,\n" +
                            "            \"url\": \"/zeninte/app/apps/app2/WeexCard/weex_app2.zip\"\n" +
                            "        }\n" +
                            "    },\n" +
                            "    {\n" +
                            "        \"id\": 56,\n" +
                            "        \"name\": \"weex_app3\",\n" +
                            "        \"friendlyName\": \"weex_app3\",\n" +
                            "        \"guid\": \"49204EB5-2BD9-498C-A9F1-2F4C3F63576242\",\n" +
                            "        \"version\": \"0.0.0.1\",\n" +
                            "        \"appIcon\": \"/zeninte/app/icon/app3.jpg\",\n" +
                            "        \"appLevel\": \"system\",\n" +
                            "        \"appType\": \"other\",\n" +
                            "        \"showInfoFlowCardsURL\": \"/zeninte/app/apps/app1/WeexInfoFlowCard/weex_app3.zip\",\n" +
                            "        \"showVRCarURL\": \"/zeninte/app/apps/app1/WeexVRCard/weex_app3.zip\",\n" +
                            "        \"keyGuid\": \"0000000000000001\",\n" +
                            "        \"visiable\": null,\n" +
                            "        \"isAssociate\": null,\n" +
                            "        \"intro\": \"weex_app3\",\n" +
                            "        \"appCard\": {\n" +
                            "            \"width\": 750,\n" +
                            "            \"url\": \"/zeninte/app/apps/app3/WeexCard/weex_app3.zip\"\n" +
                            "        }\n" +
                            "    },\n" +
                            "    {\n" +
                            "        \"id\": 57,\n" +
                            "        \"name\": \"weex_app4\",\n" +
                            "        \"friendlyName\": \"weex_app4\",\n" +
                            "        \"guid\": \"49204EB5-2BD9-498C-A9F1-2F4C3F63576243\",\n" +
                            "        \"version\": \"0.0.0.1\",\n" +
                            "        \"appIcon\": \"/zeninte/app/icon/app4.jpg\",\n" +
                            "        \"appLevel\": \"system\",\n" +
                            "        \"appType\": \"other\",\n" +
                            "        \"showInfoFlowCardsURL\": \"/zeninte/app/apps/app1/WeexInfoFlowCard/weex_app4.zip\",\n" +
                            "        \"showVRCarURL\": \"/zeninte/app/apps/app1/WeexVRCard/weex_app4.zip\",\n" +
                            "        \"keyGuid\": \"0000000000000001\",\n" +
                            "        \"visiable\": null,\n" +
                            "        \"isAssociate\": null,\n" +
                            "        \"intro\": \"weex_app4\",\n" +
                            "        \"appCard\": {\n" +
                            "            \"width\": 750,\n" +
                            "            \"url\": \"/zeninte/app/apps/app4/WeexCard/weex_app4.zip\"\n" +
                            "        }\n" +
                            "    }\n" +
                            "]";

                List<AppBean> b = JsonUtil.parseList(json, AppBean.class);
                cb.onSuccess(b);//new ArrayList<AppBean>()
                }
            }
        });
    }

    private AppListMgr(){}
    private static AppListMgr sInst = new AppListMgr();
    public static AppListMgr getInstance(){return sInst;}


}
