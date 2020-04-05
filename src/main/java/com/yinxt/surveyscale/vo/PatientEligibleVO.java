package com.yinxt.surveyscale.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-16 21:22
 */
@Data
public class PatientEligibleVO {
    /**
     * 条件编号
     */
    private String eligibleId;
    /**
     * 组名编号
     */
    private String groupName;
    /**
     * 类型
     */
    private String type;
    /**
     * 内容
     */
    private String content;
    /**
     * 默认答案
     */
    private String defaultAnswer;
    /**
     * 答案
     */
    private String answer;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
