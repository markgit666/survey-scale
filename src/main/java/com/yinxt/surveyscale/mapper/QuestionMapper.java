package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.pojo.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 问题mapper
 */
public interface QuestionMapper {

    int insertQuestion(Question question);

    int updateQuestion(Question question);

    List<Question> selectQuestionList(@Param("scaleId") String scaleId);

    Question selectQuestion(@Param("questionId") String questionId);
}
