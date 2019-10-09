package com.yinxt.surveyscale.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinxt.surveyscale.pojo.DoctorAuthInfo;
import com.yinxt.surveyscale.util.enums.StatusEnum;
import com.yinxt.surveyscale.dto.ListDataReqDTO;
import com.yinxt.surveyscale.mapper.PatientInfoMapper;
import com.yinxt.surveyscale.pojo.PatientInfo;
import com.yinxt.surveyscale.util.page.PageBean;
import com.yinxt.surveyscale.util.redis.RedisUtil;
import com.yinxt.surveyscale.util.result.Result;
import com.yinxt.surveyscale.util.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PatientInfoService {

    @Autowired
    private PatientInfoMapper patientInfoMapper;
    @Autowired
    private DoctorInfoService doctorInfoService;

    /**
     * 添加病人基本信息
     *
     * @param patientInfo
     */
    public Result savePatientInfo(PatientInfo patientInfo) {
        try {
            log.info("[savePatientInfo]请求信息：{}", JSON.toJSONString(patientInfo));
            PatientInfo checkPatientInfo = patientInfoMapper.selectPatientInfoByPatientId(patientInfo.getPatientId());
            if (checkPatientInfo == null) {
                String patientId = RedisUtil.getSequenceId("PT");
                patientInfo.setPatientId(patientId);
                String doctorId = doctorInfoService.getLoginDoctorId();
                patientInfo.setDoctorId(doctorId);
                patientInfoMapper.insertPatientInfo(patientInfo);
            } else {
                patientInfoMapper.updatePatientInfo(patientInfo);
            }
            log.info("[savePatientInfo]数据保存成功");
            return new Result(ResultEnum.SUCCESS);
        } catch (Exception e) {
            log.info("保存病人信息异常：{}", e);
            return new Result().error();
        }
    }

    /**
     * 获取病人信息
     *
     * @param patientInfo
     * @return
     */
    public Result getPatientInfo(PatientInfo patientInfo) {
        log.info("获取病人信息，请求参数：{}", JSON.toJSONString(patientInfo));
        try {
            PatientInfo patientInfo1 = patientInfoMapper.selectPatientInfo(patientInfo);
            log.info("病人信息查询结果：{}", JSON.toJSONString(patientInfo1));
            return Result.success(patientInfo1);
        } catch (Exception e) {
            log.info("获取病人信息异常：{}", e.getMessage());
            return Result.error();
        }
    }

    /**
     * 查询病人列表
     *
     * @param listDataReqDTO
     */
    public Result getPatientInfoList(ListDataReqDTO<PatientInfo> listDataReqDTO) {
        try {
            PageHelper.startPage(listDataReqDTO.getPageNo(), listDataReqDTO.getPageSize());
            log.info("病人信息列表查询参数：{}", JSON.toJSONString(listDataReqDTO));
            PatientInfo patientInfo = listDataReqDTO.getData();
            if (patientInfo == null) {
                patientInfo = new PatientInfo();
            }
            String doctorId = doctorInfoService.getLoginDoctorId();
            patientInfo.setDoctorId(doctorId);
            List<PatientInfo> patientInfos = patientInfoMapper.selectPatientInfoList(patientInfo);
            PageInfo pageInfo = new PageInfo(patientInfos);
            log.info("返回病人信息列表：{}", JSON.toJSONString(patientInfos));
            PageBean pageBean = new PageBean(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages(), pageInfo.getTotal(), patientInfos);
            return Result.success(pageBean);
        } catch (Exception e) {
            log.error("查询病人信息异常：{}", e.getMessage());
            return Result.error();
        }
    }

    public Result removePatientInfo(PatientInfo patientInfo) {
        log.info("删除病人信息，请求参数：", JSON.toJSONString(patientInfo));
        try {
            if (patientInfo != null && StringUtils.isNotBlank(patientInfo.getPatientId())) {
                patientInfo.setStatus(StatusEnum.NO.getCode());
                patientInfoMapper.updatePatientInfo(patientInfo);
            }
        } catch (Exception e) {
            log.error("删除病人信息异常：", e);
            return Result.error();
        }
        return Result.success();
    }

}
