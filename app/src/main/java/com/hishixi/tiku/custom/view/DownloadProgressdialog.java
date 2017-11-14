package com.hishixi.tiku.custom.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hishixi.tiku.R;


/**
 * Created by guolinyao on 16/11/9 09:46.
 */

public class DownloadProgressdialog extends AlertDialog {
    private TextView mProgressPercent;
    private TextView mProgressMessage;

    private Handler mViewUpdateHandler;
    private int mMax;
    private CharSequence mMessage;
    private boolean mHasStarted;
    private int mProgressVal;

    private String TAG = "DownloadProgressdialog";
    private ProgressBar progressbar;

    public DownloadProgressdialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_progressdialog_layout);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressMessage = (TextView) findViewById(R.id.tv_progress_message);
        mProgressPercent = (TextView) findViewById(R.id.tv_progress);
        mViewUpdateHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int progress = progressbar.getProgress();
                int max = progressbar.getMax();
                mProgressPercent.setText(100 * progress / max + "%");
            }
        };
        onProgressChanged();
        if (mMessage != null) {
            setMessage(mMessage);
        }
        if (mMax > 0) {
            setMax(mMax);
        }
        if (mProgressVal > 0) {
            setProgress(mProgressVal);
        }
    }

    private void onProgressChanged() {
        mViewUpdateHandler.sendEmptyMessage(0);
    }

    public int getMax() {
        if (progressbar != null) {
            return progressbar.getMax();
        }
        return mMax;
    }

    public void setMax(int max) {
        if (progressbar != null) {
            progressbar.setMax(max);
            onProgressChanged();
        } else {
            mMax = max;
        }
    }

    public void setProgress(int value) {
        if (mHasStarted) {
            progressbar.setProgress(value);
            onProgressChanged();
        } else {
            mProgressVal = value;
        }
    }

    @Override
    public void setMessage(CharSequence message) {
        if (mProgressMessage != null) {
            mProgressMessage.setText(message);
        } else {
            mMessage = message;
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        mHasStarted = true;
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        mHasStarted = false;
    }
}
