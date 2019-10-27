package com.yinxt.surveyscale.po;

import lombok.Data;

/**
 * 答卷PO
 *
 * @author yinxt
 * @version 1.0
 * @date 2019-10-27 17:40
 */
@Data
public class ExaminationPaperPO {
    /**
     * 答卷id
     */
    private String examinationPaperId;
    /**
     * 医生id
     */
    private String doctorId;
}
