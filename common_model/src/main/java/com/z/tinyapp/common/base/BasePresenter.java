package com.z.tinyapp.common.base;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import com.z.tinyapp.network.HttpEngine;


public class BasePresenter<V> {

    public V mvpView;
    protected HttpEngine httpEngine;

        public void attachView(V mvpView) {

            this.mvpView = mvpView;

            httpEngine = new HttpEngine();

        if (mvpView instanceof Activity) {
            httpEngine.setContext((Context) mvpView);
        } else if (mvpView instanceof View) {
            httpEngine.setContext(((View) mvpView).getContext());
        } else if (mvpView instanceof Dialog) {
            httpEngine.setContext(((Dialog) mvpView).getContext());
        } else if (mvpView instanceof Fragment) {
            httpEngine.setContext(((Fragment) mvpView).getActivity());
        }
    }



    /**
     * 获取HttpEngine对象
     */
    public HttpEngine getHttpEngine(){
        return httpEngine;
    }

}
