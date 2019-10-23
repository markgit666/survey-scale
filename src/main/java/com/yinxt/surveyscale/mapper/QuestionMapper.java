package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.pojo.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 问题mapper
 */
@Component
public interface QuestionMapper {

    int insertQuestion(Question question);

    int updateQuestion(Question question);

    List<Question> selectQuestionList(@Param("scaleId") String scaleId);

    Question selectQuestion(@Param("questionId") String questionId);
}
