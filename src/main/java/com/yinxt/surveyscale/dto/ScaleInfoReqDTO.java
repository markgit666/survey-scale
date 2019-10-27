package com.yinxt.surveyscale.dto;

import com.yinxt.surveyscale.entity.Question;
import lombok.Data;

import java.util.List;

/**
 * 量表请求DTO
 */
@Data
public class ScaleInfoReqDTO {
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
