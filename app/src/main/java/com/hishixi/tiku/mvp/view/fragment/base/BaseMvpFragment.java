package com.hishixi.tiku.mvp.view.fragment.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hishixi.tiku.app.BaseApplication;
import com.hishixi.tiku.custom.view.MyProgressDialog;
import com.hishixi.tiku.mvp.presenter.base.Presenter;
import com.hishixi.tiku.mvp.view.base.BaseView;
import com.trello.rxlifecycle2.components.RxFragment;

import butterknife.ButterKnife;

/**
 * 基础的mvpFragment
 * Created by guolinyao on 16/7/11 16:37.
 */
public abstract class BaseMvpFragment<V, T extends Presenter> extends RxFragment
        implements BaseView {

    public T presenter;
    protected FragmentActivity mActivity;
    public String TAG = "BaseMvpFragment";
    protected MyProgressDialog myProgressDialog;
    // 网络加载是否显示进度，默认为显示
    private boolean ifShowProgress = true;
    // 是否需要请求token
    protected boolean ifRequestToken = false;
    // 连接服务器失败或者无网络连接时的提示语
    protected TextView mTextErrorWarn;
    // 没有数据时的提示语
    protected TextView mTextNoneDataWarnTitle;
    protected TextView mTextNoneDataWarnDesc;
    // 非正常情况下显示的容器
    protected FrameLayout mMainNoneDataLayout;
    protected View mCustomNoneDataView, mCustomErrorView;
    // 通用的广播接受者
    protected BroadcastReceiver mBroadcastReceiver;
    //fragment管理器
    protected FragmentManager mFragmentManager;
    protected Bundle args;//传递的参数值

    private Fragment currentFragment;//当前Fragment
    private Fragment targetFragment;//目标Fragment
    protected BaseApplication mApp;
    private View mFragmentView = null;

    /**
     * 设置布局id
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 布局创建完成回调
     *
     * @param savedInstanceState savedInstanceState
     */
    public abstract void onViewCreatedFinish(Bundle savedInstanceState);

    public abstract T initPresenter();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (FragmentActivity) context;
        mApp = (BaseApplication) mActivity.getApplication();
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        int layoutId = getLayoutId();
        if (layoutId == 0)
            throw new RuntimeException("找不到Layout资源,Fragment初始化失败!");
        mFragmentView = inflater.inflate(layoutId, container, false);
        ViewGroup parent = (ViewGroup) mFragmentView.getParent();
        if (parent != null)
            parent.removeView(mFragmentView);
        ButterKnife.bind(this, mFragmentView);
        presenter = initPresenter();
        myProgressDialog = new MyProgressDialog(mActivity);
        initView();
        return mFragmentView;
    }

    /**
     * 初始化控件
     */
    public void initView() {
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreatedFinish(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mFragmentView != null)
            ((ViewGroup) mFragmentView.getParent()).removeView(mFragmentView);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attach((V) this);
    }

    @Override
    public void onStop() {
        cancelRequests();
        super.onStop();
    }

    public void cancelRequests() {
    }

    @Override
    public void onDestroy() {
        presenter.detach();
        super.onDestroy();
        if (null != mBroadcastReceiver) {
            BaseApplication.mApp.mLocalBroadcastManager
                    .unregisterReceiver(mBroadcastReceiver);
        }
        myProgressDialog.dismiss();
        System.gc();
    }

    /**
     * fragment的切换 写在MainActivity里
     */
//    public void switchContent(Fragment from, Fragment to) {
//        if (mContent != to) {
//            mContent = to;
//            FragmentTransaction transaction = mFragmentManager.beginTransaction().setCustomAnimations(
//                    android.R.anim.fade_in, R.anim.slide_out);
//            if (!to.isAdded()) {    // 先判断是否被add过
//                transaction.hide(from).add(R.id.content_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
//            } else {
//                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
//            }
//        }
//    }

}
