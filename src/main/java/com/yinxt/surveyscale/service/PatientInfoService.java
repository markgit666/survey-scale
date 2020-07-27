package com.yinxt.surveyscale.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinxt.surveyscale.common.enums.StatusEnum;
import com.yinxt.surveyscale.common.exeption.LogicException;
import com.yinxt.surveyscale.common.util.RSAUtil;
import com.yinxt.surveyscale.dto.ListDataReqDTO;
import com.yinxt.surveyscale.dto.PatientEligibleDTO;
import com.yinxt.surveyscale.dto.PatientInfoCommitReqDTO;
import com.yinxt.surveyscale.dto.PatientRelationInfoDTO;
import com.yinxt.surveyscale.mapper.PatientInfoMapper;
import com.yinxt.surveyscale.entity.PatientInfo;
import com.yinxt.surveyscale.common.page.PageBean;
import com.yinxt.surveyscale.common.redis.RedisUtil;
import com.yinxt.surveyscale.common.result.Result;
import com.yinxt.surveyscale.vo.PatientIdVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.util.List;

@Slf4j
@Service
public class PatientInfoService {

    @Autowired
    private PatientInfoMapper patientInfoMapper;
    @Autowired
    private DoctorInfoService doctorInfoService;
    @Autowired
    private EligibleService eligibleService;
    @Value("${rsa.key.private}")
    private String privateKey;

    /**
     * 保存病人相关信息
     *
     * @param patientRelationInfoDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public PatientIdVO savePatientRelationInfo(PatientRelationInfoDTO patientRelationInfoDTO) {
        try {
            //1、保存病人信息
            String patientId = savePatientInfo(patientRelationInfoDTO.getPatientInfo(), true);
            PatientIdVO patientIdVO = new PatientIdVO();
            patientIdVO.setPatientId(patientId);
            //2、保存实验条件的答案
            List<PatientEligibleDTO> patientEligibleDTOList = patientRelationInfoDTO.getPatientEligibleList();
            for (PatientEligibleDTO patientEligibleDTO : patientEligibleDTOList) {
                patientEligibleDTO.setPatientId(patientId);
            }
            eligibleService.savePatientEligibleList(patientEligibleDTOList);
            return patientIdVO;
        } catch (Exception e) {
            log.info("保存病人相关信息失败", e);
            throw new LogicException("病人相关信息保存失败");
        }
    }

    /**
     * 添加病人基本信息
     *
     * @param patientInfoCommitReqDTO
     */
    public String savePatientInfo(PatientInfoCommitReqDTO patientInfoCommitReqDTO, boolean isEncrypted) {
        PatientInfo patientInfo = new PatientInfo();
        BeanUtils.copyProperties(patientInfoCommitReqDTO, patientInfo);
        String patientId = patientInfo.getPatientId();
        try {
            log.info("[savePatientInfo]请求参数：{}", JSON.toJSONString(patientInfo));
            PatientInfo checkPatientInfo = patientInfoMapper.selectPatientInfoByPatientId(patientInfo.getPatientId());
            if (checkPatientInfo == null) {
                patientId = RedisUtil.getSequenceId("PT");
                patientInfo.setPatientId(patientId);
                patientInfoMapper.insertPatientInfo(patientInfo);
            } else {
                patientInfoMapper.updatePatientInfo(patientInfo);
            }
            log.info("[savePatientInfo]数据保存成功");
        } catch (Exception e) {
            log.info("保存病人信息异常：{}", e);
            throw new LogicException("保存病人信息异常");
        }
        return patientId;
    }

    /**
     * 获取病人信息
     *
     * @param patientInfo
     * @return
     */
    public PatientInfo getPatientInfo(PatientInfo patientInfo) {
        log.info("[getPatientInfo]查询参数：{}", JSON.toJSONString(patientInfo));
        patientInfo.setDoctorId(doctorInfoService.getLoginDoctorId());
        PatientInfo patient = patientInfoMapper.selectPatientInfo(patientInfo);
        log.info("[getPatientInfo]查询结果：{}", JSON.toJSONString(patient));
        return patient;
    }

    /**
     * 通过量表ID和病人ID校验当前医生名下该病人是否存在
     *
     * @param scaleId
     * @param patientId
     */
    public PatientInfo checkPatientId(String scaleId, String patientId) {
        return patientInfoMapper.selectPatientByScaleIdAndPatientId(patientId, scaleId);
    }

    /**
     * 通过报告表ID和身份证号查询当前医生名下病人是否存在
     *
     * @param reportId
     * @param idCard
     * @return
     */
    public PatientInfo getPatientInfoByReportIdAndIdCard(String reportId, String idCard) {
        PatientInfo patientInfo = patientInfoMapper.selectPatientByReportIdAndIdCard(reportId, idCard);
        return patientInfo;
    }

    public PatientInfo getPatientInfoByReportIdAndPatientId(String reportId, String patientId) {
        return patientInfoMapper.selectPatientByReportIdAndPatientId(reportId, patientId);
    }


    /**
     * 查询病人列表
     *
     * @param listDataReqDTO
     */
    public Result getPatientInfoList(ListDataReqDTO<PatientInfo> listDataReqDTO) {
        try {
            log.info("[getPatientInfoList]查询参数：{}", JSON.toJSONString(listDataReqDTO));
            PatientInfo patientInfo = listDataReqDTO.getData();
            if (patientInfo == null) {
                patientInfo = new PatientInfo();
            }
            String doctorId = doctorInfoService.getLoginDoctorId();
            patientInfo.setDoctorId(doctorId);
            PageHelper.startPage(listDataReqDTO.getPageNo(), listDataReqDTO.getPageSize());
            List<PatientInfo> patientInfos = patientInfoMapper.selectPatientInfoList(patientInfo);
            PageInfo pageInfo = new PageInfo(patientInfos);
            log.info("[getPatientInfoList]查询结果：{}", JSON.toJSONString(patientInfos));
            PageBean pageBean = new PageBean(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages(), pageInfo.getTotal(), patientInfos);
            return Result.success(pageBean);
        } catch (Exception e) {
            log.error("查询病人信息异常：{}", e.getMessage());
            return Result.error();
        }
    }

    public Result removePatientInfo(PatientInfo patientInfo) {
        log.info("【removePatientInfo]请求参数：", JSON.toJSONString(patientInfo));
        try {
            if (patientInfo != null && StringUtils.isNotBlank(patientInfo.getPatientId())) {
                patientInfo.setStatus(StatusEnum.NO.getCode());
                patientInfoMapper.updatePatientInfo(patientInfo);
            }
        } catch (Exception e) {
            log.error("删除病人信息异常：", e);
            return Result.error();
        }
        log.info("[removePatientInfo]删除成功");
        return Result.success();
    }

    /**
     * 通过病人id数组获取病人信息列表
     *
     * @param patientIdArray
     * @return
     */
    public List<PatientInfo> getPatientInfoListByIdArray(List<String> patientIdArray, String doctorId) {
        return patientInfoMapper.selectPatientListByIdArray(patientIdArray, doctorId);
    }

    /**
     * 获取医生名下的全部病人信息列表
     *
     * @return
     */
    public List<String> getAllPatientIdList(String doctorId) {
        return patientInfoMapper.selectAllPatientIdList(doctorId);
    }

}
