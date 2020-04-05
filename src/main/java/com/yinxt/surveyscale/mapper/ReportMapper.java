package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.entity.Report;
import com.yinxt.surveyscale.vo.ReportListVO;
import com.yinxt.surveyscale.dto.ReportListReqDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-12 17:37
 */
@Component
public interface ReportMapper {

    List<ReportListVO> getDoctorPersonReportList(ReportListReqDTO reportListReqDTO);

    int getReportScaleNum(@Param("reportId") String reportId);

    Report getReportById(@Param("reportId") String reportId);

    List<String> getReportScaleIdListById(@Param("reportId") String reportId);

    int selectScaleNumByReportId(@Param("reportId") String reportId);
}
