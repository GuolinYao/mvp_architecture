package com.hishixi.tiku.custom.imageloader;

import android.content.Context;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hishixi.tiku.utils.FileModel;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Glide加载图片策略
 * Created by guolinyao on 16/12/29 10:18.
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {

    /**
     * 无holder的gif加载
     *
     * @param url
     * @param imageView
     */
    @Override
    public void loadImage(String url, ImageView imageView) {
        Glide.with(imageView.getContext()).load(url).dontAnimate()
                .placeholder(imageView.getDrawable())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    @Override
    public void loadCircleImage(String url, ImageView imageView,int placeholder) {
        Glide.with(imageView.getContext()).load(url).dontAnimate()
                .placeholder(placeholder)
                .centerCrop()
                .bitmapTransform(new CropCircleTransformation(imageView.getContext()))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    @Override
    public void loadRoundCornerImage(String url, int placeholder, ImageView imageView,
                                     int radius, int margin) {
        loadRoundCornerImage(url, placeholder, imageView, radius, margin,
                RoundedCornersTransformation.CornerType.ALL);
    }

    @Override
    public void loadRoundCornerImage(String url, int placeholder, ImageView imageView,
                                     int radius, int margin,
                                     RoundedCornersTransformation.CornerType cornerType) {
        Glide.with(imageView.getContext()).load(url)
                .dontAnimate()
                .placeholder(placeholder)
                .bitmapTransform(new CenterCrop(imageView.getContext()),
                        new RoundedCornersTransformation(imageView.getContext(),
                                radius, margin, cornerType))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    @Override
    public void loadImage(String url, int placeholder, ImageView imageView) {
        loadNormal(imageView.getContext(), url, placeholder, imageView);
    }

    @Override
    public void loadImage(Context context, String url, int placeholder, ImageView
            imageView) {
        loadNormal(context, url, placeholder, imageView);
    }

    @Override
    public void loadGifImage(String url, int placeholder, ImageView imageView) {
        loadGif(imageView.getContext(), url, placeholder, imageView);
    }

//    @Override
//    public void loadImageWithProgress(String url, ImageView imageView,
// ProgressLoadListener listener) {
//
//    }

//    @Override
//    public void loadGifWithProgress(String url, ImageView imageView,
// ProgressLoadListener listener) {
//
//    }

    @Override
    public void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context.getApplicationContext()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context.getApplicationContext()).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context.getApplicationContext()).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void trimMemory(Context context, int level) {
        Glide.get(context).trimMemory(level);
    }

    @Override
    public String getCacheSize(Context context) {
        try {
            return FileModel.getFormatSize(FileModel.getFolderSize(Glide
                    .getPhotoCacheDir(context.getApplicationContext())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * load image with Glide
     */
    private void loadNormal(final Context ctx, final String url, int placeholder,
                            ImageView imageView) {
        /**
         *  为其添加缓存策略,其中缓存策略可以为:Source及None,None及为不缓存,Source缓存原型.如果为ALL和Result就不行
         *  .然后几个issue的连接:
         https://github.com/bumptech/glide/issues/513
         https://github.com/bumptech/glide/issues/281
         https://github.com/bumptech/glide/issues/600
         modified by xuqiang
         */

        //去掉动画 解决与CircleImageView冲突的问题 这个只是其中的一个解决方案
        //使用SOURCE 图片load结束再显示而不是先显示缩略图再显示最终的图片（导致图片大小不一致变化）
        final long startTime = System.currentTimeMillis();
        Glide.with(ctx).load(url).dontAnimate()
                .placeholder(placeholder)
//                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable>
                    target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model,
                                           Target<GlideDrawable> target, boolean
                                                   isFromMemoryCache, boolean
                                                   isFirstResource) {
                return false;
            }
        }).into(imageView);
    }

    /**
     * load image with Glide
     */
    private void loadGif(final Context ctx, String url, int placeholder, ImageView
            imageView) {
        final long startTime = System.currentTimeMillis();
        Glide.with(ctx).load(url).asGif().dontAnimate()
                .placeholder(placeholder).skipMemoryCache(true)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(new RequestListener<String, GifDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GifDrawable>
                    target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, String model,
                                           Target<GifDrawable> target, boolean
                                                   isFromMemoryCache, boolean
                                                   isFirstResource) {
                return false;
            }
        })
                .into(imageView);
    }
}
