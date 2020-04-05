package com.yinxt.surveyscale.dto;

import lombok.Data;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-12 17:09
 */
@Data
public class ReportListReqDTO {
    /**
     * 医生编号
     */
    private String doctorId;
    /**
     * 报告名称
     */
    private String reportName;
}
