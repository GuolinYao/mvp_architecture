package com.hishixi.tiku.utils;

import android.content.Context;
import android.os.Handler;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * jpush的util 设置别名和tag
 * Created by seamus on 17/6/21 14:51
 */

public class JpushUtils {
    private Context mContext;
    private volatile static JpushUtils jpushUtils;

    public JpushUtils() {
    }

    public static JpushUtils getInstance() {

        if (jpushUtils == null) {
            synchronized (JpushUtils.class) {
                if (jpushUtils == null)
                    jpushUtils = new JpushUtils();
            }
        }
        return jpushUtils;
    }

    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
    public void setAlias(String alias, Context context) {
        mContext = context;
        // 调用 Handler 来异步设置别名
        if (CacheUtils.getIfSetJpushAlias(mContext).equals("1")) return;
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    if (!alias.equals("0"))
                        CacheUtils.saveIfSetJpushAlias(mContext, "1");
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias),
                            1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(mContext.getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
            }
        }
    };
}
