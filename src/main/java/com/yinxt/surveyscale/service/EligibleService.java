package com.yinxt.surveyscale.service;

import com.yinxt.surveyscale.dto.PatientEligibleDTO;
import com.yinxt.surveyscale.entity.PatientEligible;
import com.yinxt.surveyscale.mapper.EligibleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-15 00:16
 */
@Service
public class EligibleService {
    @Autowired
    private EligibleMapper eligibleMapper;

    /**
     * 保存病人条件答案信息
     *
     * @param patientEligibleDTO
     */
    public void savePatientEligibleInfo(PatientEligibleDTO patientEligibleDTO) {
        eligibleMapper.insertPatientEligibleInfo(patientEligibleDTO);
    }

    /**
     * 修改病人条件答案信息
     *
     * @param patientEligibleDTO
     */
    public void modifyPatientEligibleInfo(PatientEligibleDTO patientEligibleDTO) {
        eligibleMapper.updatePatientEligibleInfo(patientEligibleDTO);
    }

    /**
     * 获取实验条件列表
     *
     * @return
     */
    public List<PatientEligible> getPatientPatientEligible() {
        List<PatientEligible> patientEligibleList = eligibleMapper.selectPatientEligibleInfo();
        return patientEligibleList;
    }

}
