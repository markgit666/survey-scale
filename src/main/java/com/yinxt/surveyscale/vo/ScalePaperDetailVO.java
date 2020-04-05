package com.yinxt.surveyscale.vo;

import com.yinxt.surveyscale.entity.JudgeInfo;
import com.yinxt.surveyscale.entity.ScaleInfo;
import lombok.Data;

import java.util.Date;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-18 19:05
 */
@Data
public class ScalePaperDetailVO {
    /**
     * 量表答卷编号
     */
    private String scalePaperId;
    /**
     * 用时
     */
    private String useTime;
    /**
     * 评定状态
     */
    private String judgeStatus;
    /**
     * 量表信息
     */
    private ScaleInfo scaleInfo;
    /**
     * 评定信息
     */
    private JudgeInfo judgeInfo;
    /**
     * 创建时间
     */
    private Date createTime;
}
