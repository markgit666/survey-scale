package com.yinxt.surveyscale.service;

import com.alibaba.fastjson.JSON;
import com.yinxt.surveyscale.common.constant.Constant;
import com.yinxt.surveyscale.common.enums.StatusEnum;
import com.yinxt.surveyscale.mapper.QuestionMapper;
import com.yinxt.surveyscale.entity.Question;
import com.yinxt.surveyscale.common.redis.RedisUtil;
import com.yinxt.surveyscale.common.result.Result;
import com.yinxt.surveyscale.common.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 题目service
 */
@Slf4j
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    /**
     * 保存（新增/修改）题目
     *
     * @param question
     */
    public Result saveQuestion(Question question) throws Exception {
        try {
            //处理选择题
            questionItemFormat(question);
            //处理附件
            questionAttachmentFormat(question);
            String questionId = question.getQuestionId();
            Question checkQuestion = questionMapper.selectQuestion(questionId);
            if (checkQuestion == null) {
                questionId = RedisUtil.getSequenceId("QT");
                question.setQuestionId(questionId);
                log.info("添加题目：{}", JSON.toJSONString(question));
                questionMapper.insertQuestion(question);
                log.info("添加题目成功");
            } else {
                log.info("修改题目：{}", JSON.toJSONString(question));
                questionMapper.updateQuestion(question);
                log.info("修改题目成功");
            }
        } catch (Exception e) {
            log.info("保存题目异常：{}", e);
            throw new Exception("保存题目异常", e);
        }
        return new Result(ResultEnum.SUCCESS);
    }

    /**
     * 选择题选项转换
     *
     * @param question
     */
    public void questionItemFormat(Question question) {
        if (question.getItems() != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map<String, String> itemMap : question.getItems()) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(Constant.ITEMS_SPLIT).append(itemMap.get("option"));
                } else {
                    stringBuilder.append(itemMap.get("option"));
                }
            }
            question.setItemStr(stringBuilder.toString());
        }
    }

    /**
     * 问题附件处理
     *
     * @param question
     */
    public void questionAttachmentFormat(Question question) {
        List<String> attachmentList = question.getAttachmentList();
        if (attachmentList != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String attachment : attachmentList) {
                if (stringBuilder.length() == 0) {
                    stringBuilder.append(attachment);
                } else {
                    stringBuilder.append(Constant.NORMAL_SPLIT).append(attachment);
                }
            }
            question.setAttachment(stringBuilder.toString());
        }
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
        questionMapper.updateQuestion(question);
    }

    /**
     * 获取问题列表
     *
     * @param scaleId
     * @return
     */
    public List<Question> getQuestion(String scaleId) {
        List<Question> questionList = questionMapper.selectQuestionList(scaleId);
        for (Question question : questionList) {
            //封装选项
            formatItems(question);
            //封装附件信息
            formatAttachment(question);
        }
        return questionList;
    }

    /**
     * 封装选项
     */
    public void formatItems(Question question) {
        String itemStr = question.getItemStr();
        if (StringUtils.isNotBlank(itemStr)) {
            String[] itemArray = itemStr.split(Constant.ITEMS_SPLIT);
            List<Map<String, String>> items = new ArrayList<>();
            for (String item : itemArray) {
                Map<String, String> itemMap = new HashMap<>();
                itemMap.put("option", item);
                items.add(itemMap);
            }
            question.setItems(items);
        }
    }

    /**
     * 封装问题附件
     *
     * @param question
     */
    public void formatAttachment(Question question) {
        String attachment = question.getAttachment();
        if (attachment != null) {
            String[] attachmentArray = attachment.split(Constant.NORMAL_SPLIT);
            List<String> attachmentList = new ArrayList<>();
            for (int i = 0; i < attachmentArray.length; i++) {
                attachmentList.add(attachmentArray[i]);
            }
            question.setAttachmentList(attachmentList);
        }
    }

}
