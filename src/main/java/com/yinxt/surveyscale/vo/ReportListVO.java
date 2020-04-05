package com.yinxt.surveyscale.vo;

import lombok.Data;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-12 20:05
 */
@Data
public class ReportListVO {

    /**
     * 报告编号
     */
    private String reportId;
    /**
     * 报告名称
     */
    private String reportName;
    /**
     * 所属医生编号
     */
    private String doctorId;
    /**
     * 所属医生姓名
     */
    private String doctorName;
    /**
     * 量表数量
     */
    private int scaleNum;

}
