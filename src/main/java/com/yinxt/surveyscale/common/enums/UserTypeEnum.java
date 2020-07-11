package com.yinxt.surveyscale.common.enums;

/**
 * 用户类型
 *
 * @author yinxt
 * @version 1.0
 * @date 2020-05-30 22:55
 */
public enum UserTypeEnum {

    /**
     * 管理员
     */
    ADMIN("ADMIN", "0"),
    /**
     * 普通用户
     */
    NORMAL("NORMAL", "1");

    UserTypeEnum(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String type;
    public String value;

}
