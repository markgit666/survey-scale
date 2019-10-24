package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 答卷评定信息请求dto
 */
@Data
public class ExaminationPaperJudgeReqDTO {

    /**
     * 答卷ID
     */
    @NotBlank(message = "答卷id不能为空")
    private String examinationPaperId;
    /**
     * 评定人
     */
    @NotBlank(message = "评定人不能为空")
    private String checkUser;
    /**
     * 总分
     */
    @NotBlank(message = "总分不能为空")
    private int totalScore;
    /**
     * 答案评定信息
     */
    private List<AnswerJudgeDTO> answerJudgeList;
}
