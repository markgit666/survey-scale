package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.dto.PatientEligibleDTO;
import com.yinxt.surveyscale.entity.PatientEligible;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-14 23:38
 */
@Component
public interface EligibleMapper {

    /**
     * 新增病人的实验条件信息
     *
     * @param patientEligibleDTO
     * @return
     */
    int insertPatientEligibleInfo(PatientEligibleDTO patientEligibleDTO);

    /**
     * 修改病人的实验条件信息
     */
    int updatePatientEligibleInfo(PatientEligibleDTO patientEligibleDTO);

    List<PatientEligible> selectPatientEligibleInfo();

}
