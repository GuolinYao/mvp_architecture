package com.hishixi.tiku.mvp.presenter.base;

/***
 * 最基础Presenter
 * @param <T>
 */
public interface Presenter<T> {

    void attach(T mView);

    void detach();

}
