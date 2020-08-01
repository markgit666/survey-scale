package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 被试者相关信息DTO
 *
 * @author yinxt
 * @version 1.0
 * @date 2020-03-14 21:52
 */
@Data
public class PatientRelationInfoDTO {

    /**
     * 被试者基本信息
     */
    @Valid
    @NotNull(message = "被试者基本信息不能为空")
    private PatientInfoCommitReqDTO patientInfo;
    /**
     * 实验条件信息
     */
//    @Valid
//    @NotEmpty(message = "实验条件信息不能为空")
    private List<PatientEligibleDTO> patientEligibleList;

}
