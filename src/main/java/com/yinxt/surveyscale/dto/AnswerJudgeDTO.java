package com.yinxt.surveyscale.dto;

import lombok.Data;

@Data
public class AnswerJudgeDTO {
    /**
     * 答案ID
     */
    private String answerId;
    /**
     * 问题编号
     */
    private String questionId;
    /**
     * 分数
     */
    private double score;

}

