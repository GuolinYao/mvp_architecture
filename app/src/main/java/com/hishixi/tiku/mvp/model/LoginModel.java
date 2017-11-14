package com.hishixi.tiku.mvp.model;

import com.hishixi.tiku.app.BaseApplication;
import com.hishixi.tiku.mvp.contract.LoginContract;
import com.hishixi.tiku.mvp.model.entity.LoginBean;
import com.hishixi.tiku.mvp.view.activity.base.BaseActivity;
import com.hishixi.tiku.net.RetrofitClient;
import com.hishixi.tiku.net.api.LoginApiService;
import com.hishixi.tiku.utils.CacheUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by seamus on 17/4/5 17:51
 */

public class LoginModel implements LoginContract.Model {

    private BaseActivity mContext;
    private BaseApplication mApplication;

    @Inject
    public LoginModel(BaseActivity context, BaseApplication application) {
        mContext = context;
        mApplication = application;
    }

    @Override
    public Observable<HttpRes<LoginBean>> getLoginObservable(String mMobile,String mPassword) {
        LoginApiService loginApiService = RetrofitClient.INSTANCE.getRetrofit().create
                (LoginApiService.class);
        Map<String, String> map = new HashMap<>();
        map.put("ticket", CacheUtils.getToken(mContext));
        map.put("loginAcc", mMobile);
        map.put("loginPwd", mPassword);
        return loginApiService.login(map);
    }

    @Override
    public void onDestroy() {

    }
}
