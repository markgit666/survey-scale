package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.dto.FindBackPasswordReqDTO;
import com.yinxt.surveyscale.dto.VerifyCodeReqDTO;
import com.yinxt.surveyscale.pojo.DoctorAuthInfo;
import com.yinxt.surveyscale.pojo.ModifyPasswordReqDTO;
import com.yinxt.surveyscale.service.CaptchaService;
import com.yinxt.surveyscale.service.DoctorInfoService;
import com.yinxt.surveyscale.util.email.service.SendEmailService;
import com.yinxt.surveyscale.util.exeption.LogicException;
import com.yinxt.surveyscale.util.redis.RedisUtil;
import com.yinxt.surveyscale.util.result.Result;
import com.yinxt.surveyscale.util.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Random;

@Slf4j
@CrossOrigin()
@RestController
@RequestMapping(value = "authc")
public class DoctorInfoController {

    @Autowired
    private DoctorInfoService doctorInfoService;
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private CaptchaService captchaService;

    /**
     * 登录
     *
     * @param doctorAuthInfo
     * @return
     */
    @RequestMapping(value = "login")
    public Result login(@RequestBody DoctorAuthInfo doctorAuthInfo) {
        //校验验证码
        Result result = captchaService.verifyCaptcha(doctorAuthInfo.getCaptchaToken(), doctorAuthInfo.getCaptcha());
        if (!result.isSuccess()) {
            return result;
        }
        //验证账号和密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(doctorAuthInfo.getLoginName(), doctorAuthInfo.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            return Result.error(ResultEnum.AUTHC_ERROR);
        }
        if (subject.isAuthenticated()) {
            String sessionId = subject.getSession().getId().toString();
            doctorAuthInfo = doctorInfoService.getDoctorInfoByLoginName(doctorAuthInfo.getLoginName());
            RedisUtil.setKey("identity_" + sessionId, doctorAuthInfo.getDoctorId(), 600);
            return Result.success(ResultEnum.AUTHC, sessionId);
        } else {
            return Result.error(ResultEnum.AUTHC_ERROR);
        }
    }

    /**
     * 注册
     *
     * @param doctorAuthInfo
     * @return
     */
    @RequestMapping(value = "register")
    public Result register(@RequestBody DoctorAuthInfo doctorAuthInfo) {
        Result result;
        //校验验证码
        String cacheKey = doctorAuthInfo.getLoginName() + "_register";
        String cacheValue = (String) RedisUtil.getKey(cacheKey);
        if (StringUtils.isNotBlank(cacheValue) && StringUtils.equals(cacheValue, doctorAuthInfo.getVerifyCode())) {
            result = doctorInfoService.register(doctorAuthInfo);
            if (ResultEnum.REGISTER_SUCCESS.getCode().equals(result.getRetCode())) {
                //删除验证码缓存
                RedisUtil.deleteKey(cacheKey);
            }
        } else {
            result = Result.error(ResultEnum.VERIFY_CODE_NOT_CORRECT);
        }
        return result;
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
        SecurityUtils.getSubject().logout();
        return Result.success();
    }

    /**
     * 获取注册验证码
     *
     * @param verifyCodeReqDTO
     * @return
     */
    @RequestMapping(value = "verifyCode/get")
    public Result getVerifyCode(@RequestBody VerifyCodeReqDTO verifyCodeReqDTO) {
        //校验是否注册
        String emailAddress = verifyCodeReqDTO.getEmailAddress();
        DoctorAuthInfo doctorAuthInfo = doctorInfoService.getDoctorInfoByLoginName(emailAddress);
        if (doctorAuthInfo != null) {
            Result.error(ResultEnum.LOGIN_NAME_EXISTS);
        }
        //发送验证码邮件
        try {
            String code = sendEmailService.sendVerifyCodeEmail(emailAddress);
            //将验证码缓存入redis
            RedisUtil.setKey(emailAddress + "_register", code, 600);
        } catch (Exception e) {
            log.error("邮件发送失败：", e);
            throw new LogicException("验证码发送失败，请检查邮件是否填写无误");
        }
        return Result.success(ResultEnum.VERIFY_CODE_SEND_SUCCESS);
    }

    /**
     * 获取图片验证码
     *
     * @return
     */
    @RequestMapping(value = "captcha/get")
    public Result getKaptcha() {
        Map<String, Object> captchaMap = captchaService.getCaptcha();
        return Result.success(captchaMap);
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
        //校验验证码
        Result result = captchaService.verifyCaptcha(findBackPasswordReqDTO.getCaptchaToken(), findBackPasswordReqDTO.getCaptcha());
        if (!result.isSuccess()) {
            return result;
        }
        //校验是否注册
        String emailAddress = findBackPasswordReqDTO.getEmailAddress();
        DoctorAuthInfo doctorAuthInfo = doctorInfoService.getDoctorInfoByLoginName(emailAddress);
        if (doctorAuthInfo == null) {
            return Result.error(ResultEnum.EMAIL_NO_REGISER);
        }
        try {
            //发送验证码邮件
            String code = sendEmailService.sendVerifyCodeEmail(emailAddress);
            RedisUtil.setKey(emailAddress + "_modifyPassword", code, 600);
        } catch (Exception e) {
            log.error("邮件发送失败：", e);
            throw new LogicException("验证码发送失败，请检查邮件是否填写无误");
        }
        return Result.success(ResultEnum.VERIFY_CODE_SEND_SUCCESS);
    }

    /**
     * 修改密码
     *
     * @param modifyPasswordReqDTO
     * @return
     */
    @RequestMapping(value = "password/modify")
    public Result modifyPassword(@RequestBody ModifyPasswordReqDTO modifyPasswordReqDTO) {
        //校验验证码
        String emailAddress = modifyPasswordReqDTO.getEmailAddress();
        String redisVerifyCode = (String) RedisUtil.getKey(emailAddress + "_modifyPassword");
        String verifyCode = modifyPasswordReqDTO.getVerifyCode();
        if (!StringUtils.equals(verifyCode, redisVerifyCode)) {
            return Result.error(ResultEnum.VERIFY_CODE_NOT_CORRECT);
        }
        //修改密码
        doctorInfoService.modifyPassword(emailAddress, modifyPasswordReqDTO.getNewPassword());
        return Result.success();
    }

}
