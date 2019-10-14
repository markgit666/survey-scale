package com.yinxt.surveyscale.service;

import com.yinxt.surveyscale.mapper.JudgeInfoMapper;
import com.yinxt.surveyscale.pojo.JudgeInfo;
import com.yinxt.surveyscale.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JudgeInfoService {

    @Autowired
    private JudgeInfoMapper judgeInfoMapper;
    @Autowired
    private DoctorInfoService doctorInfoService;

    /**
     * 保存评定信息
     *
     * @param judgeInfo
     */
    public void saveJudgeInfo(JudgeInfo judgeInfo) {
        judgeInfo.setJudgeId(RedisUtil.getSequenceId("JG"));
        String doctorId = doctorInfoService.getLoginDoctorId();
        judgeInfo.setCheckUser(doctorId);
        JudgeInfo queryResult = judgeInfoMapper.selectJudgeInfo(judgeInfo);
        if (queryResult != null) {
            judgeInfoMapper.updateJudgeInfo(judgeInfo);
            return;
        }
        judgeInfoMapper.insertJudgeInfo(judgeInfo);
    }

    /**
     * 查询评定信息
     *
     * @param judgeInfo
     * @return
     */
    public JudgeInfo getJudgeInfo(JudgeInfo judgeInfo) {
        return judgeInfoMapper.selectJudgeInfo(judgeInfo);
    }
}
