package com.yinxt.surveyscale.dto;

import lombok.Data;

/**
 * 继续答题请求DTO
 *
 * @author yinxt
 * @version 1.0
 * @date 2020-07-28 00:41
 */
@Data
public class ContinueExaminationReqDTO {
    /**
     * 报告表答卷编号
     */
    private String examinationPaperId;
    
}
