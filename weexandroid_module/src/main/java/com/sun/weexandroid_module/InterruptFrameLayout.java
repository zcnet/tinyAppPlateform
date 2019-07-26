package com.sun.weexandroid_module;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by sun on 2019/4/19
 */

public class InterruptFrameLayout extends FrameLayout {

    private boolean isInterrupt = false;

    public InterruptFrameLayout(@NonNull Context context) {
        super(context);
    }

    public InterruptFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InterruptFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public InterruptFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setInterrupt(boolean b) {
        isInterrupt = b;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isInterrupt;
    }
}
