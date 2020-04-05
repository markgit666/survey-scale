package com.yinxt.surveyscale.vo;

import lombok.Data;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-04-05 13:46
 */
@Data
public class ScalePaperQuestionListRespVO {

    /**
     * 量表答卷编号
     */
    private String scalePaperId;
    /**
     * 量表编号
     */
    private String scaleId;
    /**
     * 量表名称
     */
    private String scaleName;
    /**
     * 题目编号
     */
    private String questionId;
    /**
     * 标题
     */
    private String title;
    /**
     * （单选/多选）选项
     */
    private String items;
    /**
     * 附件
     */
    private String attachment;
    /**
     * 是否记分
     */
    private boolean recordScore;
    /**
     * 分组名称
     */
    private String groupType;
    /**
     * 显示
     */
    private boolean display;
    /**
     * 答案
     */
    private String content;
    /**
     * 得分
     */
    private double score;

}
