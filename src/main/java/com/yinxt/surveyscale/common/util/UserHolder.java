package com.yinxt.surveyscale.common.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author yinxt
 * @version 1.0
 * @date 2019-10-08 22:30
 */
public class UserHolder {

    /**
     * 获取当前用户的登录名
     *
     * @return
     */
    public static String getLoginName() {
        Subject subject = SecurityUtils.getSubject();
        String loginName = (String) subject.getPrincipal();
        return loginName;
    }
}
