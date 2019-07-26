package com.sun.weexandroid_module;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.z.tinyapp.utils.common.FileUtil;
import com.z.tinyapp.utils.logs.sLog;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun on 2018/8/10
 */
public class WxRvUtils {


    private static final String TAG = "sunHy_RvUtils";

    private static WXSDKInstance mSdkInstance;

    //rename by zf
    public interface CallBackWithView {

        void onFailed();

        void onSuccess(View view, LinearLayout viewGroup, int index, String info);
    }

    //rename by zf
    public interface CallBak {
        void onFailed();

        void onSuccess();
    }

    public interface IJsLoadSuccessCallBack {
        void onAdded();
    }

    /**
     * add by zf
     */
    public static void getJsForAdapter(Context context, String path, final LinearLayout viewGroup, final int index, final String info, final CallBackWithView listener) {

        if (context == null) {
            sLog.e(TAG, "getJsForAdapter:view not instanceof viewgroup", new NullPointerException());
            return;
        }

        if (TextUtils.isEmpty(path)) {
            sLog.e(TAG, "getJsForAdapter:---------path null");
            return;
        }

        if (listener == null) {
            sLog.e(TAG, "getJsForAdapter:---------listener null");
            return;
        }


        WXSDKInstance mWXSDKInstance = new WXSDKInstance(context);
        mWXSDKInstance.registerRenderListener(new IWXRenderListener() {
            @Override
            public void onViewCreated(WXSDKInstance instance, View view) {
                //viewGroup.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                sLog.v("weex", "getJsForAdapter: onViewCreated");
                ((View)viewGroup.getParent()).setAlpha(1.0f);
                Animation animation = new AlphaAnimation(0.0f, 1.0f);
                animation.setDuration(500);
                animation.setFillAfter(true);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ((View)viewGroup.getParent()).setAlpha(1.0f);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                listener.onSuccess(view, viewGroup, index, info);
                ((View)viewGroup.getParent()).startAnimation(animation);
            }

            @Override
            public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
                sLog.v("weex", "onRenderSuccess:"+width);
            }

            @Override
            public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

            }

            @Override
            public void onException(WXSDKInstance instance, String errCode, String msg) {
                listener.onFailed();
                sLog.e(TAG, "getFloatWindowView onException" + msg);

            }
        });
        String jsStr = WXFileUtils.loadFileOrAsset(path, context);
        if (TextUtils.isEmpty(jsStr)) {
            listener.onFailed();
            return;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("item", index);
        map.put(WXSDKInstance.BUNDLE_URL, path);
        sLog.v("weex", "getJsForAdapter: bundle_url:"+map.get(WXSDKInstance.BUNDLE_URL)+" content:" + jsStr);
        mWXSDKInstance.setContainerInfo(WXSDKInstance.BUNDLE_URL, path);

        mWXSDKInstance.render(
                "WXSample",
                jsStr,
                map,
                null,
                WXRenderStrategy.APPEND_ASYNC);
    }

    public static void getJsView(Context context, final ViewGroup viewGroup, String path, final CallBak listener) {

        if (context == null) {
            Loger.showE(TAG, "view not instanceof viewgroup", new NullPointerException());
            return;
        }

        if (TextUtils.isEmpty(path)) {
            Loger.showE(TAG, "---------path null");
            return;
        }

        if (listener == null) {
            Loger.showE(TAG, "---------listener null");
            return;
        }


        WXSDKInstance mWXSDKInstance = new WXSDKInstance(context);
        //mWXSDKInstance.setInstanceViewPortWidth(750);
        mWXSDKInstance.registerRenderListener(new IWXRenderListener() {
            @Override
            public void onViewCreated(WXSDKInstance instance, View view) {
                viewGroup.addView(view);
                listener.onSuccess();
            }

            @Override
            public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

            }

            @Override
            public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

            }

            @Override
            public void onException(WXSDKInstance instance, String errCode, String msg) {
                listener.onFailed();
                sLog.e(TAG, "getFloatWindowView onException" + msg);
            }
        });
        String jsStr = WXFileUtils.loadFileOrAsset(path, context);
        if (TextUtils.isEmpty(jsStr)) {
            listener.onFailed();
            sLog.e(TAG, "xxxxx:"+path );
            return;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(WXSDKInstance.BUNDLE_URL, path);
        sLog.v("weex", "bundle_url:"+map.get(WXSDKInstance.BUNDLE_URL));
        mWXSDKInstance.setContainerInfo(WXSDKInstance.BUNDLE_URL, path);
        mWXSDKInstance.render(
                "WXSample",
                jsStr,
                map,
                null,
                WXRenderStrategy.APPEND_ASYNC);
    }

    /**
     * 语音助手悬浮窗模块Weex生成View并显示
     */
    public static void getFloatWindowView(final Context context, final View parent, String path, final Map<String, Object> map, final IJsLoadSuccessCallBack callBack) {
        //String weexappsroot = getWeexAppRootDir(context);
        //path = (weexappsroot + "/service/WeexVRCard/" + path);
        if (context == null) {
            //Loger.showE(TAG, "view not instanceof viewgroup", new NullPointerException());
            sLog.e(TAG, "view not instanceof viewgroup");
            return;
        }

        if (!(parent instanceof LinearLayout || parent instanceof RelativeLayout)) {
            //Loger.showE(TAG, "view not instanceof viewgroup");
            sLog.e(TAG, "view not instanceof viewgroup");
            return;
        }

        if (TextUtils.isEmpty(path)) {
            //Loger.showE(TAG, "---------path null");
            sLog.e(TAG, "---------path null");
            return;
        }

        mSdkInstance = new WXSDKInstance(context);
        mSdkInstance.registerRenderListener(new IWXRenderListener() {

            @Override
            public void onViewCreated(WXSDKInstance instance, View view) {
                //final View view1 = LayoutInflater.from(context).inflate(R.layout.layout_stub, null);
                //view1.setLayoutParams(new LinearLayout.LayoutParams(1, 25));
                InterruptFrameLayout layout = new InterruptFrameLayout(instance.getContext());
                layout.addView(view);

                if (map.get("name") != null) {
                    layout.setTag((map.get("name")));
                }
                ((ViewGroup) parent).addView(layout);
                //((ViewGroup) parent).addView(view1);
                layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        callBack.onAdded();
                    }
                });
            }

            @Override
            public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

            }

            @Override
            public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

            }

            @Override
            public void onException(WXSDKInstance instance, String errCode, String msg) {
                Loger.showE(TAG, "getFloatWindowView onException" + msg);
            }
        });
        if (map.get(WXSDKInstance.BUNDLE_URL)==null)
            map.put(WXSDKInstance.BUNDLE_URL, path);
        sLog.v("weex", "bundle_url:"+map.get(WXSDKInstance.BUNDLE_URL));
        mSdkInstance.setContainerInfo(WXSDKInstance.BUNDLE_URL, path);
        mSdkInstance.render(
                "WXSample",
                WXFileUtils.loadFileOrAsset(path, context),
                map,
                null,
                WXRenderStrategy.APPEND_ASYNC);
    }

    public static  String getWeexAppRootDir(Context context) {
        String sdPath = FileUtil.getFilePath(context);
        return sdPath + "/weexapps";
    }

    /**
     * 语音助手悬浮窗模块Weex后台运行Weex逻辑
     */
    public static void getFloatWindowViewNoShow(Context context, String path, final Map<String, Object> map) {
        //String weexappsroot = getWeexAppRootDir(context);
        //path = (weexappsroot + "/service/WeexVRCard/" + path);
        if (context == null) {
            Loger.showE(TAG, "view not instanceof viewgroup", new NullPointerException());
            return;
        }

        if (TextUtils.isEmpty(path)) {
            Loger.showE(TAG, "---------path null");
            return;
        }

        WXSDKInstance sWxSDKInstance = new WXSDKInstance(context);
        if (map.get(WXSDKInstance.BUNDLE_URL)==null)
            map.put(WXSDKInstance.BUNDLE_URL, path);
        sLog.v("weex", "bundle_url:"+map.get(WXSDKInstance.BUNDLE_URL));
        sWxSDKInstance.setContainerInfo(WXSDKInstance.BUNDLE_URL, path);
        sWxSDKInstance.render(
                "WXSample",
                WXFileUtils.loadFileOrAsset(path, context),
                map,
                null,
                WXRenderStrategy.APPEND_ASYNC);
    }

    /**
     * 语音助手悬浮窗模块Weex后台运行Weex逻辑，全局WxSdkInstance
     */
    public static void getFloatWindowViewNoShowWithInstance(Context context, String path, final Map<String, Object> map) {
        //String weexappsroot = getWeexAppRootDir(context);
        //path = (weexappsroot + "/service/WeexVRCard/" + path);
        if (context == null) {
            Loger.showE(TAG, "view not instanceof viewgroup", new NullPointerException());
            return;
        }

        if (TextUtils.isEmpty(path)) {
            Loger.showE(TAG, "---------path null");
            return;
        }

        mSdkInstance = new WXSDKInstance(context);
        if (map.get(WXSDKInstance.BUNDLE_URL)==null)
            map.put(WXSDKInstance.BUNDLE_URL, path);
        sLog.v("weex", "bundle_url:"+map.get(WXSDKInstance.BUNDLE_URL));
        mSdkInstance.setContainerInfo(WXSDKInstance.BUNDLE_URL, path);
        mSdkInstance.render(
                "WXSample",
                WXFileUtils.loadFileOrAsset(path, context),
                map,
                null,
                WXRenderStrategy.APPEND_ASYNC);
    }

    public static void send2Wx(String data){
        sLog.i(TAG, "send2Wx: instance--> "+mSdkInstance);
        Map<String,Object> map = new HashMap<>();
        map.put("data",data);
        if(mSdkInstance!=null){
            mSdkInstance.fireGlobalEventCallback("onCallbackJS",map);
        }
    }


}