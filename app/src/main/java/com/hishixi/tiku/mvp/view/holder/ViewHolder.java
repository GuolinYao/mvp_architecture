package com.hishixi.tiku.mvp.view.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hishixi.tiku.custom.imageloader.ImageLoaderUtil;
import com.hishixi.tiku.utils.DisplayImageOptionsUtils;
import com.hishixi.tiku.utils.StringUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 通用的适配器的显示实体类
 *
 */
public class ViewHolder {
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context context;

    private ViewHolder(Context context, ViewGroup parent, int layoutId,
                       int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        this.context = context;
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context 上下文
     * @param convertView 回收的view
     * @param parent 父view'
     * @param layoutId 资源id
     * @param position 位置
     * @return viewholder
     */
    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getViewsCount(){
        return mViews.size();
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId 资源id
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

    /**
     * 为TextView设置字符串
     *
     * @param viewId 资源id
     * @param text 文字
     * @return Viewholder
     */
    public ViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(StringUtils.isEmpty(text) ? "" : text);
        return this;
    }

    public ViewHolder setTextBackground(int viewId, int res) {
        TextView view = getView(viewId);
        view.setBackgroundResource(res);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int res) {
        TextView view = getView(viewId);
        view.setTextColor(res);
        return this;
    }

    public ViewHolder setText(int viewId, String text,
                              LinearLayout.LayoutParams layoutParams) {
        TextView view = getView(viewId);
        view.setText(StringUtils.isEmpty(text) ? "" : text);
        if (null != layoutParams)
            view.setLayoutParams(layoutParams);
        return this;
    }

    public ViewHolder setText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public ViewHolder setText(int viewId, String text, String defaultStr) {
        TextView view = getView(viewId);
        view.setText(StringUtils.isEmpty(text) ? defaultStr : text);
        return this;
    }

    public ViewHolder setLeftCompoundDrawables(int drawables, int viewId) {
        TextView view = getView(viewId);
        Drawable left = context.getResources().getDrawable(drawables);
        left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());
        view.setCompoundDrawables(left, null, null, null);
        return this;
    }


