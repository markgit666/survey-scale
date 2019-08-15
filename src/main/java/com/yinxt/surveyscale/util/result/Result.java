package com.yinxt.surveyscale.util.result;

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

    public static Result success() {
        return Result.success(null);
    }

    public static Result success(Object data) {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getName(), data);
    }

    public static Result error() {
        return new Result<>(ResultEnum.SYSTEM_ERROR.getCode(), ResultEnum.SYSTEM_ERROR.getName());
    }
}
