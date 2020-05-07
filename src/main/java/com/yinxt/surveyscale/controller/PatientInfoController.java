package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.dto.ListDataReqDTO;
import com.yinxt.surveyscale.dto.PatientInfoCommitReqDTO;
import com.yinxt.surveyscale.dto.PatientRelationInfoDTO;
import com.yinxt.surveyscale.entity.PatientInfo;
import com.yinxt.surveyscale.service.PatientInfoService;
import com.yinxt.surveyscale.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = {"patient:"})
@RestController
@RequestMapping(value = "patient")
@Slf4j
public class PatientInfoController {

    @Autowired
    PatientInfoService patientInfoService;

    @ApiOperation(value = "保存用户信息", notes = "保存用户信息，包括实验条件等", httpMethod = "POST")
    @RequestMapping(value = "relation/info/save")
    public Result savePatientRelationInfo(@RequestBody @Validated PatientRelationInfoDTO patientRelationInfoDTO) {
        return Result.success(patientInfoService.savePatientRelationInfo(patientRelationInfoDTO));
    }

    @ApiOperation(value = "获取被试者信息", notes = "获取被试者信息", httpMethod = "POST")
    @RequestMapping(value = "info/get")
    public Result getPatientInfo(@RequestBody PatientInfo patientInfo) {
        return Result.success(patientInfoService.getPatientInfo(patientInfo));
    }

    @ApiOperation(value = "获取被试者列表", notes = "获取被试者列表", httpMethod = "POST")
    @RequestMapping(value = "info/getList")
    public Result getPatientInfoList(@RequestBody ListDataReqDTO<PatientInfo> listDataReqDTO) {
        return patientInfoService.getPatientInfoList(listDataReqDTO);
    }

    @ApiOperation(value = "被试者基本信息保存", notes = "被试者基本信息保存", httpMethod = "POST")
    @RequestMapping(value = "info/save")
    public Result savePatientInfo(@RequestBody @Valid PatientInfoCommitReqDTO patientInfoCommitReqDTO) {
        patientInfoService.savePatientInfo(patientInfoCommitReqDTO);
        return Result.success();
    }

    @ApiOperation(value = "删除被试者信息", notes = "删除被试者信息", httpMethod = "POST")
    @RequestMapping(value = "info/remove")
    public Result removePatientInfo(@RequestBody @Valid PatientInfo patientInfo) {
        return patientInfoService.removePatientInfo(patientInfo);
    }

}

