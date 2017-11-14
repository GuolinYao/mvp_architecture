package com.hishixi.tiku.mvp.model;

import com.hishixi.tiku.app.BaseApplication;
import com.hishixi.tiku.injector.scope.PerActivity;
import com.hishixi.tiku.mvp.contract.FindPasswordContract;
import com.hishixi.tiku.mvp.model.entity.LoginBean;
import com.hishixi.tiku.mvp.model.entity.VerifyCodeBean;
import com.hishixi.tiku.mvp.view.activity.base.BaseActivity;
import com.hishixi.tiku.net.RetrofitClient;
import com.hishixi.tiku.net.api.FindPasswordApiService;
import com.hishixi.tiku.utils.CacheUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by seamus on 17/4/14 14:36
 */

@PerActivity
public class FindPasswordModel implements FindPasswordContract.Model {

    private BaseActivity mContext;
    private BaseApplication mApplication;

    @Inject
    public FindPasswordModel(BaseActivity context, BaseApplication application) {
        mContext = context;
        mApplication = application;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Observable<HttpRes<VerifyCodeBean>> getVerifyCode() {
        FindPasswordApiService findPasswrodApiService = RetrofitClient.INSTANCE.getRetrofit().create
                (FindPasswordApiService.class);
        Map<String, String> map = new HashMap<>();
        map.put("ticket", CacheUtils.getToken(mContext));
        map.put("registAcc", CacheUtils.getAccountMobile(mContext));
        return findPasswrodApiService.getVerifyCode(map);
    }

    @Override
    public Observable<HttpRes<LoginBean>> changePassword(String pwd, String code) {
        FindPasswordApiService findPasswrodApiService = RetrofitClient.INSTANCE.getRetrofit().create
                (FindPasswordApiService.class);
        Map<String, String> map = new HashMap<>();
        map.put("ticket", CacheUtils.getToken(mContext));
        map.put("registAcc", CacheUtils.getAccountMobile(mContext));
        map.put("registPwd", pwd);
        map.put("registCode", code);
        return findPasswrodApiService.modifyPassword(map);
    }
}
