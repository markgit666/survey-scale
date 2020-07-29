package com.yinxt.surveyscale.vo;

import lombok.Data;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-05-30 20:40
 */
@Data
public class DoctorIdentityVO {

    /**
     * token
     */
    private String token;
    /**
     * 身份（管理员/普通用户）
     */
    private String identity;
    /**
     * 医生ID
     */
    private String doctorId;

}
