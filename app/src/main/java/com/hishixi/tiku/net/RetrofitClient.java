package com.hishixi.tiku.net;

import com.hishixi.tiku.utils.StringUtils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by seamus on 17/3/22 14:44
 */

public enum RetrofitClient  {

    INSTANCE;

    private final Retrofit retrofit;

    RetrofitClient() {
        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(OKHttpFactory.INSTANCE.getOkHttpClient())

                //baseUrl
                .baseUrl(StringUtils.getCommonIP())

                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())

                //rx,java转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
