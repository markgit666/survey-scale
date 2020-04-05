package com.yinxt.surveyscale.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-15 19:22
 */
@Data
public class ExaminationPaperDTO {

    /**
     * 试卷编号
     */
    private String examinationPaperId;
    /**
     * 被试者编号
     */
    private String patientId;
    /**
     * 报告表编号
     */
    private String reportId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
