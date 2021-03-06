package com.yinxt.surveyscale.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-18 14:35
 */
@Data
public class ScalePaperListVO {
    /**
     * 量表答卷编号
     */
    private String scalePaperId;
    /**
     * 报告表答卷
     */
    private String examinationPaperId;
    /**
     * 医生编号
     */
    private String doctorId;
    /**
     * 量表编号
     */
    private String scaleId;
    /**
     * 量表名称
     */
    private String scaleName;
    /**
     * 用时
     */
    private String useTime;
    /**
     * 答题时间
     */
    private Date createTime;
    /**
     * 评定状态
     */
    private String judgeStatus;
    /**
     * 总分
     */
    private Double totalScore;
    /**
     * 评定人
     */
    private String checkUser;
    /**
     * 评分时间
     */
    private Date checkTime;

}
