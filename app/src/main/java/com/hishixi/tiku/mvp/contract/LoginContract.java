package com.hishixi.tiku.mvp.contract;

import com.hishixi.tiku.mvp.model.HttpRes;
import com.hishixi.tiku.mvp.model.base.IModel;
import com.hishixi.tiku.mvp.model.entity.LoginBean;
import com.hishixi.tiku.mvp.view.base.BaseView2;

import io.reactivex.Observable;

/**
 * 登录页的契约
 * Created by guolinyao on 17/3/21 16:06.
 */

public interface LoginContract {

    interface View extends BaseView2 {
        void finishView();
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel {

        Observable<HttpRes<LoginBean>> getLoginObservable(String mobile, String password);
    }
}
