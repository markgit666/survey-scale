package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.pojo.DoctorAuthInfo;
import org.apache.ibatis.annotations.Param;

public interface DoctorInfoMapper {

    DoctorAuthInfo getDoctorInfoByLoginNameAndPassword(@Param("loginName") String loginName, @Param("password") String password);

    DoctorAuthInfo getDoctorInfoByLoginName(@Param("loginName") String loginName);

    int insertDoctorInfo(DoctorAuthInfo doctorAuthInfo);
}
