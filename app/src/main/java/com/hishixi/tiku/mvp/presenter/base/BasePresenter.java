package com.hishixi.tiku.mvp.presenter.base;

import com.hishixi.tiku.mvp.model.base.IModel;
import com.hishixi.tiku.mvp.view.base.BaseView2;

/***
 * 最基础Presenter
 * @param <V>
 */
public abstract class BasePresenter<M extends IModel, V extends BaseView2> implements Presenter<V> {
    protected V mView;
    protected M mModel;

//    public BasePresenter(M model, V view) {
//        this.mModel = model;
//        this.mView = view;
//    }

    public BasePresenter(M model) {
        this.mModel = model;
    }

    public BasePresenter() {
    }

    @Override
    public void attach(V mView) {
        this.mView = mView;
    }

    @Override
    public void detach() {
        if (mModel != null) {
            mModel.onDestroy();
            this.mModel = null;
        }
        mView = null;
    }

}
