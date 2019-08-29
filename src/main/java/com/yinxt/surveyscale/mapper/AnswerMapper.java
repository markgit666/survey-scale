package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.pojo.Answer;

public interface AnswerMapper {

    int insertAnswer(Answer answer);

    int updateAnswer(Answer answer);

    Answer selectAnswer(Answer answer);

}
