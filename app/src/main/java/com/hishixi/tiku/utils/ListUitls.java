package com.hishixi.tiku.utils;


import com.hishixi.tiku.mvp.model.entity.SelectedImageBean;

import java.util.ArrayList;

/**
 * 集合的工具类
 *
 */
public class ListUitls {
    /**
     * 图片初始时的选择
     *
     * @param selectedPicture selectedPicture
     * @param currentPath currentPath
     * @return SelectedImageBean
     */
    public static SelectedImageBean getImageIsSelected(ArrayList<SelectedImageBean>
                                                               selectedPicture, String
                                                               currentPath) {
        if (null == selectedPicture || selectedPicture.isEmpty())
            return new SelectedImageBean(null, -1, false);
        SelectedImageBean bean = new SelectedImageBean(null, -1, false);
        for (SelectedImageBean selectedImageBean : selectedPicture) {
            String path = selectedImageBean.getPath();
            if (path.equals(currentPath)) {
                bean = new SelectedImageBean(path, selectedImageBean
                        .getSelectedPosition(), true);
                break;
            }
        }
        return bean;
    }


}
