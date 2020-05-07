package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.dto.FindBackPasswordReqDTO;
import com.yinxt.surveyscale.dto.LoginReqDTO;
import com.yinxt.surveyscale.dto.RegisterReqDTO;
import com.yinxt.surveyscale.dto.VerifyCodeReqDTO;
import com.yinxt.surveyscale.dto.ModifyPasswordReqDTO;
import com.yinxt.surveyscale.service.CaptchaService;
import com.yinxt.surveyscale.service.DoctorInfoService;
import com.yinxt.surveyscale.common.result.Result;
import com.yinxt.surveyscale.common.result.ResultEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"doctor:"}, value = "医生相关", description = "医生相关APIdd")
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
    @ApiOperation(value = "登录", notes = "登录", httpMethod = "POST")
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
    @ApiOperation(value = "注册", notes = "注册", httpMethod = "POST")
    @RequestMapping(value = "register")
    public Result register(@RequestBody RegisterReqDTO registerReqDTO) {
        return doctorInfoService.register(registerReqDTO);
    }

    /**
     * 未认证处理
     *
     * @return
     */
    @ApiOperation(value = "未认证", notes = "未认证跳转", httpMethod = "GET")
    @RequestMapping(value = "unauthc")
    public Result unauthc() {
        return Result.error(ResultEnum.UNAUTHC);
    }

    /**
     * 登出
     *
     * @return
     */
    @ApiOperation(value = "登出", notes = "登出", httpMethod = "POST")
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
    @ApiOperation(value = "获取注册验证码", notes = "获取注册验证码", httpMethod = "POST")
    @RequestMapping(value = "verifyCode/get")
    public Result getRegisterVerifyCode(@RequestBody VerifyCodeReqDTO verifyCodeReqDTO) {
        return doctorInfoService.getRegisterVerifyCode(verifyCodeReqDTO);
    }

    /**
     * 获取图片验证码
     *
     * @return
     */
    @ApiOperation(value = "获取图片验证码", notes = "获取图片验证码", httpMethod = "GET")
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
    @ApiOperation(value = "找回密码", notes = "找回密码", httpMethod = "POST")
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
    @ApiOperation(value = "修改密码", notes = "修改密码", httpMethod = "POST")
    @RequestMapping(value = "password/modify")
    public Result modifyPassword(@RequestBody ModifyPasswordReqDTO modifyPasswordReqDTO) {
        return doctorInfoService.modifyPassword(modifyPasswordReqDTO);
    }

}
