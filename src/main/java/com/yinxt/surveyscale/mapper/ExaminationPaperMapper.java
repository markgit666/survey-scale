package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.vo.ScalePaperListVO;
import com.yinxt.surveyscale.entity.Examination;
import com.yinxt.surveyscale.entity.ScalePaperInfo;
import com.yinxt.surveyscale.po.ExaminationPaperListQueryPO;
import com.yinxt.surveyscale.po.ExaminationPaperPO;
import com.yinxt.surveyscale.vo.ExaminationPaperScalesListRespVO;
import com.yinxt.surveyscale.vo.ExaminationPaperListVO;
import com.yinxt.surveyscale.vo.ScalePaperQuestionListRespVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ExaminationPaperMapper {

    int insertScalePaperInfo(ScalePaperInfo scalePaperInfo);

    int updateScalePaperInfo(@Param("scalePaperId") String scalePaperId);

    List<ScalePaperListVO> selectScalePaperList(@Param("examinationPaperId") String examinationPaperId, @Param("doctorId") String doctorId);

    ScalePaperInfo selectScalePaperInfo(@Param("scalePaperId") String scalePaperId);

    int insertExaminationPaper(Examination examination);

    int selectCountByExaminationPaper(@Param("examinationPaperId") String examinationPaperId);

    int updateJudgeStatus(@Param("examinationPaperId") String examinationPaperId);

    List<ExaminationPaperListVO> selectExaminationPaperList(ExaminationPaperListQueryPO examinationPaperListQueryPO);

    Examination selectExaminationPaperInfo(ExaminationPaperPO examinationPaperPO);

    List<ExaminationPaperScalesListRespVO> selectExaminationPaperScaleById(@Param("examinationPaperId") String examinationPaperId, @Param("doctorId") String doctorId);

    String selectReportNameByPaperId(@Param("examinationPaperId") String examinationPaperId);

    List<String> selectAllExaminationPaperScaleId(@Param("doctorId") String doctorId);

    List<ScalePaperQuestionListRespVO> selectScalePaperQuestionById(@Param("scalePaperId") String scalePaperId);

    int deleteExaminationPaperById(@Param("examinationPaperId") String examinationPaperId);

    int deleteScalePaperById(@Param("examinationPaperId") String examinationPaperId);

}
