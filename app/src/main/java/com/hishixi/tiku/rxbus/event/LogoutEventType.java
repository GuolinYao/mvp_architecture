package com.hishixi.tiku.rxbus.event;

/**
 * Created by seamus on 17/4/19 17:34
 */

public class LogoutEventType {

    private int isLogout;//1 登录状态 2 退出状态

    public LogoutEventType(int isLogout) {
        this.isLogout = isLogout;
    }

    public int getIsLogout() {
        return isLogout;
    }

    public void setIsLogout(int isLogout) {
        this.isLogout = isLogout;
    }
}
