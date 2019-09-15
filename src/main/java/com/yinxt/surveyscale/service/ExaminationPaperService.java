package com.yinxt.surveyscale.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinxt.surveyscale.dto.*;
import com.yinxt.surveyscale.mapper.ExaminationPaperMapper;
import com.yinxt.surveyscale.pojo.*;
import com.yinxt.surveyscale.util.page.PageBean;
import com.yinxt.surveyscale.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

    /**
     * 获取空试卷
     *
     * @param examinationPaperReqDTO
     * @return
     */
    public Result getBlankExaminationPaper(ExaminationPaperReqDTO examinationPaperReqDTO) throws Exception {
        PatientInfo patientInfo = new PatientInfo();
        //查询病人信息
        patientInfo.setPatientId(examinationPaperReqDTO.getPatientId());
        patientInfo = (PatientInfo) patientInfoService.getPatientInfo(patientInfo).getData();
        //判断当前病人ID是否存在
        if (patientInfo == null) {
            throw new Exception("输入的病人ID不存在");
        } else {
            ExaminationPaper examinationPaper = new ExaminationPaper();
            //赋值病人信息
            examinationPaper.setPatientInfo(patientInfo);
            ScaleInfo scaleInfo = new ScaleInfo();
            //查询量表信息
            scaleInfo.setScaleId(examinationPaperReqDTO.getScaleId());
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
        examinationPaper.setExaminationPaperId("E_" + UUID.randomUUID().toString().substring(0, 8));
        //保存答题记录
        examinationPaperMapper.insertExaminationPaper(examinationPaper);

        List<CommitAnswerReqDTO> commitAnswerReqDTOS = examinationPaperCommitDTO.getAnswerList();
        if (commitAnswerReqDTOS != null) {
            for (CommitAnswerReqDTO commitAnswerReqDTO : commitAnswerReqDTOS) {
                /**
                 * 封装答案信息
                 */
                Answer answer = new Answer();
                answer.setAnswerId("A_" + UUID.randomUUID().toString().substring(0, 8));
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
        return Result.success();
    }

    /**
     * 试卷列表
     *
     * @param listDataReqDTO
     * @return
     */
    public Result getExaminationPaperList(ListDataReqDTO<ExaminationPaperReqDTO> listDataReqDTO) {
        log.info("获取试卷列表参数：{}", JSON.toJSONString(listDataReqDTO));
        PageHelper.startPage(listDataReqDTO.getPageNo(), listDataReqDTO.getPageSize());
        List<ExaminationPaper> examinationPaperList = examinationPaperMapper.selectExaminationPaperList(listDataReqDTO.getData());
        for (ExaminationPaper examinationPaper : examinationPaperList) {
            //封装病人信息
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

                if ("checkBox".equals(question.getQuestionType())) {
                    String answerContent = answer.getContent();
                    if (answerContent != null) {
                        String[] answerArray = answerContent.split("\\|");
                        answer.setChooseAnswerList(Arrays.asList(answerArray));
                    }
                }
            }

        }
        PageInfo pageInfo = new PageInfo(examinationPaperList);
        PageBean pageBean = new PageBean(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages(), pageInfo.getTotal(), examinationPaperList);
        log.info("获取试卷列表成功：{}", JSON.toJSONString(pageBean));
        return Result.success(pageBean);
    }
}
