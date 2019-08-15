package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.pojo.Question;
import com.yinxt.surveyscale.service.QuestionService;
import com.yinxt.surveyscale.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 题目controller
 */
@RestController
@RequestMapping(value = "question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 添加题目
     *
     * @param question
     * @return
     */
    @RequestMapping(value = "info/add")
    public Result add(@RequestBody @Valid Question question) throws Exception {
        return questionService.addQuestion(question);
    }

}
