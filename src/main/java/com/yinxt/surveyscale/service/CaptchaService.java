package com.yinxt.surveyscale.service;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.yinxt.surveyscale.util.redis.RedisUtil;
import com.yinxt.surveyscale.util.result.Result;
import com.yinxt.surveyscale.util.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author yinxt
 * @version 1.0
 * @date 2019-10-07 16:08
 */
@Service
@Slf4j
public class CaptchaService {

    @Autowired
    private DefaultKaptcha defaultKaptcha;
    //缓存时间
    private static final long time = 600;

    /**
     * 获取验证码
     *
     * @return
     */
    public Map<String, Object> getCaptcha() {
        //生成验证码文字
        String kaptchaText = defaultKaptcha.createText();

        //生成token，并缓存验证码到redis
        String token = UUID.randomUUID().toString();
        RedisUtil.setKey("captcha_" + token, kaptchaText, time);

        Map<String, Object> captchaMap = new HashMap<>();
        captchaMap.put("captchaToken", token);

        //生成验证码图片
        BufferedImage bufferedImage = defaultKaptcha.createImage(kaptchaText);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpeg", byteArrayOutputStream);

            Base64.Encoder encoder = Base64.getEncoder();
            captchaMap.put("captchaImg", encoder.encodeToString(byteArrayOutputStream.toByteArray()));

        } catch (IOException e) {
            log.error("验证码图片写入字节流失败");
        }
        return captchaMap;
    }

    /**
     * 校验验证码
     *
     * @param captchaToken
     * @param captcha
     * @return
     */
    public Result verifyCaptcha(String captchaToken, String captcha) {
        //校验验证码
        if (StringUtils.isBlank(captchaToken)) {
            return Result.error(ResultEnum.VERIFY_CODE_NOT_CORRECT);
        }
        String redisCaptcha = (String) RedisUtil.getKey("captcha_" + captchaToken);
        if (!StringUtils.equals(captcha, redisCaptcha)) {
            return Result.error(ResultEnum.VERIFY_CODE_NOT_CORRECT);
        }
        return Result.success();
    }

}
