package com.yinxt.surveyscale.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private MultipartConfigElement multipartConfigElement;

    @Autowired
    private DispatcherServlet dispatcherServlet;

    @Bean
    public ServletRegistrationBean apiServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(dispatcherServlet);
        //注入上传配置到自己注册的ServletRegistrationBean
        bean.addUrlMappings("/http/*");
        bean.setMultipartConfig(multipartConfigElement);
        bean.setName("httpServlet");
        return bean;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
