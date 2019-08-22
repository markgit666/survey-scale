package com.yinxt.surveyscale.service;

import com.yinxt.surveyscale.dto.GetAnswerReqDTO;
import com.yinxt.surveyscale.pojo.PatientInfo;
import com.yinxt.surveyscale.pojo.ScaleInfo;
import com.yinxt.surveyscale.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    private PatientInfoService patientInfoService;
    @Autowired
    private ScaleInfoService scaleInfoService;


}
