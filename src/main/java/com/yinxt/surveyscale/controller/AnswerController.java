package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.dto.GetAnswerReqDTO;
import com.yinxt.surveyscale.service.AnswerService;
import com.yinxt.surveyscale.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(value = "answer")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    /**
     *
     */
    @RequestMapping(value = "scale/info/get")
    public Result getScaleInfo(@RequestBody @Valid GetAnswerReqDTO answerReqDTO) throws Exception {
        return answerService.getScaleInfo(answerReqDTO);
    }


}
