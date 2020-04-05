package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 量表请求DTO
 */
@Data
public class ScaleInfoReqDTO {
    /**
     * 量表ID
     */
    @NotBlank(message = "量表编号不能为空")
    private String scaleId;
}
