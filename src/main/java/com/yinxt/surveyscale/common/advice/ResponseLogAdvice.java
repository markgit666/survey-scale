package com.yinxt.surveyscale.common.advice;

import com.alibaba.fastjson.JSON;
import com.yinxt.surveyscale.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.List;

@Slf4j
@ControllerAdvice
public class ResponseLogAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        Method method = returnType.getMethod();
        HttpHeaders httpHeaders = serverHttpRequest.getHeaders();
        String token = httpHeaders.getFirst(Constant.TOKEN);
        log.info("url={} | token={} | method={}.{} | responseBody={}", serverHttpRequest.getURI(), token, method.getDeclaringClass().getSimpleName(), method.getName(), JSON.toJSONString(body));
        return body;
    }

}
