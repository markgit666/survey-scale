package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.pojo.JudgeInfo;

/**
 * 评定信息mapper
 */
public interface JudgeInfoMapper {

    int insertJudgeInfo(JudgeInfo judgeInfo);

    JudgeInfo selectJudgeInfo(JudgeInfo judgeInfo);
}
