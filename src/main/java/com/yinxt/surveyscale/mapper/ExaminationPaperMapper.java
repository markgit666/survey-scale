package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.dto.ExaminationPaperReqDTO;
import com.yinxt.surveyscale.pojo.ExaminationPaper;

import java.util.List;

public interface ExaminationPaperMapper {

    int insertExaminationPaper(ExaminationPaper examinationPaper);

    List<ExaminationPaper> selectExaminationPaperList(ExaminationPaperReqDTO examinationPaperReqDTO);
}
