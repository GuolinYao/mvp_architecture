package com.hishixi.tiku.mvp.model;

import java.io.Serializable;

/**
 * 接口返回的数据对象
 *
 */
@SuppressWarnings("serial")
public class HttpRes<T> implements Serializable {
    private int returnCode;
    private String returnDesc;
    private T returnData;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnDesc() {
        return returnDesc;
    }

    public void setReturnDesc(String returnDesc) {
        this.returnDesc = returnDesc;
    }

    public T getReturnData() {
        return returnData;
    }

    public void setReturnData(T returnData) {
        this.returnData = returnData;
    }
}
