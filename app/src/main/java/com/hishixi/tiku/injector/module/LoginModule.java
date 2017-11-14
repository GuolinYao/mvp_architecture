package com.hishixi.tiku.injector.module;

import com.hishixi.tiku.injector.scope.PerActivity;
import com.hishixi.tiku.mvp.contract.LoginContract;
import com.hishixi.tiku.mvp.model.LoginModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by seamus on 17/3/21 16:37
 */
@Module
public class LoginModule {

    private LoginContract.View view;

    /**
     * 构建UserModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view view
     */
    public LoginModule(LoginContract.View view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    LoginContract.View provideLoginView() {
        return this.view;
    }

    @Provides
    @PerActivity
    LoginContract.Model provideLoginModel(LoginModel model) {
        return model;
    }

}
