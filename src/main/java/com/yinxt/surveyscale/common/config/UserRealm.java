package com.yinxt.surveyscale.common.config;

import com.yinxt.surveyscale.entity.DoctorAuthInfo;
import com.yinxt.surveyscale.service.DoctorInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义 shiro realm
 */
public class UserRealm extends AuthenticatingRealm {

    @Autowired
    private DoctorInfoService doctorInfoService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String loginName = usernamePasswordToken.getUsername();
        DoctorAuthInfo doctorAuthInfo = doctorInfoService.getDoctorInfoByLoginName(loginName);
        if (doctorAuthInfo != null) {
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(loginName, doctorAuthInfo.getPassword(), this.getName());
            return simpleAuthenticationInfo;
        } else {
            return null;
        }
    }
}
