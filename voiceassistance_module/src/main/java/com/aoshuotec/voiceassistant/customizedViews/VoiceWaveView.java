package com.aoshuotec.voiceassistant.customizedViews;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by sunHy on 2018/7/31
 */
public class VoiceWaveView extends View {
    @SuppressWarnings("unused")
    public static final String TAG = "sunHy_VoiceWaveView";

    // 波浪线一轮时间
    public static final String VOICE_WAVE_KEY_TIME = "VOICE_WAVE_KEY_TIME";
    // 波浪线数量
    public static final String VOICE_WAVE_KEY_COUNT = "VOICE_WAVE_KEY_COUNT";
    // 波浪线幅度
    public static final String VOICE_WAVE_KEY_RANGE = "VOICE_WAVE_KEY_RANGE";
    // 波浪线幅度
    public static final String VOICE_WAVE_KEY_OFFSET = "VOICE_WAVE_KEY_OFFSET";
    //画笔颜色
    public static final String VOICE_WAVE_KEY_COLOR = "VOICE_WAVE_KEY_COLOR";

    // 画波浪线
    private Paint mPaint;
    // 波浪线轨迹
    private Path mPath;
    // view宽度
    private int mWidth;
    // view高度
    private int mHeight;
    //动画移动距离
    private int mAnimWidth = 0;
    // X轴偏移量
    private int mXOffset = 0;
    // Y轴偏移量 默认80
    private int mYOffset = 30;
    // 波峰数量为变量除4
    private int mCount = 12;
    // 动画一轮的时间 单位/秒
    private int mRoundTime = 80;
    //动画效果是否开启
    private boolean mAnimStart = false;
    //是否需要刷新界面 由于性能考虑 每7次或8次刷新好一些
    private int mIsNeed = 0;

    //在OnDraw中创建的对象
    private int viewY;
    private int tempXOffset;
    private int temp;
    private int average;

    public VoiceWaveView(Context context) {
        super(context);
        init();
    }

    public VoiceWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化
     */
    private void init() {

        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#B0E0E6"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);

        mPath = new Path();
        mPath.setFillType(Path.FillType.EVEN_ODD);

        viewY = 0;
        tempXOffset = 0;
        temp = 0;
        average = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        viewY = mHeight / 2;
        viewY = mHeight - mYOffset;
//        mYOffset = viewY - 10;
        tempXOffset = mAnimWidth + mXOffset;

        //不整除额外加波浪线
        temp = (mWidth % mCount) / (mWidth / mCount);

        //波浪平均宽度
        average = mWidth / mCount;

        // 绘制屏幕内的波浪

        mPath.reset();
        mPath.moveTo(tempXOffset, viewY);

        for (int i = 0; i < mCount + temp; ) {
            mPath.quadTo(average * (++i) + tempXOffset, viewY - mYOffset, average * (++i) + tempXOffset, viewY);
            mPath.moveTo(average * i + tempXOffset, viewY);
            mPath.quadTo(average * (++i) + tempXOffset, mYOffset + viewY, average * (++i) + tempXOffset, viewY);
            mPath.moveTo(average * i + tempXOffset, viewY);
        }

        // 绘制屏幕外的波浪
        mPath.moveTo(tempXOffset, viewY);
        mPath.quadTo(-average + tempXOffset, viewY + mYOffset, -average - average + tempXOffset, viewY);

        for (int i = 2; i < mCount + temp; ) {
            mPath.quadTo(-average * (++i) + tempXOffset, viewY - mYOffset, -average * (++i) + tempXOffset, viewY);
            mPath.moveTo(-average * i + tempXOffset, viewY);
            mPath.quadTo(-average * (++i) + tempXOffset, viewY + mYOffset, -average * (++i) + tempXOffset, viewY);
            mPath.moveTo(-average * i + tempXOffset, viewY);
        }

        mPath.addRect(0, viewY, mWidth, viewY + mYOffset, Path.Direction.CW);

        canvas.drawPath(mPath, mPaint);

        if (!mAnimStart) startAnim();

    }

    /**
     * 开始动画效果
     */
    private void startAnim() {

        mAnimStart = true;

        ValueAnimator animator = new ValueAnimator();
        //方向
        animator.setFloatValues(0, mWidth);
        //持续时间
        animator.setDuration(100 * mRoundTime);
        animator.setRepeatCount(-1);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimWidth = (int) (float) animation.getAnimatedValue();
                animation.setDuration(100 * mRoundTime);

                if (mIsNeed < 7) {
                    mIsNeed++;
                } else {
                    invalidate();
                    mIsNeed = 0;
                }
            }
        });

        animator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    /**
     * 测量宽度
     */
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        //默认500
        int result = 500;
        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        mWidth = result;
        return result;
    }

    /**
     * 测量高度
     */
    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        //默认500
        int result = 500;
        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        mHeight = specSize;
        return result;
    }


    /**
     * 更新数据
     *
     * @param bundle 三个数据 Y轴偏移量 速度 波峰数量
     */
    public void updateDate(Bundle bundle) {

        float hOffset = bundle.getFloat(VOICE_WAVE_KEY_RANGE, -1f);
        int speed = bundle.getInt(VOICE_WAVE_KEY_TIME);
        int count = bundle.getInt(VOICE_WAVE_KEY_COUNT);
        int offset = bundle.getInt(VOICE_WAVE_KEY_OFFSET);
        String color = bundle.getString(VOICE_WAVE_KEY_COLOR, "");
//        sLog.e(ConstV.TAG, "-----------------------------------------");
//        sLog.e(ConstV.TAG, "mYOffset : " + mYOffset);
        if (hOffset >= 0 && hOffset <= 100) {
//            sLog.e(TAG, "updateDate: ------>"+hOffset );
            mYOffset = (int) (((float) mHeight) / 2f * hOffset / 100f);
//            mYOffset = (mYOffset >= (mHeight / 2)) ? (mHeight / 2) - 1 : mYOffset;
//            sLog.e(ConstV.TAG, "mYOffset : " + mYOffset);
        }
//        sLog.e(ConstV.TAG, "-----------------------------------------");

        if (speed > 0) {
            mRoundTime = speed;
        }

        if (count > 0) {
            mCount = count;
        }

        if (offset > 0) {
            mXOffset = offset;
        }

        if (!TextUtils.isEmpty(color)) {
            mPaint.setColor(Color.parseColor(color));
        }

    }

}
