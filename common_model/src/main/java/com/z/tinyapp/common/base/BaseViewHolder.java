package com.z.tinyapp.common.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 封装的ViewHolder
 * Created by zhengfei on 2017/8/17 0017.
 */
public class BaseViewHolder {

    public View mContentView;
    private Unbinder unbinder;

    public BaseViewHolder(Context context, @LayoutRes int layoutID) {
        unbinder = ButterKnife.bind(this, mContentView = View.inflate(context, layoutID, null));
    }

    /**
     * 销毁ViewHolder 解除绑定
     */
    public void destoryView() {
        if (unbinder != null) {
            unbinder.unbind();
        }

        if (mContentView != null) {
            mContentView = null;
        }
    }
}
