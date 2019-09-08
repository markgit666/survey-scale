package com.yinxt.surveyscale.service;

import com.yinxt.surveyscale.mapper.DoctorInfoMapper;
import com.yinxt.surveyscale.pojo.DoctorAuthInfo;
import com.yinxt.surveyscale.util.result.Result;
import com.yinxt.surveyscale.util.result.ResultEnum;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
     * @param doctorAuthInfo
     * @return
     */
    public Result register(DoctorAuthInfo doctorAuthInfo) {
        if (!doctorAuthInfo.getPassword().equals(doctorAuthInfo.getConfirmPassword())) {
            return Result.error(ResultEnum.PASSWORD_NOT_EQUAL);
        }
        DoctorAuthInfo checkDoctorAuthInfo = getDoctorInfoByLoginName(doctorAuthInfo.getLoginName());
        if (checkDoctorAuthInfo != null) {
            return Result.error(ResultEnum.LOGIN_NAME_EXISTS);
        }
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        doctorAuthInfo.setDoctorId(UUID.randomUUID().toString().substring(0, 8));
        doctorAuthInfo.setSalt(salt);
        String password = doctorAuthInfo.getPassword();
        String md5Password = new Md5Hash(password, salt, 3).toString();
        doctorAuthInfo.setPassword(md5Password);
        doctorInfoMapper.insertDoctorInfo(doctorAuthInfo);

        return Result.success(ResultEnum.REGISTER_SUCCESS);
    }
}
