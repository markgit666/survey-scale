package com.yinxt.surveyscale.service;

import com.yinxt.surveyscale.common.util.ExcelUtil;
import com.yinxt.surveyscale.entity.PatientInfo;
import com.yinxt.surveyscale.entity.ScaleInfo;
import com.yinxt.surveyscale.vo.ExaminationPaperScalesListRespVO;
import com.yinxt.surveyscale.vo.ScalePaperQuestionListRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
                "工作状态", "在职职业", "文化程度", "受教育年数（年）",
                "是否打呼噜", "居住方式", "既往病史", "其他疾病", "吸烟史",
                "一天抽几支（支）", "吸烟年数（年）", "饮酒史", "饮酒类型", "一天几两（两）", "喝酒年数（年）",
                "有无精神疾病家族史", "精神疾病家族史", "其他精神病史",
                "现病史（有无记忆下降）", "记忆力下降多久（年）", "体格检查情况",
                "是否合并使用促认知药物",
                "具体促认知药物", "具体药物的剂量"};
        String fileName = "被试者信息表-" + new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date()) + ".xlsx";
        String sheetName = "被试者信息";
        String[][] content = new String[patientInfoList.size()][header.length + 1];
        for (int i = 0; i < patientInfoList.size(); i++) {
            String[] col = content[i];
            PatientInfo patientInfo = patientInfoList.get(i);
            col[0] = patientInfo.getPatientId();
            col[1] = patientInfo.getName();
            col[2] = new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(patientInfo.getBirthday());
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
     * @param examinationPaperIdList
     */
    public void getExaminationPaperExcelById(HttpServletResponse response, List<String> examinationPaperIdList, String doctorId) {
        outputExaminationPaperList(response, examinationPaperIdList, doctorId);
    }

    /**
     * 获取医生名下全部病人的答卷信息Excel
     *
     * @param response
     */
    public void getAllExaminationPaperExcel(HttpServletResponse response, String doctorId) {
        List<String> examinationPaperIdList = examinationPaperService.getAllExaminationPaperIdList(doctorId);
        outputExaminationPaperList(response, examinationPaperIdList, doctorId);
    }

    /**
     * 导出答卷列表信息Excel
     *
     * @param response
     * @param list
     * @param doctorId
     */
    public void outputExaminationPaperList(HttpServletResponse response, List<String> list, String doctorId) {
        for (String examinationPaperId : list) {
            List<ExaminationPaperScalesListRespVO> examinationPaperScalesListRespVOS = examinationPaperService.getExaminationPaperScaleListById(examinationPaperId, doctorId);
            if (examinationPaperScalesListRespVOS.size() >= 1) {
//                String excelName = examinationPaperService.getReportNameByPaperId(examinationPaperId);
                outputExaminationPaperExcel(examinationPaperScalesListRespVOS, response);
            }
        }
    }


    /**
     * 构建导出答卷excel的参数
     *
     * @param examinationPaperScalesListRespVOS
     * @param response
     */
    public void outputExaminationPaperExcel(List<ExaminationPaperScalesListRespVO> examinationPaperScalesListRespVOS, HttpServletResponse response) {
        String[] header = {"量表答卷编号", "量表名称", "被试者姓名", "题目数量", "用时", "答题日期", "评分状态", "评定人", "总分"};
        ExaminationPaperScalesListRespVO listRespVO = examinationPaperScalesListRespVOS.get(0);
        String fileName = listRespVO.getExaminationPaperId() + "-" + listRespVO.getReportName() + "-" + new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date()) + ".xlsx";
        String sheetName = "报告表答卷信息";
        String[][] content = new String[examinationPaperScalesListRespVOS.size()][header.length + 1];
        for (int i = 0; i < examinationPaperScalesListRespVOS.size(); i++) {
            String col[] = content[i];
            ExaminationPaperScalesListRespVO examinationPaperScalesListRespVO = examinationPaperScalesListRespVOS.get(i);
            col[0] = examinationPaperScalesListRespVO.getScalePaperId();
            col[1] = examinationPaperScalesListRespVO.getScaleName();
            col[2] = examinationPaperScalesListRespVO.getPatientName();
            col[3] = String.valueOf(examinationPaperScalesListRespVO.getQuestionCount());
            col[4] = examinationPaperScalesListRespVO.getUseTime();
            col[5] = examinationPaperScalesListRespVO.getExaminationDate();
            col[6] = "1".equals(examinationPaperScalesListRespVO.getJudgeStatus()) ? "已评分" : "未评分";
            col[7] = examinationPaperScalesListRespVO.getCheckUser();
            col[8] = examinationPaperScalesListRespVO.getTotalScore();
        }
        outputExcel(response, fileName, sheetName, header, content);
    }

    /**
     * 获取量表答卷信息Excel
     *
     * @param response
     * @param scalePaperIdList
     */
    public void getScalePaperExcelById(HttpServletResponse response, List<String> scalePaperIdList) {
        for (String scalePaperId : scalePaperIdList) {
            List<ScalePaperQuestionListRespVO> scalePaperQuestionListRespVOList = examinationPaperService.getScalePaperQuestionListById(scalePaperId);
            if (scalePaperQuestionListRespVOList.size() > 0) {
                outputScalePaperExcel(response, scalePaperQuestionListRespVOList);
            }
        }
    }

    /**
     * 导出量表答卷到Excel
     *
     * @param response
     * @param scalePaperQuestionListRespVOList
     */
    public void outputScalePaperExcel(HttpServletResponse response, List<ScalePaperQuestionListRespVO> scalePaperQuestionListRespVOList) {
        String header[] = {"量表答卷编号", "题目编号", "题目标题", "(单选/多选）选项", "图片附件编号", "是否记分", "分组名称", "显示", "答案", "得分"};
        ScalePaperQuestionListRespVO paperQuestionListRespVO = scalePaperQuestionListRespVOList.get(0);
        String fileName = paperQuestionListRespVO.getScaleId() + "-" + paperQuestionListRespVO.getScaleName() + "-" + new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date()) + ".xlsx";
        String sheetName = "量表答卷信息";
        String[][] content = new String[scalePaperQuestionListRespVOList.size()][header.length + 1];
        for (int i = 0; i < scalePaperQuestionListRespVOList.size(); i++) {
            String[] col = content[i];
            ScalePaperQuestionListRespVO scalePaperQuestionListRespVO = scalePaperQuestionListRespVOList.get(i);
            col[0] = scalePaperQuestionListRespVO.getScalePaperId();
            col[1] = scalePaperQuestionListRespVO.getQuestionId();
            col[2] = scalePaperQuestionListRespVO.getTitle();
            col[3] = scalePaperQuestionListRespVO.getItems();
            col[4] = scalePaperQuestionListRespVO.getAttachment();
            col[5] = scalePaperQuestionListRespVO.isRecordScore() ? "是" : "否";
            col[6] = scalePaperQuestionListRespVO.getGroupType();
            col[7] = scalePaperQuestionListRespVO.isDisplay() ? "是" : "否";
            col[8] = scalePaperQuestionListRespVO.getContent();
            col[9] = String.valueOf(scalePaperQuestionListRespVO.getScore());
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
