package com.yinxt.surveyscale.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private InterceptorHandler interceptorHandler;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(interceptorHandler).addPathPatterns("/**");
    }

    public InterceptorHandler interceptionHandler() {
        return new InterceptorHandler();
    }
}
