package com.yinxt.surveyscale.service;

import com.yinxt.surveyscale.dto.RegisterReqDTO;
import com.yinxt.surveyscale.mapper.DoctorInfoMapper;
import com.yinxt.surveyscale.pojo.DoctorAuthInfo;
import com.yinxt.surveyscale.util.config.UserHolder;
import com.yinxt.surveyscale.util.redis.RedisUtil;
import com.yinxt.surveyscale.util.result.Result;
import com.yinxt.surveyscale.util.result.ResultEnum;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 医生service
 */
@Service
public class DoctorInfoService {
    @Autowired
    private DoctorInfoMapper doctorInfoMapper;

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
     * 注册
     *
     * @param registerReqDTO
     * @return
     */
    public Result register(RegisterReqDTO registerReqDTO) {
        if (!registerReqDTO.getPassword().equals(registerReqDTO.getConfirmPassword())) {
            return Result.error(ResultEnum.PASSWORD_NOT_EQUAL);
        }
        DoctorAuthInfo checkDoctorAuthInfo = getDoctorInfoByLoginName(registerReqDTO.getLoginName());
        if (checkDoctorAuthInfo != null) {
            return Result.error(ResultEnum.LOGIN_NAME_EXISTS);
        }
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        DoctorAuthInfo doctorAuthInfo = new DoctorAuthInfo();
        BeanUtils.copyProperties(registerReqDTO, doctorAuthInfo);
        doctorAuthInfo.setDoctorId(RedisUtil.getSequenceId("DR"));
        doctorAuthInfo.setSalt(salt);
        String password = doctorAuthInfo.getPassword();
        String md5Password = new Md5Hash(password, salt, 3).toString();
        doctorAuthInfo.setPassword(md5Password);
        doctorInfoMapper.insertDoctorInfo(doctorAuthInfo);

        return Result.success(ResultEnum.REGISTER_SUCCESS);
    }

    /**
     * 修改密码
     *
     * @param loginName
     * @param newPassword
     */
    public void modifyPassword(String loginName, String newPassword) {
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        String md5Password = new Md5Hash(newPassword, salt, 3).toString();
        doctorInfoMapper.updatePassword(loginName, md5Password, salt);
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
        return doctorAuthInfo.getDoctorId();
    }

}
