package com.hishixi.tiku.mvp.view.activity.base;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.baoyz.widget.PullRefreshLayout;
import com.hishixi.tiku.R;
import com.hishixi.tiku.constants.Constants;
import com.hishixi.tiku.custom.view.DividerItemDecoration;
import com.hishixi.tiku.mvp.presenter.base.Presenter;
import com.hishixi.tiku.mvp.view.adapter.base.BaseRecyclerViewAdapter;
import com.hishixi.tiku.mvp.view.base.BaseView2;
import com.hishixi.tiku.utils.ActivityUtils;
import com.hishixi.tiku.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 可自定义布局
 * Created by seamus on 17/4/7 09:57
 */

public abstract class BaseCustomRecyclerViewActivity<T, V extends BaseView2, P extends Presenter>
        extends BaseActivity<V, P> implements PullRefreshLayout.OnRefreshListener,
        BaseRecyclerViewAdapter.OnItemClickLitener {

    // 每页的起始位置
    protected int mPage = 1;
    // 每页的数量
    protected int mPageNumber = 10;
    // 加载更多时的view
    protected View mLoadMoreLayout;
    // 是否需要加载更多
    protected boolean isLoadMore;
    // 集合
    protected List<T> mLists;
    // 总数量
    protected int mListTotal;
    // 列表的适配器
    protected BaseRecyclerViewAdapter<T> mAdapter;
    // 是否设置没有数据的显示,默认为是
    protected boolean isExcuteNoneData = true;
    protected boolean isLoadPositionListFinish = true;// 是否加载完毕
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    PullRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void getIntentData() {

    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        //设置gif资源
//        mSwipeRefreshLayout.setGifResId(R.raw.xiao2hei_loading);
        //MATERIAL、CIRCLES、 WATER_DROP、RING and SMARTISAN,STYLE_GIF
        mSwipeRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
//        swipeRefreshLayout.setColorSchemeColors(new int[]);
        setRefreshListener();//设置下拉刷新监听
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView
                        .getLayoutManager();

                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (isLoadMore && isLoadPositionListFinish && totalItemCount <
                        (lastVisibleItem + Constants.VISIBLE_THRESHOLD)) {
                    if (ActivityUtils.isNetAvailable()) {
                        isLoadPositionListFinish = false;
                        loadMoreList();
                    } else {
                        ToastUtils.showToastInCenter("网络未连接");
                    }
                }
            }
        });
    }

    /**
     * 初始化列表的适配器
     */
    public abstract void initAdapter();

    /**
     * 刷新列表
     */
    public void refreshList() {
        setNoneDataHide();
        mPage = 1;
    }

    /**
     * 加载更多
     */
    public void loadMoreList() {
        if (mPage > 1 && isLoadMore) {// 有更多数据
            mLists.add(null);
            mAdapter.notifyItemInserted(mLists.size() - 1);
        }
    }

    /**
     * 设置没有数据时的描述
     */
    protected abstract void setNoneDataDesc();

    @Override
    protected void retryRequest() {
        super.retryRequest();
        refreshList();
    }

    /**
     * 设置刷新 和 加载更多监听
     */
    public void setRefreshListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        if (ActivityUtils.isNetAvailable()) {
            mPage = 1;
            refreshList();
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            ToastUtils.showToastInCenter("网络未连接");
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * 获取列表成功
     *
     * @param lists 数据
     */
    public void getListSuccess(List<T> lists) {

        if (mPage > 1) {// 非第一次加载
            if (mLists.get(mLists.size() - 1) == null)
                mLists.remove(mLists.size() - 1);
            mLists.addAll(lists);
        } else {
            mLists = lists;
        }
        // 判断是否有更多数据
        if (mListTotal - mLists.size() > 0) {// 有更多数据
            isLoadMore = true;
        } else {
            isLoadMore = false;
        }

        setListDataShow();
        mPage++;
        isLoadPositionListFinish = true;
    }

    /**
     * 设置列表数据
     */
    public void setListDataShow() {

        if (null == mAdapter || mPage <= 1) {// 第一次加载或刷新
            mAdapter = null;
            initAdapter();
            mAdapter.setOnItemClickLitener(this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置没有数据的显示
     */
    protected void setNoneDataShow() {
        if (isExcuteNoneData) {
            mMainNoneDataLayout.removeAllViews();
            mMainNoneDataLayout.addView(mCustomNoneDataView);
            mCustomNoneDataView.setVisibility(View.VISIBLE);
            mMainNoneDataLayout.setVisibility(View.VISIBLE);
            setNoneDataDesc();
        }
    }

    /**
     * 设置没有数据的隐藏
     */
    protected void setNoneDataHide() {
        mMainNoneDataLayout.removeAllViews();
        mCustomNoneDataView.setVisibility(View.GONE);
        mMainNoneDataLayout.setVisibility(View.GONE);
    }

    @Override
    public void onRefreshStart() {

    }


}
