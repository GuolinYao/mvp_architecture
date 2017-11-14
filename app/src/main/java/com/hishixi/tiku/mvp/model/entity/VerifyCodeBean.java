package com.hishixi.tiku.mvp.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by seamus on 17/4/14 16:16
 */

public class VerifyCodeBean {

    /**
     * registCode : 302032
     * expireTime : 1441790220
     */

    public String registCode;
    public int expireTime;

    public static List<VerifyCodeBean> arrayVerifyCodeBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<VerifyCodeBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }
}
