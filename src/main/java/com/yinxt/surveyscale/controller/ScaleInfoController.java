package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.dto.ListDataReqDTO;
import com.yinxt.surveyscale.dto.ScaleListReqDTO;
import com.yinxt.surveyscale.entity.ScaleInfo;
import com.yinxt.surveyscale.service.ScaleInfoService;
import com.yinxt.surveyscale.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 量表controller
 */
@Api(tags = {"scale:"})
@RestController
@RequestMapping(value = "scale")
public class ScaleInfoController {

    @Autowired
    private ScaleInfoService scaleInfoService;

    @ApiOperation(value = "新增量表", notes = "新增量表", httpMethod = "POST")
    @RequestMapping(value = "info/add")
    public Result saveScaleInfo(@RequestBody @Valid ScaleInfo scaleInfo) {
        return scaleInfoService.saveScaleInfo(scaleInfo);
    }

    @ApiOperation(value = "获取量表列表", notes = "获取量表列表", httpMethod = "POST")
    @RequestMapping(value = "info/getList")
    public Result getScaleInfoList(@RequestBody @Valid ListDataReqDTO<ScaleListReqDTO> dataReqDTO) {
        return scaleInfoService.getScaleInfoList(dataReqDTO);
    }

    @ApiOperation(value = "获取量表详情", notes = "获取量表详情", httpMethod = "POST")
    @RequestMapping(value = "info/get")
    public Result getScaleInfo(@RequestBody @Valid ScaleInfo scaleInfo) {
        return scaleInfoService.getScaleDetailInfo(scaleInfo);
    }

    @ApiOperation(value = "删除量表", notes = "删除量表", httpMethod = "POST")
    @RequestMapping(value = "info/remove")
    public Result remove(@RequestBody @Valid ScaleInfo scaleInfo) {
        return scaleInfoService.removeScaleInfo(scaleInfo);
    }

    @ApiOperation(value = "修改列表", notes = "修改列表", httpMethod = "POST")
    @RequestMapping(value = "info/modify")
    public Result modify(@RequestBody ScaleInfo scaleInfo) {
        return scaleInfoService.saveScaleInfo(scaleInfo);
    }

}
