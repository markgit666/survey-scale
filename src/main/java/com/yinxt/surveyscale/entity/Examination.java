package com.yinxt.surveyscale.entity;

import lombok.Data;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-16 16:39
 */
@Data
public class Examination {
    /**
     * 试卷编号
     */
    private String examinationPaperId;
    /**
     * 被试者编号
     */
    private String patientId;
    /**
     * 报告编号
     */
    private String reportId;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String updateTime;
}
