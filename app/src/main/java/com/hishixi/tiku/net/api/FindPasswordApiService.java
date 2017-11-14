package com.hishixi.tiku.net.api;

import com.hishixi.tiku.constants.api;
import com.hishixi.tiku.mvp.model.HttpRes;
import com.hishixi.tiku.mvp.model.entity.LoginBean;
import com.hishixi.tiku.mvp.model.entity.VerifyCodeBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by seamus on 17/4/14 16:03
 */

public interface FindPasswordApiService {

    @FormUrlEncoded
    @POST(api.GET_VERIFY_CODE)
    Observable<HttpRes<VerifyCodeBean>> getVerifyCode(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(api.MODIIFY_PASSWORD)
    Observable<HttpRes<LoginBean>> modifyPassword(@FieldMap Map<String, String> map);
}
