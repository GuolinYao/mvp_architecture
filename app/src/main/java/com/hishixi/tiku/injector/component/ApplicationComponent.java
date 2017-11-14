package com.hishixi.tiku.injector.component;


import com.hishixi.tiku.app.BaseApplication;
import com.hishixi.tiku.injector.module.ApplicationModule;
import com.hishixi.tiku.injector.scope.PerApplication;

import dagger.Component;

@PerApplication
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

//    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    BaseApplication application();

//    Retrofit retrofit();
}
