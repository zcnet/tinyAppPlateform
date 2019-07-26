package com.tinyapp.tinyappplateform.main;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.z.tinyapp.common.base.BaseView;

/**
 * Created by zhengfei on 2018/7/20.
 */

public interface MainView extends BaseView {
    void initalWeexBack(boolean isSuccess);
    void changedAdapter();
    void showMainContent();
    Context getContext();

    ViewGroup getllSearch();
    ViewGroup getllEdit();
    ViewGroup getllBigWeexCard();
    ListView getlvWeexnotice();
    LinearLayout getllCardWrap();

    void reloadSmallCard();
}
