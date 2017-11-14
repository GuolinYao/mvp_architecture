package com.hishixi.tiku.custom.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

import com.hishixi.tiku.R;
import com.hishixi.tiku.mvp.view.holder.RecyclerViewHolder;


/**
 * 可以侧滑删除的recyclerView
 * Created by seamus on 17/3/7 13:13.
 */

public class ItemDeleteRecyclerView extends RecyclerView {

    private Context mContext;
    private int mLastX, mLastY;
    private int mPosition;//触摸item的位置
    //item对应的布局
    private View mItemLayout;
    //删除按钮
    private TextView mDelete;

    //最大滑动距离(即删除按钮的宽度)
    private int mMaxLength;
    //是否在垂直滑动列表
    private boolean isDragging;
    //item是在否跟随手指移动
    private boolean isItemMoving;

    //item是否开始自动滑动
    private boolean isStartScroll;
    //删除按钮状态   0：关闭 1：将要关闭 2：将要打开 3：打开
    private int mDeleteBtnState;

    //检测手指在滑动过程中的速度
    private VelocityTracker mVelocityTracker;
    private Scroller mScroller;
    private OnDeleteClickListener mListener;

    public ItemDeleteRecyclerView(Context context) {
        this(context, null);
    }

    public ItemDeleteRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemDeleteRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mScroller = new Scroller(mContext, new LinearInterpolator());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        mVelocityTracker.addMovement(e);
        int x = (int) e.getX();
        int y = (int) e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mDeleteBtnState == 0) {
                    View view = findChildViewUnder(x, y);
                    if (view == null)
                        return false;
                    RecyclerViewHolder viewHolder = (RecyclerViewHolder) getChildViewHolder(view);
                    mItemLayout = viewHolder.itemView;
                    mPosition = viewHolder.getAdapterPosition();
                    mDelete = (TextView) mItemLayout.findViewById(R.id.tv_delete);
                    mMaxLength = mDelete.getWidth();
                    mDelete.setOnClickListener(view1 -> {
                        if (mListener != null) mListener.onDeleteClick(mPosition);
                        mItemLayout.scrollTo(0, 0);
                        mDeleteBtnState = 0;
                    });
                } else if (mDeleteBtnState == 3) {
                    mScroller.startScroll(mItemLayout.getScrollX(), 0, -mMaxLength, 0, 200);
                    invalidate();
                    mDeleteBtnState = 0;
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = mLastX - x;
                int dy = mLastY - y;

                int scrollX = mItemLayout.getScrollX();
                if (Math.abs(dx) > Math.abs(dy)) {//左右滑动 边界检测
                    isItemMoving = true;
                    if (scrollX + dx <= 0) {//右边界
                        mItemLayout.scrollTo(0, 0);
                        return true;
                    } else if (scrollX + dx >= mMaxLength) {//左边界
                        mItemLayout.scrollTo(mMaxLength, 0);
                        return true;
                    }
                    mItemLayout.scrollBy(dx, 0);//item随手势滑动
                }

                break;
            case MotionEvent.ACTION_UP:
                if (!isItemMoving && !isDragging && mListener != null) {
                    mListener.onItemClick(mItemLayout, mPosition);
                }
                isItemMoving = false;
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();//水平方向速度 向左为负
                float yVelocity = mVelocityTracker.getYVelocity();//上下方向速度 向上为负

                int deltaX = 0;
                int upScrollX = mItemLayout.getScrollX();
                if (Math.abs(xVelocity) > 100 && Math.abs(xVelocity) > Math.abs(yVelocity)) {
                    if (xVelocity < -100) {//左滑速度大于100 删除按钮显示
                        deltaX = mMaxLength - upScrollX;
                        mDeleteBtnState = 2;
                    } else if (xVelocity > 100) {//右滑速度大于100 删除按钮隐藏
                        deltaX = -upScrollX;
                        mDeleteBtnState = 1;
                    }
                } else {
                    if (upScrollX < mMaxLength / 2) {//item的左滑动距离小于删除按钮宽度的一半，则隐藏删除按钮
                        deltaX = -upScrollX;
                        mDeleteBtnState = 1;
                    } else if (upScrollX >= mMaxLength / 2) {//item的左滑动距离大于删除按钮宽度的一半，则显示删除按钮
                        deltaX = mMaxLength - upScrollX;
                        mDeleteBtnState = 2;
                    }
                }
                //item自动滑动到指定位置
                mScroller.startScroll(upScrollX, 0, deltaX, 0, 200);
                isStartScroll = true;
                invalidate();
                mVelocityTracker.clear();
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(e);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mItemLayout.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        } else if (isStartScroll) {
            isStartScroll = false;
            if (mDeleteBtnState == 1) {
                mDeleteBtnState = 0;
            }

            if (mDeleteBtnState == 2) {
                mDeleteBtnState = 3;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        isDragging = state == SCROLL_STATE_DRAGGING;
    }

    public void setOnDeleteListener(OnDeleteClickListener listener) {
        mListener = listener;
    }
}
