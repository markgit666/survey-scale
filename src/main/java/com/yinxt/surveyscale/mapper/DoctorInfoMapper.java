package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.entity.DoctorAuthInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface DoctorInfoMapper {

    DoctorAuthInfo getDoctorInfoByLoginNameAndPassword(@Param("loginName") String loginName, @Param("password") String password);

    DoctorAuthInfo getDoctorInfoByLoginName(@Param("loginName") String loginName);

    int insertDoctorInfo(DoctorAuthInfo doctorAuthInfo);

    int updatePassword(@Param("loginName") String loginName, @Param("newPassword") String newPassword, @Param("salt") String salt);
}
