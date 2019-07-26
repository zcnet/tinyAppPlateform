package com.aoshuotec.voiceassistant.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by sun on 2018/9/21
 */

public class ScreenShotUtil {

    private static final int KEY_BASE = 1;
    public static final int KEY_SMALL = KEY_BASE * 2;
    public static final int KEY_ALL = KEY_SMALL * 2;

    @SuppressLint("PrivateApi")
    public static Bitmap getScreenShot(Context ctx, int constant) {
        Object o = null;
        Class sc = null;
        Bitmap bitmap = null;
        try {
            sc = Class.forName("android.view.SurfaceControl");
        } catch (ClassNotFoundException e) {
            Logg.e("sunScreenShot","find class error \n"+e);
        }
        Method method = null;

        if(sc ==null){
            Logg.e("sunScreenShot","can't find class android.view.SurfaceControl");
            return null;
        }

        try {
            method = sc.getMethod("screenshot", new Class[]{int.class, int.class});
        } catch (NoSuchMethodException e) {
            Logg.e("sunScreenShot","find method error\n"+e);
        }

        try {
            o = method.invoke(sc, 1280, 720);
        } catch (IllegalAccessException e) {
            Logg.e("sunScreenShot","method.invoke(sc, 1280, 720) error1\n"+e);
        } catch (InvocationTargetException e) {
            Logg.e("sunScreenShot","method.invoke(sc, 1280, 720) error2\n"+e);
        }

        if (o == null) {
            Logg.e("sunScreenShot","method.invoke(sc, 1280, 720) error");
            return null;
        }

        switch (constant) {
            case KEY_SMALL:
                bitmap = Bitmap.createBitmap(((Bitmap) o), 0, 592, 1280, 128);
                break;
            case KEY_ALL:
                bitmap = Bitmap.createBitmap(((Bitmap) o), 0, 0, 1280, 720);
                break;
        }
        return rsBlur(ctx,bitmap,8,1/4f);
    }

    private static Bitmap getTransparentBitmap(Bitmap sourceImg, int number){
        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];

        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg

                .getWidth(), sourceImg.getHeight());// 获得图片的ARGB值

//        number = number * 255 / 100;/
        for (int i = 0; i < argb.length; i++) {

            argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);

        }

        sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg

                .getHeight(), Bitmap.Config.ARGB_8888);

        return sourceImg;
    }


    /**
     * @param context 上下文
     * @param source  需要模糊的Bitmap
     * @param radius  模糊半径
     * @param scale   先缩放的比例
     * @return 模糊后的Bitmap
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static Bitmap rsBlur(Context context, Bitmap source, int radius, float scale) {

        //先缩小 再恢复原大小 效果为丢失像素
        int width = Math.round(source.getWidth() * scale);
        int height = Math.round(source.getHeight() * scale);
        Bitmap inputBmp = Bitmap.createScaledBitmap(source, width, height, false);

        RenderScript renderScript = RenderScript.create(context);

        Allocation input = Allocation.createFromBitmap(renderScript, inputBmp);
        Allocation output = Allocation.createTyped(renderScript, input.getType());

        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.
                create(
                        renderScript,
                        Element.U8_4(renderScript)
                );
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(inputBmp);
        renderScript.destroy();

        getTransparentBitmap(inputBmp,60);

        return inputBmp;
    }

}
