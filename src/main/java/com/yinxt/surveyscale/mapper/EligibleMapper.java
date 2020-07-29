package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.dto.PatientEligibleDTO;
import com.yinxt.surveyscale.entity.PatientEligible;
import org.apache.ibatis.annotations.Param;
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
     * 新增被试者的实验条件信息
     *
     * @param patientEligibleDTO
     * @return
     */
    int insertPatientEligibleInfo(PatientEligibleDTO patientEligibleDTO);

    /**
     * 批量新增被试者的实验条件信息
     *
     * @param patientEligibleDTOList
     * @return
     */
    int insertPatientEligibleList(@Param("patientEligibleDTOList") List<PatientEligibleDTO> patientEligibleDTOList);

    /**
     * 修改被试者的实验条件信息
     *
     * @param patientEligibleDTO
     * @return
     */
    int updatePatientEligibleInfo(PatientEligibleDTO patientEligibleDTO);

    /**
     * 查询被试者的实验条件信息列表
     *
     * @return
     */
    List<PatientEligible> selectPatientEligibleList();

}
