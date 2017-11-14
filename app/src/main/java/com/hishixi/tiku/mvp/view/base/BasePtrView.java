package com.hishixi.tiku.mvp.view.base;

/**
 * 最基础view
 * Created by guolinyao on 16/6/28.
 */
public interface BasePtrView extends MainMvpView {

    /**
     * 初始化列表的适配器
     */
    public abstract void initAdapter();

    /**
     * 设置没有数据时的描述
     */
    public abstract void setNoneDataDesc();

    /**
     * 隐藏进度条
     */
    public abstract void dissMissProgress();
}
