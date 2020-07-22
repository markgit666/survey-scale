package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 答卷评定信息请求dto
 */
@Data
public class ScalePaperJudgeReqDTO {

    /**
     * 量表答卷ID
     */
    @NotBlank(message = "量表答卷id不能为空")
    private String scalePaperId;
    /**
     * 评定人
     */
    @NotBlank(message = "评定人不能为空")
    private String checkUser;
    /**
     * 总分
     */
    private double totalScore;
    /**
     * 频率总分
     */
    private Double frequencyTotalScore;
    /**
     * 严重程度总分
     */
    private Double seriousTotalScore;
    /**
     * 频率*严重程度总分
     */
    private Double frequencySeriousTotalScore;
    /**
     * 苦恼程度
     */
    private Double distressTotalScore;
    /**
     * 答案评定信息
     */
    @Valid
    private List<AnswerJudgeDTO> answerJudgeList;
}
