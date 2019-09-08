package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.pojo.PatientInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PatientInfoMapper {

    int insertPatientInfo(PatientInfo patientInfo);

    PatientInfo selectPatientInfo(PatientInfo patientInfo);

    PatientInfo selectPatientInfoByPatientId(@Param("patientId") String patientId);

    List<PatientInfo> selectPatientInfoList(@Param("dto") PatientInfo patientInfo);

    int updatePatientInfo(PatientInfo patientInfo);

}
