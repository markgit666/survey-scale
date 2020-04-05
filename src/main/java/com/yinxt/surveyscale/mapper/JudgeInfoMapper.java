package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.entity.JudgeInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 评定信息mapper
 */
@Component
public interface JudgeInfoMapper {

    int insertJudgeInfo(JudgeInfo judgeInfo);

    JudgeInfo selectJudgeInfo(@Param("scalePaperId") String scalePaperId);

    int updateJudgeInfo(JudgeInfo judgeInfo);
}
