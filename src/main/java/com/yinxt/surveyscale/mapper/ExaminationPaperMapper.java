package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.dto.ExaminationPaperReqDTO;
import com.yinxt.surveyscale.pojo.ExaminationPaper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ExaminationPaperMapper {

    int insertExaminationPaper(ExaminationPaper examinationPaper);

    List<ExaminationPaper> selectExaminationPaperList(ExaminationPaperReqDTO examinationPaperReqDTO);
}
