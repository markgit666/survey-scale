package com.yinxt.surveyscale.entity;

import lombok.Data;

import java.util.Date;

/**
 * 评定pojo
 */
@Data
public class JudgeInfo {
    /**
     * 评定ID
     */
    private String judgeId;
    /**
     * 试卷ID
     */
    private String examinationPaperId;
    /**
     * 评分人
     */
    private String checkUser;
    /**
     * 评分时间
     */
    private String checkTime;
    /**
     * 总分
     */
    private int totalScore;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String updateTime;
}
