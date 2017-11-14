package com.hishixi.tiku.mvp.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录信息
 * Created by seamus on 17/3/22 17:52
 */

public class LoginBean {

    /**
     * instructor_id : 70
     * status : 1
     * open : 1
     */

    public String instructor_id;
    public String status;
    public String open;

    public static List<LoginBean> arrayLoginBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<LoginBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }
}
