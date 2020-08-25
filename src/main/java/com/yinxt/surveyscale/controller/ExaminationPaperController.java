package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.dto.*;
import com.yinxt.surveyscale.service.ExaminationPaperService;
import com.yinxt.surveyscale.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 答卷controller
 */
@Api(tags = {"examination:"})
@RestController
@RequestMapping(value = "paper")
public class ExaminationPaperController {

    @Autowired
    private ExaminationPaperService examinationPaperService;

    @ApiOperation(value = "被试者和报告表编号状态查询", notes = "被试者和报告表编号状态查询", httpMethod = "POST")
    @RequestMapping(value = "patient/report/status/check")
    public Result checkPatientStatus(@RequestBody @Valid CheckPatientStatusReqDTO checkPatientStatusReqDTO) {
        return Result.success(examinationPaperService.checkReportAndPatientStatus(checkPatientStatusReqDTO.getDoctorId(), checkPatientStatusReqDTO.getReportId(), checkPatientStatusReqDTO.getIdCard()));
    }

    @ApiOperation(value = "获取空报告表试卷", notes = "获取空报告表试卷，只含有编号列表", httpMethod = "POST")
    @RequestMapping(value = "patient/blank/get")
    public Result getBlankExaminationPaper(@RequestBody @Valid BlankExaminationPaperReqDTO blankExaminationPaperReqDTO) {
        return Result.success(examinationPaperService.getBlankExaminationPaper(blankExaminationPaperReqDTO));
    }

    @ApiOperation(value = "随访信息提交", notes = "随访信息提交，非首次答题时填写", httpMethod = "POST")
    @RequestMapping(value = "patient/commitFollowUpInfo")
    public Result commitFollowUpInfo(@RequestBody @Valid FollowUpInfoCommitReqDTO followUpInfoCommitReqDTO) {
        return Result.success(examinationPaperService.commitFollowUpInfo(followUpInfoCommitReqDTO));
    }

    @ApiOperation(value = "修改随访信息", notes = "修改随访信息", httpMethod = "POST")
    @RequestMapping(value = "patient/modifyFollowUpInfo")
    public Result modifyFollowUpInfo(@RequestBody @Valid ModifyFollowUpInfoReqDTO modifyFollowUpInfoReqDTO) {
        examinationPaperService.modifyFollowUpInfo(modifyFollowUpInfoReqDTO);
        return Result.success();
    }

    @ApiOperation(value = "获取量表试卷详情", notes = "获取量表详情", httpMethod = "POST")
    @RequestMapping(value = "patient/scale/get")
    public Result getPaperScaleDetail(@RequestBody @Valid ScaleInfoReqDTO scaleInfoReqDTO) {
        return Result.success(examinationPaperService.getPaperScaleDetail(scaleInfoReqDTO.getScaleId()));
    }

    @ApiOperation(value = "量表答案提交", notes = "被试者提交量表答卷", httpMethod = "POST")
    @RequestMapping(value = "patient/answer/commit")
    public Result commitExaminationPaperAnswer(@RequestBody @Valid ExaminationPaperCommitDTO examinationPaperCommitDTO) {
        return examinationPaperService.commitExaminationPaperAnswer(examinationPaperCommitDTO);
    }

    @ApiOperation(value = "量表答卷评分提交", notes = "评定人对量表答卷进行评分并提交", httpMethod = "POST")
    @RequestMapping(value = "judge/commit")
    public Result commitScalePaperJudge(@RequestBody @Valid ScalePaperJudgeReqDTO scalePaperJudgeReqDTO) {
        return examinationPaperService.commitScalePaperJudge(scalePaperJudgeReqDTO);
    }

    @ApiOperation(value = "查询报告表答卷列表", notes = "查询报告表答卷列表", httpMethod = "POST")
    @RequestMapping(value = "list/get")
    public Result getExaminationPaperList(@RequestBody @Valid ListDataReqDTO<ExaminationPaperListReqDTO> listDataReqDTO) {
        return Result.success(examinationPaperService.getExaminationPaperList(listDataReqDTO));
    }

    @ApiOperation(value = "继续答题,获取量表答卷列表", notes = "继续答题,获取量表答卷列表", httpMethod = "POST")
    @RequestMapping(value = "/patient/continueAnswer")
    public Result continueExamination(@RequestBody @Valid ContinueExaminationReqDTO continueExaminationReqDTO) {
        return Result.success(examinationPaperService.continueExamination(continueExaminationReqDTO));
    }

    @ApiOperation(value = "删除报告表答卷", notes = "删除报告表答卷", httpMethod = "POST")
    @RequestMapping(value = "remove")
    public Result removeExaminationPaper(@RequestBody @Valid RemoveExaminationPaperReqDTO removeExaminationPaperReqDTO) {
        examinationPaperService.removeExaminationPaper(removeExaminationPaperReqDTO.getExaminationPaperId());
        return Result.success();
    }

    @ApiOperation(value = "查询量表答卷列表", notes = "查询量表答卷列表", httpMethod = "POST")
    @RequestMapping(value = "scale/list")
    public Result getScalePaperList(@RequestBody @Valid ListDataReqDTO<ScalePaperListReqDTO> listDataReqDTO) {
        return Result.success(examinationPaperService.getScalePaperListInfo(listDataReqDTO));
    }

    @ApiOperation(value = "获取量表答卷详情", notes = "获取量表答卷详情", httpMethod = "POST")
    @RequestMapping(value = "scale/detail")
    public Result getScalePaperDetailInfo(@RequestBody @Valid ScalePaperDetailReqDTO scalePaperDetailReqDTO) {
        return Result.success(examinationPaperService.getScalePaperDetailInfo(scalePaperDetailReqDTO.getScalePaperId()));
    }


}
