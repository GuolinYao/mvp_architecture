package com.hishixi.tiku.mvp.view.activity.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hishixi.tiku.R;
import com.hishixi.tiku.app.BaseApplication;
import com.hishixi.tiku.constants.Constants;
import com.hishixi.tiku.custom.view.MyProgressDialog;
import com.hishixi.tiku.injector.component.ApplicationComponent;
import com.hishixi.tiku.mvp.presenter.base.Presenter;
import com.hishixi.tiku.mvp.view.base.BaseView;
import com.hishixi.tiku.mvp.view.base.BaseView2;
import com.hishixi.tiku.rxbus.RxBus;
import com.hishixi.tiku.utils.ActivityUtils;
import com.hishixi.tiku.utils.CacheUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 最基础 Activity
 * Created by guolinyao on 17/3/21 15:37.
 */
public abstract class BaseActivity<V extends BaseView2, P extends Presenter> extends
        RxAppCompatActivity implements BaseView, Toolbar.OnMenuItemClickListener {

    @Inject
    protected P mPresenter;
    private LinearLayout rootLayout;
    protected Toolbar toolbar;
    protected TextView mTvTitle,mTvMsgCount;
    protected ImageView mIvIconLeft;
    protected RelativeLayout rlMsg;
    public String TAG = "BaseActivity";
    protected MyProgressDialog myProgressDialog;
    // 没有数据时的提示语 连接服务器失败或者无网络连接时的提示语
    protected TextView mTextNoneDataWarnTitle, mTextNoneDataWarnDesc, mTextErrorWarn;
    // 非正常情况下显示的容器
    protected FrameLayout mMainNoneDataLayout;
    protected View mCustomNoneDataView, mCustomErrorView;
    // 通用的广播接受者
    protected BroadcastReceiver mBroadcastReceiver;
    private long exitTime;//记录两次返回退出app
    public final int left = 1, right = 2, top = 3, bottom = 4, fade = 5;
    // 跳转的动画1向left,2right,3top,4bottom
    protected int move = 2;
    private Disposable mSubscribe;

    /**
     * 获取上一页面跳转时所带的参数
     */
    protected abstract void getIntentData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 这句很关键 注意是调用父类的方法
        super.setContentView(R.layout.activity_base);
        getIntentData();
        //代码直接申明状态栏透明更有效
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myProgressDialog = new MyProgressDialog(this);
        BaseApplication.mApp.mActivities.add(this);
        getClassName();
        injectPresenter();
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        mPresenter.attach((V) this);
        initErrorLayout();
    }

    /**
     * 初始化控件
     */
    public void initView() {
        ButterKnife.bind(this);
        mMainNoneDataLayout = (FrameLayout) findViewById(R.id.fl_none_data);
    }

    public abstract void injectPresenter();

    /**
     * 请求错误的显示
     */
    @Override
    public void setErrorShow() {
        mMainNoneDataLayout.removeAllViews();
        mMainNoneDataLayout.addView(mCustomErrorView);
        mCustomErrorView.setVisibility(View.VISIBLE);
        mMainNoneDataLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 请求错误的隐藏
     */
    @Override
    public void setErrorHide() {
        mMainNoneDataLayout.removeAllViews();
        mCustomErrorView.setVisibility(View.GONE);
        mMainNoneDataLayout.setVisibility(View.GONE);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((BaseApplication) getApplication()).getApplicationComponent();
    }

    public void getClassName() {
        TAG = this.getClass().getSimpleName();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mIvIconLeft = (ImageView) findViewById(R.id.iv_icon_left);
        mTvMsgCount = (TextView) findViewById(R.id.tv_msg_count);
        rlMsg = (RelativeLayout) findViewById(R.id.rl_msg);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(view -> finish());
            toolbar.setOnMenuItemClickListener(this);
        }
    }

    @Override
    public void setContentView(int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
        initView();
    }

    @Override
    public void setContentView(View view) {
        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        if (rootLayout == null) return;
        rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onStop() {
        cancelRequests();
        super.onStop();
    }

    public void cancelRequests() {
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        mPresenter = null;
        super.onDestroy();
        if (null != mBroadcastReceiver) {
            BaseApplication.mApp.mLocalBroadcastManager
                    .unregisterReceiver(mBroadcastReceiver);
        }
        BaseApplication.mApp.mActivities.remove(this);
        BaseApplication.mApp.mCenterTaskActivitys.remove(this);
        myProgressDialog.dismiss();
        myProgressDialog = null;
        unRegisterRxBus();
    }

    /**
     * 结束所有activity 退出
     */
    protected void finishProcess() {
        for (int i = 0; i < BaseApplication.mApp.mActivities.size(); i++) {
            if (BaseApplication.mApp.mActivities.get(i) != null) {
                BaseApplication.mApp.mActivities.get(i).finish();
            }
        }
        System.exit(0);
    }

    /**
     * 结束部分activity 退出
     */
    protected void finishPartProcess() {
        for (int i = 0; i < BaseApplication.mApp.mCenterTaskActivitys.size(); i++) {
            if (BaseApplication.mApp.mCenterTaskActivitys.get(i) != null) {
                BaseApplication.mApp.mCenterTaskActivitys.get(i).finish();
            }
        }
    }

    public void exit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this.getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
            // mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            CacheUtils.saveIndex(BaseApplication.mApp, 0);
            finishProcess();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        pendingStartTransition();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        pendingStartTransition();
    }

    /**
     * 设置activity进入方向
     *
     * @param move left = 1, right = 2, top = 3, bottom = 4, fade = 5;
     */
    public void setMove(int move) {
        this.move = move;
    }

    @Override
    public void finish() {
        super.finish();
        finishTransition();
    }

    /**
     * 结束方向
     */
    public void finishTransition() {
        if (move == left) {// 右进：左推出
            overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        }
        if (move == right) {// 左进：右推出
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        }
        if (move == top) {// 上进：下向上推出
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        }
        if (move == bottom) {// 下进：上往下推出
            overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
        }
        if (move == fade) {// 淡入淡出
            overridePendingTransition(R.anim.push_fade_in, R.anim.push_fade_out);
        }
    }

    public void pendingStartTransition() {
        if (move == right) {// 右进：右向左推出
            overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        }
        if (move == left) {// 左进：左向右推出
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        }
        if (move == top) {// 上进：下向上推出
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        }
        if (move == bottom) {// 下进：上往下推出
            overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
        }
        if (move == fade) {// 淡入淡出
            overridePendingTransition(R.anim.push_fade_in, R.anim.push_fade_out);
        }
    }

    /**
     * 初始化错误页面
     */
    public void initErrorLayout() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        mCustomNoneDataView = layoutInflater.inflate(
                R.layout.none_data_layout, null);
        mCustomErrorView = layoutInflater.inflate(R.layout.error_layout,
                null);
        mCustomErrorView.findViewById(R.id.text_server_warn).setOnClickListener(
                view -> {
                    retryRequest();
                });
        mCustomNoneDataView.findViewById(R.id.button_again).setOnClickListener(
                view -> {
                    retryRequest();
                });
        mTextErrorWarn = (TextView) mCustomErrorView.findViewById(R.id.text_server_warn);
        mTextNoneDataWarnTitle = (TextView) mCustomNoneDataView.findViewById(R.id.text_title);
        mTextNoneDataWarnDesc = (TextView) mCustomNoneDataView.findViewById(R.id.text_desc);

    }

    /**
     * 重新请求
     */
    protected void retryRequest() {
        setErrorHide();
    }

    /**
     * 注册广播接受者 通用广播
     */
    public void baseRegisterIntentFilter() {
        ActivityUtils.getLocalBroadcastManager(this);
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(Constants.ALL_UPDATE_ACTION);
        BaseApplication.mApp.mLocalBroadcastManager.registerReceiver(
                mBroadcastReceiver, mFilter);
    }

    /**
     * 需要注册广播接收者的页面调用,部分需要重写onReceive方法的需要单独调用
     */
    public void setFilterRegister() {
        setBroadcastReceiver();
        baseRegisterIntentFilter();
    }

    /**
     * 没有ProgressBar
     * 需要注册广播接收者的页面调用,部分需要重写onReceive方法的需要单独调用
     */
    public void setFilterRegisterNoProgressBar() {
        setBroadcastReceiverNoProgressBar();
        baseRegisterIntentFilter();
    }

    /**
     * 发送通用广播
     */
    public void sendBroadcast() {
        ActivityUtils.getLocalBroadcastManager(this);
        Intent intent = new Intent();
        intent.setAction(Constants.ALL_UPDATE_ACTION);// 更新触发当前页面的数据
        BaseApplication.mApp.mLocalBroadcastManager.sendBroadcast(intent);
    }

    /***
     * 发送指定广播
     *
     * @param action 相应action
     */
    public void sendBroadcast(String action) {
        ActivityUtils.getLocalBroadcastManager(this);
        Intent intent = new Intent();
        intent.setAction(action);
        BaseApplication.mApp.mLocalBroadcastManager.sendBroadcast(intent);
    }

    /**
     * 设置广播接收者
     */
    public void setBroadcastReceiver() {
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                toRequestServer(intent);
            }
        };
    }

    /**
     * 设置广播接收者 无ProgressBar
     */
    public void setBroadcastReceiverNoProgressBar() {
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                toRequestServerNoProgressBar(intent);
            }
        };
    }

    /**
     * 重新请求
     *
     * @param intent 广播intent
     */
    public void toRequestServer(Intent intent) {
        if (intent.getAction().equals(Constants.ALL_UPDATE_ACTION)) {
            setRequestParamsPre(TAG, true);
        }
    }

    /**
     * 重新请求 无ProgressBar
     *
     * @param intent 广播intent
     */
    public void toRequestServerNoProgressBar(Intent intent) {
        if (intent.getAction().equals(Constants.ALL_UPDATE_ACTION)) {
            setRequestParamsPre(TAG, false);
        }
    }

    /**
     * @param tag TAG
     * @param b   是否需要显示progressbar
     */
    protected boolean setRequestParamsPre(String tag, boolean b) {
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public void showProgress() {
        if (null != myProgressDialog)
            myProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (null != myProgressDialog)
            myProgressDialog.dismiss();
    }

    /**
     * 发送事件
     *
     * @param o event
     */
    public void postRxBus(Object o) {
        RxBus.getInstance().post(o);
    }

    /**
     * 注册Rxbus 事件
     *
     * @param eventClazz 事件类型
     */
    public void registerRxBus(Class eventClazz) {
        mSubscribe = RxBus.getInstance().toObservable(eventClazz)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        doOnNext(o);
                    }
                });

    }

    /**
     * 处理事件
     *
     * @param o Object
     */
    protected void doOnNext(Object o) {

    }

    /**
     * 取消订阅事件
     */
    private void unRegisterRxBus() {
        if (mSubscribe != null) {
            mSubscribe.dispose();
            mSubscribe = null;
        }
    }
}
