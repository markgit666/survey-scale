package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AnswerJudgeDTO {
    /**
     * 答案ID
     */
    @NotBlank(message = "答案编号不能为空")
    private String answerId;
    /**
     * 问题编号
     */
    @NotBlank(message = "题目编号不能为空")
    private String questionId;
    /**
     * 分数
     */
    private double score;

}

