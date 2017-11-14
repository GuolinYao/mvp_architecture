package com.hishixi.tiku.constants;

import android.os.Environment;

import java.io.File;

/**
 * 常用常量
 */
public class Constants {
    // 共用的通知action
    public static final String ALL_UPDATE_ACTION = "all.update.action";
    public static final long HTTP_CONNECT_TIMEOUT = 15000;//网络连接超时时间
    public static final long HTTP_READ_TIMEOUT = 20000;//网络
    public static final long HTTP_CACHE_SIZE = 20 * 1024 * 1024;
    public static final int VISIBLE_THRESHOLD = 2;//recyclerview 可见阈值
    // 上传图片之前图片的保存地址
    public static String avatarPath = Environment.getExternalStorageDirectory() + File
            .separator + "Android" + File.separator + "data" + File.separator + "hishixi_mentor"
            + File.separator + "photo";
    // 上传图片之前图片的保存地址
    public static String ImageLoadPath = Environment.getExternalStorageDirectory() + File
            .separator + "Android" + File.separator + "data" + File.separator + "hishixi_mentor"
            + File.separator + "ImageUpload";
    // 上传多图时的图片地址
    public static String postImgePath = Environment.getExternalStorageDirectory() + File
            .separator + "Android" + File.separator + "data" + File.separator + "hishixi_mentor"
            + File.separator + "postImagePath";

    //订单附件
    public static String ATTACHMENT_ORDER = Environment.getExternalStorageDirectory()
            + File.separator + "Android" + File.separator + "data" + File.separator +
            "hishixi_mentor" + File.separator + "attachment_order" + File.separator;
    // 上传图片之前图片的保存地址
    public static String photoPath = Environment.getExternalStorageDirectory() + File
            .separator + "hishixi" + File.separator + "mentor" + File.separator + "photo";
    //个人资料图片
    public static String personalIntroPic = photoPath + File.separator + "intro_pic";
    //将要上传的文件
    public static String uploadIntroPic = photoPath + File.separator + "upload_pic";
    public static String cachePic = photoPath + File.separator + "cache_pic";
}
