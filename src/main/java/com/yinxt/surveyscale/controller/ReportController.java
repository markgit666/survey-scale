package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.common.result.Result;
import com.yinxt.surveyscale.dto.ReportDetailReqDTO;
import com.yinxt.surveyscale.dto.ReportListReqDTO;
import com.yinxt.surveyscale.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-12 17:06
 */
@Api(tags = {"report:"})
@RestController
@RequestMapping(value = "report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 获取医生个人报告表
     *
     * @param reportListReqDTO
     * @return
     */
    @ApiOperation(value = "获取医生名下报告表列表", notes = "获取医生名下报告表列表", httpMethod = "POST")
    @RequestMapping(value = "doctor/person/list")
    public Result getDoctorPersonReportList(@Valid @RequestBody ReportListReqDTO reportListReqDTO) {
        return Result.success(reportService.getDoctorPersonReportList(reportListReqDTO));
    }

    @ApiOperation(value = "删除报告表", notes = "删除报告表", httpMethod = "POST")
    @RequestMapping(value = "detail/info")
    public Result getReportDetailInfo(@Valid @RequestBody ReportDetailReqDTO reportDetailReqDTO) {
        return Result.success(reportService.getReportDetailInfo(reportDetailReqDTO.getReportId(), true));
    }

}
