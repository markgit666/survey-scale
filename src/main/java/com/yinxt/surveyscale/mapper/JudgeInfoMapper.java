package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.entity.JudgeInfo;
import org.springframework.stereotype.Component;

/**
 * 评定信息mapper
 */
@Component
public interface JudgeInfoMapper {

    int insertJudgeInfo(JudgeInfo judgeInfo);

    JudgeInfo selectJudgeInfo(JudgeInfo judgeInfo);

    int updateJudgeInfo(JudgeInfo judgeInfo);
}
