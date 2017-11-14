package com.hishixi.tiku.custom.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hishixi.tiku.R;


/**
 * 自定义webview 带交互
 * Created by guolinyao on 16/12/14 13:48.
 */

public class MyWebView extends WebView {
    private ProgressBar progressbar;
    private Context mContext;

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        progressbar = (ProgressBar) LayoutInflater.from(context).inflate(R.layout
                .webview_progressbar, null);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 6, 0, 0));
        addView(progressbar);
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String
                    description, String failingUrl) {
                Toast.makeText(mContext, "Oh no! " + description, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        this.getSettings().setBuiltInZoomControls(true);
        this.getSettings().setUseWideViewPort(true);
        this.getSettings().setSupportZoom(true); // 支持缩放
        this.getSettings().setJavaScriptEnabled(true); // 支持js
        this.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //解决webview不显示图片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        this.getSettings().setBlockNetworkImage(false);
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            if(newProgress==100){
                progressbar.setProgress(100);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressbar.setVisibility(View.INVISIBLE);
                    }
                },300);

            }else {
                if (progressbar.getVisibility() == View.INVISIBLE)
                    progressbar.setVisibility(View.VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
