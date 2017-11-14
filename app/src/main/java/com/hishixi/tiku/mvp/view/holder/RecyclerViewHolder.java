package com.hishixi.tiku.mvp.view.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hishixi.tiku.custom.imageloader.ImageLoaderUtil;


public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public RecyclerViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }


    public static RecyclerViewHolder get(Context context, ViewGroup parent, int
            layoutId) {

        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        return new RecyclerViewHolder(context, itemView, parent);
    }


    /**
     * 通过viewId获取控件
     *
     * @param viewId viewId
     * @return view
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public RecyclerViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public RecyclerViewHolder setTextColor(int viewId, int color) {
        TextView tv = getView(viewId);
        tv.setTextColor(color);
        return this;
    }

    public RecyclerViewHolder setText(int viewId, SpannableString text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public RecyclerViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public RecyclerViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView image = getView(viewId);
        image.setImageBitmap(bitmap);
        return this;
    }

    public RecyclerViewHolder setImageNet(int viewId, String url, int defaultDrawable,
                                          final int roundPixels) {

        final ImageView imageView = getView(viewId);
//        DisplayImageOptions options = DisplayImageOptionsUtils.getRoundBitMap(
//                roundPixels, defaultDrawable);
//        try {
//            ImageLoader.getInstance().displayImage(url, imageView, options);
//        } catch (Exception e) {
//            ImageLoader.getInstance().displayImage(
//                    "drawable://" + defaultDrawable, imageView, options);
//        }
        ImageLoaderUtil.getInstance().loadRoundCornerImage(url, defaultDrawable,
                imageView, roundPixels, 0);
        return this;
    }

    public RecyclerViewHolder setOnClickListener(int viewId,
                                                 View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置view的显示与隐藏
     *
     * @param viewId  view id
     * @param visible 显示 隐藏
     */
    public void setViewVisibility(int viewId, int visible) {
        getView(viewId).setVisibility(visible);
    }

    /**
     * 设置view的背景
     *
     * @param backgroundRes backgroundRes
     */
    public void setViewBackground(int viewId, int backgroundRes) {
        getView(viewId).setBackgroundResource(backgroundRes);
    }

    /**
     * 设置圆形图片
     *
     * @param image_user      imageview id
     * @param sns_avatar      图片url
     * @param defaultDrawable 默认图片
     */
    public void setCircleImageByUrl(int image_user, String sns_avatar, int
            defaultDrawable) {
        final ImageView imageView = getView(image_user);
//        DisplayImageOptions options = DisplayImageOptionsUtils.getCircleBitMap(
//                defaultDrawable);
//        try {
//            ImageLoader.getInstance().displayImage(sns_avatar, imageView, options);
//        } catch (Exception e) {
//            ImageLoader.getInstance().displayImage(
//                    "drawable://" + defaultDrawable, imageView, options);
//        }
        ImageLoaderUtil.getInstance().loadCircleImage(sns_avatar,
                imageView, defaultDrawable);
    }

}
