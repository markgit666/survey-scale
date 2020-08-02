package com.yinxt.surveyscale.entity;

import lombok.Data;

import java.util.List;

/**
 * 答案pojo
 */
@Data
public class Answer {
    /**
     * 答案ID
     */
    private String answerId;
    /**
     * 问题ID
     */
    private String questionId;
    /**
     * 答卷ID
     */
    private String scalePaperId;
    /**
     * 答案内容
     */
    private String content;
    /**
     * 补充插入的内容
     */
    private String insertContent;
    /**
     * 选择题答案列表
     */
    private List<String> chooseAnswerList;
    /**
     * 每道题的分数
     */
    private double score;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;

}
