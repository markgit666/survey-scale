package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 检验病人信息状态请求DTO
 *
 * @author yinxt
 * @version 1.0
 * @date 2020-03-14 12:45
 */
@Data
public class CheckPatientStatusReqDTO {
    /**
     * 报告表id
     */
    @NotBlank(message = "报告表编号不能为空")
    private String reportId;
    /**
     * 身份证号
     */
    @NotBlank(message = "身份证号不能为空")
    private String idCard;

}