    /**
     * 为ImageView设置图片
     *
     * @param viewId 资源id
     * @param drawableId 图片id
     * @return ViewHolder
     */
    public ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    public ViewHolder setImageResource(int viewId, int drawableId,
                                       LayoutParams layoutParams) {
        ImageView view = getView(viewId);
        view.setLayoutParams(layoutParams);
        view.setImageResource(drawableId);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId 资源id
     * @return ViewHolder
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为imageview设置图片
     *
     * @param viewId 资源id
     * @param url 连接
     * @param defaultDrawable 默认图片
     * @param roundPixels 圆角像素
     * @return ViewHolder
     */
    public ViewHolder setImageByUrl(int viewId, String url,
                                    int defaultDrawable, final int roundPixels) {
        final ImageView imageView = getView(viewId);
//        DisplayImageOptions options = DisplayImageOptionsUtils.getRoundBitMap(
//				roundPixels, defaultDrawable);
//		try {
//			ImageLoader.getInstance().displayImage(url, imageView, options);
//		} catch (Exception e) {
//			ImageLoader.getInstance().displayImage(
//					"drawable://" + defaultDrawable, imageView, options);
//		}
        ImageLoaderUtil.getInstance().loadRoundCornerImage(url, defaultDrawable,
                imageView, roundPixels, 0);
        //使用Glide优化加载
//        Glide.with(context)
//                .load(url)
//                .asBitmap()
////                .fitCenter()
//                .placeholder(defaultDrawable)
////				.crossFade()
//                .into(new BitmapImageViewTarget(imageView) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        RoundedBitmapDrawable circularBitmapDrawable =
//                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                        circularBitmapDrawable.setCornerRadius(roundPixels);
//                        imageView.setImageDrawable(circularBitmapDrawable);
//                    }
//                });
        return this;
    }

    /**
     * 设置圆形图片
     *
     * @param viewId 资源id
     * @param url 链接
     * @param defaultDrawable 默认图片
     * @return ViewHolder
     */
    public ViewHolder setCircleImageByUrl(int viewId, String url,
                                          int defaultDrawable) {
        ImageView imageView = (ImageView) getView(viewId);
        DisplayImageOptions options = DisplayImageOptionsUtils
                .getCircleBitMap(defaultDrawable);
        try {
            ImageLoader.getInstance().displayImage(url, imageView, options);
        } catch (Exception e) {
            ImageLoader.getInstance().displayImage(
                    "drawable://" + defaultDrawable, imageView, options);
        }
        return this;
    }

    /**
     * 显示本地图片
     *
     * @param viewId 资源id
     * @param path 图片路径
     * @param defaultDrawable 默认图片
     * @return ViewHolder
     */
    public ViewHolder setImageView(int viewId, String path,
                                   int defaultDrawable, LayoutParams layoutParams) {
        ImageView imageView = (ImageView) getView(viewId);
        if (null != layoutParams) {
            imageView.setLayoutParams(layoutParams);
        }
        DisplayImageOptions options = DisplayImageOptionsUtils
                .getPickBitMap(defaultDrawable);
        try {
            ImageLoader.getInstance().displayImage(path, imageView, options);
        } catch (Exception e) {
            ImageLoader.getInstance().displayImage(
                    "drawable://" + defaultDrawable, imageView, options);
            // imageView.setImageDrawable(BaseApplication.mApp.getResources().getDrawable(defaultDrawable));
        }
        return this;
    }

    /**
     * 为RatingBar设置评分
     *
     * @param viewId 资源id
     * @param rate 星数
     * @return ViewHolder
     */
    public ViewHolder setRating(int viewId, String rate) {
        RatingBar view = getView(viewId);
        float rating = Float.parseFloat(rate);
        if (rating < 0) {
            rating = 0;
        }
        view.setRating(rating);
        return this;
    }

    public int getPosition() {
        return mPosition;
    }

    /**
     * 设置相对布局的显示和影藏
     *
     * @param viewId  资源id
     * @param visibility 是否可见
     */
    public void setRelativeLayoutVisibility(int viewId, int visibility) {
        RelativeLayout relativeLayout = getView(viewId);
        relativeLayout.setVisibility(visibility);
    }

    /**
     * 设置线性布局的显示和隐藏
     *
     * @param viewId 资源id
     * @param visibility 是否可见
     */
    public void setLinearLayoutVisibility(int viewId, int visibility) {
        LinearLayout linearLayout = getView(viewId);
        linearLayout.setVisibility(visibility);
    }

    /**
     * 设置imageview的显示与隐藏
     *
     * @param viewId 资源id
     * @param visibility 是否可见
     */
    public void setImageViewVisibility(int viewId, int visibility) {
        ImageView view = getView(viewId);
        view.setVisibility(visibility);
    }

    /**
     * 设置textview的显示与隐藏
     *
     * @param viewId 资源id
     * @param visibility 是否可见
     */
    public void setTextViewVisibility(int viewId, int visibility) {
        TextView view = getView(viewId);
        view.setVisibility(visibility);
    }

    public void setButtonVisibility(int viewId, int visibility) {
        Button view = getView(viewId);
        view.setVisibility(visibility);
    }

    public void setButtonSelected(int viewId, boolean isSelected) {
        Button view = getView(viewId);
        view.setSelected(isSelected);
    }

    public ViewHolder setViewVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    /**
     * 设置进度条
     *
     * @param viewId 资源id
     * @param text 文字
     * @return ViewHolder
     */
    @SuppressLint("UseValueOf")
    public ViewHolder setProgress(int viewId, String text) {
        ProgressBar view = getView(viewId);
        view.setProgress(new Integer(text));
        return this;
    }

    /**
     * 设置进度条最大进度
     *
     * @param viewId 资源id
     * @param text 文字
     * @return ViewHolder
     */
    @SuppressLint("UseValueOf")
    public ViewHolder setProgressMax(int viewId, String text) {
        ProgressBar view = getView(viewId);
        view.setMax(new Integer(text));
        return this;
    }
}
