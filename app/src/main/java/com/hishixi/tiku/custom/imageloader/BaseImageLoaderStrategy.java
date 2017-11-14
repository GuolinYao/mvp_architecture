package com.hishixi.tiku.custom.imageloader;

import android.content.Context;
import android.widget.ImageView;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 图片加载策略接口
 * Created by guolinyao on 16/12/29 10:16.
 */
public interface BaseImageLoaderStrategy {

    //无占位图
    void loadImage(String url, ImageView imageView);

    void loadImage(String url, int placeholder, ImageView imageView);

    void loadImage(Context context, String url, int placeholder, ImageView imageView);

    void loadGifImage(String url, int placeholder, ImageView imageView);

//    void loadImageWithProgress(String url, ImageView imageView, ProgressLoadListener
// listener);

//    void loadGifWithProgress(String url, ImageView imageView, ProgressLoadListener
// listener);

    //清除硬盘缓存
    void clearImageDiskCache(final Context context);

    //清除内存缓存
    void clearImageMemoryCache(Context context);

    //根据不同的内存状态，来响应不同的内存释放策略
    void trimMemory(Context context, int level);

    //获取缓存大小
    String getCacheSize(Context context);

    //加载圆形图
    void loadCircleImage(String url, ImageView imageView, int placeholder);

    //加载圆角图片
    void loadRoundCornerImage(String url, int placeholder, ImageView imageView, int
            radius, int margin);

    //加载不同类型的圆角图片
    void loadRoundCornerImage(String url, int placeholder, ImageView imageView, int
            radius, int margin, RoundedCornersTransformation.CornerType cornerType);
}
