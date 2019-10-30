package com.yinxt.surveyscale.service;

import com.yinxt.surveyscale.common.util.ExcelUtil;
import com.yinxt.surveyscale.entity.PatientInfo;
import com.yinxt.surveyscale.vo.ExaminationPaperListRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * excel service
 *
 * @author yinxt
 * @version 1.0
 * @date 2019-10-28 21:41
 */
@Service
@Slf4j
public class ExcelService {

    @Autowired
    private PatientInfoService patientInfoService;
    @Autowired
    private ExaminationPaperService examinationPaperService;

    /**
     * 根据病人编号导出病人信息Excel
     *
     * @param response
     * @param patientIdArray
     */
    public void getPatientInfoExcelById(HttpServletResponse response, String[] patientIdArray, String doctorId) {
        List<PatientInfo> patientInfoList = patientInfoService.getPatientInfoListByIdArray(patientIdArray, doctorId);
        outputPatientInfoExcel(patientInfoList, response);
    }

    /**
     * 导出医生名下全部病人信息Excel
     *
     * @param response
     */
    public void getAllPatientInfoExcel(HttpServletResponse response, String doctorId) {
        List<PatientInfo> patientInfoList = patientInfoService.getAllPatientInfo(doctorId);
        outputPatientInfoExcel(patientInfoList, response);
    }

    /**
     * 构建导出Excel的参数
     *
     * @param patientInfoList
     * @param response
     */
    private void outputPatientInfoExcel(List<PatientInfo> patientInfoList, HttpServletResponse response) {
        String[] header = {"编号", "姓名", "出生日期", "性别", "家庭地址", "联系方式", "利手", "民族", "婚姻",
                "工作状态", "在职职业", "文化程度", "受教育年数",
                "是否打呼噜", "居住方式", "既往病史", "其他疾病", "吸烟史",
                "一天抽几支", "吸烟年数", "饮酒史", "饮酒类型", "一天几两", "喝酒年数",
                "有无精神疾病家族史", "精神疾病家族史", "其他精神病史",
                "现病史（有无记忆下降）", "记忆力下降多久", "体格检查情况",
                "是否合并使用促认知药物",
                "具体促认知药物", "具体药物的剂量"};
        String fileName = "被试者信息表-" + new SimpleDateFormat("yyyyMMddHHmmSS").format(new Date()) + ".xlsx";
        String sheetName = "被试者信息表";
        String[][] content = new String[patientInfoList.size()][header.length + 1];
        for (int i = 0; i < patientInfoList.size(); i++) {
            String[] col = content[i];
            PatientInfo patientInfo = patientInfoList.get(i);

            col[0] = patientInfo.getPatientId();
            col[1] = patientInfo.getName();
            col[2] = new SimpleDateFormat("yyyy-MM-dd").format(patientInfo.getBirthday());
            col[3] = "1".equals(patientInfo.getGender()) ? "男" : "女";
            col[4] = patientInfo.getFamilyAddress();
            col[5] = patientInfo.getTelephoneNumber();
            col[6] = patientInfo.getHand();
            col[7] = patientInfo.getNation();
            col[8] = patientInfo.getMarriageStatus();
            col[9] = patientInfo.getWorkStatus();
            col[10] = patientInfo.getInServiceJob();
            col[11] = patientInfo.getEducationLevel();
            col[12] = String.valueOf(patientInfo.getEducationYears());
            col[13] = patientInfo.getIsSnoring();
            col[14] = patientInfo.getLivingWay();
            col[15] = patientInfo.getMedicalHistory();
            col[16] = patientInfo.getOtherMedicalHistory();
            col[17] = patientInfo.getSmokingHistory();
            col[18] = String.valueOf(patientInfo.getSmokingNumEachDay());
            col[19] = String.valueOf(patientInfo.getSmokingYears());
            col[20] = patientInfo.getDrinkingHistory();
            col[21] = patientInfo.getDrinkingType();
            col[22] = patientInfo.getDrinkingNumEachDay();
            col[23] = String.valueOf(patientInfo.getDrinkingYears());
            col[24] = patientInfo.getIsMentalDiseaseFamilyHistory();
            col[25] = patientInfo.getMentalDiseaseFamilyHistory();
            col[26] = patientInfo.getOtherMentalDiseaseFamilyHistory();
            col[27] = patientInfo.getCurrentMedicalHistoryMemoryLoss();
            col[28] = patientInfo.getCurrentMedicalHistoryMemoryLoss();
            col[29] = patientInfo.getMemoryLossTime();
            col[30] = patientInfo.getPhysicalExamination();
            col[31] = patientInfo.getIsUseCognitiveDrugs();
            col[32] = patientInfo.getDrugsType();
            col[33] = patientInfo.getDrugsDosage();
        }
        outputExcel(response, fileName, sheetName, header, content);
    }

