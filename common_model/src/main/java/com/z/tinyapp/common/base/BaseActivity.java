package com.z.tinyapp.common.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SGMAlertPopupAbs;
import android.widget.SGMGeneralPopupAbs;
import android.widget.TextView;

import com.z.tinyapp.common.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * activity 基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 上下文对象
     */
    public BaseActivity baseAct = null;
    private View mContentView;
//    private ProgressDialog mProgressDialog;
    private Unbinder unbinder;
    public boolean isResume;//是否处于前台
    private  SGMGeneralPopupAbs lodingPop;
    private SGMGeneralPopupAbs errorPop;
    private SGMGeneralPopupAbs textLodingPop;
    private SGMAlertPopupAbs progressPop;
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
        super.setContentView(view);
        initalLoadDialog();

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

    /**
     * 点击返回按钮
     */
    protected void onBack() {
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
//        setInVisible(mContentView);
    }

    public void showLoadingDialog() {
        if (lodingPop == null) {
            initalLoadDialog();
        }
        if (!lodingPop.isShowing()) {
            pbLoading.startAnimation(AnimationUtils.loadAnimation(BaseActivity.this,
                    R.anim.anim_loding));
            lodingPop.show();
        }
    }

    public void hideLoadingDialog() {
        if (lodingPop != null && lodingPop.isShowing()) {
            lodingPop.dismiss();
        }
    }

    /**
     * 显示内容视图
     */
    public void showContent() {
        hideLoadingDialog();
//        setVisible(mContentView);
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (isUseEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if(errorPop!=null&&errorPop.isShowing()){
            errorPop.dismiss();
        }
        if(textLodingPop!=null&&textLodingPop.isShowing()){
            textLodingPop.dismiss();
        }
        if(lodingPop!=null&&lodingPop.isShowing()){
            lodingPop.dismiss();
        }
        if(progressPop!=null&&progressPop.isShowing()){
            progressPop.dismiss();
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
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private ProgressBar pbLoading;
    private void initalLoadDialog(){
        lodingPop =  new SGMGeneralPopupAbs(BaseActivity.this,R.layout.dialog_loading,
                R.style.SGMAlertDialogTheme,1280,720);
        View view=lodingPop.getLayoutView();
        pbLoading= (ProgressBar) view.findViewById(R.id.pb_loading);
        pbLoading.startAnimation(AnimationUtils.loadAnimation(BaseActivity.this,
                R.anim.anim_loding));
        lodingPop.setMsgType(SGMGeneralPopupAbs.TYPE_TIMEOUT);
        lodingPop.setAnimation(R.style.sgm_dialog_animstyle);
        lodingPop.setTimeout(20000);

    }

    public View getPoPView(){
        return lodingPop.getLayoutView();
    }
    private void showErrorDialog(){
        if(errorPop==null){
            errorPop=new SGMGeneralPopupAbs(BaseActivity.this,R.layout.dialog_error,
                    R.style.SGMAlertDialogTheme,1280,720);
            View view=lodingPop.getLayoutView();
            TextView rtguist=(TextView)view.findViewById(R.id.tv_error_reguist);
            TextView back=(TextView)view.findViewById(R.id.tv_error_back);
            rtguist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRetry();
                }
            });
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    errorPop.dismiss();
                    onBack();
                }
            });
            errorPop.setMsgType(SGMGeneralPopupAbs.TYPE_NO_TIMEOUT);
            errorPop.show();
        }else{
            errorPop.show();
        }
    }
    private TextView msg;
    private ImageView close;
    private ProgressBar pb;

    /**
     * 显示加载弹窗
     * @param text
     * @param isShowLoading
     * @param isShowClose
     */
    public void showTextLoading(String text,boolean isShowLoading,boolean isShowClose){
        if(textLodingPop==null){
            textLodingPop=new SGMGeneralPopupAbs(BaseActivity.this,R.layout.dialog_text_loading,
                    R.style.SGMAlertDialogTheme,822,530);
            View view=textLodingPop.getLayoutView();
            msg= (TextView) view.findViewById(R.id.tv_dialog_msg);
            close= (ImageView) view.findViewById(R.id.iv_dialog_close);
            pb= (ProgressBar) view.findViewById(R.id.pb_loading);
            pb.startAnimation(AnimationUtils.loadAnimation(BaseActivity.this,
                    R.anim.anim_loding));
            textLodingPop.setMsgType(SGMGeneralPopupAbs.TYPE_TIMEOUT);
            textLodingPop.setTimeout(20000);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textLodingPop.dismiss();
                }
            });
        }else{
            pb.startAnimation(AnimationUtils.loadAnimation(BaseActivity.this,
                    R.anim.anim_loding));
        }
        msg.setText(text+"");
        if(!isShowLoading){
            pb.setVisibility(View.GONE);
        }else{
            pb.setVisibility(View.VISIBLE);
        }
        if(!isShowClose){
            close.setVisibility(View.GONE);
        }else{
            close.setVisibility(View.VISIBLE);
        }
        textLodingPop.show();
    }

    public void hideTextLoading(){
        if(textLodingPop!=null&&textLodingPop.isShowing()){
            textLodingPop.dismiss();
        }
    }

    private TextView tvProgress;
    private ImageView ivProgress;
    public void showProgress(String text,boolean isShowClose,int time){
        if(progressPop==null){
            progressPop=new SGMAlertPopupAbs(BaseActivity.this,R.layout.dialog_progress,1280,170);
            View view = progressPop.getLayoutView();
            ivProgress= (ImageView) view.findViewById(R.id.iv_dialog_progress_close);
            tvProgress=((TextView)view.findViewById(R.id.tv_dialog_protext));
            progressPop.setAnimation(R.style.sgm_alert_popup_animstyle);
            ivProgress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressPop.dismiss();
                }
            });

        }
        if(isShowClose){
            ivProgress.setVisibility(View.VISIBLE);
        }else{
            ivProgress.setVisibility(View.GONE);
        }
        tvProgress.setText(text);
        if(time==0){
            progressPop.setTimeout(15000);
        }else{
            progressPop.setTimeout(time);
        }
        progressPop.show();
    }

    public void hideProgress(){
        if(progressPop!=null&&progressPop.isShowing()){
            progressPop.dismiss();
        }
    }
}
