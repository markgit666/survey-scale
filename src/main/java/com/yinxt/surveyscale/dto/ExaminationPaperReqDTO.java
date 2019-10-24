package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 提交试卷信息dto
 */
@Data
public class ExaminationPaperReqDTO {
    /**
     * 量表ID
     */
    @NotBlank(message = "量表id不能为空")
    private String scaleId;
    /**
     * 病人ID
     */
    @NotBlank(message = "被试者id不能为空")
    private String patientId;
    /**
     * 医生ID
     */
    private String doctorId;
    /**
     * 答卷ID
     */
    private String examinationPaperId;

}
