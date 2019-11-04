package com.yinxt.surveyscale.common.email.service;

import com.yinxt.surveyscale.common.email.pojo.EmailInfo;
import com.yinxt.surveyscale.common.email.util.EmailUtil;
import com.yinxt.surveyscale.common.exeption.LogicException;
import com.yinxt.surveyscale.common.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 邮件发送service
 */
@Slf4j
@Service
public class SendEmailService {

    @Autowired
    private EmailUtil emailUtil;
    @Value("${mail.content}")
    private String content;

    /**
     * 发送验证码邮件
     *
     * @param sendTo
     */
    public String sendVerifyCodeEmail(String sendTo) {
        String code = String.valueOf(new Random().nextInt(899999) + 100000);
        EmailInfo emailInfo = new EmailInfo();
        emailInfo.setSendTo(sendTo);
        emailInfo.setTitle("验证码");
        String emailContent = content.replace("code", code);
        log.info("邮件内容：{}", emailContent);
        emailInfo.setContent(emailContent);
        try {
            emailUtil.send(emailInfo);//发送邮件
        } catch (Exception e) {
            log.error("验证码发送异常：", e);
            throw new LogicException(ResultEnum.VERIFY_CODE_SEND_ERROR);
        }
        return code;
    }

}
