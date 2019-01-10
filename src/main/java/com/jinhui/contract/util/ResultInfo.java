package com.jinhui.contract.util;

/**
 * REST请求相应体
 */
public class ResultInfo<T> {

    // 操作结果
    private String resultCode;

    // 错误信息
    private String errorInfo;

    // 附属对象
    private T object;

    public ResultInfo(String resultCode, String errorInfo) {
        super();
        this.resultCode = resultCode;
        this.errorInfo = errorInfo;
    }

    public ResultInfo(String resultCode, String errorInfo, T object) {
        super();
        this.resultCode = resultCode;
        this.errorInfo = errorInfo;
        this.object = object;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }


}

