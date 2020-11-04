package com.yinxt.surveyscale.service;

import com.yinxt.surveyscale.entity.Report;
import com.yinxt.surveyscale.entity.ScaleInfo;
import com.yinxt.surveyscale.vo.ReportInfoVO;
import com.yinxt.surveyscale.vo.ReportListVO;
import com.yinxt.surveyscale.dto.ReportListReqDTO;
import com.yinxt.surveyscale.mapper.ReportMapper;
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
        return reportListVOList;
    }

    /**
     * 查询报告表详情
     *
     * @param reportId
     * @param detail
     * @return
     */
    public ReportInfoVO getReportDetailInfo(String reportId, boolean detail, boolean needLogin, boolean isNeedQuestions) {
        //查询报告表
        Report report;
        if (needLogin) {
            report = reportMapper.getReportByReportAndDoctorId(reportId, doctorInfoService.getLoginDoctorId());
        } else {
            report = reportMapper.getReportById(reportId);
        }
        //报告表VO
        ReportInfoVO reportInfoVO = new ReportInfoVO();
        //判断是否查询到报告表
        if (report != null) {
            BeanUtils.copyProperties(report, reportInfoVO);
            //查询量表ID集合
            List<String> list = reportMapper.getReportScaleIdListById(reportId);
            reportInfoVO.setScaleIdList(list);
            //判断是否需要详细数据
            if (detail) {
                //量表信息列表
                List<ScaleInfo> formatScaleInfoList = new ArrayList<>();
                List<ScaleInfo> scaleInfoList = scaleInfoService.getScaleInfoByIdList(list);
                for (ScaleInfo scaleInfo : scaleInfoList) {
                    formatScaleInfoList.add(scaleInfoService.getFormatScaleInfo(scaleInfo, isNeedQuestions));
                }
                reportInfoVO.setScaleInfoList(formatScaleInfoList);
            }
        }
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
