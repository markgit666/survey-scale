package com.yinxt.surveyscale.service;

import com.yinxt.surveyscale.common.constant.Constant;
import com.yinxt.surveyscale.common.enums.UserTypeEnum;
import com.yinxt.surveyscale.common.threadpool.ThreadPoolUtil;
import com.yinxt.surveyscale.common.util.RSAUtil;
import com.yinxt.surveyscale.dto.FindBackPasswordReqDTO;
import com.yinxt.surveyscale.dto.LoginReqDTO;
import com.yinxt.surveyscale.dto.RegisterReqDTO;
import com.yinxt.surveyscale.dto.VerifyCodeReqDTO;
import com.yinxt.surveyscale.mapper.DoctorInfoMapper;
import com.yinxt.surveyscale.entity.DoctorAuthInfo;
import com.yinxt.surveyscale.dto.ModifyPasswordReqDTO;
import com.yinxt.surveyscale.common.util.UserHolder;
import com.yinxt.surveyscale.common.email.service.SendEmailService;
import com.yinxt.surveyscale.common.exeption.LogicException;
import com.yinxt.surveyscale.common.redis.RedisUtil;
import com.yinxt.surveyscale.common.result.Result;
import com.yinxt.surveyscale.common.result.ResultEnum;
import com.yinxt.surveyscale.vo.DoctorIdentityVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 医生service
 */
@Slf4j
@Service
public class DoctorInfoService {
    @Autowired
    private DoctorInfoMapper doctorInfoMapper;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private SendEmailService sendEmailService;
    /**
     * 私钥
     */
    @Value("${rsa.key.private}")
    private String privateKey;

    private static final long TIME = 600;

    /**
     * 通过登录名和密码查询医生信息
     *
     * @param loginName
     * @param password
     * @return
     */
    public DoctorAuthInfo getDoctorInfoByLoginNameAndPassword(String loginName, String password) {
        return doctorInfoMapper.getDoctorInfoByLoginNameAndPassword(loginName, password);
    }

    /**
     * 通过登录名查询医生信息
     *
     * @param loginName
     * @return
     */
    public DoctorAuthInfo getDoctorInfoByLoginName(String loginName) {
        return doctorInfoMapper.getDoctorInfoByLoginName(loginName);
    }

