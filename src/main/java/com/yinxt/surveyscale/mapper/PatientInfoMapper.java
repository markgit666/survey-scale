package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.entity.PatientInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PatientInfoMapper {

    int insertPatientInfo(PatientInfo patientInfo);

    PatientInfo selectPatientInfo(PatientInfo patientInfo);

    PatientInfo selectPatientInfoByPatientId(@Param("patientId") String patientId);

    List<PatientInfo> selectPatientInfoList(PatientInfo patientInfo);

    List<PatientInfo> selectPatientListByIdArray(@Param("array") String[] patientIdArray, @Param("doctorId") String doctorId);

    PatientInfo selectPatientByScaleIdAndPatientId(@Param("patientId") String patientId, @Param("scaleId") String scaleId);

    int updatePatientInfo(PatientInfo patientInfo);

    List<PatientInfo> selectAllPatientInfo(String doctorId);

}
