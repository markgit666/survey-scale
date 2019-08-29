package com.yinxt.surveyscale.dto;

import lombok.Data;

import java.util.List;

/**
 * 答卷评定信息请求dto
 */
@Data
public class ExaminationPaperJudgeReqDTO {

    /**
     * 答卷ID
     */
    private String examinationPaperId;
    /**
     * 评定人
     */
    private String checkUser;
    /**
     * 总分
     */
    private int totalScore;
    /**
     * 答案评定信息
     */
    private List<AnswerJudgeDTO> answerJudgeList;
}
