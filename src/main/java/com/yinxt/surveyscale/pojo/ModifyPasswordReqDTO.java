package com.yinxt.surveyscale.pojo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 修改密码dto
 *
 * @author yinxt
 * @version 1.0
 * @date 2019-10-07 23:08
 */
@Data
public class ModifyPasswordReqDTO {

    /**
     * 邮箱地址
     */
    @NotBlank(message = "邮箱地址不能为空")
    private String emailAddress;
    /**
     * 邮箱验证码
     */
    @NotBlank(message = "邮箱验证码不能为空")
    private String verifyCode;
    /**
     * 新密码
     */
    @NotBlank(message = "密码不能为空")
    private String newPassword;
}
