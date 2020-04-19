package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.entity.Answer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AnswerMapper {

    int insertAnswer(Answer answer);

    int insertAnswerList(@Param("answerList") List<Answer> answerList);

    int updateAnswer(Answer answer);

    Answer selectAnswer(Answer answer);

}
