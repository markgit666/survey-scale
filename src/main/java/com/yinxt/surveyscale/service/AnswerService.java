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

    /**
     * 答题-获取量表信息
     *
     * @param answerReqDTO
     * @return
     */
    public Result getScaleInfo(GetAnswerReqDTO answerReqDTO) {
        PatientInfo patientInfo = new PatientInfo();
        patientInfo.setPatientId(answerReqDTO.getPatientId());
        Result result = patientInfoService.getPatientInfo(patientInfo);
        //判断当前病人ID是否存在
        if (result.getData() == null) {
            return Result.error();
        } else {
            ScaleInfo scaleInfo = new ScaleInfo();
            scaleInfo.setScaleId(answerReqDTO.getScaleId());
            return scaleInfoService.getScaleInfo(scaleInfo);
        }
    }
}
