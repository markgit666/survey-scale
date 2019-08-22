package com.yinxt.surveyscale.util.exeption;

import com.yinxt.surveyscale.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public Result handlerException(Exception e) {
        log.error("全局异常：", e);
        return Result.error();
    }


}
