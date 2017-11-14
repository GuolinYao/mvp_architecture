package com.hishixi.tiku.custom.recycler.animators;

import android.animation.Animator;
import android.view.View;

/**
 * 动画组接口
 */
public interface IAnimation {

    Animator[] getAnimators(View view);
}
