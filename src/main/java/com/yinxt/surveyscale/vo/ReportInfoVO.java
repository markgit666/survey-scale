package com.yinxt.surveyscale.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yinxt.surveyscale.entity.ScaleInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-13 00:17
 */
@Data
public class ReportInfoVO {
    /**
     * 报告编号
     */
    private String reportId;
    /**
     * 报告名称
     */
    private String reportName;
    /**
     * 所包含的量表明细
     */
    private List<ScaleInfo> scaleInfoList;
    /**
     * 所包含的量表编号
     */
    private List<String> scaleIdList;
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
