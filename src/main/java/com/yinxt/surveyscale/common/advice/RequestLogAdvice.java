package com.yinxt.surveyscale.common.advice;

import com.alibaba.fastjson.JSON;
import com.yinxt.surveyscale.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@Slf4j
@ControllerAdvice
public class RequestLogAdvice implements RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        return httpInputMessage;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        Method method = methodParameter.getMethod();
        log.info("{}.{}", method.getDeclaringClass().getSimpleName(), method.getName());
        return body;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        Method method = methodParameter.getMethod();
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpHeaders httpHeaders = httpInputMessage.getHeaders();
        String token = httpHeaders.getFirst(Constant.TOKEN);
        log.info("url={} | token={} | method={}.{} | requestBody={}", httpServletRequest.getRequestURI(), token, method.getDeclaringClass().getSimpleName(), method.getName(), JSON.toJSONString(body));
        return body;
    }

}
