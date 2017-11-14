package com.hishixi.tiku.mvp.presenter.base;

/***
 * 最基础Presenter 2
 * @param <T>
 */
public interface BasePresenter2<T> {

    void attach(T mView);

    void detach();

}
