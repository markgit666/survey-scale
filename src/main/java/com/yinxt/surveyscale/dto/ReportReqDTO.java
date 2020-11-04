package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-12 23:46
 */
@Data
public class ReportReqDTO {

    /**
     * 报告表ID
     */
    @NotBlank(message = "报告表ID不能为空")
    private String reportId;
}
