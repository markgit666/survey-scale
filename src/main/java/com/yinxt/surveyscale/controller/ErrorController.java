package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.common.result.Result;
import com.yinxt.surveyscale.common.result.ResultEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020/5/7 14:07
 */
@Slf4j
@Api(value = "", description = "")
@RestController
public class ErrorController extends AbstractErrorController {

    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @ApiOperation(value = "error异常跳转", notes = "error异常跳转", httpMethod = "GET")
    @RequestMapping(value = "error")
    public Result error(HttpServletRequest request) {
        Map<String, Object> map = getErrorAttributes(request, true);
        HttpStatus httpStatus = getStatus(request);
        log.error("error异常跳转至ErrorControlle: error={} | path={}", map.get("error"), map.get("path"));
        if (httpStatus.is4xxClientError()) {
            return new Result(ResultEnum.URL_ERROR.getCode(), ResultEnum.URL_ERROR.getName() + map.get("path"));
        }
        return new Result(ResultEnum.SERVCIE_ERROR.getCode(), (String) map.get("error"));
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
