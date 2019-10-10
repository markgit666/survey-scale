package com.yinxt.surveyscale.common.enums;

/**
 * 文件类型枚举
 */
public enum FileTypeEnum {

    IMAGE("1", "图片"),
    DOC("2", "文档"),
    AUDIO("3", "音频"),
    VIDEO("4", "视频");

    private String code;
    private String name;

    FileTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
