package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 空白试卷请求dto
 */
@Data
public class BlankExaminationPaperReqDTO {
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

}
