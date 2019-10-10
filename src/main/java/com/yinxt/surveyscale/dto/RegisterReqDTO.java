package com.yinxt.surveyscale.dto;

import lombok.Data;

/**
 * 注册请求dto
 *
 * @author yinxt
 * @version 1.0
 * @date 2019-10-10 22:43
 */
@Data
public class RegisterReqDTO {
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 密码
     */
    private String password;
    /**
     * 确认密码
     */
    private String confirmPassword;
    /**
     * 注册验证码
     */
    private String verifyCode;
}
