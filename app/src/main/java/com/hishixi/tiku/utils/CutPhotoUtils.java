package com.hishixi.tiku.utils;

import android.content.Intent;
import android.net.Uri;

/**
 * 裁剪图片
 *
 * @author guolin
 */
public class CutPhotoUtils {
    /**
     * @param uri 图片的uri
     */
    public static Intent startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");
        // 裁剪框的宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪图片的宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        // 黑边
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", true);

        return intent;
    }
}
