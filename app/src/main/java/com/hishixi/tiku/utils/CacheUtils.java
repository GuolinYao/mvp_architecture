package com.hishixi.tiku.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 常用小数据的保存
 *
 * @author Administrator
 */
public class CacheUtils {
    private static SharedPreferences userPreferences;// 个人信息
    private static SharedPreferences tabPositionPreferences;// 底部导航点击的位置

    /**
     * 保存token
     *
     * @param context context
     * @param token   token
     */
    public static void saveToken(Context context, String token) {
        userPreferences = context.getSharedPreferences("userinfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    /**
     * 获取token的时间
     *
     * @param context context
     * @return String
     */
    public static String getTokenTime(Context context) {
        userPreferences = context.getSharedPreferences("userinfo",
                Context.MODE_PRIVATE);
        return userPreferences.getString("tokenTime", "");
    }

    public static void saveTokenTime(Context context, String tokenTime) {
        userPreferences = context.getSharedPreferences("userinfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();
        editor.putString("tokenTime", tokenTime);
        editor.apply();
    }

    /**
     * 获取token
     *
     * @param context context
     * @return String
     */
    public static String getToken(Context context) {
        userPreferences = context.getSharedPreferences("userinfo",
                Context.MODE_PRIVATE);
        return userPreferences.getString("token", "");
    }

    /**
     * 保存账号id
     *
     * @param context   context
     * @param accountId accountId
     */
    public static void saveAccountId(Context context, String accountId) {
        userPreferences = context.getSharedPreferences("userinfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();
        editor.putString("accountId", accountId);
        editor.apply();
    }

    public static String getAccountId(Context context) {
        userPreferences = context.getSharedPreferences("userinfo",
                Context.MODE_PRIVATE);
        return userPreferences.getString("accountId", "");
    }
    /**
     * 保存账号是否已经完善信息
     *
     * @param context   context
     * @param isOpen isOpen  1 已完善 2 未完善
     */
    public static void saveIsOpen(Context context, String isOpen) {
        userPreferences = context.getSharedPreferences("userinfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();
        editor.putString("isOpen", isOpen);
        editor.apply();
    }

    public static String getIsOpen(Context context) {
        userPreferences = context.getSharedPreferences("userinfo",
                Context.MODE_PRIVATE);
        return userPreferences.getString("isOpen", "2");
    }

    /**
     * 保存登录手机
     *
     * @param context context
     */
    public static void saveAccountMobile(Context context, String mobile) {
        userPreferences = context.getSharedPreferences("userinfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();
        editor.putString("mobile", mobile);
        editor.apply();
    }

    public static String getAccountMobile(Context context) {
        userPreferences = context.getSharedPreferences("userinfo",
                Context.MODE_PRIVATE);
        return userPreferences.getString("mobile", "");
    }

    /**
     * 保存学生头像
     *
     * @param context context
     */
    public static void saveStudentImage(Context context, String studentImage) {
        userPreferences = context.getSharedPreferences("userinfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();
        editor.putString("image", studentImage);
        editor.apply();
    }

    public static String getStudentImage(Context context) {
        userPreferences = context.getSharedPreferences("userinfo",
                Context.MODE_PRIVATE);
        return userPreferences.getString("image", "");
    }

    /**
     * 保存上次获取token的时间
     *
     * @param context context
     * @param oldTime oldTime
     */
    public static void saveOldGetTokenTime(Context context, long oldTime) {
        // 存本地时间，两个本地时间相比较减小误差
        userPreferences = context.getSharedPreferences("userinfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();
        // editor.putLong("oldTime", oldTime);
        editor.putLong("oldTime", DateUtils.getCurrentTimeSecond());
        editor.apply();
    }

    public static long getOldGetTokenTime(Context context) {
        try {
            userPreferences = context.getSharedPreferences("userinfo",
                    Context.MODE_PRIVATE);
            return userPreferences.getLong("oldTime", 0);
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * 清除用户信息
     *
     * @param context context
     */
    public static void clearUserInfo(Context context) {
        userPreferences = context.getSharedPreferences("userinfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 保存是否是第一次打开
     *
     * @param context context
     * @param isFirst isFirst
     */
    public static void saveIsFirstOpen(Context context, boolean isFirst) {
        userPreferences = context.getSharedPreferences("installInfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();
        editor.putBoolean("isFirstOpen", isFirst);
        editor.apply();
    }

    public static boolean getIsFirstOpen(Context context) {
        userPreferences = context.getSharedPreferences("installInfo",
                Context.MODE_PRIVATE);
        return userPreferences.getBoolean("isFirstOpen", true);
    }

    /**
     * 保存是否是第一次登陆
     *
     * @param context context
     * @param isFirst isFirst
     */
    public static void saveIsFirstLogin(Context context, boolean isFirst) {
        userPreferences = context.getSharedPreferences("installInfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();
        editor.putBoolean("isFirstLogin", isFirst);
        editor.apply();
    }

    public static boolean getIsFirstLogin(Context context) {
        userPreferences = context.getSharedPreferences("installInfo",
                Context.MODE_PRIVATE);
        return userPreferences.getBoolean("isFirstLogin", true);
    }

    /**
     * 保存底部导航点击的位置
     *
     * @param context context
     * @param index   index
     */
    public static void saveIndex(Context context, int index) {
        tabPositionPreferences = context.getSharedPreferences("tabindex",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = tabPositionPreferences.edit();
        editor.putInt("index", index);
        editor.apply();
    }

    public static int getIndex(Context context) {
        tabPositionPreferences = context.getSharedPreferences("tabindex",
                Context.MODE_PRIVATE);
        return tabPositionPreferences.getInt("index", 0);
    }

    public static void clearTabIndex(Context context) {
        tabPositionPreferences = context.getSharedPreferences("tabindex",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = tabPositionPreferences.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 保存JpushAlias
     *
     * @param context  context
     * @param status  1 已设置 0 未设置
     */
    public static void saveIfSetJpushAlias(Context context, String status) {
        userPreferences = context.getSharedPreferences("userinfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();
        editor.putString("ifSetJpushAlias", status);
        editor.apply();
    }

    /**
     * 获取是否已经设置别名
     * @param context context
     * @return 1 已设置 0 未设置
     */
    public static String getIfSetJpushAlias(Context context) {
        userPreferences = context.getSharedPreferences("userinfo",
                Context.MODE_PRIVATE);
        return userPreferences.getString("ifSetJpushAlias", "");
    }

}
