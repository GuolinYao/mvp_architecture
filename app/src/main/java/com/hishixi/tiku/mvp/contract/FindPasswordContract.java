package com.hishixi.tiku.mvp.contract;

import com.hishixi.tiku.mvp.model.HttpRes;
import com.hishixi.tiku.mvp.model.base.IModel;
import com.hishixi.tiku.mvp.model.entity.LoginBean;
import com.hishixi.tiku.mvp.model.entity.VerifyCodeBean;
import com.hishixi.tiku.mvp.view.base.BaseView2;

import io.reactivex.Observable;

/**
 * 找回密码契约
 * Created by seamus on 17/3/30 17:59
 */

public interface FindPasswordContract {
    interface View extends BaseView2 {
        void showProgress();
        void hideProgress();
        void finishActivity();
    }

    interface Model extends IModel {
        Observable<HttpRes<VerifyCodeBean>> getVerifyCode();

        Observable<HttpRes<LoginBean>> changePassword(String password, String code);
    }
}
