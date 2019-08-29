package com.yinxt.surveyscale.dto;

import lombok.Data;

import java.util.List;

/**
 * 每题的答案dto
 */
@Data
public class CommitAnswerReqDTO {
    /**
     * 题目ID
     */
    private String questionId;
    /**
     * 内容ID
     */
    private String content;
    /**
     * 选择题答案列表
     */
    private List<String> chooseAnswerList;
}
