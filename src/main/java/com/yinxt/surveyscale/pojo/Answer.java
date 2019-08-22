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
     * 病人ID
     */
    private String patientId;
    /**
     * 问题ID
     */
    private String questionId;
    /**
     * 答案内容
     */
    private String content;
    /**
     * 选择题答案列表
     */
    private List<String> chooseAnswerList;
    /**
     * 答题时间
     */
    private int useTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
