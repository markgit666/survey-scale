package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 找回密码dto
 *
 * @author yinxt
 * @version 1.0
 * @date 2019-10-07 22:23
 */
@Data
public class FindBackPasswordReqDTO {

    /**
     * 邮箱地址
     */
    @NotBlank(message = "邮箱地址不能为空")
    private String emailAddress;
    /**
     * 验证码
     */
    private String captcha;
    /**
     * 验证码token
     */
    private String captchaToken;

}
