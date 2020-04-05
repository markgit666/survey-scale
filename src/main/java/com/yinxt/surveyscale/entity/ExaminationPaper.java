package com.yinxt.surveyscale.entity;

import com.yinxt.surveyscale.vo.ScalePaperListVO;
import lombok.Data;

import java.util.List;

/**
 * 试卷pojo
 */
@Data
public class ExaminationPaper {
    /**
     * 试卷ID
     */
    private String examinationPaperId;
    /**
     * 病人信息
     */
    private PatientInfo patientInfo;
    /**
     * 所包含的量表编号
     */
    private List<String> scaleIdList;
    /**
     * 所包含的量表明细
     */
    private List<ScalePaperListVO> scalePaperListVOList;
}
