package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.dto.ListDataReqDTO;
import com.yinxt.surveyscale.dto.PatientInfoCommitReqDTO;
import com.yinxt.surveyscale.dto.PatientRelationInfoDTO;
import com.yinxt.surveyscale.entity.PatientInfo;
import com.yinxt.surveyscale.service.PatientInfoService;
import com.yinxt.surveyscale.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "patient")
@Slf4j
public class PatientInfoController {

    @Autowired
    PatientInfoService patientInfoService;

    @RequestMapping(value = "relation/info/save")
    public Result savePatientRelationInfo(@RequestBody @Validated PatientRelationInfoDTO patientRelationInfoDTO) {
        return Result.success(patientInfoService.savePatientRelationInfo(patientRelationInfoDTO));
    }

    @RequestMapping(value = "info/get")
    public Result getPatientInfo(@RequestBody PatientInfo patientInfo) {
        return Result.success(patientInfoService.getPatientInfo(patientInfo));
    }

    @RequestMapping(value = "info/getList")
    public Result getPatientInfoList(@RequestBody ListDataReqDTO<PatientInfo> listDataReqDTO) {
        return patientInfoService.getPatientInfoList(listDataReqDTO);
    }

    @RequestMapping(value = "info/save")
    public Result savePatientInfo(@RequestBody @Valid PatientInfoCommitReqDTO patientInfoCommitReqDTO) {
        patientInfoService.savePatientInfo(patientInfoCommitReqDTO);
        return Result.success();
    }

    @RequestMapping(value = "info/remove")
    public Result removePatientInfo(@RequestBody @Valid PatientInfo patientInfo) {
        return patientInfoService.removePatientInfo(patientInfo);
    }

}

