package com.hishixi.tiku.mvp.view.activity.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.hishixi.tiku.BuildConfig;
import com.hishixi.tiku.R;
import com.hishixi.tiku.mvp.view.activity.account.LoginActivity;
import com.hishixi.tiku.utils.CacheUtils;
import com.hishixi.tiku.utils.StringUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by seamus on 17/4/21 13:31
 */

public class SplashActivity extends RxAppCompatActivity {

    @BindView(R.id.tv_version)
    TextView mTvVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);
        ButterKnife.bind(this);
        mTvVersion.setText("Hi实习导师版（" + BuildConfig.VERSION_NAME + "）");
        Handler mHandler = new Handler();
        mHandler.postDelayed(() -> {
            delayedJump();
            finish();
        }, 500);
    }

    public void delayedJump() {
        if (StringUtils.isEmpty(CacheUtils.getAccountId(this))) {
            LoginActivity.startAction(this);
        } else if (CacheUtils.getIsOpen(this).equals("2")) {
//            PersonalInfoActivity.startAction(this);
        } else {
//            IndexActivity.startAction(this);
        }
    }

    @Override
    public void finish() {
        super.finish();
        finishTransition();
    }

    public void finishTransition() {
        overridePendingTransition(R.anim.push_fade_in, R.anim.push_fade_out);
    }

}
