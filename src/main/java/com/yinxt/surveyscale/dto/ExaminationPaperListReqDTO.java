package com.yinxt.surveyscale.dto;

import lombok.Data;

/**
 * 答卷列表请求dto
 *
 * @author yinxt
 * @version 1.0
 * @date 2019-10-27 17:13
 */
@Data
public class ExaminationPaperListReqDTO {
    /**
     * 报告表名称
     */
    private String reportName;
    /**
     * 被试者姓名
     */
    private String patientName;
    /**
     * 答题次序
     */
    private String answerSequence;

}
