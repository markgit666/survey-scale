package com.yinxt.surveyscale.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinxt.surveyscale.common.constant.Constant;
import com.yinxt.surveyscale.common.exeption.LogicException;
import com.yinxt.surveyscale.common.page.PageBean;
import com.yinxt.surveyscale.common.redis.RedisUtil;
import com.yinxt.surveyscale.common.result.Result;
import com.yinxt.surveyscale.common.util.RSAUtil;
import com.yinxt.surveyscale.dto.*;
import com.yinxt.surveyscale.entity.*;
import com.yinxt.surveyscale.mapper.ExaminationPaperMapper;
import com.yinxt.surveyscale.po.ExaminationPaperListQueryPO;
import com.yinxt.surveyscale.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

/**
 * 试卷service
 */
@Slf4j
@Service
public class ExaminationPaperService {
    @Autowired
    private PatientInfoService patientInfoService;
    @Autowired
    private ScaleInfoService scaleInfoService;
    @Autowired
    private ExaminationPaperMapper examinationPaperMapper;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private JudgeInfoService judgeInfoService;
    @Autowired
    private DoctorInfoService doctorInfoService;
    @Autowired
    private ReportService reportService;
    @Value("${rsa.key.private}")
    private String privateKey;

    /**
     * 校验报告表和医生状态
     *
     * @param reportId
     * @param idCard
     * @return
     */
    public PatientInfo checkReportAndPatientStatus(String reportId, String idCard) {
        String decryptReportId = checkReportId(reportId);
        return patientInfoService.getPatientInfoByReportIdAndIdCard(decryptReportId, idCard);
    }

    /**
     * 检验报告表编号
     *
     * @param reportId
     */
    public String checkReportId(String reportId) {
        try {
            reportId = URLDecoder.decode(reportId, "UTF-8").replace(" ", "+");
            reportId = RSAUtil.decrypt(reportId, privateKey);
        } catch (Exception e) {
            log.error("报告表id解密失败：", e);
            throw new LogicException("报告表不存在");
        }
        if (reportService.getReportById(reportId) == null) {
            throw new LogicException("报告表不存在");
        }
        return reportId;
    }

    /**
     * 获取空试卷
     *
     * @param blankExaminationPaperReqDTO
     * @return
     */
    public BlankExaminationPaperVO getBlankExaminationPaper(BlankExaminationPaperReqDTO blankExaminationPaperReqDTO) {
        String encryptReportId = blankExaminationPaperReqDTO.getReportId();
        String patientId = blankExaminationPaperReqDTO.getPatientId();
        String reportId = checkReportId(encryptReportId);
        //查询病人信息
        PatientInfo patientInfo = patientInfoService.getPatientInfoByReportIdAndPatientId(reportId, patientId);
        //判断当前病人ID是否存在
        if (patientInfo == null) {
            throw new LogicException("被试者编号不存在");
        }
        //生成试卷编号
        String examinationPaperId = RedisUtil.getSequenceId("EX");
        BlankExaminationPaperVO blankExaminationPaperVO = new BlankExaminationPaperVO();
        blankExaminationPaperVO.setExaminationPaperId(examinationPaperId);
        //设置报告表信息
        ReportInfoVO reportInfoVO = reportService.getReportDetailInfo(reportId, false);
        blankExaminationPaperVO.setReportInfoVO(reportInfoVO);
        return blankExaminationPaperVO;
    }

    /**
     * 获取量表明细
     *
     * @param scaleId
     * @return
     */
    public ScaleInfo getPaperScaleDetail(String scaleId) {
        return scaleInfoService.getFormatScaleInfo(scaleId);
    }

