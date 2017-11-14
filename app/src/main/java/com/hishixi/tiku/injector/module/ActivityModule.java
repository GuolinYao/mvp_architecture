package com.hishixi.tiku.injector.module;

import com.hishixi.tiku.injector.scope.PerActivity;
import com.hishixi.tiku.mvp.view.activity.base.BaseActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    BaseActivity activity() {
        return this.activity;
    }
}
