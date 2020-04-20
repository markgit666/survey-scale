package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.dto.*;
import com.yinxt.surveyscale.service.ExaminationPaperService;
import com.yinxt.surveyscale.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "patient/report/status/check")
    public Result checkPatientStatus(@RequestBody @Valid CheckPatientStatusReqDTO checkPatientStatusReqDTO) {
        return Result.success(examinationPaperService.checkReportAndPatientStatus(checkPatientStatusReqDTO.getReportId(), checkPatientStatusReqDTO.getIdCard()));
    }

    @RequestMapping(value = "patient/blank/get")
    public Result getBlankExaminationPaper(@RequestBody @Valid BlankExaminationPaperReqDTO blankExaminationPaperReqDTO) {
        return Result.success(examinationPaperService.getBlankExaminationPaper(blankExaminationPaperReqDTO));
    }

    @RequestMapping(value = "patient/scale/get")
    public Result getPaperScaleDetail(@RequestBody ScaleInfoReqDTO scaleInfoReqDTO) {
        return Result.success(examinationPaperService.getPaperScaleDetail(scaleInfoReqDTO.getScaleId()));
    }

    @RequestMapping(value = "patient/answer/commit")
    public Result commitExaminationPaperAnswer(@RequestBody @Valid ExaminationPaperCommitDTO examinationPaperCommitDTO) {
        return examinationPaperService.commitExaminationPaperAnswer(examinationPaperCommitDTO);
    }

    @RequestMapping(value = "judge/commit")
    public Result commitScalePaperJudge(@RequestBody @Valid ScalePaperJudgeReqDTO scalePaperJudgeReqDTO) {
        return examinationPaperService.commitScalePaperJudge(scalePaperJudgeReqDTO);
    }

    @RequestMapping(value = "list/get")
    public Result getExaminationPaperList(@RequestBody @Valid ListDataReqDTO<ExaminationPaperListReqDTO> listDataReqDTO) {
        return Result.success(examinationPaperService.getExaminationPaperList(listDataReqDTO));
    }

    @RequestMapping(value = "scale/list")
    public Result getScalePaperList(@RequestBody @Valid ListDataReqDTO<ScalePaperListReqDTO> listDataReqDTO) {
        return Result.success(examinationPaperService.getScalePaperListInfo(listDataReqDTO));
    }

    @RequestMapping(value = "scale/detail")
    public Result getScalePaperDetailInfo(@RequestBody @Valid ScalePaperDetailReqDTO scalePaperDetailReqDTO) {
        return Result.success(examinationPaperService.getScalePaperDetailInfo(scalePaperDetailReqDTO.getScalePaperId()));
    }

    @RequestMapping(value = "remove")
    public Result removeExaminationPaper(@RequestBody RemoveExaminationPaperReqDTO removeExaminationPaperReqDTO) {
        examinationPaperService.removeExaminationPaper(removeExaminationPaperReqDTO.getExaminationPaperId());
        return Result.success();
    }

}
