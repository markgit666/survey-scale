package com.yinxt.surveyscale.po;

import lombok.Data;

/**
 * 答卷列表查询po
 *
 * @author yinxt
 * @version 1.0
 * @date 2019-10-27 18:00
 */
@Data
public class ExaminationPaperListQueryPO {
    /**
     * 报告表名称
     */
    private String reportName;
    /**
     * 病人姓名
     */
    private String patientName;
    /**
     * 医生id
     */
    private String doctorId;
}
