package com.yinxt.surveyscale.dto;

import lombok.Data;

/**
 * 验证码发送请求DTO
 *
 * @author yinxt
 * @version 1.0
 * @date 2019-10-03 18:18
 */
@Data
public class VerifyCodeReqDTO {

    /**
     * 邮箱地址
     */
    private String emailAddress;

}
