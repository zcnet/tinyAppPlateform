package com.z.tinyapp.common.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.z.tinyapp.common.R;
import com.z.tinyapp.common.util.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * activity 基类
 */
public abstract class BaseActivity2 extends Activity {
    /**
     * 上下文对象
     */
    public BaseActivity2 baseAct = null;
    private View mContentView;
    private ProgressDialog mProgressDialog;
    private LoadingDialog loadingDialog;
    private Unbinder unbinder;
    public boolean isResume;//是否处于前台
    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isUseEventBus()) {
            EventBus.getDefault().register(this);
        }
        if(isimmersive()){
            immersive();
        }
        setContentView(LayoutUtils.LayoutInflater(this));
        baseAct = this;
        unbinder = ButterKnife.bind(this);
        initMap(savedInstanceState);
        initView();
        initData();

    }

    protected abstract void initView();
    protected abstract void initData();
    protected boolean isUseEventBus() {
        return false;
    }
    protected boolean isimmersive() {
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    protected void initMap(Bundle savedInstanceState){}

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(getLayoutInflater().inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, null);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(R.layout.activity_base_mall);
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.activity_base_mall);
        if (params == null) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
            relativeLayout.addView(view, layoutParams);
        }
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mContentView = view;
    }



    public void setVisible(View view) {
        if (view != null && view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void setInVisible(View view) {
        if (view != null && view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
        }
    }
    public void setGONE(View view){
        if (view != null && view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 点击页面重试
     */
    protected void onRetry() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResume=true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
        isResume=false;
        hideLoadingDialog();

    }

    /**
     * 显示loading视图
     */
    public void showLoading(boolean isPaddingTop) {
        showLoading();
    }

    /**
     * 显示loading视图
     */
    public void showLoading() {
        showLoadingDialog();
        loadingDialog.show();
        setInVisible(mContentView);
    }

    public void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 显示内容视图
     */
    public void showContent() {
        hideLoadingDialog();
        setVisible(mContentView);
    }

    /**
     * 如果show为true则显示进度条对话框，否则关闭对话框.
     *
     * @param message 对话所要显示的提示消息
     */
    protected void showProgressDialog(String message) {
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    protected void hideProgressDialog() {
        mProgressDialog.dismiss();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (isUseEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        unbinder.unbind();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    //状态栏透明
    public void immersive(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.setNavigationBarColor(Color.TRANSPARENT);
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
