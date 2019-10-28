package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.po.ExaminationPaperListQueryPO;
import com.yinxt.surveyscale.po.ExaminationPaperPO;
import com.yinxt.surveyscale.entity.ExaminationPaper;
import com.yinxt.surveyscale.vo.ExaminationPaperListRespVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ExaminationPaperMapper {

    int insertExaminationPaper(ExaminationPaper examinationPaper);

    int updateJudgeStatus(@Param("examinationPaperId") String examinationPaperId);

    List<ExaminationPaper> selectExaminationPaperList(ExaminationPaperListQueryPO examinationPaperListQueryPO);

    ExaminationPaper selectExaminationPaperInfo(ExaminationPaperPO examinationPaperPO);

    List<ExaminationPaperListRespVO> selectExaminationPaperListByIdArray(String[] examinationPaperIdArray, String doctorId);
}
