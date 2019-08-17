package com.yinxt.surveyscale.dto;

import lombok.Data;

/**
 * 获取答题量表请求DTO
 */
@Data
public class GetAnswerReqDTO {
    /**
     * 病人ID
     */
    private String patientId;
    /**
     * 量表ID
     */
    private String scaleId;
}
