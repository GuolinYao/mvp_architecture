package com.hishixi.tiku.net;

import com.hishixi.tiku.constants.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by seamus on 17/3/22 14:45
 */

enum OKHttpFactory {

    INSTANCE;

    private final OkHttpClient okHttpClient;

    OKHttpFactory() {
        //打印请求Log
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //缓存目录
//        Cache cache = new Cache(BaseApplication.mApp.getCacheDir(), 10 * 1024 * 1024);

        okHttpClient = new OkHttpClient.Builder()
                //打印请求log
                .addInterceptor(interceptor)

//                //stetho,可以在chrome中查看请求
//                .addNetworkInterceptor(new StethoInterceptor())

                //添加UA
//                .addInterceptor(new UserAgentInterceptor(HttpHelper.getUserAgent()))

                //必须是设置Cache目录
//                .cache(cache)

                //走缓存，两个都要设置
//                .addInterceptor(new OnOffLineCachedInterceptor())
//                .addNetworkInterceptor(new OnOffLineCachedInterceptor())

                //失败重连
                .retryOnConnectionFailure(true)

                //time out
                .readTimeout(Constants.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(Constants.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)

                .build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
