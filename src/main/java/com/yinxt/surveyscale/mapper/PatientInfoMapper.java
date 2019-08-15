package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.pojo.PatientInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PatientInfoMapper {

    int insertPatientInfo(PatientInfo patientInfo);

    PatientInfo selectPatientInfo(PatientInfo patientInfo);

    List<PatientInfo> selectPatientInfoList(@Param("dto") PatientInfo patientInfo);

    int updatePatientInfo(PatientInfo patientInfo);

}
