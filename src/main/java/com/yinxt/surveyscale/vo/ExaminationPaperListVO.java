package com.yinxt.surveyscale.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-17 17:46
 */
@Data
public class ExaminationPaperListVO {
    /**
     * 试卷编号
     */
    private String examinationPaperId;
    /**
     * 报告表编号
     */
    private String reportId;
    /**
     * 报告表名称
     */
    private String reportName;
    /**
     * 被试者名称
     */
    private String patientName;
    /**
     * 答题次序
     */
    private int answerSequence;
    /**
     * 已答量表数
     */
    private int answerNum;
    /**
     * 是否需要继续答题
     */
    private String isNeedContinueAnswer;
    /**
     * 不良反应
     */
    private String adverseReactions;
    /**
     * 用药情况
     */
    private String medication;
    /**
     * 量表数量
     */
    private int scaleNum;
    /**
     * 答题时间
     */
    private Date createTime;
}
