package com.aoshuotec.voiceassistant.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import com.z.tinyapp.utils.logs.sLog;
import com.squareup.picasso.Picasso;
import com.taobao.weex.bridge.JSCallback;

import java.io.File;
import java.io.IOException;

/**
 * Created by sun on 2019/4/3
 */

public class ImgUtils {

    private static final String TAG = "sunImgUtils";

    public static void composeImage(String markerImg, String otherImg, JSCallback callback) {

        File file = new File(markerImg);

        if (!file.exists()) {
            return;
        }

        Bitmap markerBitmap = null;
        Bitmap frontBitmap = null;
        try {
            markerBitmap = Picasso.get().load(file).get().copy(Bitmap.Config.ARGB_8888, true);
            frontBitmap = Picasso.get().load(otherImg).get();
        } catch (IOException e) {
            sLog.i(TAG, "composeImage: error -> ", e);
        }

        if (markerBitmap == null || frontBitmap == null) {
            sLog.i(TAG, "composeImage: markerBitmap ==null");
            return;
        }

        boolean small = false;

        if (markerBitmap.getHeight() < 80) {
            small = true;
        }

        Bitmap bmp = getRoundBitmap(frontBitmap, small);
        bmp = getBigMarkerBitmap(markerBitmap, bmp, small);

        callback.invokeAndKeepAlive(bmp);
    }


    private static Bitmap getRoundBitmap(Bitmap inBitmap, boolean isSmall) {
        int w = inBitmap.getWidth();
        int h = inBitmap.getHeight();
        Bitmap bmp;
        if (w > h) {
            w = h;
            bmp = Bitmap.createBitmap(inBitmap, (w - h) / 2, 0, h, h);
        } else {
            h = w;
            bmp = Bitmap.createBitmap(inBitmap, 0, (h - w) / 2, w, w);
        }

        float radius;

        if (isSmall) {
            radius = 21f;
        } else {
            radius = 30f;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(radius * 2 / w, radius * 2 / h);
        bmp = Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, false);
        Bitmap backBitmap = Bitmap.createBitmap(
                ((int) (radius * 2 + 2 * 2)),
                ((int) (radius * 2 + 2 * 2)),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(backBitmap);
        canvas.drawColor(Color.parseColor("#00FFFFFF"));
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0, 0, radius * 2 + 2 * 2, radius * 2 + 2 * 2);
        canvas.drawRoundRect(rectF, radius + 2, radius + 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bmp, null, rectF, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);
        RectF rect = new RectF(0, 0, radius * 2 + 2 * 2, radius * 2 + 2 * 2);
        canvas.drawArc(rect, 0, 360, false, paint);
        return backBitmap;
    }

    private static Bitmap getBigMarkerBitmap(Bitmap markerBitmap, Bitmap inBitmap, boolean isSmall) {
        Canvas canvas = new Canvas(markerBitmap);
        Paint paint = new Paint();
        if (isSmall) {
            canvas.drawBitmap(inBitmap, 6, 6, paint);

        } else {
            canvas.drawBitmap(inBitmap, 5.5f, 5f, paint);
        }
        return markerBitmap;
    }


}
