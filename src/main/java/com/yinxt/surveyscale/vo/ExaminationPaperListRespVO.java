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
public class ExaminationPaperListRespVO {
    /**
     * 答卷编号
     */
    private String examinationPaperId;
    /**
     * 量表名称
     */
    private String scaleName;
    /**
     * 病人名称
     */
    private String patientName;
    /**
     * 用时
     */
    private String useTime;
    /**
     * 评分状态
     */
    private String judgeStatus;
    /**
     * 总分
     */
    private String totalScore;
}
