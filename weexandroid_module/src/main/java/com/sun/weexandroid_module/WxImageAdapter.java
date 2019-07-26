package com.sun.weexandroid_module;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.z.tinyapp.utils.logs.sLog;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

import java.io.File;

/**
 * Created by sun on 2018/8/6
 * <p>
 * <p>
 * 图片下载
 * 需要时集成接口 IWXImgLoaderAdapter，实现 setImage 方法。
 */
public class WxImageAdapter implements IWXImgLoaderAdapter {

    @SuppressWarnings("unused")
    private static final String TAG = "sunHy_ImgAdapter";

    public WxImageAdapter() {

    }

    @Override
    public void setImage(final String url, final ImageView view, WXImageQuality quality, final WXImageStrategy strategy) {


        WXSDKManager.getInstance().postOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (view == null || view.getLayoutParams() == null) {
                    return;
                }
                if (TextUtils.isEmpty(url)) {
                    view.setImageBitmap(null);
                    return;
                }

                if (url.startsWith("/images/")){
                    String bundle_url = WXSDKManager.getInstance().getSDKInstance(strategy.instanceId).getContainerInfo().get(WXSDKInstance.BUNDLE_URL).toString();//
                    sLog.v("weex", "image_url:"+url+":xxx:"+bundle_url);
                    if (!bundle_url.isEmpty()){
                        int start = bundle_url.indexOf("/weexapps/");
                        if (start>=0){
                            int end = bundle_url.indexOf('/', start+10);
                            if (end > start){
                                String imgUrl = bundle_url.substring(0, end)+url;
                                sLog.v("weex", "image_url_:"+imgUrl);
                                view.setImageURI(Uri.fromFile(new File(imgUrl)));
                                return;
                            }
                        }
                    }
                }

                if (url.startsWith("/globals/images/")){
                    String bundle_url = WXSDKManager.getInstance().getSDKInstance(strategy.instanceId).getContainerInfo().get(WXSDKInstance.BUNDLE_URL).toString();//
                    sLog.v("weex", "image_url:"+url+":xxx:"+bundle_url);
                    if (!bundle_url.isEmpty()){
                        int start = bundle_url.indexOf("/weexapps/");
                        if (start>=0){
                            String imgUrl = bundle_url.substring(0, start+9)+url;
                            sLog.v("weex", "globals/image_url_:"+imgUrl);
                            view.setImageURI(Uri.fromFile(new File(imgUrl)));
                            return;

                        }
                    }
                }

                String temp = url;
                if (url.startsWith("//")) {
                    temp = "http:" + url;
                }
                if (view.getLayoutParams().width <= 0 || view.getLayoutParams().height <= 0) {
                    Loger.showI(TAG,"load img error , size 0");
                    return;
                }

                if (!TextUtils.isEmpty(strategy.placeHolder)) {
                    Picasso.get().load(Uri.parse(strategy.placeHolder)).into(view);
                }

                Picasso.get()
                        .load(temp)
                        .into(view, new Callback() {
                            @Override
                            public void onSuccess() {
                                if (strategy.getImageListener() != null) {
                                    strategy.getImageListener().onImageFinish(url, view, true, null);
                                }
                            }

                            @Override
                            public void onError(Exception e) {
                                Loger.showI(TAG,"image load failed",e);
                                if (strategy.getImageListener() != null) {
                                    strategy.getImageListener().onImageFinish(url, view, false, null);
                                }
                            }
                        });
            }
        }, 0);
    }
}
