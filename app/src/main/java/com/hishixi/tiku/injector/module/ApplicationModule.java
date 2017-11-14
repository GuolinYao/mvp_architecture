package com.hishixi.tiku.injector.module;

import com.hishixi.tiku.app.BaseApplication;
import com.hishixi.tiku.injector.scope.PerApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final BaseApplication mApp;

    public ApplicationModule(BaseApplication mApp) {
        this.mApp = mApp;
    }

    @Provides
    @PerApplication
    BaseApplication provideApplication() {
        return this.mApp;
    }

}
