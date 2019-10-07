package com.yinxt.surveyscale.pojo;

import lombok.Data;

/**
 * 医生pojo
 */
@Data
public class DoctorAuthInfo {

    /**
     * 医生ID
     */
    private String doctorId;
    /**
     * 医生姓名
     */
    private String doctorName;
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
    /**
     * 验证码token
     */
    private String captchaToken;
    /**
     * 验证码
     */
    private String captcha;
    /**
     * 盐
     */
    private String salt;

}
