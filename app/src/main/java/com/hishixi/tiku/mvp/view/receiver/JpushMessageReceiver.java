//package com.hishixi.tiku.mvp.view.receiver;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.content.LocalBroadcastManager;
//import android.text.TextUtils;
//
//import com.hishixi.tiku.utils.ActivityUtils;
//import com.hishixi.tiku.utils.StringUtils;
//import com.orhanobut.logger.Logger;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.Iterator;
//
//import cn.jpush.android.api.JPushInterface;
//
///**
// * jpush消息接收receiver
// * * 如果不定义这个 Receiver，则：
// * 1) 默认用户会打开主界面
// * 2) 接收不到自定义消息
// * Created by seamus on 17/6/21 15:16
// */
//
//public class JpushMessageReceiver extends BroadcastReceiver {
//    private static final String TAG = "JPush";
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        try {
//            Bundle bundle = intent.getExtras();
//            Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " +
//                    printBundle(bundle));
//
//            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//                Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
//                //send the Registration Id to your server...
//
//            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//                Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface
//                        .EXTRA_MESSAGE));
//                processCustomMessage(context, bundle);
//
//            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
//                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
//
//            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//                Logger.d(TAG, "[MyReceiver] 用户点击打开了通知");
//                Intent i;
//                JSONObject jsonObject = new JSONObject(bundle.getString(JPushInterface
//                        .EXTRA_EXTRA));
//                String page = "";
//                if (bundle.getString(JPushInterface.EXTRA_EXTRA).contains("page")) {
//                    page = jsonObject.getString("page");
//                }
//                if (ActivityUtils.isActivityRunning(context, IndexActivity.class.getName())) {
//                    switch (page) {
//                        case "msg_detail":
//                            i = new Intent(context, MessageDetailActivity.class);
//                            break;
//                        default:
//                            i = new Intent(context, IndexActivity.class);
//                            break;
//                    }
//                } else {
//                    i = new Intent(context, IndexActivity.class);
//                    i.putExtra("from", 2);
//                }
//                if (bundle.getString(JPushInterface.EXTRA_EXTRA).contains("url"))
//                    i.putExtra("url", jsonObject.getString("url"));
//                if (bundle.getString(JPushInterface.EXTRA_EXTRA).contains("order_num"))
//                    i.putExtra("order_num", jsonObject.getString("order_num"));
////                i.putExtras(bundle);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(i);
//
//            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
//                Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString
//                        (JPushInterface.EXTRA_EXTRA));
//                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
//
//            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
//                boolean connected = intent.getBooleanExtra(JPushInterface
//                        .EXTRA_CONNECTION_CHANGE, false);
//                Logger.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to "
//                        + connected);
//            } else {
//                Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 打印所有的 intent extra 数据
//    private static String printBundle(Bundle bundle) {
//        StringBuilder sb = new StringBuilder();
//        for (String key : bundle.keySet()) {
//            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
//                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
//            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
//                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
//            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
//                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
//                    Logger.i(TAG, "This message has no Extra data");
//                    continue;
//                }
//                try {
//                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
//                    Iterator<String> it = json.keys();
//
//                    while (it.hasNext()) {
//                        String myKey = it.next().toString();
//                        sb.append("\nkey:" + key + ", value: [" +
//                                myKey + " - " + json.optString(myKey) + "]");
//                    }
//                } catch (JSONException e) {
//                    Logger.e(TAG, "Get message extra JSON error!");
//                }
//
//            } else {
//                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
//            }
//        }
//        return sb.toString();
//    }
//
//    //send msg to IndexActivity
//    private void processCustomMessage(Context context, Bundle bundle) {
//        if (IndexActivity.isForeground) {
//            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//            Intent msgIntent = new Intent(IndexActivity.MESSAGE_RECEIVED_ACTION);
//            msgIntent.putExtra(IndexActivity.KEY_MESSAGE, message);
//            if (StringUtils.isNotEmpty(extras)) {
//                try {
//                    JSONObject extraJson = new JSONObject(extras);
//                    if (extraJson.length() > 0) {
//                        msgIntent.putExtra(IndexActivity.KEY_EXTRAS, extras);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//        }
//    }
//}
