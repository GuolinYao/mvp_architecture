package com.hishixi.tiku.net.api;

import com.hishixi.tiku.constants.api;
import com.hishixi.tiku.mvp.model.HttpRes;
import com.hishixi.tiku.mvp.model.entity.TokenBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by seamus on 17/3/22 15:03
 */

public interface TokenApiService {

    @FormUrlEncoded
    @POST(api.POWER_URL)
    Observable<HttpRes<TokenBean>> getToken(@FieldMap(encoded = false) Map<String, String> map);
}
