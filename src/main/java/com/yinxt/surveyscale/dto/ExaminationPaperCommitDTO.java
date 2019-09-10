package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 提交试卷信息dto
 */
@Data
public class ExaminationPaperCommitDTO {
    /**
     * 量表ID
     */
    @NotBlank(message = "量表ID不能为空")
    private String scaleId;
    /**
     * 病人ID
     */
    @NotBlank(message = "病人编号不能为空")
    private String patientId;
    /**
     * 答题时间
     */
    private int useTime;
    /**
     * 答案信息
     */
    private List<CommitAnswerReqDTO> answerList;

}
