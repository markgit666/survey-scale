package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.service.ExcelService;
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
@RequestMapping(value = "excel")
@Controller
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @RequestMapping(value = "export/patient/info")
    public void exportPatientInfoExcel(@RequestParam("patientIdArray") List<String> patientIdArray, @RequestParam("doctorId") String doctorId, HttpServletResponse response) {
        excelService.getPatientInfoExcelById(response, patientIdArray, doctorId);
    }

    @RequestMapping(value = "export/all/patient/info")
    public void exportAllPatientInfoExcel(HttpServletResponse response, @RequestParam("doctorId") String doctorId) {
        excelService.getAllPatientInfoExcel(response, doctorId);
    }

    @RequestMapping(value = "export/examinationPaper/info")
    public void exportExaminationPaperExcel(@RequestParam("examinationPaperIdArray") List<String> examinationPaperIdArray, @RequestParam("doctorId") String doctorId, HttpServletResponse response) {
        excelService.getExaminationPaperExcelById(response, examinationPaperIdArray, doctorId);
    }

    @RequestMapping(value = "export/all/examinationPaper/info")
    public void exportAllExaminationPaperExcel(HttpServletResponse response, @RequestParam("doctorId") String doctorId) {
        excelService.getAllExaminationPaperExcel(response, doctorId);
    }

    @RequestMapping(value = "export/scalePaper/info")
    public void exportScalePaperExcel(HttpServletResponse response, @RequestParam("scalePaperIdArray") List<String> scalePaperIdArray) {
        excelService.getScalePaperExcelById(response, scalePaperIdArray);
    }


}
