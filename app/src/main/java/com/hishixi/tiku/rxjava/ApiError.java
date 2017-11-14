package com.hishixi.tiku.rxjava;

/**
 * Created by seamus on 2017/11/2 18:22
 */

public class ApiError extends Throwable {
    private int errorCode;

    public ApiError(int returnCode) {
        this.errorCode = returnCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
