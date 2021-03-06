package com.yinxt.surveyscale.service;

import com.yinxt.surveyscale.common.exeption.LogicException;
import com.yinxt.surveyscale.common.util.ExcelUtil;
import com.yinxt.surveyscale.common.util.ZipFilesUtils;
import com.yinxt.surveyscale.entity.PatientInfo;
import com.yinxt.surveyscale.vo.ExaminationPaperScalesListRespVO;
import com.yinxt.surveyscale.vo.ScalePaperQuestionListRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.logging.LogException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    @Value("${excel.path}")
    private String excelPath;

    private static final String NPI_SCALE_NAME = "神经精神科量表";

    /**
     * 根据被试者编号导出被试者信息Excel
     *
     * @param response
     * @param patientIdArray
     * @param doctorId
     */
    public void getPatientInfoExcelById(HttpServletResponse response, List<String> patientIdArray, String doctorId) {
        outPutPatientInfoList(response, patientIdArray, doctorId);
    }

    /**
     * 导出医生名下全部被试者信息Excel
     *
     * @param response
     * @param doctorId
     */
    public void getAllPatientInfoExcel(HttpServletResponse response, String doctorId) {
        List<String> patientInfoList = patientInfoService.getAllPatientIdList(doctorId);
        outPutPatientInfoList(response, patientInfoList, doctorId);
    }

    public void outPutPatientInfoList(HttpServletResponse response, List<String> list, String doctorId) {
        String nowDateTime = new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date());
        String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String nowTime = new SimpleDateFormat("HHmmss").format(new Date());
        String directory = excelPath + File.separator + "patientInfo" + File.separator + "original" + File.separator + nowDate + File.separator + nowTime;
        List<PatientInfo> patientInfoList = patientInfoService.getPatientInfoListByIdArray(list, doctorId);
        outputPatientInfoExcel(patientInfoList, directory, nowDateTime);
        //压缩文件
        String zipFilePath = excelPath + File.separator + "patientInfo" + File.separator + "zip" + File.separator + nowDate + File.separator + "patientInfo" + "-" + nowDateTime + ".zip";
        try {
            ZipFilesUtils.zipByFolder(directory, zipFilePath);
        } catch (IOException e) {
            log.error("压缩被试者信息表失败", e);
            throw new LogException("导出被试者信息失败");
        }
        outputExcel(response, zipFilePath);
    }


    /**
     * 构建导出Excel的参数
     *
     * @param patientInfoList
     * @param directory
     * @param nowDateStr
     */
    private void outputPatientInfoExcel(List<PatientInfo> patientInfoList, String directory, String nowDateStr) {
        String[] header = {"病历号", "姓名", "组别", "身份证号", "出生日期", "性别", "家庭地址", "联系方式", "利手", "民族", "婚姻",
                "工作状态", "在职职业", "文化程度", "受教育年数（年）",
                "是否打呼噜", "居住方式", "既往病史", "吸烟史",
                "一天抽几支（支）", "吸烟年数（年）", "饮酒史", "饮酒类型", "一天几两（两）", "喝酒年数（年）",
                "有无精神疾病家族史", "具体精神疾病家族史", "其他精神病史",
                "现病史（有无记忆下降）", "记忆力下降多久（年）", "体格检查情况",
                "是否合并使用促认知药物",
                "具体促认知药物、剂量、起始时间"};
        String fileName = "被试者信息表-" + nowDateStr + ".xlsx";
        String sheetName = "被试者信息";
        String[][] content = new String[patientInfoList.size()][header.length + 1];
        for (int i = 0; i < patientInfoList.size(); i++) {
            String[] col = content[i];
            PatientInfo patientInfo = patientInfoList.get(i);
            col[0] = patientInfo.getMedicalRecordNum();
            col[1] = patientInfo.getName();
            col[2] = patientInfo.getPatientGroup();
            col[3] = patientInfo.getIdCard();
            col[4] = new SimpleDateFormat("yyyy-MM-dd").format(patientInfo.getBirthday());
            col[5] = "1".equals(patientInfo.getGender()) ? "男" : "女";
            col[6] = patientInfo.getFamilyAddress();
            col[7] = patientInfo.getTelephoneNumber();
            col[8] = patientInfo.getHand();
            col[9] = patientInfo.getNation();
            col[10] = patientInfo.getMarriageStatus();
            col[11] = patientInfo.getWorkStatus();
            col[12] = patientInfo.getInServiceJob();
            col[13] = patientInfo.getEducationLevel();
            col[14] = String.valueOf(patientInfo.getEducationYears());
            col[15] = patientInfo.getIsSnoring();
            col[16] = patientInfo.getLivingWay();
            col[17] = patientInfo.getMedicalHistory();
            col[18] = patientInfo.getSmokingHistory();
            col[19] = String.valueOf(patientInfo.getSmokingNumEachDay());
            col[20] = String.valueOf(patientInfo.getSmokingYears());
            col[21] = patientInfo.getDrinkingHistory();
            col[22] = patientInfo.getDrinkingType();
            col[23] = patientInfo.getDrinkingNumEachDay();
            col[24] = String.valueOf(patientInfo.getDrinkingYears());
            col[25] = patientInfo.getIsMentalDiseaseFamilyHistory();
            col[26] = patientInfo.getMentalDiseaseFamilyHistory();
            col[27] = patientInfo.getOtherMentalDiseaseFamilyHistory();
            col[28] = patientInfo.getCurrentMedicalHistoryMemoryLoss();
            col[29] = patientInfo.getMemoryLossTime();
            col[30] = patientInfo.getPhysicalExamination();
            col[31] = patientInfo.getIsUseCognitiveDrugs();
            col[32] = patientInfo.getDrugsDosage();
        }
        XSSFWorkbook xssfWorkbook = ExcelUtil.getWorkbook(sheetName, header, content);
        saveExcelToDirectory(xssfWorkbook, fileName, directory);
    }

    /**
     * 根据答卷id导出答卷信息Excel
     *
     * @param response
     * @param examinationPaperIdList
     * @param doctorId
     */
    public void getExaminationPaperExcelById(HttpServletResponse response, List<String> examinationPaperIdList, String doctorId) {
        outputExaminationPaperList(response, examinationPaperIdList, doctorId);
    }

    /**
     * 获取医生名下全部被试者的答卷信息Excel
     *
     * @param response
     * @param doctorId
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
        String nowDateTime = new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date());
        String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String nowTime = new SimpleDateFormat("HHmmss").format(new Date());
        String directory = excelPath + File.separator + "examinationPaper" + File.separator + "original" + File.separator + nowDate + File.separator + nowTime;
        for (String examinationPaperId : list) {
            List<ExaminationPaperScalesListRespVO> examinationPaperScalesListRespVOS = examinationPaperService.getExaminationPaperScaleListById(examinationPaperId, doctorId);
            if (examinationPaperScalesListRespVOS.size() >= 1) {
                String excelName = examinationPaperService.getReportNameByPaperId(examinationPaperId);
                examinationPaperScalesListRespVOS.get(0).setReportName(excelName);
                outputExaminationPaperExcel(examinationPaperScalesListRespVOS, directory, nowDateTime);
            }
        }
        //压缩文件
        String zipFilePath = excelPath + File.separator + "examinationPaper" + File.separator + "zip" + File.separator + nowDate + File.separator + "scalePaper" + "-" + nowDateTime + ".zip";
        try {
            ZipFilesUtils.zipByFolder(directory, zipFilePath);
        } catch (IOException e) {
            log.error("压缩报告表答卷信息表失败", e);
            throw new LogException("导出报告表答卷信息失败");
        }
        outputExcel(response, zipFilePath);
    }


    /**
     * 构建导出答卷excel的参数
     *
     * @param examinationPaperScalesListRespVOS
     * @param directory
     * @param nowDateStr
     */
    public void outputExaminationPaperExcel(List<ExaminationPaperScalesListRespVO> examinationPaperScalesListRespVOS, String directory, String nowDateStr) {
        String[] header = {"量表答卷编号", "量表名称", "被试者姓名", "题目数量", "用时（分钟）", "答题日期", "评分状态", "评定人", "总分", "频率总分(仅NPI)", "严重程度总分(仅NPI)", "频率*严重程度总分(仅NPI)", "使照料者苦恼程度(仅NPI)"};
        ExaminationPaperScalesListRespVO listRespVO = examinationPaperScalesListRespVOS.get(0);
        String fileName = listRespVO.getExaminationPaperId() + "-" + listRespVO.getReportName() + "-" + nowDateStr + ".xlsx";
        String sheetName = "报告表答卷信息";
        String[][] content = new String[examinationPaperScalesListRespVOS.size()][header.length + 1];
        for (int i = 0; i < examinationPaperScalesListRespVOS.size(); i++) {
            String col[] = content[i];
            ExaminationPaperScalesListRespVO examinationPaperScalesListRespVO = examinationPaperScalesListRespVOS.get(i);
            if (StringUtils.isNotBlank(examinationPaperScalesListRespVO.getScalePaperId()) && StringUtils.isNotBlank(examinationPaperScalesListRespVO.getScaleName())) {
                col[0] = examinationPaperScalesListRespVO.getScalePaperId();
                col[1] = examinationPaperScalesListRespVO.getScaleName();
                col[2] = examinationPaperScalesListRespVO.getPatientName();
                col[3] = String.valueOf(examinationPaperScalesListRespVO.getQuestionCount());
                col[4] = examinationPaperScalesListRespVO.getUseTime();
                col[5] = examinationPaperScalesListRespVO.getExaminationDate();
                col[6] = "1".equals(examinationPaperScalesListRespVO.getJudgeStatus()) ? "已评分" : "未评分";
                col[7] = examinationPaperScalesListRespVO.getCheckUser();
                col[8] = examinationPaperScalesListRespVO.getTotalScore() == null ? "" : String.valueOf(examinationPaperScalesListRespVO.getTotalScore());
                if (examinationPaperScalesListRespVO.getScaleName().contains(NPI_SCALE_NAME)) {
                    col[9] = String.valueOf(examinationPaperScalesListRespVO.getFrequencyTotalScore());
                    col[10] = String.valueOf(examinationPaperScalesListRespVO.getSeriousTotalScore());
                    col[11] = String.valueOf(examinationPaperScalesListRespVO.getFrequencySeriousTotalScore());
                    col[12] = String.valueOf(examinationPaperScalesListRespVO.getDistressTotalScore());
                }
            }
        }
        XSSFWorkbook xssfWorkbook = ExcelUtil.getWorkbook(sheetName, header, content);
        saveExcelToDirectory(xssfWorkbook, fileName, directory);
    }

    /**
     * 获取量表答卷信息Excel
     *
     * @param response
     * @param scalePaperIdList
     */
    public void getScalePaperExcelById(HttpServletResponse response, List<String> scalePaperIdList) {
        String nowDateTime = new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date());
        String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String nowTime = new SimpleDateFormat("HHmmss").format(new Date());
        String directory = excelPath + File.separator + "scalePaper" + File.separator + "original" + File.separator + nowDate + File.separator + nowTime;
        for (String scalePaperId : scalePaperIdList) {
            List<ScalePaperQuestionListRespVO> scalePaperQuestionListRespVOList = examinationPaperService.getScalePaperQuestionListById(scalePaperId);
            if (scalePaperQuestionListRespVOList.size() > 0) {
                outputScalePaperExcel(scalePaperQuestionListRespVOList, directory, nowDateTime);
            }
        }
        //压缩文件
        String zipFilePath = excelPath + File.separator + "scalePaper" + File.separator + "zip" + File.separator + nowDate + File.separator + "scalePaper" + "-" + nowDateTime + ".zip";
        try {
            ZipFilesUtils.zipByFolder(directory, zipFilePath);
        } catch (IOException e) {
            log.error("压缩量表答卷信息表失败", e);
            throw new LogException("导出量表答卷信息失败");
        }
        outputExcel(response, zipFilePath);
    }

    /**
     * 导出量表答卷到Excel
     *
     * @param scalePaperQuestionListRespVOList
     * @param directory
     * @param nowDateStr
     */
    public void outputScalePaperExcel(List<ScalePaperQuestionListRespVO> scalePaperQuestionListRespVOList, String directory, String nowDateStr) {
        String header[] = {"量表答卷编号", "题目编号", "题目标题", "(单选/多选）选项", "图片附件编号", "答案", "得分"};
        ScalePaperQuestionListRespVO paperQuestionListRespVO = scalePaperQuestionListRespVOList.get(0);
        String fileName = paperQuestionListRespVO.getScaleId() + "-" + paperQuestionListRespVO.getScaleName() + "-" + nowDateStr + ".xlsx";
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
            col[5] = scalePaperQuestionListRespVO.getContent();
            col[6] = scalePaperQuestionListRespVO.getScore() == null ? "" : String.valueOf(scalePaperQuestionListRespVO.getScore());
        }
        XSSFWorkbook xssfWorkbook = ExcelUtil.getWorkbook(sheetName, header, content);
        saveExcelToDirectory(xssfWorkbook, fileName, directory);
    }

    /**
     * 保存excel到文件路径中
     *
     * @param xssfWorkbook
     * @param fileName
     * @param directory
     */
    private void saveExcelToDirectory(XSSFWorkbook xssfWorkbook, String fileName, String directory) {
        File file = new File(directory);
        if (!file.exists()) {
            file.mkdirs();
        }
        try (
                OutputStream outputStream = new FileOutputStream(directory + File.separator + fileName);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)
        ) {
            xssfWorkbook.write(bufferedOutputStream);
            bufferedOutputStream.flush();
        } catch (Exception e) {
            log.error("保存excel到文件路径失败", e);
            throw new LogicException("下载文件失败");
        }

    }

    /**
     * 导出excel
     *
     * @param response
     * @param filePath
     */
    private void outputExcel(HttpServletResponse response, String filePath) {
        File file = new File(filePath);
        //响应到客户端
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
             InputStream inputStream = new FileInputStream(file);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)
        ) {
            byte[] b = new byte[1024];
            int readLength = bufferedInputStream.read(b);
            while (readLength != -1) {
                bufferedOutputStream.write(b);
                readLength = bufferedInputStream.read(b);
            }
            //设置响应头
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            //刷新输出流
            bufferedOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
