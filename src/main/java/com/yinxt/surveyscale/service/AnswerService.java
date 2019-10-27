package com.yinxt.surveyscale.service;

import com.yinxt.surveyscale.mapper.AnswerMapper;
import com.yinxt.surveyscale.entity.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    /**
     * 保存答案
     *
     * @param answer
     * @return
     */
    public void saveAnswer(Answer answer) {
        answerMapper.insertAnswer(answer);
    }

    /**
     * 更新答案
     *
     * @param answer
     */
    public void updateAnswer(Answer answer) {
        answerMapper.updateAnswer(answer);
    }

    /**
     * 查询答案
     *
     * @param answer
     * @return
     */
    public Answer queryAnswer(Answer answer) {
        return answerMapper.selectAnswer(answer);
    }

}
