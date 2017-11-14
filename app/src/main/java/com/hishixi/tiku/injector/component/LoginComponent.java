package com.hishixi.tiku.injector.component;

import com.hishixi.tiku.injector.module.ActivityModule;
import com.hishixi.tiku.injector.module.LoginModule;
import com.hishixi.tiku.injector.scope.PerActivity;
import com.hishixi.tiku.mvp.view.activity.account.LoginActivity;

import dagger.Component;

/**
 * Created by seamus on 17/3/21 16:34
 * 登录组件
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class,
        LoginModule.class})
public interface LoginComponent {

    void inject(LoginActivity loginActivity);

//    void inject(LoginPresenter loginPresenter);
}

