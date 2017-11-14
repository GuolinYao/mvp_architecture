package com.hishixi.tiku.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.List;

public class FileUtil {

    private static final String HTTP_CACHE_DIR = "http";

    public static File getHttpCacheDir(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return new File(context.getExternalCacheDir(), HTTP_CACHE_DIR);
        }
        return new File(context.getCacheDir(), HTTP_CACHE_DIR);
    }

    /**
     * Get PDF file Intent
     */
    public static Intent openPdfFileIntent(String filePath, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Uri uri = Uri.fromFile(new File(filePath));
        Uri uri = FileProvider.getUriForFile(context, "com.hishixi.tiku.fileprovider", new
                File(filePath));
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    /**
     * 打开Word文件的intent
     *
     * @param filePath 文件路径
     * @return Intent
     */
    public static Intent openWordFileIntent(String filePath, Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Uri uri = Uri.fromFile(new File(filePath));
        Uri uri = FileProvider.getUriForFile(context, "com.hishixi.tiku.fileprovider", new
                File(filePath));
        intent.setDataAndType(uri, "application/msword");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return intent;
    }

    /**
     * 打开Excel文件的Intent
     *
     * @param filePath filePath
     * @return Intent
     */
    public  static Intent openExcelFileIntent(String filePath, Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Uri uri = Uri.fromFile(new File(filePath));
        Uri uri = FileProvider.getUriForFile(context, "com.hishixi.tiku.fileprovider", new
                File(filePath));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        // 授予目录临时共享权限
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
//                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        return intent;
    }
    /**
     * 判断Intent 是否存在 防止崩溃
     *
     * @param context  context
     * @param  intent intent
     * @return boolean
     */
    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.GET_ACTIVITIES);
        return list.size() > 0;
    }

}
