package com.jinhui.contract.util;

/**
 * 获取错误信息的工具类
 */
public class ResultInfoFactory<T> {

    /**
     * 带错误信息错误信息相应体
     * @param errorInfo
     * @return
     */
    public static ResultInfo getErrorResultInfo(String errorInfo){
        return new ResultInfo("fail",errorInfo);
    }

    /**
     * 不带参数错误信息相应体
     * 默认为错误信息为操作失败
     * @return
     */
    public static ResultInfo getErrorResultInfo(){
        return getErrorResultInfo("操作失败！！！");
    }

    /**
     * 带参数正确的实体相应题
     * @param errorInfo
     * @return
     */
    public static ResultInfo getSuccessResultInfo(String errorInfo){
        return new ResultInfo("success",errorInfo);
    }

    /**
     * 不带参数正确的信息相应体
     * 默认为错误信息为操作成功
     * @return
     */
    public static ResultInfo getSuccessResultInfo(){
        return getSuccessResultInfo("操作成功！！！");
    }


    public ResultInfo getSuccessData(T userInfo) {
        ResultInfo successResultInfo = new ResultInfo("success","操作成功！！！");
        successResultInfo.setObject(userInfo);
        return successResultInfo;
    }


}
