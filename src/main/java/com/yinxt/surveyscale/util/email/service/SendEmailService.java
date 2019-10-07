package com.yinxt.surveyscale.util.email.service;

import com.yinxt.surveyscale.util.email.pojo.EmailInfo;
import com.yinxt.surveyscale.util.email.util.EmailUtil;
import com.yinxt.surveyscale.util.exeption.LogicException;
import com.yinxt.surveyscale.util.redis.RedisUtil;
import com.yinxt.surveyscale.util.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        String content = "<p>【医用量表调查网】尊敬的用户您好，您的验证码为" + code + "，如非本人操作，请检查账号安全，" +
                "该验证码将在10分钟后失效，谢谢！</p>";
        emailInfo.setContent(content);
        try {
            emailUtil.send(emailInfo);//发送邮件
        } catch (Exception e) {
            log.error("邮件发送异常：", e);
            throw new LogicException(ResultEnum.VERIFY_CODE_SEND_ERROR);
        }
        return code;
    }

}
