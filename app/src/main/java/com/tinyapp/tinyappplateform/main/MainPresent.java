package com.tinyapp.tinyappplateform.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.SGMAlertPopupAbs;

import com.z.tinyapp.common.base.BasePresenter;
import com.z.tinyapp.network.httputil.HttpConstant;
import com.z.tinyapp.network.httputil.NetUtils;
import com.z.tinyapp.network.okhttp.GsonObjectCallback;
import com.z.tinyapp.userinfo.user.BindUserManager;
import com.z.tinyapp.userinfo.user.UserInfo;
import com.z.tinyapp.utils.common.AsscetsUtil;
import com.z.tinyapp.utils.common.FileUtil;
import com.z.tinyapp.utils.common.PrefernceUtil;
import com.z.tinyapp.utils.logs.sLog;
import com.z.tinyapp.utils.thred.ThreadPoolUtil;
import com.sun.weexandroid_module.IWxApps;
import com.sun.weexandroid_module.IWxNav;
import com.sun.weexandroid_module.WxAndroidApplication;
import com.sun.weexandroid_module.WxAppsModule;
import com.sun.weexandroid_module.WxNavModule;
import com.sun.weexandroid_module.WxRvUtils;
import com.tinyapp.tinyappplateform.R;
import com.tinyapp.tinyappplateform.TapApplication;
import com.tinyapp.tinyappplateform.adapter.WeexListAdapter;
import com.tinyapp.tinyappplateform.bean.AppBean;
import com.tinyapp.tinyappplateform.bean.AppListBean;
import com.tinyapp.tinyappplateform.bean.Card;
import com.tinyapp.tinyappplateform.bean.WeexUpdateBean;
import com.tinyapp.tinyappplateform.broadcastreceiver.BroadCastConstant;
import com.tinyapp.tinyappplateform.broadcastreceiver.MainBroadcastReceiver;
import com.tinyapp.tinyappplateform.weex.BaseWeexActivity;
import com.tinyapp.tinyappplateform.weexapps.AppDirs;
import com.tinyapp.tinyappplateform.weexapps.AppListMgr;
import com.tinyapp.tinyappplateform.weexapps.InfoFlowItem;
import com.tinyapp.tinyappplateform.weexapps.User;
import com.tinyapp.tinyappplateform.weexapps.UserMgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Created by zhengfei on 2018/7/20.
 */

public class MainPresent extends BasePresenter<MainView> {

    private static final float BIGCARD_HEIGHT = 467.0f;
    private static final float EDITCARD_HEIGHT = 100.0f;

    private MainBroadcastReceiver broadcastReceiver;
    private boolean initalUser = false;
    private List<InfoFlowItem> listNofit;
    private WeexListAdapter noticeAdapter;
    private String card_status = "normal";
    private boolean bigCardLoaded = false;
    private boolean sliping = false;
    private boolean editcard_extend = false;

    public static Map<String, Object> sCallbackMap = new HashMap<>();

    public void init() {

    }

