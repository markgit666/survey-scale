package com.yinxt.surveyscale.pojo;

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
     * 状态
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 题目列表
     */
    private List<Question> questionList;
}
