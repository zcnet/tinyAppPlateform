package com.z.tinyapp.utils.common;

/**
 * Created by kevin on 17/1/4.
 */

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.z.tinyapp.utils.R;

/**
 * Toast统一管理类
 */
public class ToastUtil {

    private ToastUtil() {
        throw new AssertionError();
    }


    public static void show(Context context, int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    public static void showLongCenter(Context context, CharSequence text, int duration) {
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showLongCenter(Context context, CharSequence text) {
        showLongCenter(context, text, Toast.LENGTH_LONG);
    }

    public static void show(Context context, int resId, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String format, Object... args) {
        show(context, String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), duration);
    }

    public static void show(Context context, String format, int duration, Object... args) {
        show(context, String.format(format, args), duration);
    }

    /** 上次提示错误消息时间 */
    private static long ERROR_TOAST_TIME = 0;
    /** 网络请求错误提醒 */
    public synchronized static void showNetErrorToast(Context contenxt) {
        if (System.currentTimeMillis() - ERROR_TOAST_TIME > 1 * 1000) {
            ToastUtil.show(contenxt, R.string.net_error);
            ERROR_TOAST_TIME=System.currentTimeMillis();
        }
    }

}
