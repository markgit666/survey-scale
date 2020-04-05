package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 提交试卷信息dto
 */
@Data
public class ExaminationPaperCommitDTO {
    /**
     * 试卷编号
     */
    @NotBlank(message = "试卷编号不能为空")
    private String examinationPaperId;
    /**
     * 报告表编号
     */
    @NotBlank(message = "报告表编号不能为空")
    private String reportId;
    /**
     * 量表ID
     */
    @NotBlank(message = "量表编号不能为空")
    private String scaleId;
    /**
     * 病人ID
     */
    @NotBlank(message = "被试者编号不能为空")
    private String patientId;
    /**
     * 答题时间
     */
    private Double useTime;
    /**
     * 评定人
     */
    private String checkUser;
    /**
     * 总分
     */
    private double totalScore;
    /**
     * 答案信息
     */
    @Valid
    @NotEmpty(message = "答案信息不能为空")
    private List<CommitAnswerReqDTO> answerList;

}
