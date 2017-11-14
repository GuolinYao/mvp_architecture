package com.hishixi.tiku.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 数据存储 用户基本个人信息
 * Created by guolinyao on 17/1/20 16:58.
 */
@Entity
public class PersonalInfo {

    //不能用int
    @Id(autoincrement = true)
    private long id;

    /**
     * 这里主要保存一个 个人信息的实体
     */
    private String content;

    /**
     * 学生Id
     */
    @Unique
    private String account_id;

    @Generated(hash = 1374062274)
    public PersonalInfo(long id, String content, String account_id) {
        this.id = id;
        this.content = content;
        this.account_id = account_id;
    }

    @Generated(hash = 205152550)
    public PersonalInfo() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAccount_id() {
        return this.account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

}
