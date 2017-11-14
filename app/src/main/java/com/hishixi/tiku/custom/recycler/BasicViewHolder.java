package com.hishixi.tiku.custom.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * 重新封装ViewHolder
 */
public class BasicViewHolder extends RecyclerView.ViewHolder {

    public BasicViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
