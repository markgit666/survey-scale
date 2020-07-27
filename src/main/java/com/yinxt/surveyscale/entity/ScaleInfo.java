package com.yinxt.surveyscale.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 量表信息
 */
@Data
public class ScaleInfo {
    /**
     * 量表ID
     */
    private String scaleId;
    /**
     * 医生ID
     */
    private String doctorId;
    /**
     * 量表名称
     */
    private String scaleName;
    /**
     * 题目顺序
     */
    private String questionSort;
    /**
     * 真实题目数量
     */
    private Integer questionCount;
    /**
     * 状态
     */
    private String status;
    /**
     * 是否完成答题
     */
    private String isAnswer;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 题目列表
     */
    private List<Question> questionList;
}
