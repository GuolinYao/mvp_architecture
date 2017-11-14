package com.hishixi.tiku.custom.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.hishixi.tiku.R;
import com.hishixi.tiku.utils.ActivityUtils;
import com.hishixi.tiku.utils.StringUtils;

/**
 * 确定对话框
 *
 * @author guolin
 */
@SuppressLint("InflateParams")
public class CustomDialog {

    /**
     * 不带标题的提示对话框
     *
     * @param str str
     * @param confirmStr confirmStr
     * @param cancelStr cancelStr
     * @param context context
     * @param listener listener
     * @return Dialog
     */
    @SuppressLint("InflateParams")
    public static Dialog CancelAlertToDialog(final String str,
                                             final String confirmStr, final String
                                                     cancelStr, Context context,
                                             OnClickListener listener) {
        final Dialog dlg = new Dialog(context, R.style.CancelTheme_DataSheet);// 对话框主题
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.dialog_notitle_base, null);
        LayoutParams params = new LayoutParams(ActivityUtils.getScreenWidth()
                - ActivityUtils.dip2px(context, 90), LayoutParams.WRAP_CONTENT);
        TextView strDes = (TextView) layout.findViewById(R.id.tv_confirm_des);
        strDes.setText(str);
        final TextView confirmBtn = (TextView) layout
                .findViewById(R.id.tv_confrim_btn);
        confirmBtn.setText(confirmStr);
        final TextView cancelBtn = (TextView) layout
                .findViewById(R.id.tv_cancel_btn);
        cancelBtn.setText(cancelStr);
        cancelBtn.setOnClickListener(listener);
        confirmBtn.setOnClickListener(listener);
        Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.gravity = Gravity.CENTER_VERTICAL;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(true);
        dlg.setContentView(layout, params);
        dlg.show();
        return dlg;
    }

    /**
     * 不带标题的提示对话框
     *
     * @param str str
     * @param confirmStr confirmStr
     * @param cancelStr cancelStr
     * @param context context
     * @param listener listener
     * @return Dialog
     */
    @SuppressLint("InflateParams")
    public static Dialog CancelAlertToDialog(final SpannableString str,
                                             final String cancelStr, final String
                                                     confirmStr, Context context,
                                             OnClickListener listener) {
        final Dialog dlg = new Dialog(context, R.style.CancelTheme_DataSheet);// 对话框主题
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.dialog_notitle_base, null);
        LayoutParams params = new LayoutParams(ActivityUtils.getScreenWidth()
                - ActivityUtils.dip2px(context, 90), LayoutParams.WRAP_CONTENT);
        TextView strDes = (TextView) layout.findViewById(R.id.tv_confirm_des);
        strDes.setText(str);
        final TextView confirmBtn = (TextView) layout
                .findViewById(R.id.tv_confrim_btn);
        confirmBtn.setText(confirmStr);
        final TextView cancelBtn = (TextView) layout
                .findViewById(R.id.tv_cancel_btn);
        cancelBtn.setText(cancelStr);
        cancelBtn.setOnClickListener(listener);
        confirmBtn.setOnClickListener(listener);
        Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.gravity = Gravity.CENTER_VERTICAL;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(true);
        dlg.setContentView(layout, params);
        dlg.show();
        return dlg;
    }


    /**
     * 设置隐 动画
     *
     * @param view view
     * @param visibility visibility
     */
    public static void hideListAnimation(final View view, final int visibility) {
        TranslateAnimation ta = new TranslateAnimation(1, 0f, 1, 0f, 1, 0f, 1,
                1f);
        ta.setDuration(200);
        view.startAnimation(ta);
        ta.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(visibility);
            }
        });
    }

    /**
     * 显示动画
     */
    public static void showListAnimation(View view) {
        TranslateAnimation ta = new TranslateAnimation(1, 0f, 1, 0f, 1, 1f, 1,
                0f);
        ta.setDuration(200);
        view.startAnimation(ta);
    }

    /**
     * 带标题的提示框
     *
     * @param warnTitle warnTitle
     * @param warnContent warnContent
     * @param leftBtn leftBtn
     * @param rightBtn rightBtn
     * @param listener listener
     * @param context context
     * @return Dialog
     */
    public static Dialog warnDialogHasTitle(final String warnTitle,
                                            final String warnContent, final String
                                                    leftBtn,
                                            final String rightBtn,OnClickListener listener,
                                            final Context context) {
        final Dialog dlg = new Dialog(context, R.style.CancelTheme_DataSheet);// 对话框主题
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.dialog_base, null);
        TextView tvTitle = (TextView) layout.findViewById(R.id.tv_dialog_title);
        tvTitle.setText(StringUtils.isNotEmpty(warnTitle) ? warnTitle : "温馨提示");
        TextView strDes = (TextView) layout.findViewById(R.id.tv_confirm_des);
        strDes.setText(warnContent);
        final TextView confirmBtn = (TextView) layout
                .findViewById(R.id.tv_title_confrim_btn);
        confirmBtn.setText(rightBtn);
        final TextView cancelBtn = (TextView) layout
                .findViewById(R.id.tv_title_cancel_btn);
        cancelBtn.setText(leftBtn);
        cancelBtn.setOnClickListener(listener);
        confirmBtn.setOnClickListener(listener);
        Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.gravity = Gravity.CENTER_VERTICAL;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(false);
        LayoutParams params = new LayoutParams(ActivityUtils.getScreenWidth()
                - ActivityUtils.dip2px(context, 90), LayoutParams.WRAP_CONTENT);
        dlg.setContentView(layout, params);
        dlg.show();
        return dlg;
    }



    /**
     * 只有一个按钮的提示对话框
     *
     * @param context context
     * @param listener listener
     * @return Dialog
     */
    public static Dialog oneBtnTipsDialog(Context context, String content, OnClickListener listener) {
        final Dialog dlg = new Dialog(context, R.style.Supernatant_Theme);// 对话框主题
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.dialog_onebtn_tips, null);
        TextView tv_tips = (TextView) layout.findViewById(R.id.tv_tips);
        tv_tips.setText(content);
        TextView btn_confirm = (TextView) layout.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(listener);
        Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.gravity = Gravity.CENTER_VERTICAL;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(true);
        LayoutParams params = new LayoutParams(ActivityUtils.getScreenWidth()
                - ActivityUtils.dip2px(context, 90), LayoutParams.WRAP_CONTENT);
        dlg.setContentView(layout, params);
        dlg.show();
        return dlg;
    }

}
