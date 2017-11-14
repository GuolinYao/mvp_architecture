package com.hishixi.tiku.rxbus.event;

/**
 * Created by seamus on 17/4/21 14:03
 */

public class PersonalInfoUpdateEventType {
    private int isUpdate;//1 更新 2 不更新

    public PersonalInfoUpdateEventType(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }
}
