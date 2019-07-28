package com.z.voiceassistant.customizedViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by GongDongdong on 2018/8/16.
 */

@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    long timeLimit = 1000;
    public long exactLimit = 3000;
    long gap = 0;

    class MyRunnable implements Runnable {
        public boolean isRun = false;

        @Override
        public void run() {

        }
    }

    List<MyRunnable> runnableList = new LinkedList<>();

    public void setTextString(final List<String> myText2Show) {
        if (myText2Show == null || myText2Show.size() == 0) {
            return;
        }
        runnableList.clear();
        int size = myText2Show.size();
        gap = timeLimit / size;
        exactLimit = gap * size;

        for (int i = 0; i < size; i++) {
            final int num = i;
            MyRunnable runnable = new MyRunnable() {
                @Override
                public void run() {
                    String str2show = myText2Show.get(num);
                    MyTextView.this.setText(str2show);
                    isRun = true;
                }
            };
            runnableList.add(runnable);
            postDelayed(runnable, i * gap);

        }
    }

    public boolean checkFinishedAndShowFinal() {
        boolean isFinished = true;
        MyRunnable togetLastOne = null;
        for (MyRunnable runnable : runnableList) {
            if (!runnable.isRun) {
                isFinished = false;
                removeCallbacks(runnable);
            }
            togetLastOne = runnable;
        }
        if (!isFinished) {
            post(togetLastOne);
        }
        return isFinished;
    }
}
