package com.tinyapp.tinyappplateform.adapter;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

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
                String temp = url;
                if (url.startsWith("//")) {
                    temp = "http:" + url;
                }
                if (view.getLayoutParams().width <= 0 || view.getLayoutParams().height <= 0) {
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
                                if (strategy.getImageListener() != null) {
                                    strategy.getImageListener().onImageFinish(url, view, false, null);
                                }
                            }
                        });
            }
        }, 0);
    }
}
