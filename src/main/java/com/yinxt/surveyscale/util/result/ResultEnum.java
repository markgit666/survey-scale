package com.yinxt.surveyscale.util.result;

/**
 * 消息返回状态枚举
 */
public enum ResultEnum {
    SYSTEM_ERROR("100000", "系统错误"),
    SUCCESS("000000", "成功"),
    AUTHC("000001", "认证成功"),
    REGISTER_SUCCESS("000002", "注册成功"),
    UNAUTHC("100001", "用户未登录"),
    AUTHC_ERROR("100002", "认证失败"),
    PASSWORD_NOT_EQUAL("100003", "两次密码不相等"),
    LOGIN_NAME_EXISTS("100004", "登录名已存在"),
    PARRAM_NOT_READ_ERROR("100005", "参数异常");

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
