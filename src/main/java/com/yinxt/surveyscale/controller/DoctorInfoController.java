package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.pojo.DoctorAuthInfo;
import com.yinxt.surveyscale.service.DoctorInfoService;
import com.yinxt.surveyscale.util.result.Result;
import com.yinxt.surveyscale.util.result.ResultEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin()
@RestController
@RequestMapping(value = "authc")
public class DoctorInfoController {

    @Autowired
    private DoctorInfoService doctorInfoService;

    /**
     * 未认证处理
     *
     * @return
     */
    @RequestMapping(value = "unauthc")
    public Result unauthc() {
        return Result.error(ResultEnum.UNAUTHC);
    }

    @RequestMapping(value = "login")
    public Result login(@RequestBody DoctorAuthInfo doctorAuthInfo) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(doctorAuthInfo.getLoginName(), doctorAuthInfo.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            return Result.error(ResultEnum.AUTHC_ERROR);
        }
        if (subject.isAuthenticated()) {
            return Result.success(ResultEnum.AUTHC, subject.getSession().getId());
        } else {
            return Result.error(ResultEnum.AUTHC_ERROR);
        }
    }

    @RequestMapping(value = "register")
    public Result register(@RequestBody DoctorAuthInfo doctorAuthInfo) {
        return doctorInfoService.register(doctorAuthInfo);
    }

    @RequestMapping(value = "logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success();
    }
}
