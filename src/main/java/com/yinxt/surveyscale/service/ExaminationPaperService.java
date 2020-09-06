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
import java.util.ArrayList;
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
     * 答题时获取报告表信息
     *
     * @param reportId
     * @return
     */
    public Report getReportInfo(String reportId) {
        return reportService.getReportById(reportId);
    }

    /**
     * 校验报告表和医生状态
     *
     * @param reportId
     * @param idCard
     * @return
     */
    public PatientInfo checkReportAndPatientStatus(String doctorId, String reportId, String idCard) {
        if (patientInfoService.checkIdCardAlreadyExist(doctorId, idCard)) {
            throw new LogicException("该身份证号已其他医生名下注册！");
        }
        return patientInfoService.getPatientInfoByReportIdAndIdCard(doctorId, reportId, idCard);
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
     * @param patientId
     * @param reportId
     * @return
     */
    public ExaminationPaperVO getBlankExaminationPaper(String patientId, String reportId) {
        String examinationPaperId = createBlankExaminationPaper(patientId, reportId);
        ExaminationPaperVO examinationPaperVO = new ExaminationPaperVO();
        examinationPaperVO.setExaminationPaperId(examinationPaperId);
        //设置报告表信息
        examinationPaperVO.setReportInfoVO(formatScaleAnswerStatus(reportId, examinationPaperId));
        return examinationPaperVO;
    }

    /**
     * 生成空白试卷
     *
     * @param patientId
     * @param reportId
     * @return
     */
    public String createBlankExaminationPaper(String patientId, String reportId) {
        if (reportService.getReportById(reportId) == null) {
            throw new LogicException("报告表不存在");
        }
        //判断当前被试者ID是否存在
        if (patientInfoService.getPatientInfoByReportIdAndPatientId(reportId, patientId) == null) {
            throw new LogicException("被试者编号不存在");
        }
        //生成试卷编号
        String examinationPaperId = RedisUtil.getSequenceId(Constant.EXAMINATION_PREFIX);
        Examination examination = new Examination();
        examination.setExaminationPaperId(examinationPaperId);
        examination.setReportId(reportId);
        examination.setPatientId(patientId);
        examination.setAnswerSequence(1);
        examinationPaperMapper.insertExaminationPaper(examination);
        return examinationPaperId;
    }

    /**
     * 随访信息提交
     *
     * @param followUpInfoCommitReqDTO
     */

    public FollowUpRespDTO commitFollowUpInfo(FollowUpInfoCommitReqDTO followUpInfoCommitReqDTO) {
        Examination examination = new Examination();
        BeanUtils.copyProperties(followUpInfoCommitReqDTO, examination);
        examination.setExaminationPaperId(RedisUtil.getSequenceId(Constant.EXAMINATION_PREFIX));
        int answerCount = examinationPaperMapper.selectCountByReportIdAndPatientId(followUpInfoCommitReqDTO.getReportId(), followUpInfoCommitReqDTO.getPatientId());
        examination.setAnswerSequence(++answerCount);
        examinationPaperMapper.insertExaminationPaper(examination);
        FollowUpRespDTO followUpRespDTO = new FollowUpRespDTO();
        followUpRespDTO.setExaminationPaperId(examination.getExaminationPaperId());
        return followUpRespDTO;
    }

    /**
     * description:修改随访信息
     * <end>
     *
     * @param modifyFollowUpInfoReqDTO
     * @return void
     * @author yinxt
     * @date 2020年08月25日 17:34
     */
    public void modifyFollowUpInfo(ModifyFollowUpInfoReqDTO modifyFollowUpInfoReqDTO) {
        Examination examination = examinationPaperMapper.selectExamination(modifyFollowUpInfoReqDTO.getExaminationPaperId());
        if (examination == null) {
            throw new LogicException("答卷不存在，请输入正确的答卷编号！");
        }
        if (examination.getAnswerSequence() == 1) {
            throw new LogicException("首次答题不存在随访信息！");
        }
        examination.setAdverseReactions(modifyFollowUpInfoReqDTO.getAdverseReactions());
        examination.setMedication(modifyFollowUpInfoReqDTO.getMedication());
        examinationPaperMapper.updateExaminationPaper(examination);
    }

    /**
     * 获取量表明细
     *
     * @param scaleId
     * @return
     */
    public ScaleInfo getPaperScaleDetail(String scaleId) {
        ScaleInfo scaleInfo = scaleInfoService.getScaleInfoById(scaleId);
        return scaleInfoService.getFormatScaleInfo(scaleInfo, true);
    }

    /**
     * 提交答卷
     *
     * @param examinationPaperCommitDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Result commitExaminationPaperAnswer(ExaminationPaperCommitDTO examinationPaperCommitDTO) {
        /**
         * 封装试卷作答记录
         */
        //试卷编号
        String examinationId = examinationPaperCommitDTO.getExaminationPaperId();
        String scaleId = examinationPaperCommitDTO.getScaleId();

        //保存量表答题记录
        ScalePaperInfo scalePaperInfo = new ScalePaperInfo();
        String scalePaperId = RedisUtil.getSequenceId("SP");
        scalePaperInfo.setScalePaperId(scalePaperId);
        scalePaperInfo.setPaperId(examinationId);
        scalePaperInfo.setScaleId(scaleId);
        scalePaperInfo.setUseTime(String.valueOf(examinationPaperCommitDTO.getUseTime()));
        scalePaperInfo.setJudgeStatus("1");
        //保存评定记录信息
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setScalePaperId(scalePaperId);
        BeanUtils.copyProperties(examinationPaperCommitDTO, judgeInfo);
        judgeInfoService.saveJudgeInfo(judgeInfo);

        examinationPaperMapper.insertScalePaperInfo(scalePaperInfo);

        //保存答案
        List<CommitAnswerReqDTO> commitAnswerReqDTOS = examinationPaperCommitDTO.getAnswerList();
        if (commitAnswerReqDTOS != null) {
            List<Answer> answerList = new ArrayList<>();
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
                answer.setInsertContent(commitAnswerReqDTO.getInsertContent());
                answer.setScore(commitAnswerReqDTO.getScore());
                formatAnswerChoose(commitAnswerReqDTO);
                answer.setContent(commitAnswerReqDTO.getContent());
                answerList.add(answer);
            }
            answerService.saveAnswerList(answerList);
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
        BeanUtils.copyProperties(scalePaperJudgeReqDTO, judgeInfo);
        judgeInfoService.saveJudgeInfo(judgeInfo);

        //保存每一题的评分
        List<AnswerJudgeDTO> answerJudgeDTOS = scalePaperJudgeReqDTO.getAnswerJudgeList();
        if (answerJudgeDTOS != null) {
            for (AnswerJudgeDTO answerJudgeDTO : answerJudgeDTOS) {
                Answer answer = new Answer();
                answer.setAnswerId(answerJudgeDTO.getAnswerId());
                answer.setScore(answerJudgeDTO.getScore());
                answer.setQuestionId(answerJudgeDTO.getQuestionId());
                answerService.updateAnswer(answer);
            }
        }
        //更新试卷的评分状态为已评分
        examinationPaperMapper.updateScalePaperJudgeStatus(scalePaperJudgeReqDTO.getScalePaperId());
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
        examinationPaperListVOS.forEach(examinationPaperListVO -> {
            int answerCount = examinationPaperMapper.selectCountScalePaperByPaperId(examinationPaperListVO.getExaminationPaperId());
            examinationPaperListVO.setAnswerNum(answerCount);
            examinationPaperListVO.setIsNeedContinueAnswer(answerCount < examinationPaperListVO.getScaleNum() ? "1" : "0");
        });
        DoctorInfoVO doctorInfoVO = new DoctorInfoVO();
        doctorInfoVO.setDoctorId(doctorId);
        PageInfo pageInfo = new PageInfo(examinationPaperListVOS);
        PageBean pageBean = new PageBean(pageInfo, doctorInfoVO);
        log.info("获取试卷列表成功：{}", JSON.toJSONString(pageBean));
        return pageBean;
    }

    /**
     * 继续答题，获取量表答卷列表
     *
     * @param continueExaminationReqDTO
     */
    public ExaminationPaperVO continueExamination(ContinueExaminationReqDTO continueExaminationReqDTO) {
        String examinationPaperId = continueExaminationReqDTO.getExaminationPaperId();
        ExaminationPaperVO examinationPaperVO = new ExaminationPaperVO();
        examinationPaperVO.setExaminationPaperId(examinationPaperId);
        String reportId = examinationPaperMapper.selectReportIdByPaperId(examinationPaperId);
        //设置报告表信息
        examinationPaperVO.setReportInfoVO(formatScaleAnswerStatus(reportId, examinationPaperId));
        return examinationPaperVO;
    }

    /**
     * description:封装量表答题状态
     * <end>
     *
     * @param reportId
     * @param examinationPaperId
     * @return com.yinxt.surveyscale.vo.ReportInfoVO
     * @author yinxt
     * @date 2020年07月28日 15:23
     */
    public ReportInfoVO formatScaleAnswerStatus(String reportId, String examinationPaperId) {
        ReportInfoVO reportInfoVO = reportService.getReportDetailInfo(reportId, true, false, false);
        List<ScaleInfo> scaleInfoList = reportInfoVO.getScaleInfoList();
        scaleInfoList.forEach(scaleInfo -> {
            int count = examinationPaperMapper.selectCountScalePaperByPaperIdAndScaleId(examinationPaperId, scaleInfo.getScaleId());
            scaleInfo.setIsAnswer(count > 0 ? "1" : "0");
        });
        return reportInfoVO;
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
        List<ScalePaperListVO> scalePaperListVOList = examinationPaperMapper.selectScalePaperList(scalePaperListReqDTO.getExaminationPaperId(), scalePaperListReqDTO.getScaleName(), doctorInfoService.getLoginDoctorId());
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

        ScaleInfo scaleInfo = scaleInfoService.getScaleInfoById(scaleId);
        scaleInfo = scaleInfoService.getFormatScaleInfo(scaleInfo, true);
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
     * 获取医生名下所有的被试者的答卷编号列表
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
     * description:同时删除报告表和量表答卷
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
