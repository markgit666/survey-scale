package com.yinxt.surveyscale.service;

import com.yinxt.surveyscale.mapper.JudgeInfoMapper;
import com.yinxt.surveyscale.entity.JudgeInfo;
import com.yinxt.surveyscale.common.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JudgeInfoService {

    @Autowired
    private JudgeInfoMapper judgeInfoMapper;

    /**
     * 保存评定信息
     *
     * @param judgeInfo
     */
    public void saveJudgeInfo(JudgeInfo judgeInfo) {
        judgeInfo.setJudgeId(RedisUtil.getSequenceId("JG"));
        JudgeInfo queryResult = judgeInfoMapper.selectJudgeInfo(judgeInfo.getScalePaperId());
        if (queryResult != null) {
            judgeInfoMapper.updateJudgeInfo(judgeInfo);
            return;
        }
        judgeInfoMapper.insertJudgeInfo(judgeInfo);
    }

    /**
     * 查询评定信息
     *
     * @param scalePaperId
     * @return
     */
    public JudgeInfo getJudgeInfo(String scalePaperId) {
        return judgeInfoMapper.selectJudgeInfo(scalePaperId);
    }
}
