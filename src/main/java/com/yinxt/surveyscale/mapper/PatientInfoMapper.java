package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.entity.PatientInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PatientInfoMapper {

    int insertPatientInfo(PatientInfo patientInfo);

    PatientInfo selectPatientInfo(PatientInfo patientInfo);

    int selectCountPatientByIdCard(@Param("idCard") String idCard, @Param("patientId") String patientId);

    int selectCountPatientByDocotorIdAndIdCard(@Param("doctorId") String doctorId, @Param("idCard") String idCard);

    int selectCountPatientByMedicalNum(@Param("medicalRecordNum") String medicalRecordNum, @Param("patientId") String patientId);

    PatientInfo selectPatientInfoByPatientId(@Param("patientId") String patientId);

    List<PatientInfo> selectPatientInfoList(PatientInfo patientInfo);

    List<PatientInfo> selectPatientListByIdArray(@Param("array") List<String> patientIdArray, @Param("doctorId") String doctorId);

    PatientInfo selectPatientByScaleIdAndPatientId(@Param("scaleId") String scaleId, @Param("patientId") String patientId);

    PatientInfo selectPatientByReportIdAndIdCardAndDoctorId(@Param("reportId") String reportId, @Param("idCard") String idCard, @Param("doctorId") String doctorId);

    PatientInfo selectPatientByReportIdAndPatientId(@Param("reportId") String reportId, @Param("patientId") String patientId);

    int updatePatientInfo(PatientInfo patientInfo);

    List<String> selectAllPatientIdList(String doctorId);

}
