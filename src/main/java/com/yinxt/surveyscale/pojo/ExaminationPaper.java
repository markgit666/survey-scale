package com.yinxt.surveyscale.pojo;

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
