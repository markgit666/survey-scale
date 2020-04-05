package com.yinxt.surveyscale.entity;

import lombok.Data;

import java.util.Date;

/**
 * 量表答卷信息
 *
 * @author yinxt
 * @version 1.0
 * @date 2020-03-16 18:47
 */
@Data
public class ScalePaperInfo {
    /**
     * 量表答卷编号
     */
    private String scalePaperId;
    /**
     * 报告表编号
     */
    private String paperId;
    /**
     * 量表编号
     */
    private String scaleId;
    /**
     * 用时
     */
    private String useTime;
    /**
     * 评定状态
     */
    private String judgeStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
