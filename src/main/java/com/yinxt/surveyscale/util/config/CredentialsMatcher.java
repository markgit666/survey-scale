package com.yinxt.surveyscale.util.config;

import com.yinxt.surveyscale.mapper.DoctorInfoMapper;
import com.yinxt.surveyscale.pojo.DoctorAuthInfo;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;

public class CredentialsMatcher extends SimpleCredentialsMatcher {

    @Autowired
    private DoctorInfoMapper doctorInfoMapper;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String userName = usernamePasswordToken.getUsername();
        String userPassword = new String(usernamePasswordToken.getPassword());
        String password = (String) info.getCredentials();

        DoctorAuthInfo doctorAuthInfo = doctorInfoMapper.getDoctorInfoByLoginName(userName);
        if (doctorAuthInfo == null) {
            return false;
        }
        String md5Password = new Md5Hash(userPassword, doctorAuthInfo.getSalt(), 3).toString();
        return this.equals(md5Password, password);
    }
}
