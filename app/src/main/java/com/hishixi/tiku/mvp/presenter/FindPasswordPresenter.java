package com.hishixi.tiku.mvp.presenter;

import com.hishixi.tiku.app.BaseApplication;
import com.hishixi.tiku.injector.scope.PerActivity;
import com.hishixi.tiku.mvp.contract.FindPasswordContract;
import com.hishixi.tiku.mvp.model.HttpRes;
import com.hishixi.tiku.mvp.model.entity.LoginBean;
import com.hishixi.tiku.mvp.model.entity.TokenBean;
import com.hishixi.tiku.mvp.presenter.base.BasePresenter;
import com.hishixi.tiku.mvp.view.activity.base.BaseActivity;
import com.hishixi.tiku.net.TokenFactory;
import com.hishixi.tiku.rxjava.ApiError;
import com.hishixi.tiku.rxjava.RetryWithNewToken;
import com.hishixi.tiku.utils.CacheUtils;
import com.hishixi.tiku.utils.DateUtils;
import com.hishixi.tiku.utils.ToastUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by seamus on 17/4/14 14:35
 */

@PerActivity
public class FindPasswordPresenter extends BasePresenter<FindPasswordContract.Model,
        FindPasswordContract.View> {

    private FindPasswordContract.View mView;
    private BaseActivity mContext;
    private BaseApplication mApplication;
    private FindPasswordContract.Model mModel;

    @Inject
    public FindPasswordPresenter(BaseActivity context, BaseApplication application,
                                 FindPasswordContract.Model
                                         model, FindPasswordContract.View view) {
        this.mContext = context;
        this.mApplication = application;
        this.mModel = model;
        this.mView = view;
    }

    /**
     * 请求网路 验证码
     */
    public void requestGetVerifyCodePre() {
        mView.showProgress();
        requestGetVerifyCode();
    }

    /**
     * 请求网路 修改密码
     */
    public void requestModifyPre(String pwd, String code) {
        mView.showProgress();
        if (DateUtils.getTimeInterval()) {
            requestTokenAndModify(pwd, code);
        } else {
            requestModify(pwd, code);
        }
    }

    /**
     * 直接请求
     */
    private void requestGetVerifyCode() {
        Observable.defer(() -> mModel.getVerifyCode())
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
                                ToastUtils.showToastInCenter("获取成功，请在一分钟内填写");
                            }
                        },
                        exception -> {
                            ToastUtils.showToastInCenter("请求失败");
                            mView.hideProgress();
                        });
    }

    /**
     * 直接请求
     */
    private void requestModify(String pwd, String code) {
        mModel.changePassword(pwd, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mContext.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(loginBeanHttpRes -> {
                            mView.hideProgress();
                            if (loginBeanHttpRes.getReturnCode() != 0) {
                                ToastUtils.showToastInCenter(loginBeanHttpRes.getReturnDesc());
                            } else {
                                CacheUtils.saveAccountId(mContext, loginBeanHttpRes.getReturnData()
                                        .instructor_id);
                                ToastUtils.showToastInCenter("修改成功");
                                mView.finishActivity();
                            }
                        },
                        exception -> {
                            ToastUtils.showToastInCenter("请求失败");
                            mView.hideProgress();
                        });
    }

    /**
     * 先请求token
     */
    private void requestTokenAndModify(String pwd, String code) {
        TokenFactory.INSTANCE.getTokenObservable()
                .flatMap(new Function<HttpRes<TokenBean>, ObservableSource<HttpRes<LoginBean>>>
                        () {
                    @Override
                    public ObservableSource<HttpRes<LoginBean>> apply(@NonNull
                                                                              HttpRes<TokenBean>
                                                                              tokenBeanHttpRes)
                            throws Exception {
                        if (tokenBeanHttpRes.getReturnCode() != 0) {
                            ToastUtils.showToastInCenter(tokenBeanHttpRes.getReturnDesc());
                            return null;
                        } else {
                            CacheUtils.saveOldGetTokenTime(BaseApplication.mApp, tokenBeanHttpRes
                                    .getReturnData().expireTime);
                            CacheUtils.saveToken(BaseApplication.mApp, tokenBeanHttpRes
                                    .getReturnData().ticket);
                            return mModel.changePassword(pwd, code);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mContext.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<HttpRes<LoginBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(HttpRes<LoginBean> user) {
                        mView.hideProgress();
                        if (user.getReturnCode() != 0) {
                            ToastUtils.showToastInCenter(user.getReturnDesc());
                        } else {
                            CacheUtils.saveAccountId(mContext, user.getReturnData()
                                    .instructor_id);
                            ToastUtils.showToastInCenter("修改成功");
                            mView.finishActivity();
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        ToastUtils.showToastInCenter("请求失败");
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
