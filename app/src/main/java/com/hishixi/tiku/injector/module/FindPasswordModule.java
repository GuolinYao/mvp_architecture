package com.hishixi.tiku.injector.module;

import com.hishixi.tiku.injector.scope.PerActivity;
import com.hishixi.tiku.mvp.contract.FindPasswordContract;
import com.hishixi.tiku.mvp.model.FindPasswordModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by seamus on 17/4/14 14:40
 */
@Module
public class FindPasswordModule {

    private FindPasswordContract.View view;

    /**
     * 构建UserModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view view
     */
    public FindPasswordModule(FindPasswordContract.View view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    FindPasswordContract.View provideView() {
        return this.view;
    }

    @Provides
    @PerActivity
    FindPasswordContract.Model provideModel(FindPasswordModel model) {
        return model;
    }

}
