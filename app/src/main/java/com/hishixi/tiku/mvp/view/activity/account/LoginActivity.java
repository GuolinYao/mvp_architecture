package com.hishixi.tiku.mvp.view.activity.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hishixi.tiku.R;
import com.hishixi.tiku.app.BaseApplication;
import com.hishixi.tiku.custom.view.ClearEditTextViewInLogin;
import com.hishixi.tiku.injector.component.ApplicationComponent;
import com.hishixi.tiku.injector.component.DaggerLoginComponent;
import com.hishixi.tiku.injector.module.ActivityModule;
import com.hishixi.tiku.injector.module.LoginModule;
import com.hishixi.tiku.mvp.contract.LoginContract;
import com.hishixi.tiku.mvp.presenter.LoginPresenter;
import com.hishixi.tiku.mvp.view.activity.base.BaseActivity;
import com.hishixi.tiku.utils.CacheUtils;
import com.hishixi.tiku.utils.StringUtils;
import com.hishixi.tiku.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by seamus on 17/3/21 16:13.
 * 登录页面
 */

public class LoginActivity extends BaseActivity<LoginContract.View, LoginPresenter>
        implements LoginContract.View {

    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.cetv_account_number)
    ClearEditTextViewInLogin mCetvAccountNumber;
    @BindView(R.id.cetv_password)
    ClearEditTextViewInLogin mCetvPassword;
    @BindView(R.id.iv_show_password)
    ImageView mIvShowPassword;
    @BindView(R.id.fl_show_password)
    FrameLayout mFlShowPassword;
    @BindView(R.id.tv_forget_password)
    TextView mTvForgetPassword;
    private boolean ifShowPwd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public static void startAction(Activity context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void getIntentData() {
    }

    @Override
    public void initView() {
        super.initView();
        toolbar.setVisibility(View.GONE);
        Looper.prepare();
    }


    @OnClick(R.id.btn_login)
    void login() {
//        LoginActivityPermissionsDispatcher.getExtStorageWithCheck(this);
        String mobile = mCetvAccountNumber.getText().toString().trim();
        String password = mCetvPassword.getText().toString().trim();
        if (StringUtils.isNotEmpty(mobile) && StringUtils.isNotEmpty(password)) {
            CacheUtils.saveAccountMobile(this, mobile);
            mPresenter.login(mobile, password);
        }
    }

    @OnClick(R.id.fl_show_password)
    void showHidePwd() {
        ifShowPwd = !ifShowPwd;
        setPassWordShow();
    }

    /**
     * 设置密码的显示与隐藏
     */
    public void setPassWordShow() {
        mIvShowPassword.setImageResource(ifShowPwd ? R.drawable.icon_show_password : R
                .drawable.icon_hide_password);
        // 普通文本
        int inputtype_showpwd = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
        // 密码
        int inputtype_hidepwd = InputType.TYPE_CLASS_TEXT | InputType
                .TYPE_TEXT_VARIATION_PASSWORD;
        mCetvPassword.setInputType(ifShowPwd ? inputtype_showpwd : inputtype_hidepwd);
        mCetvPassword.setSelection(mCetvPassword.getText().toString().trim().length());
    }

    @OnClick(R.id.tv_forget_password)
    void forgetPassword() {
        ToastUtils.showToastInCenter("请联系客服");
    }


    @Override
    public void injectPresenter() {
        ApplicationComponent applicationComponent = ((BaseApplication) getApplication())
                .getApplicationComponent();
        DaggerLoginComponent.builder().applicationComponent
                (applicationComponent)
                .activityModule(new ActivityModule(this))
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);

    }

    @Override
    public void finishView() {
        finish();
    }


//    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//    void getExtStorage() {
//
//        ToastUtils.showToastInCenter("success");
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        LoginActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
//                grantResults);
//    }
//
//    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
//    void getExtStorageRationale(final PermissionRequest request) {
//        showRationaleDialog(R.string.app_name, request);
//    }
//
//    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
//    void getExtStorageDeny() {
//        ToastUtils.showToastInCenter("deny");
//    }
//
//    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
//    void getExtStorageDenyNeverAsk() {
//        ToastUtils.showToastInCenter("never ask again");
//    }
//
//    private void showRationaleDialog(@StringRes int messageResId, final PermissionRequest
// request) {
//        new AlertDialog.Builder(this)
//                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(@NonNull DialogInterface dialog, int which) {
//                        request.proceed();
//                    }
//                })
//                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(@NonNull DialogInterface dialog, int which) {
//                        request.cancel();
//                    }
//                })
//                .setCancelable(false)
//                .setMessage(messageResId)
//                .show();
//    }
}
