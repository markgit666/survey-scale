package com.yinxt.surveyscale.common.result;

import lombok.Data;

/**
 * 消息返回类
 */
@Data
public class Result<T> {
    /**
     * 状态码
     */
    private String retCode;
    /**
     * 消息
     */
    private String retMsg;
    /**
     * 数据内容
     */
    private T data;

    public Result() {

    }

    public Result(String retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public Result(String retCode, String retMsg, T data) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.data = data;
    }

    public Result(ResultEnum resultEnum) {
        this.retCode = resultEnum.getCode();
        this.retMsg = resultEnum.getName();
    }

    public Result(ResultEnum resultEnum, T data) {
        this.retCode = resultEnum.getCode();
        this.retMsg = resultEnum.getName();
        this.data = data;
    }

    public boolean isSuccess() {
        if (ResultEnum.SUCCESS.getCode().equals(this.getRetCode())) {
            return true;
        } else {
            return false;
        }
    }

    public static Result success() {
        return new Result(ResultEnum.SUCCESS);
    }

    public static Result success(ResultEnum resultEnum) {
        return new Result(resultEnum.getCode(), resultEnum.getName());
    }

    public static Result success(Object data) {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getName(), data);
    }

    public static Result success(ResultEnum resultEnum, Object data) {
        return new Result(resultEnum.getCode(), resultEnum.getName(), data);
    }

    public static Result error() {
        return new Result(ResultEnum.SYSTEM_ERROR.getCode(), ResultEnum.SYSTEM_ERROR.getName());
    }

    public static Result error(String errorCode, String errorMsg) {
        return new Result(errorCode, errorMsg);
    }

    public static Result error(ResultEnum resultEnum) {
        return new Result(resultEnum.getCode(), resultEnum.getName());
    }

    public static Result paramError(String errorMsg) {
        return new Result(ResultEnum.PARAM_NOT_READ_ERROR.getCode(), errorMsg);
    }
}
