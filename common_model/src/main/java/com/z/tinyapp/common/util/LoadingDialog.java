package com.z.tinyapp.common.util;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.z.tinyapp.common.R;


/**
 * Created by zhengfei on 2017/7/18.
 */

public class LoadingDialog extends Dialog {

    private ImageView loadingImg;
    Animation anim;
    Context context;
    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.context=context;
        setContentView(R.layout.loading_main);
        loadingImg = (ImageView) this.findViewById(R.id.loadingImg);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        anim = AnimationUtils.loadAnimation(context,
                R.anim.rotate_repeat);
        anim.setInterpolator(new LinearInterpolator());
    }

    @Override
    public void show() {
        if (isShowing()) {
            return;
        }
        loadingImg.startAnimation(anim);
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