    /**
     * 登录
     *
     * @param loginReqDTO
     * @return
     */
    public Result login(LoginReqDTO loginReqDTO) {
        //校验验证码
        Result result = captchaService.verifyCaptcha(loginReqDTO.getCaptchaToken(), loginReqDTO.getCaptcha());
        if (!result.isSuccess()) {
            return result;
        }
        //验证账号和密码
        try {
            loginReqDTO.setPassword(RSAUtil.decrypt(loginReqDTO.getPassword(), privateKey));
        } catch (Exception e) {
            log.error("解密失败：", e);
        }
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginReqDTO.getLoginName(), loginReqDTO.getPassword(), loginReqDTO.isRememberMe());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            return Result.error(ResultEnum.AUTHC_ERROR);
        }
        if (subject.isAuthenticated()) {
            String sessionId = subject.getSession().getId().toString();
            DoctorAuthInfo doctorAuthInfo = doctorInfoMapper.getDoctorInfoByLoginName(loginReqDTO.getLoginName());
            DoctorIdentityVO doctorIdentityVO = new DoctorIdentityVO();
            doctorIdentityVO.setToken(sessionId);
            doctorIdentityVO.setIdentity(doctorAuthInfo.getIdentity());
            return Result.success(ResultEnum.AUTHC, doctorIdentityVO);
        } else {
            return Result.error(ResultEnum.AUTHC_ERROR);
        }
    }

    /**
     * 登出
     *
     * @return
     */
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
    public Result getRegisterVerifyCode(VerifyCodeReqDTO verifyCodeReqDTO) {
        //校验是否注册
        String emailAddress = verifyCodeReqDTO.getEmailAddress();
        DoctorAuthInfo doctorAuthInfo = getDoctorInfoByLoginName(emailAddress);
        if (doctorAuthInfo != null) {
            Result.error(ResultEnum.LOGIN_NAME_EXISTS);
        }
        //异步发送邮件
        ThreadPoolUtil.getInstance().executeTask(() -> {
            //发送验证码邮件
            try {
                String code = sendEmailService.sendVerifyCodeEmail(emailAddress);
                //将验证码缓存入redis
                RedisUtil.setKey(Constant.REDIS_REGISTER_PREFIX + emailAddress, code, TIME);
            } catch (Exception e) {
                log.error("邮件发送失败：", e);
                throw new LogicException(e.getMessage());
            }
        });
        return Result.success(ResultEnum.VERIFY_CODE_SEND_SUCCESS);
    }

    /**
     * 注册
     *
     * @param registerReqDTO
     * @return
     */
    public Result register(RegisterReqDTO registerReqDTO) {
        //校验验证码
        String cacheKey = Constant.REDIS_REGISTER_PREFIX + registerReqDTO.getLoginName();
        String cacheValue = (String) RedisUtil.getKey(cacheKey);
        if (StringUtils.isBlank(cacheValue) || !StringUtils.equals(cacheValue, registerReqDTO.getVerifyCode())) {
            return Result.error(ResultEnum.VERIFY_CODE_NOT_CORRECT);
        }
        try {
            registerReqDTO.setPassword(RSAUtil.decrypt(registerReqDTO.getPassword(), privateKey));
            registerReqDTO.setConfirmPassword(RSAUtil.decrypt(registerReqDTO.getConfirmPassword(), privateKey));
        } catch (Exception e) {
            log.error("解密失败：", e);
        }
        //校验密码是否相等
        if (!registerReqDTO.getPassword().equals(registerReqDTO.getConfirmPassword())) {
            return Result.error(ResultEnum.PASSWORD_NOT_EQUAL);
        }
        //校验登录名是否已存在
        DoctorAuthInfo checkDoctorAuthInfo = getDoctorInfoByLoginName(registerReqDTO.getLoginName());
        if (checkDoctorAuthInfo != null) {
            return Result.error(ResultEnum.LOGIN_NAME_EXISTS);
        }
        DoctorAuthInfo doctorAuthInfo = new DoctorAuthInfo();
        BeanUtils.copyProperties(registerReqDTO, doctorAuthInfo);
        doctorAuthInfo.setDoctorId(RedisUtil.getSequenceId(Constant.DOCTOR_PREFIX));
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        doctorAuthInfo.setSalt(salt);
        String md5Password = new Md5Hash(doctorAuthInfo.getPassword(), salt, Constant.salt).toString();
        doctorAuthInfo.setPassword(md5Password);
        doctorAuthInfo.setIdentity(UserTypeEnum.ADMIN.type);
        doctorInfoMapper.insertDoctorInfo(doctorAuthInfo);
        //删除缓存
        RedisUtil.deleteKey(cacheKey);

        return Result.success(ResultEnum.REGISTER_SUCCESS);
    }

    /**
     * 修改密码
     *
     * @param modifyPasswordReqDTO
     */
    public Result modifyPassword(ModifyPasswordReqDTO modifyPasswordReqDTO) {
        //校验验证码
        String emailAddress = modifyPasswordReqDTO.getEmailAddress();
        String redisVerifyCode = (String) RedisUtil.getKey(Constant.REDIS_MODIFY_PASSWORD_PREFIX + emailAddress);
        String verifyCode = modifyPasswordReqDTO.getVerifyCode();
        if (!StringUtils.equals(verifyCode, redisVerifyCode)) {
            return Result.error(ResultEnum.VERIFY_CODE_NOT_CORRECT);
        }
        //修改密码
        try {
            modifyPasswordReqDTO.setNewPassword(RSAUtil.decrypt(modifyPasswordReqDTO.getNewPassword(), privateKey));
        } catch (Exception e) {
            log.error("解密失败", e);
        }
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        String md5Password = new Md5Hash(modifyPasswordReqDTO.getNewPassword(), salt, Constant.salt).toString();
        doctorInfoMapper.updatePassword(modifyPasswordReqDTO.getEmailAddress(), md5Password, salt);
        return Result.success();
    }

    /**
     * 找回密码
     * 校验图片验证码，校验是否注册，并且发送验证码邮件
     *
     * @param findBackPasswordReqDTO
     * @return
     */
    public Result findBackPassword(FindBackPasswordReqDTO findBackPasswordReqDTO) {
        //校验验证码
        Result result = captchaService.verifyCaptcha(findBackPasswordReqDTO.getCaptchaToken(), findBackPasswordReqDTO.getCaptcha());
        if (!result.isSuccess()) {
            return result;
        }
        //校验是否注册
        String emailAddress = findBackPasswordReqDTO.getEmailAddress();
        DoctorAuthInfo doctorAuthInfo = getDoctorInfoByLoginName(emailAddress);
        if (doctorAuthInfo == null) {
            return Result.error(ResultEnum.EMAIL_NO_REGISTER);
        }
        ThreadPoolUtil.getInstance().executeTask(() -> {
            try {
                //发送验证码邮件
                String code = sendEmailService.sendVerifyCodeEmail(emailAddress);
                RedisUtil.setKey(Constant.REDIS_MODIFY_PASSWORD_PREFIX + emailAddress, code, TIME);
            } catch (Exception e) {
                log.error("邮件发送失败：", e);
                throw new LogicException(e.getMessage());
            }
        });
        return Result.success(ResultEnum.VERIFY_CODE_SEND_SUCCESS);
    }

    /**
     * 获取医生ID
     *
     * @return
     */
    public String getLoginDoctorId() {
        //获取登录名
        String loginName = UserHolder.getLoginName();
        //通过登录名获取账户信息
        DoctorAuthInfo doctorAuthInfo = getDoctorInfoByLoginName(loginName);
        //返回医生ID
        if (doctorAuthInfo != null) {
            return doctorAuthInfo.getDoctorId();
        }
        return null;
    }

}
