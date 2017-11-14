package com.hishixi.tiku.custom.view.wheel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.hishixi.tiku.R;
import com.hishixi.tiku.utils.ActivityUtils;


/**
 * 单轮滚动view
 */
public class SingleWheelFrameLayout extends FrameLayout implements View.OnClickListener {
    private Button btn_submit, btn_cancel;
    private WheelView mWheelview;
    // 选择
    private int mSelectIndex;
    private OnSelectChangedListener selectChangedListener;
    private Context mContext;
    private String[] mArraylist;

    public interface OnSelectChangedListener {
        void onSelectChanged(String currentItem, int currentIndex);

        void onCancel();
    }

    public SingleWheelFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    /**
     * 对外的公开方法
     *
     * @param callback
     */
    public void setOnDataChangedListener(OnSelectChangedListener callback) {
        selectChangedListener = callback;
    }

    public SingleWheelFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @SuppressWarnings("static-access")
    public SingleWheelFrameLayout(Context context, String[] arraylist, int selectIndex) {
        super(context);
        this.mContext = context;
        this.mArraylist = arraylist;
        this.mSelectIndex = selectIndex;
        this.inflate(context, R.layout.single_wheel_layout, this);
        initView();
        setData();
    }

    public void initView() {
        mWheelview = (WheelView) findViewById(R.id.wheelview);
        btn_submit = (Button) findViewById(R.id.submit);
        btn_cancel = (Button) findViewById(R.id.cancel);
        btn_submit.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    public void setData() {
        mWheelview.setAdapter(new ArrayWheelAdapter<>(mArraylist
                , mArraylist.length));
        mWheelview.setCyclic(false);// 可循环滚动
        mWheelview.setCurrentItem(mSelectIndex);
        mWheelview.setVisibleItems(mArraylist.length < 4 ? 3 : 5);
//        mWheelview.setLabel("年");// 添加文字

        // 设置文字显示的大小
        int textSize = ActivityUtils.sp2px(mContext, 20);
        mWheelview.TEXT_SIZE = textSize;

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit) {
            int currentItem = mWheelview.getCurrentItem();
            selectChangedListener.onSelectChanged(mArraylist[currentItem], currentItem);
        }
        if (v.getId() == R.id.cancel) {
            selectChangedListener.onCancel();
        }
    }
}
