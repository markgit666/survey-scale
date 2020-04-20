package com.yinxt.surveyscale.dto;

import lombok.Data;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-04-04 12:39
 */
@Data
public class OptionInfoDTO {

    /**
     * 选项内容
     */
    private String option;
    /**
     * 选项分数
     */
    private Double optionScore;
}
