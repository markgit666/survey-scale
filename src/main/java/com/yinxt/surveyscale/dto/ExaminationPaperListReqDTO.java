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
     * 量表名称
     */
    private String scaleName;
    /**
     * 病人姓名
     */
    private String patientName;
    /**
     * 评分状态（已评分：1；未评分：0）
     */
    private String judgeStatus;
}
