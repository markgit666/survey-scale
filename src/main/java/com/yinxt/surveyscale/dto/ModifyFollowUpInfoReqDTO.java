package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 修改随访信息请求DTO
 *
 * @author yinxt
 * @version 1.0
 * @date 2020/8/25 17:23
 */
@Data
public class ModifyFollowUpInfoReqDTO {

    @NotBlank(message = "答卷编号不能为空")
    private String examinationPaperId;
    /**
     * 不良反应
     */
    private String adverseReactions;
    /**
     * 用药情况
     */
    private String medication;

}
