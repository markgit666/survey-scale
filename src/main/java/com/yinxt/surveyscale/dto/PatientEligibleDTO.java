package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-14 23:09
 */
@Data
public class PatientEligibleDTO {
    /**
     * 条件编号
     */
    @NotBlank(message = "实验条件编号不能为空")
    private String eligibleId;
    /**
     * 被试者编号
     */
    private String patientId;
    /**
     * 答案
     */
    @NotBlank(message = "实验条件答案不能为空")
    private String answer;
    /**
     * 备注
     */
    private String remarks;
}
