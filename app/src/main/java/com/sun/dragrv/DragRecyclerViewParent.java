package com.sun.dragrv;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class DragRecyclerViewParent extends RelativeLayout implements NestedScrollingParent {

    public interface IRecyclerViewParentListener {
        void onFlingDown();
        void onFlingUp();
    }

    private IRecyclerViewParentListener recyclerViewParentListener = null;

    public void setRecyclerViewParentListener(IRecyclerViewParentListener listener){
        recyclerViewParentListener = listener;
    }

    public DragRecyclerViewParent(Context context) {
        super(context);
    }

    public DragRecyclerViewParent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragRecyclerViewParent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DragRecyclerViewParent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        //sLog.i(null, "onStartNestedScroll");
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        //sLog.i(null, "onNestedScrollAccepted" + nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        //sLog.i(null, "onStopNestedScroll");
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        //sLog.i(null, "onNestedScroll dyConsumed:" + dyConsumed + " dyUnconsumed："+dyUnconsumed);
        if (null != recyclerViewParentListener && dyUnconsumed < 0) {
            recyclerViewParentListener.onFlingDown();
        } else if (null != recyclerViewParentListener && dyUnconsumed > 0){
            recyclerViewParentListener.onFlingUp();
        }
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        //sLog.i(null, "onNestedPreScroll dx" + dx + "dy"+dy+"consumed:"+consumed);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        //sLog.i(null, "onNestedFling");
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        //sLog.i(null, "onNestedPreFling");
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        //sLog.i(null, "getNestedScrollAxes");
        return 0;
    }
}
