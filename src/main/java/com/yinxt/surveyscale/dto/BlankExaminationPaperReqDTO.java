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
    @NotBlank(message = "报告表id不能为空")
    private String reportId;
    /**
     * 被试者ID
     */
    @NotBlank(message = "被试者编号不能为空")
    private String patientId;

}