    /**
     * 提交答卷
     *
     * @param examinationPaperCommitDTO
     * @return
     */
    @Transactional
    public Result commitExaminationPaperAnswer(ExaminationPaperCommitDTO examinationPaperCommitDTO) {
        /**
         * 封装试卷作答记录
         */
        String reportId;
        try {
            String decryptReportId = URLDecoder.decode(examinationPaperCommitDTO.getReportId(), "UTF-8");
            String target = decryptReportId.replace(" ", "+");
            reportId = RSAUtil.decrypt(target, privateKey);
        } catch (Exception e) {
            log.error("解密失败：{}", e);
            throw new LogicException("答卷保存失败");
        }
        //试卷编号
        String examinationId = examinationPaperCommitDTO.getExaminationPaperId();
        //被试者编号
        String patientId = examinationPaperCommitDTO.getPatientId();
        String scaleId = examinationPaperCommitDTO.getScaleId();
        Examination examination = new Examination();
        examination.setExaminationPaperId(examinationId);
        examination.setPatientId(patientId);
        examination.setReportId(reportId);
        //保存报告表答题记录
        if (examinationPaperMapper.selectCountByExaminationPaper(examinationId) == 0) {
            examinationPaperMapper.insertExaminationPaper(examination);
        }

        //保存量表答题记录
        ScalePaperInfo scalePaperInfo = new ScalePaperInfo();
        String scalePaperId = RedisUtil.getSequenceId("SP");
        scalePaperInfo.setScalePaperId(scalePaperId);
        scalePaperInfo.setPaperId(examinationId);
        scalePaperInfo.setScaleId(scaleId);
        scalePaperInfo.setUseTime(String.valueOf(examinationPaperCommitDTO.getUseTime()));
        if (examinationPaperCommitDTO.getTotalScore() > 0) {
            scalePaperInfo.setJudgeStatus("1");
            //保存评定记录信息
            JudgeInfo judgeInfo = new JudgeInfo();
            judgeInfo.setScalePaperId(scalePaperId);
            judgeInfo.setCheckUser(examinationPaperCommitDTO.getCheckUser());
            judgeInfo.setTotalScore(examinationPaperCommitDTO.getTotalScore());
            judgeInfoService.saveJudgeInfo(judgeInfo);

        }
        examinationPaperMapper.insertScalePaperInfo(scalePaperInfo);

        //保存答案
        List<CommitAnswerReqDTO> commitAnswerReqDTOS = examinationPaperCommitDTO.getAnswerList();
        if (commitAnswerReqDTOS != null) {
            for (CommitAnswerReqDTO commitAnswerReqDTO : commitAnswerReqDTOS) {
                /**
                 * 封装答案信息
                 */
                Answer answer = new Answer();
                String questionId = commitAnswerReqDTO.getQuestionId();
                String answerId = RedisUtil.getSequenceId("AN");
                answer.setAnswerId(answerId);
                answer.setQuestionId(questionId);
                answer.setScalePaperId(scalePaperInfo.getScalePaperId());
                answer.setContent(commitAnswerReqDTO.getContent());
                answer.setScore(commitAnswerReqDTO.getScore());
                formatAnswerChoose(commitAnswerReqDTO);
                answer.setContent(commitAnswerReqDTO.getContent());
                answerService.saveAnswer(answer);
            }
        }
        return Result.success();
    }

