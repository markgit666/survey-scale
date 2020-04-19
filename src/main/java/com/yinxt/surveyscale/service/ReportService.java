package com.yinxt.surveyscale.service;

import com.yinxt.surveyscale.dto.ReportDetailReqDTO;
import com.yinxt.surveyscale.entity.Report;
import com.yinxt.surveyscale.entity.ScaleInfo;
import com.yinxt.surveyscale.vo.ReportInfoVO;
import com.yinxt.surveyscale.vo.ReportListVO;
import com.yinxt.surveyscale.dto.ReportListReqDTO;
import com.yinxt.surveyscale.mapper.ReportMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-12 17:35
 */
@Service
public class ReportService {
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private ScaleInfoService scaleInfoService;
    @Autowired
    private DoctorInfoService doctorInfoService;

    /**
     * 获取医生个人的报告表
     *
     * @param reportListReqDTO
     * @return
     */
    public List<ReportListVO> getDoctorPersonReportList(ReportListReqDTO reportListReqDTO) {
        reportListReqDTO.setDoctorId(doctorInfoService.getLoginDoctorId());
        List<ReportListVO> reportListVOList = reportMapper.getDoctorPersonReportList(reportListReqDTO);
        for (ReportListVO reportListVO : reportListVOList) {
            reportListVO.setScaleNum(reportMapper.getReportScaleNum(reportListVO.getReportId()));
        }
        return reportListVOList;
    }

    /**
     * 查询报告表详情
     *
     * @param reportId
     * @param detail
     * @return
     */
    public ReportInfoVO getReportDetailInfo(String reportId, boolean detail) {
        //查询报告表
        Report report = reportMapper.getReportById(reportId);
        List<ScaleInfo> scaleInfoList = new ArrayList<>();
        List<String> scaleIdList = new ArrayList<>();
        //判断是否查询到报告表
        //todo
        if (report != null || StringUtils.isNotEmpty(reportId)) {
            //查询量表ID集合
            List<String> list = reportMapper.getReportScaleIdListById(reportId);
            scaleIdList = list;
            for (String scaleId : list) {
                scaleInfoList.add(scaleInfoService.getFormatScaleInfo(scaleId));
            }
        }
        ReportInfoVO reportInfoVO = new ReportInfoVO();
        BeanUtils.copyProperties(report, reportInfoVO);
        //判断是否需要详细数据
        if (detail) {
            reportInfoVO.setScaleInfoList(scaleInfoList);
        }
        reportInfoVO.setScaleIdList(scaleIdList);
        return reportInfoVO;
    }

    /**
     * 通过id获取报告表
     *
     * @param reportId
     * @return
     */
    public Report getReportById(String reportId) {
        return reportMapper.getReportById(reportId);
    }

    /**
     * 通过报告表id获取量表数量
     *
     * @return
     */
    public int getScaleNumByReportId(String reportId) {
        return reportMapper.selectScaleNumByReportId(reportId);
    }
}
