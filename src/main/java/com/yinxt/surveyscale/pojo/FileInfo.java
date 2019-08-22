package com.yinxt.surveyscale.pojo;

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
    private Date createTime;
    private Date updateTime;
}
