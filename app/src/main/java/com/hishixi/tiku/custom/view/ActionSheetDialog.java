package com.hishixi.tiku.custom.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.hishixi.tiku.R;
import com.hishixi.tiku.utils.ActivityUtils;

/**
 * @author guolinyao
 * @date 2016年2月29日 上午10:35:04
 */
public class ActionSheetDialog {
    public interface OnAlertSelectId {
        void onClick(int whichButton);
    }

    public static Dialog showAlert(final Context context,
                                   final OnAlertSelectId alertDo, String selectOne,
                                   String selectTwo) {
        return showAlert(context, null, alertDo, selectOne, selectTwo);
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    @SuppressLint("InflateParams")
    public static Dialog showAlert(final Context context,
                                   OnCancelListener cancelListener, final
                                   OnAlertSelectId alertDo,
                                   String selectOne, String selectTwo) {
        final Dialog dlg = new Dialog(context, R.style.MMTheme);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.action_sheet_layout, null);
        dlg.setOnShowListener(dialog -> {
            AnimatorSet mAnimatorSet = new AnimatorSet();
//                ViewHelper.setPivotX(layout, layout.getMeasuredWidth() / 2.0f);
//                ViewHelper.setPivotY(layout, layout.getMeasuredHeight() );
            ObjectAnimator translationYAnimator = ObjectAnimator.ofFloat(layout,
                    "translationY", 200, 0).setDuration(300);
            ObjectAnimator fadeAnimator = ObjectAnimator.ofFloat(layout, "alpha",
                    0, 1).setDuration(300);

            mAnimatorSet.playTogether(translationYAnimator, fadeAnimator);
            mAnimatorSet.start();
        });
        final int cFullFillWidth = 10000;
        layout.setMinimumWidth(cFullFillWidth);
        final Button cancelBtn = (Button) layout
                .findViewById(R.id.btn_select_cancel);
        final Button oneBtn = (Button) layout.findViewById(R.id.btn_select_one);
        final Button twoBtn = (Button) layout.findViewById(R.id.btn_select_two);
        oneBtn.setText(selectOne);
        twoBtn.setText(selectTwo);
        cancelBtn.setOnClickListener(v -> dlg.dismiss());
        oneBtn.setOnClickListener(v -> {
            alertDo.onClick(1);
            dlg.dismiss();
        });

        twoBtn.setOnClickListener(v -> {
            alertDo.onClick(2);
            dlg.dismiss();
        });
        Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(true);
        if (cancelListener != null) {
            dlg.setOnCancelListener(cancelListener);
        }
        int width = ActivityUtils.getScreenWidth()
                - ActivityUtils.dip2px(context, 20);
        LayoutParams params = new LayoutParams(width, LayoutParams.WRAP_CONTENT);
        dlg.setContentView(layout, params);
        // dlg.setContentView(layout);
        dlg.show();
        return dlg;
    }

}
