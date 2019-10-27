package com.yinxt.surveyscale.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinxt.surveyscale.dto.*;
import com.yinxt.surveyscale.mapper.ExaminationPaperMapper;
import com.yinxt.surveyscale.po.ExaminationPaperListQueryPO;
import com.yinxt.surveyscale.po.ExaminationPaperPO;
import com.yinxt.surveyscale.entity.*;
import com.yinxt.surveyscale.common.exeption.LogicException;
import com.yinxt.surveyscale.common.page.PageBean;
import com.yinxt.surveyscale.common.redis.RedisUtil;
import com.yinxt.surveyscale.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 获取空试卷
     *
     * @param blankExaminationPaperReqDTO
     * @return
     */
    public Result getBlankExaminationPaper(BlankExaminationPaperReqDTO blankExaminationPaperReqDTO) {
        PatientInfo patientInfo = new PatientInfo();
        //查询病人信息
        patientInfo.setPatientId(blankExaminationPaperReqDTO.getPatientId());
        patientInfo = (PatientInfo) patientInfoService.getPatientInfo(patientInfo).getData();
        //判断当前病人ID是否存在
        if (patientInfo == null) {
            throw new LogicException("输入的病人ID不存在");
        } else {
            ExaminationPaper examinationPaper = new ExaminationPaper();
            //赋值病人信息
            examinationPaper.setPatientInfo(patientInfo);
            ScaleInfo scaleInfo = new ScaleInfo();
            //查询量表信息
            scaleInfo.setScaleId(blankExaminationPaperReqDTO.getScaleId());
            scaleInfo = (ScaleInfo) scaleInfoService.getScaleInfo(scaleInfo).getData();
            //赋值量表信息
            examinationPaper.setScaleInfo(scaleInfo);
            return Result.success(examinationPaper);
        }
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
        ExaminationPaper examinationPaper = new ExaminationPaper();
        examinationPaper.setScaleId(examinationPaperCommitDTO.getScaleId());
        examinationPaper.setPatientId(examinationPaperCommitDTO.getPatientId());
        examinationPaper.setUseTime(examinationPaperCommitDTO.getUseTime());
        examinationPaper.setExaminationPaperId(RedisUtil.getSequenceId("EX"));
        //保存答题记录
        examinationPaperMapper.insertExaminationPaper(examinationPaper);

        List<CommitAnswerReqDTO> commitAnswerReqDTOS = examinationPaperCommitDTO.getAnswerList();
        if (commitAnswerReqDTOS != null) {
            for (CommitAnswerReqDTO commitAnswerReqDTO : commitAnswerReqDTOS) {
                /**
                 * 封装答案信息
                 */
                Answer answer = new Answer();
                answer.setAnswerId(RedisUtil.getSequenceId("AN"));
                answer.setQuestionId(commitAnswerReqDTO.getQuestionId());
                answer.setExaminationPaperId(examinationPaper.getExaminationPaperId());
                answer.setContent(commitAnswerReqDTO.getContent());
                formatAnswerChoose(commitAnswerReqDTO);
                answer.setContent(commitAnswerReqDTO.getContent());
                //保存答案
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
                    stringBuilder.append("|").append(chooseAnswer);
                }
            }
            commitAnswerReqDTO.setContent(stringBuilder.toString());
        }
    }

    /**
     * 提交评定信息
     *
     * @param examinationPaperJudgeReqDTO
     * @return
     */
    @Transactional
    public Result commitExaminationPaperJudge(ExaminationPaperJudgeReqDTO examinationPaperJudgeReqDTO) {
        //保存评定记录信息
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setExaminationPaperId(examinationPaperJudgeReqDTO.getExaminationPaperId());
        judgeInfo.setCheckUser(examinationPaperJudgeReqDTO.getCheckUser());
        judgeInfo.setTotalScore(examinationPaperJudgeReqDTO.getTotalScore());
        judgeInfoService.saveJudgeInfo(judgeInfo);

        //保存每一题的评分
        List<AnswerJudgeDTO> answerJudgeDTOS = examinationPaperJudgeReqDTO.getAnswerJudgeList();
        if (answerJudgeDTOS != null) {
            for (AnswerJudgeDTO answerJudgeDTO : answerJudgeDTOS) {
                Answer answer = new Answer();
                answer.setAnswerId(answerJudgeDTO.getAnswerId());
                answer.setScore(answerJudgeDTO.getScore());
                answerService.updateAnswer(answer);
            }
        }
        //更新试卷的评分状态为已评分
        examinationPaperMapper.updateJudgeStatus(examinationPaperJudgeReqDTO.getExaminationPaperId());
        return Result.success();
    }

    /**
     * 试卷列表
     *
     * @param listDataReqDTO
     * @return
     */
    public Result getExaminationPaperList(ListDataReqDTO<ExaminationPaperListReqDTO> listDataReqDTO) {
        log.info("获取试卷列表参数：{}", JSON.toJSONString(listDataReqDTO));
        ExaminationPaperListReqDTO examinationPaperListReqDTO = listDataReqDTO.getData();
        ExaminationPaperListQueryPO examinationPaperListQueryPO = new ExaminationPaperListQueryPO();
        BeanUtils.copyProperties(examinationPaperListReqDTO, examinationPaperListQueryPO);
        examinationPaperListQueryPO.setDoctorId(doctorInfoService.getLoginDoctorId());
        PageHelper.startPage(listDataReqDTO.getPageNo(), listDataReqDTO.getPageSize());
        List<ExaminationPaper> examinationPaperList = examinationPaperMapper.selectExaminationPaperList(examinationPaperListQueryPO);
        for (ExaminationPaper examinationPaper : examinationPaperList) {
            encapsulationExaminationPaper(examinationPaper);
        }
        PageInfo pageInfo = new PageInfo(examinationPaperList);
        PageBean pageBean = new PageBean(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages(), pageInfo.getTotal(), examinationPaperList);
        log.info("获取试卷列表成功：{}", JSON.toJSONString(pageBean));
        return Result.success(pageBean);
    }

    /**
     * 获取答卷详细
     *
     * @param examinationPaperInfoReqDTO
     * @return
     */
    public Result getExaminationPaperInfo(ExaminationPaperInfoReqDTO examinationPaperInfoReqDTO) {
        ExaminationPaperPO examinationPaperPO = new ExaminationPaperPO();
        examinationPaperPO.setExaminationPaperId(examinationPaperInfoReqDTO.getExaminationPaperId());
        examinationPaperPO.setDoctorId(doctorInfoService.getLoginDoctorId());
        ExaminationPaper examinationPaper = examinationPaperMapper.selectExaminationPaperInfo(examinationPaperPO);
        encapsulationExaminationPaper(examinationPaper);
        return Result.success(examinationPaper);
    }

    /**
     * 封装答卷信息
     *
     * @param examinationPaper
     */
    public void encapsulationExaminationPaper(ExaminationPaper examinationPaper) {
        String patientId = examinationPaper.getPatientId();
        PatientInfo patientInfo = new PatientInfo();
        patientInfo.setPatientId(patientId);
        Result result = patientInfoService.getPatientInfo(patientInfo);
        patientInfo = (PatientInfo) result.getData();
        examinationPaper.setPatientInfo(patientInfo);

        //封装量表及题目信息
        String scaleId = examinationPaper.getScaleId();
        ScaleInfo scaleInfo = new ScaleInfo();
        scaleInfo.setScaleId(scaleId);
        result = scaleInfoService.getScaleInfo(scaleInfo);
        scaleInfo = (ScaleInfo) result.getData();
        examinationPaper.setScaleInfo(scaleInfo);

        //封装评定信息
        String examinationPaperId = examinationPaper.getExaminationPaperId();
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setExaminationPaperId(examinationPaperId);
        judgeInfo = judgeInfoService.getJudgeInfo(judgeInfo);
        examinationPaper.setJudgeInfo(judgeInfo);

        //封装题目答案
        List<Question> questionList = scaleInfo.getQuestionList();
        for (Question question : questionList) {
            Answer answer = new Answer();
            answer.setExaminationPaperId(examinationPaperId);
            String questionId = question.getQuestionId();
            answer.setQuestionId(questionId);
            answer = answerService.queryAnswer(answer);
            question.setAnswer(answer);

            if ("checkBox".equals(question.getQuestionType()) && answer != null) {
                String answerContent = answer.getContent();
                if (answerContent != null) {
                    String[] answerArray = answerContent.split("\\|");
                    answer.setChooseAnswerList(Arrays.asList(answerArray));
                }
            }
        }
    }
}