    //初始化weex
    public void initalWeexData() {
        final Context context = mvpView.getContext();
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
                    isSuccess = false;
                }
            }
            mvpView.initalWeexBack(isSuccess);
        } else {
            initalWeex(context);
            mvpView.initalWeexBack(true);
        }
    }

    private void initalWeex(Context context) {

        AppListMgr.getInstance().init(context);
        UserMgr.getInstance().init();
    }

    public void showPopWindow() {
        final Context context = mvpView.getContext();
        final SGMAlertPopupAbs popupCheck = new SGMAlertPopupAbs(context,
                SGMAlertPopupAbs.DEFAULT_TYPE_NORMAL_POPUP);
        String messages = context.getString(R.string.whether_jump_to_user_login);
        String leftbtntxt = context.getString(R.string.btn_ok_text);
        popupCheck.setMessage(messages);
        popupCheck.setLeftButton(leftbtntxt, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("com.z.hmi.users.MainActivity");
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
        popupCheck.show();
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
        final String backGuid=UUID.randomUUID().toString();
        map.put("backGuid", backGuid);
        httpEngine.postJson(HttpConstant.GETMAINFILE, map, new GsonObjectCallback<WeexUpdateBean>() {
            @Override
            public void onSuccess(WeexUpdateBean baseJsonBean) {
                if(baseJsonBean.appList!=null&&baseJsonBean.appList.size()>0){

                }
            }

            @Override
            public void onFailed(String code, String msg) {
            }
        });
    }


    private void initWeexService(){

        final Context context = mvpView.getContext();
        String searchPath = new AppDirs(context, "__").getRootDir() +
                AppListMgr.getInstance().findSmallCard("liteapp_systembase", "search").path;//"/service/WeexCard/search.js";
        WxRvUtils.getJsView(context, mvpView.getllSearch(), searchPath,  new WxRvUtils.CallBak(){

            @Override
            public void onFailed() {

            }

            @Override
            public void onSuccess() {

            }
        });

        //
        String editPath = new AppDirs(context, "__").getRootDir() +
                AppListMgr.getInstance().findSmallCard("liteapp_systembase", "eidt").path;;//"/service/WeexCard/edit.js";
        WxRvUtils.getJsView(context, mvpView.getllEdit(), editPath,  new WxRvUtils.CallBak(){

            @Override
            public void onFailed() {

            }

            @Override
            public void onSuccess() {

            }
        });

        //
        sendInfoFlow("liteapp_systeminfo", "tianqi");
        sendInfoFlow("dianyin", "dianyin");
        sendInfoFlow("youhao", "youhao");
    }

    private void sendInfoFlow(String appName, String name){
        mvpView.getContext().sendBroadcast(new Intent().setAction(BroadCastConstant.EXEC_CMD).putExtra("cmd", "infoflow")
                .putExtra("app", appName)
                .putExtra("name", name));
    }

    private void sendBigCard(String appName, String name){
        mvpView.getContext().sendBroadcast(new Intent().setAction(BroadCastConstant.EXEC_CMD).putExtra("cmd", "bigcard")
                .putExtra("app", appName)
                .putExtra("name", name));
    }

    // todo
    private void sendSmallCard(String appName, String name){
        mvpView.getContext().sendBroadcast(new Intent().setAction(BroadCastConstant.EXEC_CMD).putExtra("cmd", "smallcard")
                .putExtra("app", appName)
                .putExtra("name", name));
    }

    private void sendOpenMain(String appName, IWxApps.CallbackForMain callback) {
        String mapKey = "openmain_" + appName;
        sCallbackMap.put(mapKey, callback);
        mvpView.getContext().sendBroadcast(new Intent().setAction(BroadCastConstant.EXEC_CMD).putExtra("cmd", "openmain")
                .putExtra("app", appName)
                .putExtra("callbackkey", mapKey));
    }

    private void sendPushMain(String appName, String path, IWxApps.CallbackForMain callback) {
        String mapKey = "pushmain_" + appName;
        sCallbackMap.put(mapKey, callback);
        mvpView.getContext().sendBroadcast(new Intent().setAction(BroadCastConstant.EXEC_CMD).putExtra("cmd", "pushmain")
                .putExtra("app", appName)
                .putExtra("path", path)
                .putExtra("callbackkey", mapKey));
    }

    public void hideBigCard() {
        final TranslateAnimation trans0 = new TranslateAnimation(0.0f, 0.0f, -1.0f*BIGCARD_HEIGHT, -1.0f*BIGCARD_HEIGHT);
        trans0.setDuration(1);
        trans0.setFillAfter(true);
        final float y = mvpView.getllCardWrap().getY();
        trans0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mvpView.getllCardWrap().clearAnimation();
                mvpView.getllCardWrap().setY(-467.0f);
                card_status = "normal";// normal / bigcard_show / edit_button
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mvpView.getllCardWrap().startAnimation(trans0);

    }

    public void extendBigCard() {
        if (sliping) {
            return;
        }
        sliping = true;
        if (!bigCardLoaded){
            sliping = false;
            return ;
        }
        if("bigcard_show".equals(card_status)) {
            mvpView.getllCardWrap().clearAnimation();
            mvpView.getllCardWrap().setY(0);//y+BIGCARD_HEIGHT
            card_status = "bigcard_show";// normal / bigcard_show / edit_button
            sliping = false;
            return;
        }
        final TranslateAnimation trans0 = new TranslateAnimation(0.0f, 0.0f, 0.0f, BIGCARD_HEIGHT);
        trans0.setDuration(500);
        trans0.setFillAfter(true);
        final float y = mvpView.getllCardWrap().getY();
        trans0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mvpView.getllCardWrap().clearAnimation();
                mvpView.getllCardWrap().setY(0);//y+BIGCARD_HEIGHT
                card_status = "bigcard_show";// normal / bigcard_show / edit_button
                sliping = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mvpView.getllCardWrap().startAnimation(trans0);

    }



    public void collapseBigCard() {
        if (sliping) {
            return;
        }
        sliping = true;
        if("normal".equals(card_status)) {
            mvpView.getllCardWrap().clearAnimation();
            mvpView.getllCardWrap().setY(-467.0f);//y+1*EDITCARD_HEIGHT
            card_status = "normal";// normal / bigcard_show / edit_button
            sliping = false;
            return;
        }
        final TranslateAnimation trans0 = new TranslateAnimation(0.0f, 0.0f, 0.0f, -1*BIGCARD_HEIGHT);
        trans0.setDuration(500);
        trans0.setFillAfter(true);
        final float y = mvpView.getllCardWrap().getY();
        trans0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mvpView.getllCardWrap().clearAnimation();
                mvpView.getllCardWrap().setY(-467.0f);//y-1*BIGCARD_HEIGHT
                card_status = "normal";// normal / bigcard_show / edit_button
                sliping = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mvpView.getllCardWrap().startAnimation(trans0);
    }

    public void collapseEditCard(){
        if(sliping || !editcard_extend) {
            return;
        }
        sliping = true;
        if("normal".equals(card_status)) {
            mvpView.getllCardWrap().clearAnimation();
            mvpView.getllCardWrap().setY(-467.0f);//y+1*EDITCARD_HEIGHT
            card_status = "normal";// normal / bigcard_show / edit_button
            sliping = false;
            return;
        }
        final TranslateAnimation trans0 = new TranslateAnimation(0.0f, 0.0f, 0.0f, EDITCARD_HEIGHT);
        trans0.setDuration(500);
        trans0.setFillAfter(true);
        final float y = mvpView.getllCardWrap().getY();
        editcard_extend = false;
        trans0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mvpView.getllCardWrap().clearAnimation();
                mvpView.getllCardWrap().setY(-467.0f);//y+1*EDITCARD_HEIGHT
                card_status = "normal";// normal / bigcard_show / edit_button
                sliping = false;

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mvpView.getllCardWrap().startAnimation(trans0);
    }

    public void extendEditCard(){
        if (sliping || editcard_extend) {
            return ;
        }
        sliping = true;
        if("edit_button".equals(card_status)){
            mvpView.getllCardWrap().clearAnimation();
            mvpView.getllCardWrap().setY(-567.0f);//y-EDITCARD_HEIGHT
            card_status = "edit_button";// normal / bigcard_show / edit_button
            sliping = false;
            return;
        }
        final TranslateAnimation trans0 = new TranslateAnimation(0.0f, 0.0f, 0.0f, -1*EDITCARD_HEIGHT);
        trans0.setDuration(500);
        trans0.setFillAfter(true);
        final float y = mvpView.getllCardWrap().getY();
        editcard_extend = true;
        trans0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mvpView.getllCardWrap().clearAnimation();
                mvpView.getllCardWrap().setY(-567.0f);//y-EDITCARD_HEIGHT
                card_status = "edit_button";// normal / bigcard_show / edit_button
                sliping = false;

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mvpView.getllCardWrap().startAnimation(trans0);
    }

    public void initData() {
        hideBigCard();
        initBroadcast();
        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                WxAndroidApplication.initWx(TapApplication.getInstance() );
                initalWeexData();
                mvpView.getContext().sendBroadcast(new Intent().setAction(BroadCastConstant.INITWEEXSERVICEAPP));

                bindService();
                //presenter.getWeexList();
            }
        });


        WxNavModule.setNav(new IWxNav() {
            @Override
            public void push(String path) {
                String mainPath = new AppDirs(mvpView.getContext(), "__").getRootDir() + path;
                mvpView.getContext().startActivity(new Intent(mvpView.getContext(), BaseWeexActivity.class)
                        .putExtra(BaseWeexActivity.JSPATH, mainPath));
            }
        });

        WxAppsModule.setCallback(new IWxApps() {
            @Override
            public void cmd(String cmd) {
                if ("0".equals(cmd)){
                    // 显示信息流
                    mvpView.getContext().sendBroadcast(new Intent().setAction(BroadCastConstant.EXEC_CMD).putExtra("cmd", "infoflow")
                            .putExtra("app", "tianqi")
                            .putExtra("name", "tianqi"));
                } else if ("1".equals(cmd)){
                    // 显示信息流
                    mvpView.getContext().sendBroadcast(new Intent().setAction(BroadCastConstant.EXEC_CMD).putExtra("cmd", "infoflow")
                            .putExtra("app", "dianyin")
                            .putExtra("name", "dianyin"));
                } else if ("2".equals(cmd)){
                    // 显示信息流
                    mvpView.getContext().sendBroadcast(new Intent().setAction(BroadCastConstant.EXEC_CMD).putExtra("cmd", "infoflow")
                            .putExtra("app", "youhao")
                            .putExtra("name", "youhao"));

                } else if ("01".equals(cmd)){

                } else if ("10".equals(cmd)){

                } else if ("11".equals(cmd)){

                }
            }

            @Override
            public void showInfoFlow(String appName, String name, IWxApps.Callback callback) {
                sendInfoFlow(appName, name);
                if (null != callback)
                    callback.invoke(null);
            }

            @Override
            public void showBigCard(String appName, String name, IWxApps.Callback callback) {
                sendBigCard(appName, name);
                if (null != callback)
                    callback.invoke(null);
            }

            @Override
            public void showSmallCard(String appName, String name, Callback callback) {
                //InfoFlowItem item = AppListMgr.getInstance().findInfoFlow(app, name);
                sendSmallCard(appName, name);
                if (null != callback)
                    callback.invoke(null);
            }

            @Override
            public void openMain(String appName, CallbackForMain callback) {
                sendOpenMain(appName, callback);
            }

            @Override
            public void push(String appName, String path, CallbackForMain callbackForMain) {
                sendPushMain(appName, path, callbackForMain);
            }

        });
    }




    private void initBroadcast() {
        broadcastReceiver = new MainBroadcastReceiver();
        broadcastReceiver.setCallback(new MainBroadcastReceiver.ICallback() {
            @Override
            public void cmd(Intent cmd) {
                String cmdStr = cmd.getStringExtra("cmd");

                if ("bigcard".equals(cmdStr)) {
                    String app = cmd.getStringExtra("app");
                    String name = cmd.getStringExtra("name");
                    if (app != null && name != null && app.length() > 0 && name.length()>0) {
                        Card c = AppListMgr.getInstance().findBigCard(app, name);
                        WxRvUtils.getJsView(mvpView.getContext(), mvpView.getllBigWeexCard(),
                                new AppDirs(mvpView.getContext(), "--").getRootDir() +
                                        c.path, new WxRvUtils.CallBak() {
                                    @Override
                                    public void onFailed() {
                                        sLog.i(null, "cardDown -------------- F");
                                    }

                                    @Override
                                    public void onSuccess() {
                                        sLog.i(null, "cardDown -------------- S");
                                        bigCardLoaded = true;
                                        extendBigCard();
                                    }
                                });
                    }
                } else if ("infoflow".equals(cmdStr)){
                    String app = cmd.getStringExtra("app");
                    String name = cmd.getStringExtra("name");
                    if (app != null && name != null && app.length() > 0 && name.length()>0) {
                        InfoFlowItem item = AppListMgr.getInstance().findInfoFlow(app, name);
                        if (null != item) {
                            if (listNofit == null) {
                                listNofit = new ArrayList<>();
                            }

                            listNofit.add(item);
                            if (noticeAdapter == null) {
                                noticeAdapter = new WeexListAdapter(mvpView.getContext(), listNofit, mvpView.getlvWeexnotice());
                                mvpView.getlvWeexnotice().setAdapter(noticeAdapter);
                            } else {
                                noticeAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                } else if ("smallcard".equals(cmdStr)){
                    String app = cmd.getStringExtra("app");
                    String name = cmd.getStringExtra("name");
                    if (app != null && name != null && app.length() > 0 && name.length()>0) {
                        Card item = AppListMgr.getInstance().findSmallCard(app, name);
                        if (null != item) {
                            User curUser = UserMgr.getInstance().getCurrentUser();
                            boolean b = curUser.addSmallCard(app, name);
                            if (b){
                                mvpView.reloadSmallCard();
                            }
                        }
                    }
                }else if ("openmain".equals(cmdStr)){
                    String app = cmd.getStringExtra("app");
                    String cbKey = cmd.getStringExtra("callbackkey");
                    if (app != null) {
                        AppBean item = AppListMgr.getInstance().find(app);
                        if (null != item && null != item.appInfo.apppath && !"".equals(item.appInfo.apppath)) {
                            String mainPath = new AppDirs(mvpView.getContext(), "__").getRootDir() + item.appInfo.apppath;
                            mvpView.getContext().startActivity(new Intent(mvpView.getContext(), BaseWeexActivity.class)
                                    .putExtra(BaseWeexActivity.JSPATH, mainPath)
                                    .putExtra("callbackkey", cbKey));
                        }
                    }
                }else if ("pushmain".equals(cmdStr)){
                    String app = cmd.getStringExtra("app");
                    String path = cmd.getStringExtra("path");
                    String cbKey = cmd.getStringExtra("callbackkey");
                    if (app != null && path != null && path.length() > 0 && path.length()>0) {
                        AppBean item = AppListMgr.getInstance().find(app);
                        if (null != item && null != item.appInfo.apppath && !"".equals(item.appInfo.apppath)) {
                            String mainPath = new AppDirs(mvpView.getContext(), "__").getRootDir() + path;
                            mvpView.getContext().startActivity(new Intent(mvpView.getContext(), BaseWeexActivity.class)
                                    .putExtra(BaseWeexActivity.JSPATH, mainPath)
                                    .putExtra("callbackkey", cbKey));
                        }
                    }
                }
            }

            @Override
            public void updataBack(String guid, String version) {//更新应用通知
            }

            @Override
            public void cardDown(String weexid, Boolean isSuccess) {//卡片包下载
                synchronized (this) {
                    sLog.i(null, "cardDown --------------");
                    WxRvUtils.getJsView(mvpView.getContext(), mvpView.getllBigWeexCard(), new AppDirs(mvpView.getContext(), "--").getRootDir() + AppListMgr.getInstance().getAppList().get(0).appInfo.appcard.bigcard.get(0).path, new WxRvUtils.CallBak() {
                        @Override
                        public void onFailed() {
                            sLog.i(null, "cardDown -------------- F");
                        }

                        @Override
                        public void onSuccess() {
                            sLog.i(null, "cardDown -------------- S");
                        }
                    });
                }
            }

            @Override
            public void nofit(String weexId) {//接收到weex推送消息
                InfoFlowItem item = AppListMgr.getInstance().findInfoFlow(weexId);
                if (null != item) {
                    if (listNofit == null) {
                        listNofit = new ArrayList<>();
                    }

                    listNofit.add(item);
                    if (noticeAdapter == null) {
                        noticeAdapter = new WeexListAdapter(mvpView.getContext(), listNofit, mvpView.getlvWeexnotice());
                        mvpView.getlvWeexnotice().setAdapter(noticeAdapter);
                    } else {
                        noticeAdapter.notifyDataSetChanged();
                    }
                }


            }

            @Override
            public synchronized void mainDown(String giud) {//主应用包下载成功
                /*for (AppListBean bean : presenter.getWeexDatas()) {
                    if (bean.guid.equals(giud)) {
                        bean.status = 0;
                    }
                }*/
            }

            @Override
            public void initWeexServiceApp() {
                initWeexService();
            }


        });
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastConstant.ACCOUNTLOGOUT_ACTION);
        filter.addAction(BroadCastConstant.MAINCARDDOWN);
        filter.addAction(BroadCastConstant.INITWEEXSERVICEAPP);
        filter.addAction(BroadCastConstant.EXEC_CMD);
        mvpView.getContext().registerReceiver(broadcastReceiver, filter);
    }

    public void bindService() {
        BindUserManager.initial(mvpView.getContext());
        BindUserManager.getInstenst().setCallBack(new BindUserManager.BindBack() {
            @Override
            public void bindSuccess() {
                ((Activity)mvpView.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //绑定成功并获取到了用户信息
                        if (!TextUtils.isEmpty(UserInfo.getInstance().getAccessToken())) {
                            getUserSuccess();
                        } else {//没有获取到用户信息
                            showPopWindow();
                        }
                    }
                });
            }
        }).bindService();
    }

    //绑定账户成功
    public void getUserSuccess() {
        initalUser = true;
        //mvpView.showMainContent();
    }

    public void Destroy() {
        mvpView.getContext().unregisterReceiver(broadcastReceiver);
    }
}
