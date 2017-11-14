package com.hishixi.tiku.mvp.view.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hishixi.tiku.R;
import com.hishixi.tiku.app.BaseApplication;
import com.hishixi.tiku.custom.view.MyWebView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * 最简单webview Activity
 * Created by guolinyao on 17/3/21 15:37.
 */
public abstract class BaseSingleWebViewActivity extends RxAppCompatActivity {

    protected MyWebView mWebview;
    private LinearLayout rootLayout;
    protected Toolbar toolbar;
    protected TextView mTvTitle;
    public String TAG = "BaseSingleWebViewActivity";
    // 连接服务器失败或者无网络连接时的提示语
    protected TextView mTextErrorWarn;
    // 没有数据时的提示语
    protected TextView mTextNoneDataWarnTitle;
    protected TextView mTextNoneDataWarnDesc;
    // 非正常情况下显示的容器
    protected FrameLayout mMainNoneDataLayout;
    protected View mCustomNoneDataView, mCustomErrorView;
    public final int left = 1, right = 2, top = 3, bottom = 4, fade = 5;
    // 跳转的动画1向left,2right,3top,4bottom
    protected int move = 2;
    protected Button mCheckOrder;

    /**
     * 获取上一页面跳转时所带的参数
     */
    protected abstract void getIntentData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 这句很关键 注意是调用父类的方法
        super.setContentView(R.layout.activity_base_webview_layout);
        getIntentData();
        initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        BaseApplication.mApp.mActivities.add(this);
        getClassName();
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        initErrorLayout();
        initView();
    }

    /**
     * 初始化控件
     */
    public void initView() {
        mWebview = (MyWebView) findViewById(R.id.webview);
        mCheckOrder = (Button) findViewById(R.id.btn_check_order);
    }

    public void getClassName() {
        TAG = this.getClass().getSimpleName();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(view -> finish());
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

    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.mApp.mActivities.remove(this);
        BaseApplication.mApp.mCenterTaskActivitys.remove(this);
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
        if (move == left) {// 右进：右向左推出
            overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        }
        if (move == right) {// 左进：左向右推出
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
        mCustomErrorView.findViewById(R.id.button_again).setOnClickListener(
                view -> {
                    retryRequest();
                });
        mTextErrorWarn = (TextView) mCustomErrorView
                .findViewById(R.id.text_server_warn);
        mTextNoneDataWarnTitle = (TextView) mCustomNoneDataView
                .findViewById(R.id.text_title);
        mTextNoneDataWarnDesc = (TextView) mCustomNoneDataView
                .findViewById(R.id.text_desc);

    }

    /**
     * 重新请求
     */
    protected void retryRequest() {
    }


}
