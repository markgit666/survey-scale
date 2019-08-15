package com.yinxt.surveyscale.pojo;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
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
     * 01-06 选择题的选项
     */
    private String item_01;
    private String item_02;
    private String item_03;
    private String item_04;
    private String item_05;
    private String item_06;
    /**
     * 选项列表
     */
    private List<Map<String, String>> items;
    /**
     * 附件
     */
    private String attachment;
    /**
     * 状态
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
