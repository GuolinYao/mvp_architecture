package com.hishixi.tiku.mvp.view.activity.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.hishixi.tiku.injector.component.DaggerFindPasswordComponent;
import com.hishixi.tiku.injector.module.ActivityModule;
import com.hishixi.tiku.injector.module.FindPasswordModule;
import com.hishixi.tiku.mvp.contract.FindPasswordContract;
import com.hishixi.tiku.mvp.presenter.FindPasswordPresenter;
import com.hishixi.tiku.mvp.view.activity.base.BaseActivity;
import com.hishixi.tiku.utils.CacheUtils;
import com.hishixi.tiku.utils.StringUtils;
import com.hishixi.tiku.utils.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by seamus on 17/4/14 14:46
 */

public class FindPasswordActivity extends BaseActivity<FindPasswordContract.View,
        FindPasswordPresenter> implements FindPasswordContract.View {

    private static final long SECOND = 60;
    @BindView(R.id.tv_mobile)
    TextView mTvMobile;
    @BindView(R.id.cetv_verification_code)
    ClearEditTextViewInLogin mCetvVerificationCode;
    @BindView(R.id.btn_get_verification_code)
    Button mBtnGetVerificationCode;
    @BindView(R.id.cetv_password)
    ClearEditTextViewInLogin mCetvPassword;
    @BindView(R.id.iv_show_password)
    ImageView mIvShowPassword;
    @BindView(R.id.fl_show_password)
    FrameLayout mFlShowPassword;
    @BindView(R.id.btn_reset_password)
    Button mBtnResetPassword;
    private Observable<Object> mVerifyCodeObservable;
    private boolean ifShowPwd = true;

    public static void startAction(Activity context) {
        Intent intent = new Intent(context, FindPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void getIntentData() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
        initVerify();
    }

    @Override
    public void initView() {
        super.initView();
        mTvTitle.setText("修改密码");
        mTvMobile.setText("当前登录手机号：" + CacheUtils.getAccountMobile(this));
    }

    /**
     * 初始化获取验证码控件 使用RxBinding
     */
    private void initVerify() {
        mVerifyCodeObservable = RxView.clicks(mBtnGetVerificationCode)
                .throttleFirst(SECOND, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Object>() {

                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        RxView.enabled(mBtnGetVerificationCode).accept(false);
                        mPresenter.requestGetVerifyCodePre();
                    }
                });

        mVerifyCodeObservable.subscribe(new Consumer<Object>() {

            @Override
            public void accept(@NonNull Object o) throws Exception {
                Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .take(SECOND)
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Long aLong) {
                                try {
                                    RxTextView.text(mBtnGetVerificationCode).accept((SECOND -
                                            aLong) + "s");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                ToastUtils.showToastInCenter(e.toString());
                            }

                            @Override
                            public void onComplete() {
                                try {
                                    RxTextView.text(mBtnGetVerificationCode).accept("获取验证码");
                                    RxView.enabled(mBtnGetVerificationCode).accept(true);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    @OnClick({R.id.btn_get_verification_code, R.id.btn_reset_password})
    void click(View v) {
        switch (v.getId()) {
            case R.id.btn_get_verification_code:
//                mPresenter.requestGetVerifyCodePre();
                break;
            case R.id.btn_reset_password:
                String code = mCetvVerificationCode.getText().toString().trim();
                String pwd = mCetvPassword.getText().toString().trim();
                if (StringUtils.isEmpty(code) || StringUtils.isEmpty(pwd)) {
                    ToastUtils.showToastInCenter("请填写正确的密码和验证码");
                } else if (!StringUtils.isPassword(pwd)) {
                    ToastUtils.showToastInCenter("密码不合法");
                } else {
                    mPresenter.requestModifyPre(pwd, code);
                }
                break;
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

    @Override
    public void showProgress() {
        myProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        myProgressDialog.dismiss();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void injectPresenter() {
        ApplicationComponent applicationComponent = ((BaseApplication) getApplication())
                .getApplicationComponent();
        DaggerFindPasswordComponent.builder().applicationComponent
                (applicationComponent)
                .activityModule(new ActivityModule(this))
                .findPasswordModule(new FindPasswordModule(this))// //请将FindPasswordModule()
                // 第一个首字母改为小写
                .build()
                .inject(this);

    }

}
