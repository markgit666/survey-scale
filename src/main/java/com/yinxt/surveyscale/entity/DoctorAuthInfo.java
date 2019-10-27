package com.yinxt.surveyscale.entity;

import lombok.Data;

/**
 * 医生pojo
 */
@Data
public class DoctorAuthInfo {

    /**
     * 医生ID
     */
    private String doctorId;
    /**
     * 医生姓名
     */
    private String doctorName;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐
     */
    private String salt;

}
