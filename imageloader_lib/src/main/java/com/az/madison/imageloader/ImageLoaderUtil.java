package com.az.madison.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.az.madison.imageloader.glideprogress.ProgressLoadListener;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;


/**
 * Created by soulrelay on 2016/10/11 13:42.
 * Class Note:
 * use this class to load image,single instance
 */
public class ImageLoaderUtil {

    //图片默认加载类型 以后有可能有多种类型
    public static final int PIC_DEFAULT_TYPE = 0;

    //图片默认加载策略 以后有可能有多种图片加载策略
    public static final int LOAD_STRATEGY_DEFAULT = 0;

    private volatile static ImageLoaderUtil mInstance;
    //本应该使用策略模式，用基类声明，但是因为Glide特殊问题
    //持续优化更新
    private BaseImageLoaderStrategy mStrategy;

    public ImageLoaderUtil() {
        mStrategy = new GlideImageLoaderStrategy();
    }

    //单例模式，节省资源
    public static ImageLoaderUtil getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoaderUtil.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderUtil();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }


    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */
    public void loadImageUseFitWidth(Context context, final String imageUrl, int errorImageId, final ImageView imageView) {
        mStrategy.loadImageUseFitWidth(context, imageUrl, errorImageId, imageView);
    }

    /**
     * 统一使用App context
     * 可能带来的问题：http://stackoverflow.com/questions/31964737/glide-image-loading-with-application-context
     *
     * @param url
     * @param placeholder
     * @param imageView
     */
    public void loadImage(String url, int placeholder, ImageView imageView) {
        mStrategy.loadImage(imageView.getContext(), url, placeholder, imageView);
    }

    /**
     * 无缓存加载图片
     * @param url
     * @param imageView
     */
    public void loadImageNoCache(String url, ImageView imageView) {
        mStrategy.loadImageNoCache(url, imageView);
    }

    /**
     * 加载图片设置圆角
     */
    public void loadFilletImage(String url, ImageView imageView, BitmapTransformation bitmapTransformation) {
        mStrategy.loadFilletImage(url, imageView, bitmapTransformation);
    }

    /**
     * 加载一张bitmap
     */
    public void loadImage(Context context, String url, OnLoadBitmapListener listener) {
        mStrategy.loadImage(context, url, listener);
    }

    public void loadGifImage(String url, int placeholder, ImageView imageView) {
        mStrategy.loadGifImage(url, placeholder, imageView);
    }

    public void loadGifImage(int resID, ImageView imageView) {
        mStrategy.loadGifImage(resID, imageView);
    }

    public void loadImage(int resID, ImageView imageView) {
        mStrategy.loadImage(resID, imageView);
    }

    public void loadImage(String url, ImageView imageView) {
        mStrategy.loadImage(url, imageView);
    }

    public void loadImage(String url, ImageView imageView, OnLoadBitmapListener listener) {
        mStrategy.loadImage(url, imageView, listener);
    }

    public void loadImageWithAppCxt(String url, ImageView imageView) {
        mStrategy.loadImageWithAppCxt(url, imageView);
    }

    public void loadImageWithProgress(String url, ImageView imageView, ProgressLoadListener listener) {
        mStrategy.loadImageWithProgress(url, imageView, listener);
    }

    public void loadGifWithPrepareCall(String url, ImageView imageView, SourceReadyListener listener) {
        mStrategy.loadGifWithPrepareCall(url, imageView, listener);
    }

    /**
     * 策略模式的注入操作
     *
     * @param strategy
     */
    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        mStrategy = strategy;
    }

    /**
     * 需要展示图片加载进度的请参考 GalleryAdapter
     * 样例如下所示
     */

    /**
     * 清除图片磁盘缓存
     */
    public void clearImageDiskCache(final Context context) {
        mStrategy.clearImageDiskCache(context);
    }

    /**
     * 清除图片内存缓存
     */
    public void clearImageMemoryCache(Context context) {
        mStrategy.clearImageMemoryCache(context);
    }

    /**
     * 根据不同的内存状态，来响应不同的内存释放策略
     *
     * @param context
     * @param level
     */
    public void trimMemory(Context context, int level) {
        mStrategy.trimMemory(context, level);
    }

    /**
     * 清除图片所有缓存
     */
    public void clearImageAllCache(Context context) {
        clearImageDiskCache(context.getApplicationContext());
        clearImageMemoryCache(context.getApplicationContext());
    }

    /**
     * 获取缓存大小
     *
     * @return CacheSize
     */
    public String getCacheSize(Context context) {
        return mStrategy.getCacheSize(context);
    }

    public void saveImage(Context context, String url, String savePath, String saveFileName, ImageSaveListener listener) {
        mStrategy.saveImage(context, url, savePath, saveFileName, listener);
    }

    public void loadImageUseFitWidthAddListen(Context context, String imgUrl, ImageView imageView, OnLoadBitmapListener onLoadBitmapListener) {
        mStrategy.loadImageUseFitWidthAddListen(context, imgUrl, imageView, onLoadBitmapListener);
    }


    /**
     * 加载图图片监听
     */
    public interface OnLoadBitmapListener {
        /**
         * 开始加载
         */
        void onStart();

        /**
         * 加载成功
         */
        void onFinish(Bitmap bmp);

        /**
         * 加载失败
         */
        void onError(Exception e);
    }
}
