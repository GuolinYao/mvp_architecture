package com.hishixi.tiku.net;

import com.hishixi.tiku.app.BaseApplication;
import com.hishixi.tiku.constants.api;
import com.hishixi.tiku.mvp.model.HttpRes;
import com.hishixi.tiku.mvp.model.entity.TokenBean;
import com.hishixi.tiku.net.api.TokenApiService;
import com.hishixi.tiku.utils.ActivityUtils;
import com.hishixi.tiku.utils.CacheUtils;
import com.hishixi.tiku.utils.DateUtils;
import com.hishixi.tiku.utils.StringUtils;
import com.hishixi.tiku.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * Created by seamus on 17/3/22 14:44
 */

public enum TokenFactory {

    INSTANCE;

    private String token;
    private boolean ifRequestToken = true;

    TokenFactory() {

    }

    /**
     * 获取tokenObservable
     * @return 获取tokenObservable
     */
    public Observable<HttpRes<TokenBean>> getTokenObservable(){
        Map<String, String> map = new HashMap<>();
        map.put("authUserAcc", api.API_ACCOUNT_NUMER);
        map.put("authUserPwd", api.API_PASSWORD);
        map.put("authSign", StringUtils.getMD5Str());
        map.put("version", ActivityUtils.getVersionName());//传版本号
        TokenApiService tokenApiService = RetrofitClient.INSTANCE.getRetrofit().create
                (TokenApiService.class);
        return tokenApiService.getToken(map);
    }

    public boolean getToken() {
        ifRequestToken = DateUtils.getTimeInterval();
        if (ifRequestToken) {//需要重新请求token
            Map<String, String> map = new HashMap<>();
            map.put("authUserAcc", api.API_ACCOUNT_NUMER);
            map.put("authUserPwd", api.API_PASSWORD);
            map.put("authSign", StringUtils.getMD5Str());
            map.put("version", ActivityUtils.getVersionName());//传版本号
            TokenApiService tokenApiService = RetrofitClient.INSTANCE.getRetrofit().create
                    (TokenApiService.class);
            Observable<HttpRes<TokenBean>> tokenObservable = tokenApiService.getToken(map);
            tokenObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribe(tokenBeanHttpRes -> {
                        if (tokenBeanHttpRes.getReturnCode() != 0) {
                            ToastUtils.showToastInCenter(tokenBeanHttpRes.getReturnDesc());
                        } else {
                            ToastUtils.showToastInCenter(tokenBeanHttpRes.getReturnData()
                                    .ticket);
                            CacheUtils.saveOldGetTokenTime(BaseApplication.mApp, tokenBeanHttpRes
                                    .getReturnData().expireTime);
                            CacheUtils.saveToken(BaseApplication.mApp, tokenBeanHttpRes
                                    .getReturnData().ticket);
                        }
                    }, exception -> ToastUtils.showToastInCenter("请求失败"));
        }
        return true;
    }
}
