package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.dto.FindBackPasswordReqDTO;
import com.yinxt.surveyscale.dto.LoginReqDTO;
import com.yinxt.surveyscale.dto.RegisterReqDTO;
import com.yinxt.surveyscale.dto.VerifyCodeReqDTO;
import com.yinxt.surveyscale.pojo.ModifyPasswordReqDTO;
import com.yinxt.surveyscale.service.CaptchaService;
import com.yinxt.surveyscale.service.DoctorInfoService;
import com.yinxt.surveyscale.common.email.service.SendEmailService;
import com.yinxt.surveyscale.common.result.Result;
import com.yinxt.surveyscale.common.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "authc")
public class DoctorInfoController {

    @Autowired
    private DoctorInfoService doctorInfoService;
    @Autowired
    private CaptchaService captchaService;

    /**
     * 登录
     *
     * @param loginReqDTO
     * @return
     */
    @RequestMapping(value = "login")
    public Result login(@RequestBody @Valid LoginReqDTO loginReqDTO) {
        return doctorInfoService.login(loginReqDTO);
    }

    /**
     * 注册
     *
     * @param registerReqDTO
     * @return
     */
    @RequestMapping(value = "register")
    public Result register(@RequestBody RegisterReqDTO registerReqDTO) {
        return doctorInfoService.register(registerReqDTO);
    }

    /**
     * 未认证处理
     *
     * @return
     */
    @RequestMapping(value = "unauthc")
    public Result unauthc() {
        return Result.error(ResultEnum.UNAUTHC);
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "logout")
    public Result logout() {
        return doctorInfoService.logout();
    }

    /**
     * 获取注册验证码
     *
     * @param verifyCodeReqDTO
     * @return
     */
    @RequestMapping(value = "verifyCode/get")
    public Result getRegisterVerifyCode(@RequestBody VerifyCodeReqDTO verifyCodeReqDTO) {
        return doctorInfoService.getRegisterVerifyCode(verifyCodeReqDTO);
    }

    /**
     * 获取图片验证码
     *
     * @return
     */
    @RequestMapping(value = "captcha/get")
    public Result getCaptcha() {
        return captchaService.getCaptcha();
    }

    /**
     * 找回密码
     * 校验是否注册，并且发送验证码邮件
     *
     * @param findBackPasswordReqDTO
     * @return
     */
    @RequestMapping(value = "password/findBack")
    public Result findBackPassword(@RequestBody @Valid FindBackPasswordReqDTO findBackPasswordReqDTO) {
        return doctorInfoService.findBackPassword(findBackPasswordReqDTO);
    }

    /**
     * 修改密码
     *
     * @param modifyPasswordReqDTO
     * @return
     */
    @RequestMapping(value = "password/modify")
    public Result modifyPassword(@RequestBody ModifyPasswordReqDTO modifyPasswordReqDTO) {
        return doctorInfoService.modifyPassword(modifyPasswordReqDTO);
    }

}
