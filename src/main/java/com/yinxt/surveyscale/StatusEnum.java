package com.yinxt.surveyscale;

import lombok.Data;

/**
 * 状态枚举
 */
public enum StatusEnum {

    YES("1", "有效"),
    NO("0", "失效");

    StatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
