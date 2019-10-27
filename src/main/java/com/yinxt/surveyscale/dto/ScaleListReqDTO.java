package com.yinxt.surveyscale.dto;

import lombok.Data;

/**
 * 量表列表请求dto
 *
 * @author yinxt
 * @version 1.0
 * @date 2019-10-27 16:50
 */
@Data
public class ScaleListReqDTO {
    /**
     * 量表名称
     */
    private String scaleName;
}
