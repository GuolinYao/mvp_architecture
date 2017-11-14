package com.hishixi.tiku.net.api;

import com.hishixi.tiku.constants.api;
import com.hishixi.tiku.mvp.model.HttpRes;
import com.hishixi.tiku.mvp.model.entity.LoginBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 登录接口
 * Created by seamus on 17/3/22 13:58
 */

public interface LoginApiService {

    @FormUrlEncoded
    @POST(api.LOGIN_URL)
    Observable<HttpRes<LoginBean>> login(@FieldMap Map<String, String> map);
}