    /**
     * 根据答卷id导出答卷信息Excel
     *
     * @param response
     * @param examinationPaperIdArray
     */
    public void getExaminationPaperExcelById(HttpServletResponse response, String[] examinationPaperIdArray, String doctorId) {
        List<ExaminationPaperListRespVO> examinationPaperListRespVOS = examinationPaperService.getExaminationPaperListByIdArray(examinationPaperIdArray, doctorId);
        outputExaminationPaperExcel(examinationPaperListRespVOS, response);
    }

    /**
     * 获取医生名下全部病人答卷信息Excel
     *
     * @param response
     */
    public void getAllExaminationPaperExcel(HttpServletResponse response, String doctorId) {
        List<ExaminationPaperListRespVO> examinationPaperListRespVOS = examinationPaperService.getAllExaminationPaper(doctorId);
        outputExaminationPaperExcel(examinationPaperListRespVOS, response);
    }


    /**
     * 构建导出答卷excel的参数
     *
     * @param examinationPaperListRespVOS
     * @param response
     */
    public void outputExaminationPaperExcel(List<ExaminationPaperListRespVO> examinationPaperListRespVOS, HttpServletResponse response) {
        String[] header = {"答卷编号", "量表名称", "被试者", "答题时间（分钟）", "评分状态", "总分"};
        String fileName = "答卷信息表-" + new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date()) + ".xlsx";
        String sheetName = "答卷信息表";
        String[][] content = new String[examinationPaperListRespVOS.size()][header.length + 1];
        for (int i = 0; i < examinationPaperListRespVOS.size(); i++) {
            String col[] = content[i];
            ExaminationPaperListRespVO examinationPaperListRespVO = examinationPaperListRespVOS.get(i);
            col[0] = examinationPaperListRespVO.getExaminationPaperId();
            col[1] = examinationPaperListRespVO.getScaleName();
            col[2] = examinationPaperListRespVO.getPatientName();
            col[3] = examinationPaperListRespVO.getUseTime();
            col[4] = "1".equals(examinationPaperListRespVO.getJudgeStatus()) ? "已评分" : "未评分";
            col[5] = examinationPaperListRespVO.getTotalScore();
        }
        outputExcel(response, fileName, sheetName, header, content);
    }

    /**
     * 导出excel
     *
     * @param response
     * @param sheetName
     * @param header
     * @param content
     */
    private void outputExcel(HttpServletResponse response, String fileName, String sheetName, String[] header, String[][] content) {
        XSSFWorkbook xssfWorkbook = ExcelUtil.getWorkbook(sheetName, header, content);
        //声明输出流
        OutputStream outputStream = null;
        //响应到客户端
        try {
            //设置响应头
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");

            //获取输出流
            outputStream = response.getOutputStream();

            //用文档写输出流
            xssfWorkbook.write(outputStream);

            //刷新输出流
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭输出流
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("关闭输出流失败：", e);
                }
            }
        }
    }

}