package com.yinxt.surveyscale.vo;

import lombok.Data;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-15 16:02
 */
@Data
public class BlankExaminationPaperVO {
    /**
     * 试卷编号
     */
    private String examinationPaperId;
    /**
     * 报告表信息
     */
    private ReportInfoVO reportInfoVO;
}
