package com.yinxt.surveyscale.entity;

import lombok.Data;

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
     * 量表试卷ID
     */
    private String scalePaperId;
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
    private double totalScore;
    /**
     * 频率总分
     */
    private Double frequencyTotalScore;
    /**
     * 严重程度总分
     */
    private Double seriousTotalScore;
    /**
     * 频率*严重程度总分
     */
    private Double frequencySeriousTotalScore;
    /**
     * 苦恼程度
     */
    private Double distressTotalScore;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String updateTime;
}
