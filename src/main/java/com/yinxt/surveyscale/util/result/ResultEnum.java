package com.yinxt.surveyscale.util.result;

/**
 * 消息返回状态枚举
 */
public enum ResultEnum {
    SYSTEM_ERROR("100000", "系统错误"),
    SUCCESS("000000", "成功");

    private String code;
    private String name;

    ResultEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
