package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.dto.ListDataReqDTO;
import com.yinxt.surveyscale.pojo.ScaleInfo;
import com.yinxt.surveyscale.service.ScaleInfoService;
import com.yinxt.surveyscale.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 量表controller
 */
@RestController
@RequestMapping(value = "scale")
public class ScaleInfoController {

    @Autowired
    private ScaleInfoService scaleInfoService;

    @RequestMapping(value = "info/add")
    public Result saveScaleInfo(@RequestBody @Valid ScaleInfo scaleInfo) throws Exception {
        return scaleInfoService.saveScaleInfo(scaleInfo);
    }

    @RequestMapping(value = "info/getList")
    public Result getScaleInfoList(@RequestBody @Valid ListDataReqDTO<String> dataReqDTO) {
        return scaleInfoService.getScaleInfoList(dataReqDTO);
    }

    @RequestMapping(value = "info/get")
    public Result getScaleInfo(@RequestBody @Valid ScaleInfo scaleInfo) {
        return scaleInfoService.getScaleInfo(scaleInfo);
    }

    @RequestMapping(value = "info/remove")
    public Result remove(@RequestBody @Valid ScaleInfo scaleInfo) {
        return scaleInfoService.removeScaleInfo(scaleInfo);
    }

    @RequestMapping(value = "info/modify")
    public Result modify(@RequestBody ScaleInfo scaleInfo) {
        return scaleInfoService.saveScaleInfo(scaleInfo);
    }


}
