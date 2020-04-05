package com.yinxt.surveyscale.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-16 21:08
 */
@Data
public class PatientEligible {
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
