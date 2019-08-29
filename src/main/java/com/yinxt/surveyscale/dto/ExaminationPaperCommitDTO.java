package com.yinxt.surveyscale.dto;

import lombok.Data;

import java.util.List;

/**
 * 提交试卷信息dto
 */
@Data
public class ExaminationPaperCommitDTO {
    /**
     * 量表ID
     */
    private String scaleId;
    /**
     * 病人ID
     */
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
