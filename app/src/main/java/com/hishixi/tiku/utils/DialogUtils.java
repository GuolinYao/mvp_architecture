package com.hishixi.tiku.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import permissions.dispatcher.PermissionRequest;

/**
 * Created by seamus on 17/4/12 10:30
 */

public class DialogUtils {

    public static void showRationaleDialog(Context context, @StringRes int messageResId, final
    PermissionRequest request) {
        new AlertDialog.Builder(context)
                .setPositiveButton("允许", (dialog, which) -> request.proceed())
                .setNegativeButton("拒绝", (dialog, which) -> request.cancel())
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }
}
