package com.yinxt.surveyscale.vo;

import lombok.Data;

/**
 * 答卷信息列表返回vo
 *
 * @author yinxt
 * @version 1.0
 * @date 2019-10-29 00:01
 */
@Data
public class ExaminationPaperScalesListRespVO {
    /**
     * 报告表答卷编号
     */
    private String examinationPaperId;
    /**
     * 报告表名称
     */
    private String reportName;
    /**
     * 量表答卷编号
     */
    private String scalePaperId;
    /**
     * 量表名称
     */
    private String scaleName;
    /**
     * 被试者姓名
     */
    private String patientName;
    /**
     * 题目数量
     */
    private int questionCount;
    /**
     * 用时
     */
    private String useTime;
    /**
     * 答题日期
     */
    private String examinationDate;
    /**
     * 评分状态
     */
    private String judgeStatus;
    /**
     * 评定人
     */
    private String checkUser;
    /**
     * 总分
     */
    private Double totalScore;
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

}
