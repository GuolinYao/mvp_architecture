package com.hishixi.tiku.mvp.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by seamus on 17/4/11 13:51
 */

public class PersonalInfoBean {

    /**
     * avatar : http://apipre.hishixi
     * .cn/Public/upload/instructor_headpic_app/2017-03-30/58dcd18320a19.png
     * name : 邹致远
     * company : babai.cn
     * job_title : baibailele2
     * slogan : 每当想放弃的时候就再坚持一丢丢3
     * introduce : 风筝飞的再高都需一根握在手里的线4
     * introduce_image : ["http://apipre.hishixi
     * .cn/Public/upload/instructor_introduce_app/2017-04-12/58edcaa905a73.jpg","http://apipre
     * .hishixi.cn/Public/upload/instructor_introduce_app/2017-04-12/58edcaa905a73.jpg"]
     * tag : ["大本2","八百3","牛奶46"]
     */

    public String avatar;
    public String name;
    public String company;
    public String job_title;
    public String slogan;
    public String introduce;
    public int avatar_show;//头像开关,1:显示,2:隐藏
    public List<String> introduce_image;
    public List<String> tag;

    public static List<PersonalInfoBean> arrayPersonalInfoBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<PersonalInfoBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }
}
