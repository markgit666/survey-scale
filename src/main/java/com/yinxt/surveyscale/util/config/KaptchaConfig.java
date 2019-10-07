package com.yinxt.surveyscale.util.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author yinxt
 * @version 1.0
 * @date 2019-10-07 15:13
 */
@Component
public class KaptchaConfig {

    @Value("${kaptcha.border}")
    private String border;
    @Value("${kaptcha.border.color}")
    private String borderColor;
    @Value("${kaptcha.textproducer.font.color}")
    private String textProducerFontColor;
    @Value("${kaptcha.textproducer.font.size}")
    private String textProducerFontSize;
    @Value("${kaptcha.textproducer.font.names}")
    private String textProducerFontNames;
    @Value("${kaptcha.textproducer.char.length}")
    private String textProducerCharLength;
    @Value("${kaptcha.image.width}")
    private String imageWidth;
    @Value("${kaptcha.image.height}")
    private String imageHeight;

    @Bean
    public DefaultKaptcha defaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", border);
        properties.setProperty("kaptcha.border.color", borderColor);
        properties.setProperty("kaptcha.textproducer.font.color", textProducerFontColor);
        properties.setProperty("kaptcha.textproducer.font.size", textProducerFontSize);
        properties.setProperty("kaptcha.textproducer.font.names", textProducerFontNames);
        properties.setProperty("kaptcha.textproducer.char.length", textProducerCharLength);
        properties.setProperty("kaptcha.image.width", imageWidth);
        properties.setProperty("kaptcha.image.height", imageHeight);
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
