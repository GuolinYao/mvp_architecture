package com.hishixi.tiku.mvp.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by seamus on 17/3/22 18:54
 */

public class TokenBean {

    /**
     * ticket : 5669f0464b09803bd00114f008f29a2e
     * expireTime : 1408357759
     */

    public String ticket;
    public long expireTime;

    public static List<TokenBean> arrayTokenBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<TokenBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }
}
