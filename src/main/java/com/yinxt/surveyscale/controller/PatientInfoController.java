package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.dto.ListDataReqDTO;
import com.yinxt.surveyscale.pojo.PatientInfo;
import com.yinxt.surveyscale.service.PatientInfoService;
import com.yinxt.surveyscale.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(value = "patient")
@Slf4j
public class PatientInfoController {

    @Autowired
    PatientInfoService patientInfoService;

    @RequestMapping(value = "info/get")
    public Result getPatientInfo(@RequestBody PatientInfo patientInfo) {
        return patientInfoService.getPatientInfo(patientInfo);
    }

    @RequestMapping(value = "info/getList")
    public Result getPatientInfoList(@RequestBody ListDataReqDTO<PatientInfo> listDataReqDTO) {
        return patientInfoService.getPatientInfoList(listDataReqDTO);
    }

    @RequestMapping(value = "info/save")
    public Result savePatientInfo(@RequestBody @Valid PatientInfo patientInfo) {
        return patientInfoService.addPatientInfo(patientInfo);
    }

    @RequestMapping(value = "info/remove")
    public Result removePatientInfo(@RequestBody @Valid PatientInfo patientInfo) {
        return patientInfoService.removePatientInfo(patientInfo);
    }

}

