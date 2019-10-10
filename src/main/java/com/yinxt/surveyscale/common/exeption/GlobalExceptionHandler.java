package com.yinxt.surveyscale.common.exeption;

import com.yinxt.surveyscale.common.result.Result;
import com.yinxt.surveyscale.common.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.LogException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) {
        logError(request, e);
        return Result.error();
    }

    @ResponseBody
    @ExceptionHandler(value = LogException.class)
    public Result logicExceptionHandler(HttpServletRequest request, LogicException e) {
        logError(request, e);
        return Result.error(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Result httpMessageNotReadableExceptionHandler(HttpServletRequest request, HttpMessageNotReadableException e) {
        logError(request, e);
        return Result.error(ResultEnum.PARAM_NOT_READ_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        logError(request, e);
        String message = "参数不合法";
        List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
        if (objectErrors.size() > 0) {
            message = objectErrors.get(0).getDefaultMessage();
        }
        return Result.paramError(message);
    }

    private void logError(HttpServletRequest request, Exception e) {
        log.info("url={} | error={}", request.getRequestURI(), e);
    }

}
