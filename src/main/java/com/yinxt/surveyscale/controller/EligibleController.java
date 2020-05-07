package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.common.result.Result;
import com.yinxt.surveyscale.service.EligibleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-16 21:33
 */
@Api(tags = {"eligible:"})
@RestController
@RequestMapping(value = "eligible")
public class EligibleController {
    @Autowired
    private EligibleService eligibleService;

    @RequestMapping(value = "list")
    @ApiOperation(value = "获取实现条件列表", notes = "获取实现条件列表", httpMethod = "GET")
    public Result getEligibleInfo() {
        return Result.success(eligibleService.getPatientPatientEligible());
    }

}
