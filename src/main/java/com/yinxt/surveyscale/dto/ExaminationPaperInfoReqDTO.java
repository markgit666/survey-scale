package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 答卷信息请求dto
 *
 * @author yinxt
 * @version 1.0
 * @date 2019-10-27 17:20
 */
@Data
public class ExaminationPaperInfoReqDTO {
    /**
     * 答卷id
     */
    @NotBlank(message = "答卷id不能未空")
    private String examinationPaperId;
}
