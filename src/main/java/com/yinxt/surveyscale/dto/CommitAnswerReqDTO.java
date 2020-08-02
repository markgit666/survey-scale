package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 每题的答案dto
 */
@Data
public class CommitAnswerReqDTO {
    /**
     * 题目ID
     */
    @NotBlank(message = "问题编号不能为空")
    private String questionId;
    /**
     * 内容
     */
//    @NotBlank(message = "答案内容不能为空")
    private String content;
    /**
     * 每一题的得分
     */
    private double score;
    /**
     * 插入内容
     */
    private String insertContent;
    /**
     * 选择题答案列表
     */
    private List<String> chooseAnswerList;
}
