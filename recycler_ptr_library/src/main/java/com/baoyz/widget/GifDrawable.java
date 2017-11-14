package com.baoyz.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;

import java.io.InputStream;

/**
 * Created by guolinyao on 16/11/23 09:23.
 */

public class GifDrawable extends RefreshDrawable {

//    private String releaseLabel, pullLabel, refreshingLabel;
    private Paint mPaint;
    private boolean isRunning = false;//正在加载
    /**
     * 播放GIF动画的关键类
     */
    private Movie mMovie;
    /**
     * 记录动画开始的时间
     */
    private long mMovieStart;
    private int mTop;
    private int mDrawWidth;
    private int mDrawHeight;
    private Rect mBounds;
    private int mDiameter;
    private int mHalf;

    private boolean mPaused = true;
    private static final int DEFAULT_MOVIE_DURATION = 2;
    private int mCurrentAnimationTime = 0;
    private int mHeight;

    public GifDrawable(Context context, PullRefreshLayout layout, int gifId) {
        super(context, layout);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#656565"));
        mPaint.setTextSize(dp2px(16));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            layout.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        int resourceId = gifId;
        if (resourceId != 0) {
            // 当资源id不等于0时，就去获取该资源的流
            InputStream is = context.getResources().openRawResource(resourceId);
            // 使用Movie类对流进行解码
            mMovie = Movie.decodeStream(is);
        }
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mDrawWidth = dp2px(75);
        mDrawHeight = mDrawWidth;
        mBounds = bounds;
        measureCircleProgress(mDrawWidth, mDrawHeight);
    }

    private void measureCircleProgress(int width, int height) {
        mDiameter = Math.min(width, height);
        mHalf = mDiameter / 2;
    }


    @Override
    public void setPercent(float percent) {

    }

    @Override
    public void setColorSchemeColors(int[] colorSchemeColors) {

    }

    @Override
    public void offsetTopAndBottom(int offset) {
        mHeight += offset;
        mTop = mHeight - getRefreshLayout().getFinalOffset();
        invalidateSelf();
    }

    @Override
    public void start() {
        isRunning = true;
        setPaused(false);
        invalidateSelf();
    }

    @Override
    public void stop() {
        isRunning = false;
        setPaused(true);
        invalidateSelf();
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(mBounds.width() / 2 - mHalf, mTop);
        makeGifView(canvas);
//        canvas.drawText("正在加载", mBounds.width() / 2 - mHalf, mTop + dp2px(90), mPaint);
        canvas.restore();
    }

    private void makeGifView(Canvas canvas) {
        if (mMovie != null) {
            if (!mPaused) {
                updateAnimationTime();
                drawMovieFrame(canvas);
                invalidateSelf();
            } else {
                drawMovieFrame(canvas);
            }
        }
    }

    private void updateAnimationTime() {
        long now = android.os.SystemClock.uptimeMillis();
        // 如果第一帧，记录起始时间
        if (mMovieStart == 0) {
            mMovieStart = now;
        }
        // 取出动画的时长
        int dur = mMovie.duration();
        if (dur == 0) {
            dur = DEFAULT_MOVIE_DURATION;
        }
        // 算出需要显示第几帧
        mCurrentAnimationTime = (int) ((now - mMovieStart) % dur);
    }

    private void drawMovieFrame(Canvas canvas) {
        // 设置要显示的帧，绘制即可
        mMovie.setTime(mCurrentAnimationTime);
//        canvas.save(Canvas.MATRIX_SAVE_FLAG);
//        canvas.scale(mScale, mScale);
        mMovie.draw(canvas, 0, 0);
        canvas.restore();
    }

    /**
     * 设置暂停
     *
     * @param paused
     */
    public void setPaused(boolean paused) {
        this.mPaused = paused;
        if (!paused) {
            mMovieStart = android.os.SystemClock.uptimeMillis()
                    - mCurrentAnimationTime;
        } else {
            //初始化gif 回到第一帧
            mCurrentAnimationTime = 0;
        }
        invalidateSelf();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics());
    }

}
