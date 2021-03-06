package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 提交试卷信息dto
 */
@Data
public class ExaminationPaperCommitDTO {
    /**
     * 试卷编号
     */
    @NotBlank(message = "报告表答卷编号不能为空")
    private String examinationPaperId;
    /**
     * 量表ID
     */
    @NotBlank(message = "量表编号不能为空")
    private String scaleId;
    /**
     * 答题时间
     */
    private Double useTime;
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
     * 答案信息
     */
    @Valid
    @NotEmpty(message = "答案信息不能为空")
    private List<CommitAnswerReqDTO> answerList;

}
