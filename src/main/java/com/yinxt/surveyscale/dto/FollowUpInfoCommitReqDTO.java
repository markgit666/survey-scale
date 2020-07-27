package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-07-27 22:47
 */
@Data
public class FollowUpInfoCommitReqDTO {
    /**
     * 报告表答卷编号
     */
    @NotBlank(message = "报告表不能为空")
    private String examinationPaperId;
    /**
     * 被试者编号
     */
    @NotBlank(message = "被试者编号为空")
    private String patientId;
    /**
     * 报告表编号
     */
    @NotBlank(message = "报告表编号为空")
    private String reportId;
    /**
     * 不良反应
     */
    private String adverseReactions;
    /**
     * 用药情况
     */
    private String medication;
}
