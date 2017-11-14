package com.hishixi.tiku.mvp.model.entity;

import java.io.Serializable;

/**
 * @author guolin
 * @date:2015-11-24 下午5:44:08
 */
public class ImageItem implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public String imageId;
    public String thumbnailPath;// 缩略图路径
    public String imagePath;// 原图路径
    public boolean isSelected = false;
    public String path;

    public ImageItem(String path) {
        this.path = path;
    }
}