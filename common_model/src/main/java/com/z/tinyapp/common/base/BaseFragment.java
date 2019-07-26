package com.z.tinyapp.common.base;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.z.tinyapp.common.R;
import butterknife.ButterKnife;
import butterknife.Unbinder;
/**
 * Created by kevin on 17/1/4.
 */

public abstract class BaseFragment extends Fragment {
    private View mContentView;

    private ProgressDialog mProgressDialog;

    private Unbinder unbinder;

    public Context mContext;

    protected abstract void initView();

    protected abstract void initData();

    protected <T> T findViewById(int id) {
        return (T) mContentView.findViewById(id);
    }

    protected void setListener() {
    }


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mContext=getActivity();
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_base_mall, container, false);
        View view = inflater.inflate(LayoutUtils.LayoutInflater(this), relativeLayout, false);
        unbinder = ButterKnife.bind(this, view);
        relativeLayout.addView(view);
        mContentView = view;
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        onViewInflateOver();
        return relativeLayout;
    }

    //View初始化结束后后调用此方法
    protected void onViewInflateOver() {
    }

    @Override
    public final void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setListener();
        initData();
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
     * 显示loading视图
     */
    public void showLoading() {
        showLoading(false);
    }


    /**
     * 显示loading视图
     *
     * @param isPaddingTop 是否置顶对齐
     */
    public void showLoading(boolean isPaddingTop) {
        if (!this.isAdded()) {
            return;
        }
        setInVisible(mContentView);
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
        if(mProgressDialog!=null&&mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
