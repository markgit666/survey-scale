package com.yinxt.surveyscale.service;

import com.alibaba.fastjson.JSON;
import com.yinxt.surveyscale.util.enums.StatusEnum;
import com.yinxt.surveyscale.mapper.QuestionMapper;
import com.yinxt.surveyscale.pojo.Question;
import com.yinxt.surveyscale.util.result.Result;
import com.yinxt.surveyscale.util.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 题目service
 */
@Slf4j
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    /**
     * 添加题目
     *
     * @param question
     */
    public Result addQuestion(Question question) throws Exception {
        try {
            log.info("添加题目：{}", JSON.toJSONString(question));
            if (StringUtils.isBlank(question.getQuestionId())) {
                String questionId = "Q_" + UUID.randomUUID().toString().substring(0, 8);
                question.setQuestionId(questionId);
            }
            questionMapper.insertQuestion(question);
            log.info("添加题目成功");
        } catch (Exception e) {
            log.info("添加题目异常：{}", e);
            throw new Exception("添加题目异常", e);
        }
        return new Result(ResultEnum.SUCCESS);
    }

    /**
     * 删除题目
     *
     * @param scaleId
     */
    public void removeQuestion(String scaleId) {
        Question question = new Question();
        question.setScaleId(scaleId);
        question.setStatus(StatusEnum.NO.getCode());
        questionMapper.updatePatientInfo(question);
    }

    public List<Question> getQuestion(String scaleId) {
        return questionMapper.selectQuestion(scaleId);
    }

}
