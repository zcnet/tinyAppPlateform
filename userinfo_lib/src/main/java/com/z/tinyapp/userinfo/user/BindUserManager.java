package com.z.tinyapp.userinfo.user;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.sgm.APSManager;

import com.z.hmi.IUserAcountSerivce;
import com.z.tinyapp.userinfo.R;
import com.z.tinyapp.utils.common.APKVersionCodeUtils;
import com.z.tinyapp.utils.common.ToastUtil;
import com.z.tinyapp.utils.thred.ThreadPoolUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * Created by zhengfei on 2018/8/7.
 */

public class BindUserManager {
    private  Context context;
    private BindBack bindBack;
    private boolean isFlushTocken=false;
    private static BindUserManager bindUserManager;
    private BindUserManager(Context context){
        this.context=context;
    }
    public static void initial(Context context){
        bindUserManager=new BindUserManager(context);
    }
    public static BindUserManager getInstenst(){
        return bindUserManager;
    }
    public BindUserManager setIsFlushTocken(boolean isFlushTocken){
        this.isFlushTocken=isFlushTocken;
        return this;
    }
    public BindUserManager setCallBack(BindBack back){
        this.bindBack=back;
        return this;
    }
    public  Intent getExplicitIntent(Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);
        // Set the component to be explicit
        explicitIntent.setComponent(component);
        return explicitIntent;
    }
    long time=0;
    public void bindService() {
        long nowTime=new Date().getTime();
        if((nowTime- time)<10000){
            return;
        }
        time=nowTime;
        Intent mIntent = new Intent();
        mIntent.setAction("com.z.hmi.users.UserAccountServiceAction");
        Intent ExplicitIntent = getExplicitIntent( mIntent);
        Intent eintent = null;
        if(ExplicitIntent != null) {
            eintent = new Intent(ExplicitIntent);
        }
        else{
            eintent = null;
        }
        if(eintent != null){
            context.bindService(eintent,
                    conn, Context.BIND_AUTO_CREATE);
        }
    }
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            final IUserAcountSerivce mRemoteService = IUserAcountSerivce.Stub.asInterface(iBinder);

            ThreadPoolUtil.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(isFlushTocken){
                            boolean isSuccess= mRemoteService.refreshUserprofileToken();
                            if(isSuccess){
                                if(mRemoteService.getCurrentLoginStatus()){
                                    getTheUsefulInformation(mRemoteService);
                                }
                            }else{
                                if (context!=null){
                                    context.unbindService(conn);
                                    ToastUtil.show(context,context.getResources().getString(R.string.toaken_unuse));
                                }
                                bindBack=null;
                                System.exit(0);
                            }
                        }else{
                            if(mRemoteService.getCurrentLoginStatus()){
                                getTheUsefulInformation(mRemoteService);
                            }
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            if (context!=null){
                                context.unbindService(conn);
                            }
                            if(bindBack!=null) {
                                bindBack.bindSuccess();
                                bindBack=null;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            if (context!=null){
                context.unbindService(conn);
            }
            if(bindBack!=null) {
                bindBack.bindSuccess();
                bindBack=null;
            }
        }
    };
    private void getTheUsefulInformation(IUserAcountSerivce mRemoteService) throws RemoteException {
        UserInfo userInfo= UserInfo.getInstance();
        userInfo.setAccessToken(mRemoteService.getUserprofileToken());
        String mUserPhoneNum = "";
        try {
            mUserPhoneNum=new JSONObject(mRemoteService.getCurrentUserInfo()).optString("phoneNumber");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        userInfo.setIdmLoginName(mUserPhoneNum);
        String vin = getVinCode(context);
        userInfo.setDeviceCode(vin);
        userInfo.setDeviceType("0");
        userInfo.setVerCode(APKVersionCodeUtils.getVersionCode(context));
        userInfo.setVerName(APKVersionCodeUtils.getVerName(context));
    }
    @SuppressLint("WrongConstant")
    private String getVinCode(Context context) {
        APSManager mgr = null;
        mgr = (APSManager) context.getSystemService(Context.APS_SERVICE);
        return mgr.requestVIN();
    }
    public interface BindBack{
        void bindSuccess();
    }
}
