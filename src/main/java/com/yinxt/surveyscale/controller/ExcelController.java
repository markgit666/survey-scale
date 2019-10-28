package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

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
    public void exportExcel(@RequestParam("patientIdArray") String[] patientIdArray, HttpServletResponse response) {
        excelService.getPatientInfoExcel(response, patientIdArray);
    }
}
