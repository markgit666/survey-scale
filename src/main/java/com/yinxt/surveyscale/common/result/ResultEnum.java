package com.yinxt.surveyscale.common.result;

/**
 * 消息返回状态枚举
 */
public enum ResultEnum {
    SYSTEM_ERROR("100000", "网络异常，请稍后重试"),
    SUCCESS("000000", "成功"),
    AUTHC("000001", "认证成功"),
    REGISTER_SUCCESS("000002", "注册成功"),
    VERIFY_CODE_SEND_SUCCESS("000003", "验证码发送成功，请注意查收"),
    UNAUTHC("100001", "用户未登录"),
    AUTHC_ERROR("100002", "认证失败"),
    PASSWORD_NOT_EQUAL("100003", "两次密码不相等"),
    LOGIN_NAME_EXISTS("100004", "登录名已存在"),
    PARAM_NOT_READ_ERROR("100005", "参数异常"),
    EMAIL_SEND_EROOR("100006", "邮件发送异常"),
    VERIFY_CODE_SEND_ERROR("100007", "验证码发送失败，请稍后重试"),
    VERIFY_CODE_NOT_CORRECT("100008", "验证码不正确"),
    EMAIL_NO_REGISTER("100009", "邮箱未注册"),
    FILE_NUM_TOO_MUCH("100010", "文件数量过多"),
    FILE_TOO_BIG("100011", "文件过大，请调整文件大小后重试"),
    SERVCIE_ERROR("100012", "服务异常"),
    URL_ERROR("100013", "无此资源");

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
