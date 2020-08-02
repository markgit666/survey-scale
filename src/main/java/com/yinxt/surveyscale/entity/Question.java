package com.yinxt.surveyscale.entity;

import com.yinxt.surveyscale.dto.OptionInfoDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 题目信息
 */
@Data
public class Question {
    /**
     * 题目ID
     */
    private String questionId;
    /**
     * 量表ID
     */
    private String scaleId;
    /**
     * 题目类型
     */
    private String questionType;
    /**
     * 标题
     */
    private String title;
    /**
     * 选择题的选项拼接到字符串
     */
    private String itemStr;
    /**
     * 选项列表
     */
    private List<OptionInfoDTO> items;
    /**
     * 附件
     */
    private List<String> attachmentList;
    /**
     * 附件字符串
     */
    private String attachment;
    /**
     * 答案信息
     */
    private Answer answer;
    /**
     * 状态
     */
    private String status;
    /**
     * 是否计入总分
     */
    private boolean recordScore;
    /**
     * 分组类别
     */
    private String groupType;
    /**
     * 是否展示
     */
    private String display;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String updateTime;
}
