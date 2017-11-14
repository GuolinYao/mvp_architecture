package com.hishixi.tiku.mvp.view.adapter.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hishixi.tiku.mvp.view.holder.ViewHolder;
import com.hishixi.tiku.utils.DisplayImageOptionsUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * listview
 * @param <T>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Activity mContext;
    public List<T> mLists;
    protected LayoutInflater mInflater;
    protected int mItemLayoutId;

    public List<T> getmLists() {
        return mLists;
    }

    public void setmLists(List<T> mLists) {
        this.mLists = mLists;
    }

    public void updateData(List<T> lists) {
        mLists = lists;
        this.notifyDataSetChanged();
    }

    public CommonAdapter(Activity context, List<T> mDatas, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mLists = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return null == mLists || mLists.isEmpty() ? 0 : mLists.size();
    }

    @Override
    public T getItem(int position) {
        return mLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        setItemView(position);
        final ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        convert(viewHolder, getItem(position), position);
        convert(viewHolder, getItem(position), position, null == convertView ? false :
                true);
        return viewHolder.getConvertView();

    }

    /**
     * 可扩展的设置mItemLayoutId方法，如布局比较复杂可以分开写的可以用到
     *
     * @param position 位置
     */
    public void setItemView(int position) {

    }

    /**
     * 对外公开的方法，主要用来设置数据显示
     *
     * @param helper ViewHolder
     * @param item   条目
     */
    public abstract void convert(ViewHolder helper, T item, int position);

    /**
     * 用来制造listview阴影 不是都需要复写
     * @param helper
     * @param item
     * @param position
     * @param isConvertView
     */
    public void convert(ViewHolder helper, T item, int position, boolean isConvertView) {

    }

    ;

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }

    public void setImageByUrl(ImageView imageView, String url, int defaultDrawable, int
            roundPixels) {
        DisplayImageOptions options = DisplayImageOptionsUtils.getRoundBitMap
                (roundPixels, defaultDrawable);
        try {
            ImageLoader.getInstance().displayImage(url, imageView, options);
        } catch (Exception e) {
            ImageLoader.getInstance().displayImage("drawable://" + defaultDrawable,
                    imageView, options);
        }
    }

}