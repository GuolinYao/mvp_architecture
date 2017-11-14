package com.hishixi.tiku.db.dao;

import com.hishixi.tiku.app.BaseApplication;
import com.hishixi.tiku.db.entity.PersonalInfo;
import com.hishixi.tiku.db.entity.PersonalInfoDao;
import com.hishixi.tiku.mvp.model.entity.PersonalInfoBean;
import com.google.gson.Gson;

import java.util.List;

/**
 * personalInfo 数据表的增删改查
 */

public class UserInfoDao {

    private static Gson gson = new Gson();

    //增加数据 如果有重复数据则覆盖
    public static void insertBasicInfo(PersonalInfoBean userinfo, String account_id) {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setContent(gson.toJson(userinfo));
        personalInfo.setAccount_id(account_id);
        BaseApplication.getDaoSession().getPersonalInfoDao().insertOrReplace(personalInfo);
    }

    //删除数据
    public static void deleteBasicInfo(String account_id) {
        BaseApplication.getDaoSession().getPersonalInfoDao().deleteAll();
    }

    //更新数据
    public static void updateBasicInfo(PersonalInfoBean userinfo, String account_id) {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setContent(gson.toJson(userinfo));
        personalInfo.setAccount_id(account_id);
        BaseApplication.getDaoSession().getPersonalInfoDao().update(personalInfo);
    }

    //查询数据
    public static PersonalInfoBean queryBasicInfo(String account_id) {
        List<PersonalInfo> list = BaseApplication.getDaoSession().getPersonalInfoDao()
                .queryBuilder().where(PersonalInfoDao.Properties.Account_id.eq(account_id)).list();
        if (list.size() > 0) {
            return gson.fromJson(list.get(0).getContent(), PersonalInfoBean.class);
        }
        return null;
    }

}
