package com.hishixi.tiku.mvp.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by seamus on 17/4/14 11:23
 */

public class IndexBean {

    /**
     * avatar : http://apipre.hishixi
     * .cn/Public/upload/instructor_headpic_app/2017-04-12/58edf6183dcb1.jpg
     * name : 麻花豆腐
     * msg : 1
     */

    public String avatar;
    public String name;
    public String msg;
    public String last_time;
    public String last_count;

    public static List<IndexBean> arrayIndexBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<IndexBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }
}