    /**
     * 处理选择题答案选项
     *
     * @param commitAnswerReqDTO
     */
    public void formatAnswerChoose(CommitAnswerReqDTO commitAnswerReqDTO) {
        List<String> chooseAnswerList = commitAnswerReqDTO.getChooseAnswerList();
        if (chooseAnswerList != null && chooseAnswerList.size() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String chooseAnswer : chooseAnswerList) {
                if (stringBuilder.length() == 0) {
                    stringBuilder.append(chooseAnswer);
                } else {
                    stringBuilder.append(Constant.ITEMS_SPLIT).append(chooseAnswer);
                }
            }
            commitAnswerReqDTO.setContent(stringBuilder.toString());
        }
    }

    /**
     * 提交评定信息
     *
     * @param scalePaperJudgeReqDTO
     * @return
     */
    @Transactional
    public Result commitScalePaperJudge(ScalePaperJudgeReqDTO scalePaperJudgeReqDTO) {
        //保存评定记录信息
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setScalePaperId(scalePaperJudgeReqDTO.getScalePaperId());
        judgeInfo.setCheckUser(scalePaperJudgeReqDTO.getCheckUser());
        judgeInfo.setTotalScore(scalePaperJudgeReqDTO.getTotalScore());
        judgeInfoService.saveJudgeInfo(judgeInfo);

        //保存每一题的评分
        List<AnswerJudgeDTO> answerJudgeDTOS = scalePaperJudgeReqDTO.getAnswerJudgeList();
        if (answerJudgeDTOS != null) {
            for (AnswerJudgeDTO answerJudgeDTO : answerJudgeDTOS) {
                Answer answer = new Answer();
                answer.setAnswerId(answerJudgeDTO.getAnswerId());
                answer.setScore(answerJudgeDTO.getScore());
                answerService.updateAnswer(answer);
            }
        }
        //更新试卷的评分状态为已评分
        examinationPaperMapper.updateScalePaperInfo(scalePaperJudgeReqDTO.getScalePaperId());
        return Result.success();
    }

    /**
     * 获取报告表试卷列表
     *
     * @param listDataReqDTO
     * @return
     */
    public PageBean getExaminationPaperList(ListDataReqDTO<ExaminationPaperListReqDTO> listDataReqDTO) {
        log.info("获取试卷列表参数：{}", JSON.toJSONString(listDataReqDTO));
        ExaminationPaperListReqDTO examinationPaperListReqDTO = listDataReqDTO.getData();
        ExaminationPaperListQueryPO examinationPaperListQueryPO = new ExaminationPaperListQueryPO();
        if (examinationPaperListReqDTO != null) {
            BeanUtils.copyProperties(examinationPaperListReqDTO, examinationPaperListQueryPO);
        }
        String doctorId = doctorInfoService.getLoginDoctorId();
        examinationPaperListQueryPO.setDoctorId(doctorId);

        PageHelper.startPage(listDataReqDTO.getPageNo(), listDataReqDTO.getPageSize());
        List<ExaminationPaperListVO> examinationPaperListVOS = examinationPaperMapper.selectExaminationPaperList(examinationPaperListQueryPO);
        DoctorInfoVO doctorInfoVO = new DoctorInfoVO();
        doctorInfoVO.setDoctorId(doctorId);
        PageInfo pageInfo = new PageInfo(examinationPaperListVOS);
        PageBean pageBean = new PageBean(pageInfo, doctorInfoVO);
        log.info("获取试卷列表成功：{}", JSON.toJSONString(pageBean));
        return pageBean;
    }

    /**
     * 获取量表答卷列表
     *
     * @param listDataReqDTO
     * @return
     */
    public PageBean getScalePaperListInfo(ListDataReqDTO<ScalePaperListReqDTO> listDataReqDTO) {
        ScalePaperListReqDTO scalePaperListReqDTO = listDataReqDTO.getData();
        if (scalePaperListReqDTO == null) {
            throw new LogicException("报告表答卷编号不能为空");
        }
        PageHelper.startPage(listDataReqDTO.getPageNo(), listDataReqDTO.getPageSize());
        List<ScalePaperListVO> scalePaperListVOList = examinationPaperMapper.selectScalePaperList(scalePaperListReqDTO.getExaminationPaperId(), doctorInfoService.getLoginDoctorId());
        PageInfo pageInfo = new PageInfo(scalePaperListVOList);
        PageBean pageBean = new PageBean(pageInfo);
        return pageBean;
    }

    /**
     * 获取量表答卷详情
     *
     * @param scalePaperId
     * @return
     */
    public ScalePaperDetailVO getScalePaperDetailInfo(String scalePaperId) {
        ScalePaperInfo scalePaperInfo = examinationPaperMapper.selectScalePaperInfo(scalePaperId);
        ScalePaperDetailVO scalePaperDetailVO = new ScalePaperDetailVO();
        if (scalePaperInfo == null) {
            return scalePaperDetailVO;
        }
        BeanUtils.copyProperties(scalePaperInfo, scalePaperDetailVO);
        //量表信息
        String scaleId = scalePaperInfo.getScaleId();
        ScaleInfo scaleInfo = scaleInfoService.getFormatScaleInfo(scaleId);
        scalePaperDetailVO.setScaleInfo(scaleInfo);
        //封装题目答案
        List<Question> questionList = scaleInfo.getQuestionList();
        for (Question question : questionList) {
            Answer answer = new Answer();
            answer.setScalePaperId(scalePaperId);
            String questionId = question.getQuestionId();
            answer.setQuestionId(questionId);
            answer = answerService.queryAnswer(answer);
            question.setAnswer(answer);

            if ("checkBox".equals(question.getQuestionType()) && answer != null) {
                String answerContent = answer.getContent();
                if (answerContent != null) {
                    String[] answerArray = answerContent.split(Constant.ITEMS_SPLIT);
                    answer.setChooseAnswerList(Arrays.asList(answerArray));
                }
            }
        }
        //评定信息
        JudgeInfo judgeInfo = judgeInfoService.getJudgeInfo(scalePaperId);
        scalePaperDetailVO.setJudgeInfo(judgeInfo);
        return scalePaperDetailVO;
    }

    /**
     * 通过报告表答卷编号获取报告表名称
     *
     * @param examinationPaperId
     * @return
     */
    public String getReportNameByPaperId(String examinationPaperId) {
        return examinationPaperMapper.selectReportNameByPaperId(examinationPaperId);
    }


    /**
     * 通过答卷编号获取量表信息
     *
     * @param examinationPaperId
     * @return
     */
    public List<ExaminationPaperScalesListRespVO> getExaminationPaperScaleListById(String examinationPaperId, String doctorId) {
        return examinationPaperMapper.selectExaminationPaperScaleById(examinationPaperId, doctorId);
    }

    /**
     * 获取医生名下所有的病人的答卷编号列表
     *
     * @return
     */
    public List<String> getAllExaminationPaperIdList(String doctorId) {
        return examinationPaperMapper.selectAllExaminationPaperScaleId(doctorId);
    }

    /**
     * 通过量表答卷编号获取题目信息
     *
     * @param scalePaperId
     * @return
     */
    public List<ScalePaperQuestionListRespVO> getScalePaperQuestionListById(String scalePaperId) {
        return examinationPaperMapper.selectScalePaperQuestionById(scalePaperId);
    }

    /**
     * description:同事删除报告表和量表答卷
     * <end>
     *
     * @param examinationPaperId
     * @return void
     * @author yinxt
     * @date 2020年04月20日 18:53
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void removeExaminationPaper(String examinationPaperId) {
        examinationPaperMapper.deleteExaminationPaperById(examinationPaperId);
        examinationPaperMapper.deleteScalePaperById(examinationPaperId);
    }

}
