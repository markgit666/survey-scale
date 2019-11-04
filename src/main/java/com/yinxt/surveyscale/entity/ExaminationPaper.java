package com.yinxt.surveyscale.entity;

import lombok.Data;

import java.util.Date;

/**
 * 试卷pojo
 */
@Data
public class ExaminationPaper {
    /**
     * 试卷ID
     */
    private String examinationPaperId;
    /**
     * 病人ID
     */
    private String patientId;
    /**
     * 病人信息
     */
    private PatientInfo patientInfo;
    /**
     * 量表ID
     */
    private String scaleId;
    /**
     * 量表信息
     */
    private ScaleInfo scaleInfo;
    /**
     * 评定信息
     */
    private JudgeInfo judgeInfo;
    /**
     * 答题时间
     */
    private Double useTime;
    /**
     * 评分状态
     */
    private String judgeStatus;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
}
