package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录请求dto
 *
 * @author yinxt
 * @version 1.0
 * @date 2019-10-10 22:30
 */
@Data
public class LoginReqDTO {
    /**
     * 登录名
     */
    @NotBlank(message = "登录名不能为空")
    private String loginName;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
    /**
     * 记住我
     */
    private boolean rememberMe;
    /**
     * 验证码token
     */
    @NotBlank(message = "验证码token不能为空")
    private String captchaToken;
    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String captcha;
}
