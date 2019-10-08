package com.yinxt.surveyscale.dto;

import lombok.Data;

/**
 * 提交试卷信息dto
 */
@Data
public class ExaminationPaperReqDTO {
    /**
     * 量表ID
     */
    private String scaleId;
    /**
     * 病人ID
     */
    private String patientId;
    /**
     * 医生ID
     */
    private String doctorId;
    /**
     * 答卷ID
     */
    private String examinationPaperId;

}
