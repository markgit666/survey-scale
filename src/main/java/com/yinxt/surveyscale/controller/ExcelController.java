package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.service.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yinxt
 * @version 1.0
 * @date 2019-10-28 21:40
 */
@Api(tags = {"excel:"})
@RequestMapping(value = "excel")
@Controller
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @ApiOperation(value = "导出被试者信息到excel", notes = "根据被试者编号列表，批量导出被试者信息到excel", httpMethod = "POST")
    @RequestMapping(value = "export/patient/info")
    public void exportPatientInfoExcel(@RequestParam("patientIdArray") List<String> patientIdArray, @RequestParam("doctorId") String doctorId, HttpServletResponse response) {
        excelService.getPatientInfoExcelById(response, patientIdArray, doctorId);
    }

    @ApiOperation(value = "导出医生名下全部被试者信息到excel", notes = "导出医生名下全部被试者信息到excel", httpMethod = "POST")
    @RequestMapping(value = "export/all/patient/info")
    public void exportAllPatientInfoExcel(HttpServletResponse response, @RequestParam("doctorId") String doctorId) {
        excelService.getAllPatientInfoExcel(response, doctorId);
    }

    @ApiOperation(value = "导出报告表答卷到excel", notes = "根据报告表答卷编号列表，导出报告表答卷到excel", httpMethod = "POST")
    @RequestMapping(value = "export/examinationPaper/info")
    public void exportExaminationPaperExcel(@RequestParam("examinationPaperIdArray") List<String> examinationPaperIdArray, @RequestParam("doctorId") String doctorId, HttpServletResponse response) {
        excelService.getExaminationPaperExcelById(response, examinationPaperIdArray, doctorId);
    }

    @ApiOperation(value = "导出医生名下全部报告表答卷到excel", notes = "导出医生名下全部报告表答卷到excel", httpMethod = "POST")
    @RequestMapping(value = "export/all/examinationPaper/info")
    public void exportAllExaminationPaperExcel(HttpServletResponse response, @RequestParam("doctorId") String doctorId) {
        excelService.getAllExaminationPaperExcel(response, doctorId);
    }

    @ApiOperation(value = "导出量表答卷到excel", notes = "根据量表答卷编号，导出量表答卷到excel", httpMethod = "POST")
    @RequestMapping(value = "export/scalePaper/info")
    public void exportScalePaperExcel(HttpServletResponse response, @RequestParam("scalePaperIdArray") List<String> scalePaperIdArray) {
        excelService.getScalePaperExcelById(response, scalePaperIdArray);
    }

}
