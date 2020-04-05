package com.yinxt.surveyscale.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-12 19:54
 */
@Data
public class Report {

    /**
     * 报告编号
     */
    private String reportId;
    /**
     * 报告名称
     */
    private String reportName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}
