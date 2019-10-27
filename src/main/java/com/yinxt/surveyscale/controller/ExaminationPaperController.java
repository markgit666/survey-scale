package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.dto.*;
import com.yinxt.surveyscale.service.ExaminationPaperService;
import com.yinxt.surveyscale.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 答卷controller
 */
@RestController
@RequestMapping(value = "paper")
public class ExaminationPaperController {

    @Autowired
    private ExaminationPaperService examinationPaperService;

    @RequestMapping(value = "blank/get")
    public Result getBlankExaminationPaper(@RequestBody @Valid BlankExaminationPaperReqDTO blankExaminationPaperReqDTO) throws Exception {
        return examinationPaperService.getBlankExaminationPaper(blankExaminationPaperReqDTO);
    }

    @RequestMapping(value = "answer/commit")
    public Result commitExaminationPaperAnswer(@RequestBody @Valid ExaminationPaperCommitDTO examinationPaperCommitDTO) {
        return examinationPaperService.commitExaminationPaperAnswer(examinationPaperCommitDTO);
    }

    @RequestMapping(value = "judge/commit")
    public Result commitExaminationPaperJudge(@RequestBody @Valid ExaminationPaperJudgeReqDTO examinationPaperJudgeReqDTO) {
        return examinationPaperService.commitExaminationPaperJudge(examinationPaperJudgeReqDTO);
    }

    @RequestMapping(value = "info/get")
    public Result getExaminationPaper(@RequestBody @Valid ExaminationPaperInfoReqDTO examinationPaperInfoReqDTO) {
        return examinationPaperService.getExaminationPaperInfo(examinationPaperInfoReqDTO);
    }

    @RequestMapping(value = "list/get")
    public Result getExaminationPaperList(@RequestBody @Valid ListDataReqDTO<ExaminationPaperListReqDTO> listDataReqDTO) {
        return examinationPaperService.getExaminationPaperList(listDataReqDTO);
    }

}
