package com.hishixi.tiku.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by seamus on 17/4/13 10:42
 */

public class SystemUtils {

    /**
     * 更新系统相册
     */
    public static void changeSystemPick(Context context, String mCameraName) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(mCameraName);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, "com.hishixi.tiku.fileprovider", file);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }

        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    private  void insertImage(Context context, File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                insertImage(context, f);
            }
        } else {
            insertImage(context, file);
        }
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file
                    .getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
