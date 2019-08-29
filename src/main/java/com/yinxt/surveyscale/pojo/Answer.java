package com.yinxt.surveyscale.pojo;

import lombok.Data;

import java.util.Date;
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
    private String examinationPaperId;
    /**
     * 答案内容
     */
    private String content;
    /**
     * 选择题答案列表
     */
    private List<String> chooseAnswerList;
    /**
     * 每道题的分数
     */
    private int score;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
