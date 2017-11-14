package com.hishixi.tiku.mvp.presenter;

import com.hishixi.tiku.app.BaseApplication;
import com.hishixi.tiku.injector.scope.PerActivity;
import com.hishixi.tiku.mvp.contract.LoginContract;
import com.hishixi.tiku.mvp.model.entity.LoginBean;
import com.hishixi.tiku.mvp.presenter.base.BasePresenter;
import com.hishixi.tiku.mvp.view.activity.base.BaseActivity;
import com.hishixi.tiku.rxjava.ApiError;
import com.hishixi.tiku.rxjava.RetryWithNewToken;
import com.hishixi.tiku.utils.CacheUtils;
import com.hishixi.tiku.utils.StringUtils;
import com.hishixi.tiku.utils.ToastUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by seamus on 17/3/21 16:26
 */
@PerActivity
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {

    private LoginContract.View mView;
    private BaseActivity mContext;
    private BaseApplication mApplication;
    private String mMobile, mPassword;
    private LoginContract.Model mModel;

    @Inject
    public LoginPresenter(BaseActivity context, BaseApplication application, LoginContract.Model
            model, LoginContract.View view) {
        this.mContext = context;
        this.mApplication = application;
        this.mModel = model;
        this.mView = view;
    }

    public void login(String mobile, String password) {

        if (!checkAccountAndPwd(mobile, password))//检查用户名和密码是否合法
            return;
        this.mMobile = mobile;
        this.mPassword = password;
        mView.showProgress();
        requestLogin();
    }

    /**
     * //检查用户名和密码是否合法
     *
     * @param mobile   手机
     * @param password 密码
     * @return 是否合法
     */
    private boolean checkAccountAndPwd(String mobile, String password) {
        if (!StringUtils.isPhoneNum(mobile)) {
            ToastUtils.showToastInCenter("账号不符合要求");
            return false;
        } else if (!StringUtils.isPassword(password)) {
            ToastUtils.showToastInCenter("密码不符合要求");
            return false;
        } else {
            return true;
        }

    }


    /**
     * 直接请求登录
     */
    private void requestLogin() {
        Observable.defer(() -> mModel.getLoginObservable(mMobile, mPassword))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(httpRes -> {
                    if (httpRes.getReturnCode() == 5)
                        return Observable.error(new ApiError(5)); //0005 令牌值无效
                    return Observable.just(httpRes);
                })
                .retryWhen(new RetryWithNewToken())
                .compose(mContext.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(loginBeanHttpRes -> {
                            mView.hideProgress();
                            if (loginBeanHttpRes.getReturnCode() != 0) {
                                ToastUtils.showToastInCenter(loginBeanHttpRes.getReturnDesc());
                            } else {
                                proceedLogin(loginBeanHttpRes.getReturnData());
                            }
                        },
                        exception -> {
                            ToastUtils.showToastInCenter("登录失败");
                            mView.hideProgress();
                        });
    }

    /**
     * 处理登录的情况
     *
     * @param loginBean loginBean
     */
    private void proceedLogin(LoginBean loginBean) {
        CacheUtils.saveAccountId(mContext, loginBean.instructor_id);
        CacheUtils.saveIsOpen(mContext, loginBean.open);
        if (loginBean.status.equals("2")) {
            ToastUtils.showToastInCenter("您已被禁止登录");
        } else if (loginBean.open.equals("1")) {//已启用
//            IndexActivity.startAction(mContext);
            mView.finishView();
        } else {//跳转到资料编辑页
//            PersonalInfoActivity.startAction(mContext);
            mView.finishView();
        }
    }
}
