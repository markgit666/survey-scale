package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.common.result.Result;
import com.yinxt.surveyscale.service.EligibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-16 21:33
 */
@RestController
@RequestMapping(value = "eligible")
public class EligibleController {
    @Autowired
    private EligibleService eligibleService;

    @RequestMapping(value = "list")
    public Result getEligibleInfo() {
        return Result.success(eligibleService.getPatientPatientEligible());
    }

}
