package com.hishixi.tiku.custom.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.hishixi.tiku.R;
import com.hishixi.tiku.utils.DisplayImageOptionsUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 *    // File cacheDir = StorageUtils.getOwnCacheDirectory(context,
 // "shixiAndroid/image/");
 // // 默认配置对象
 // DisplayImageOptions.Builder oBuilder = new
 // DisplayImageOptions.Builder()
 // // 缓存到内存
 // .cacheInMemory()
 // // 缓存到SD卡
 // .cacheOnDisc()
 // // 加载开始前的默认图片
 // .showStubImage(R.drawable.touxiang_listdefault).showImageForEmptyUri(R
 // .drawable.touxiang_listdefault)
 // .showImageOnFail(R.drawable.touxiang_listdefault)
 // // 设置图片显示为圆角
 // .displayer(new RoundedBitmapDisplayer(30))
 // // 图片质量，防止内存溢出
 // .bitmapConfig(Bitmap.Config.RGB_565);
 // // 图片圆角显示，值为整数，不建议使用容易内存溢出
 // // .displayer(new RoundedBitmapDisplayer(15));
 // DisplayImageOptions options = oBuilder.build();
 //
 // ImageLoaderConfiguration.Builder cBuilder = new
 // ImageLoaderConfiguration.Builder(context)
 // .defaultDisplayImageOptions(options);
 //
 // ImageLoaderConfiguration config = cBuilder
 // // 缓存在内存的图片的最大宽和高度，超过了就缩小
 // .memoryCacheExtraOptions(480, 800)
 // // CompressFormat.PNG类型，70质量（0-100）
 // // .discCacheExtraOptions(400, 400, CompressFormat.PNG, 70, null)
 // // 线程池大小
 // .threadPoolSize(5)
 // // 线程优先级
 // .threadPriority(Thread.NORM_PRIORITY - 2)
 // // 弱引用内存
 // .memoryCache(new WeakMemoryCache())
 // // 内存缓存大小
 // // .memoryCacheSize(20 * 1024 * 1024)
 // // 本地缓存大小
 // // .discCacheSize(50 * 1024 * 1024)
 // // 禁止同一张图多尺寸缓存
 // .denyCacheImageMultipleSizesInMemory()
 // // 设置硬盘缓存路径并不限制硬盘缓存大小
 // // .discCache(new
 // UnlimitedDiscCache(cacheDir)).discCacheFileNameGenerator(new
 // Md5FileNameGenerator())
 // .tasksProcessingOrder(QueueProcessingType.LIFO).build();
 // myImageLoader =
 // com.nostra13.universalimageloader.core.ImageLoader.getInstance();
 // // 禁用输出log功能
 // myImageLoader.init(config);
 * Created by seamus on 17/4/20 14:00
 */

public class ImageLoaderStrategy implements BaseImageLoaderStrategy {
    @Override
    public void loadImage(String url, ImageView imageView) {
        loadImage(url, R.drawable.default_image_banner,imageView);
    }

    @Override
    public void loadImage(String url, int placeholder, ImageView imageView) {
        DisplayImageOptions options = DisplayImageOptionsUtils
                .getBigBitMap(placeholder);
        try {
            ImageLoader.getInstance().displayImage(url, imageView, options);
        } catch (Exception e) {
            ImageLoader.getInstance().displayImage(
                    "drawable://" + placeholder, imageView, options);
        }
    }

    @Override
    public void loadImage(Context context, String url, int placeholder, ImageView imageView) {
        loadImage(url,placeholder,imageView);
    }

    @Override
    public void loadGifImage(String url, int placeholder, ImageView imageView) {

    }

    @Override
    public void clearImageDiskCache(Context context) {

    }

    @Override
    public void clearImageMemoryCache(Context context) {

    }

    @Override
    public void trimMemory(Context context, int level) {

    }

    @Override
    public String getCacheSize(Context context) {
        return null;
    }

    @Override
    public void loadCircleImage(String url, ImageView imageView, int placeholder) {

    }

    @Override
    public void loadRoundCornerImage(String url, int placeholder, ImageView imageView, int
            radius, int margin) {
        DisplayImageOptions options = DisplayImageOptionsUtils.getRoundBitMap(
                radius, placeholder);
        try {
            ImageLoader.getInstance().displayImage(url, imageView, options);
        } catch (Exception e) {
            ImageLoader.getInstance().displayImage(
                    "drawable://" + placeholder, imageView, options);
        }
    }

    @Override
    public void loadRoundCornerImage(String url, int placeholder, ImageView imageView, int
            radius, int margin, RoundedCornersTransformation.CornerType cornerType) {

    }
}
