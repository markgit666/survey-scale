package com.yinxt.surveyscale.entity;

import lombok.Data;

import java.util.Date;

/**
 * 文档pojo
 */
@Data
public class FileInfo {
    private String fileNo;
    private String fileName;
    private String fileType;
    private String createTime;
    private String updateTime;
}
