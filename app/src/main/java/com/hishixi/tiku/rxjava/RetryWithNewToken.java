package com.hishixi.tiku.rxjava;


import com.hishixi.tiku.app.BaseApplication;
import com.hishixi.tiku.net.TokenFactory;
import com.hishixi.tiku.utils.CacheUtils;
import com.hishixi.tiku.utils.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by seamus on 2017/11/2 15:16
 * 获取token失效的重试逻辑
 */

public class RetryWithNewToken implements Function<Observable<Throwable>, ObservableSource<?>> {

    @Override
    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
        return throwableObservable.flatMap((Function<Throwable, ObservableSource<?>>) apiError -> {
            if (apiError instanceof ApiError &&((ApiError) apiError).getErrorCode() == 5) {
                return TokenFactory.INSTANCE.getTokenObservable()
                        .flatMap(tokenBeanHttpRes -> {
                                    if (tokenBeanHttpRes.getReturnCode() != 0) {
                                        ToastUtils.showToastInCenter("rx 令牌失效");
                                        return null;
                                    } else {
                                        CacheUtils.saveOldGetTokenTime(BaseApplication.mApp,
                                                tokenBeanHttpRes.getReturnData().expireTime);
                                        CacheUtils.saveToken(BaseApplication.mApp, tokenBeanHttpRes
                                                .getReturnData().ticket);
                                        return Observable.just("get token");
                                    }
                                }
                        ).subscribeOn(Schedulers.io());
            } else {
                return Observable.error(apiError);
            }
        }).subscribeOn(Schedulers.io());

    }

}
