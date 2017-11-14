package com.hishixi.tiku.mvp.view.adapter.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hishixi.tiku.R;
import com.hishixi.tiku.mvp.view.holder.RecyclerViewHolder;

import java.util.List;

/**
 * 基础recycler view的adapter
 */
public abstract class BaseRecyclerViewAdapter<T>
        extends RecyclerView.Adapter<RecyclerViewHolder> {

    protected Context mContext;

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

    public List<T> mLists;
    protected LayoutInflater mInflater;
    protected int mItemLayoutId;
    public final static int TYPE_HEADER = 0; // 头部
    public final static int TYPE_NORMAL = 1; // 正常的一条
    public final static int TYPE_FOOTER = 2;//底部--往往是loading_more

    public BaseRecyclerViewAdapter(Context context, List<T> mDatas, @LayoutRes int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mLists = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            default:
            case TYPE_NORMAL:
                return RecyclerViewHolder.get(mContext, parent, mItemLayoutId);
            case TYPE_FOOTER:
                return RecyclerViewHolder.get(mContext, parent, R.layout.loadmore_layout);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        if (null != mOnItemClickLitener) {
            holder.itemView.setOnClickListener(view -> mOnItemClickLitener.onItemClick(holder
                    .itemView, position));

            holder.itemView.setOnLongClickListener(view -> {
                mOnItemClickLitener.onItemLongClick(holder.itemView, position);
                return false;
            });
        }
        switch (getItemViewType(position)) {
            case TYPE_FOOTER://加载更多
                break;
            default:
            case TYPE_NORMAL://普通条目
                convert(holder, mLists.get(position), position);
                break;

        }

    }

    /**
     * 用来制造recyclerView阴影 不是都需要复写
     *
     * @param holder        holder
     * @param item          条目
     * @param position      位置
     * @param isConvertView 是否是复用view
     */
    public void convert(RecyclerViewHolder holder, T item, int position, boolean
            isConvertView) {

    }

    /**
     * 设置itemview 数据
     *
     * @param holder viewholder
     * @param t      具体数据Bean
     */
    public abstract void convert(RecyclerViewHolder holder, T t, int position);

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public int getItemViewType(int position) {
        T t = mLists.get(position);
        if (null == mLists.get(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }

    }
}
