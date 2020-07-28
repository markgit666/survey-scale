package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 删除报告表答请求DTO
 *
 * @author yinxt
 * @version 1.0
 * @date 2020/4/20 17:31
 */
@Data
public class RemoveExaminationPaperReqDTO {

    /**
     * 报告表答卷编号
     */
    @NotBlank(message = "报告表答卷编号不能为空")
    private String examinationPaperId;
}
