package com.yinxt.surveyscale.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinxt.surveyscale.dto.ListDataReqDTO;
import com.yinxt.surveyscale.mapper.ScaleInfoMapper;
import com.yinxt.surveyscale.pojo.Question;
import com.yinxt.surveyscale.pojo.ScaleInfo;
import com.yinxt.surveyscale.common.enums.StatusEnum;
import com.yinxt.surveyscale.common.page.PageBean;
import com.yinxt.surveyscale.common.redis.RedisUtil;
import com.yinxt.surveyscale.common.result.Result;
import com.yinxt.surveyscale.common.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 量表service
 */
@Service
@Slf4j
public class ScaleInfoService {

    @Autowired
    private ScaleInfoMapper scaleInfoMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private DoctorInfoService doctorInfoService;

    /**
     * 添加量表
     *
     * @param scaleInfo
     * @return
     */
    @Transactional
    public Result saveScaleInfo(ScaleInfo scaleInfo) {
        log.info("保存量表：请求参数：{}", JSON.toJSONString(scaleInfo));
        try {
            Result result = getScaleInfo(scaleInfo);
            if (result.getData() == null) {
                String scaleId = RedisUtil.getSequenceId("SL");
                scaleInfo.setScaleId(scaleId);
            }
            //doctorId取当前登录人
            String doctorId = doctorInfoService.getLoginDoctorId();
            scaleInfo.setDoctorId(doctorId);
            /**
             * 处理题目
             */
            StringBuffer stringBuffer = new StringBuffer();
            List<Question> questionList = scaleInfo.getQuestionList();
            if (questionList != null) {
                for (Question question : questionList) {
                    question.setScaleId(scaleInfo.getScaleId());
                    //保存题目
                    questionService.saveQuestion(question);
                    //记录题目顺序
                    if (stringBuffer.length() > 0) {
                        stringBuffer.append("%").append(question.getQuestionId());
                    } else {
                        stringBuffer.append(question.getQuestionId());
                    }
                }
            }
            /**
             * 添加量表
             */
            scaleInfo.setQuestionSort(stringBuffer.toString());
            if (result.getData() == null) {
                scaleInfoMapper.insertScaleInfo(scaleInfo);
            } else {
                scaleInfoMapper.updateScaleInfo(scaleInfo);
            }
            log.info("保存量表成功");
        } catch (Exception e) {
            log.info("保存量表异常：{}", e);
            throw new RuntimeException("保存量表异常");
        }
        return new Result(ResultEnum.SUCCESS);
    }

    /**
     * 获取量表信息列表
     *
     * @param listDataReqDTO
     * @return
     */
    public Result getScaleInfoList(ListDataReqDTO<String> listDataReqDTO) {
        try {
            PageHelper.startPage(listDataReqDTO.getPageNo(), listDataReqDTO.getPageSize());
            //查找量表
            String doctorId = doctorInfoService.getLoginDoctorId();
            List<ScaleInfo> scaleInfos = scaleInfoMapper.selectScaleInfoList(listDataReqDTO.getData(), doctorId);
            PageInfo pageInfo = new PageInfo(scaleInfos);
            PageBean pageBean = new PageBean(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages(), pageInfo.getTotal(), scaleInfos);
            return Result.success(pageBean);
        } catch (Exception e) {
            log.error("获取量表信息列表异常", e);
            return new Result().error();
        }
    }

    /**
     * 获取量表详细信息
     *
     * @param scaleInfo
     * @return
     */
    @Transactional
    public Result getScaleInfo(ScaleInfo scaleInfo) {
        ScaleInfo info = scaleInfoMapper.selectScaleInfo(scaleInfo.getScaleId());
        //若找不到量表信息
        if (info == null) {
            return Result.error();
        }
        /**
         * 查找每张量表的题目
         */
        List<Question> questionList = questionService.getQuestion(scaleInfo.getScaleId());

        //题目顺序列表
        List<Question> questionSortList = new ArrayList<>();
        String[] questionIdSortArray = info.getQuestionSort().split("%");
        //如果题目顺序列表不为空则对题目进行排序
        if (questionIdSortArray.length > 0) {
            for (int i = 0; i < questionIdSortArray.length; i++) {
                for (Question question : questionList) {
                    if (StringUtils.equals(questionIdSortArray[i], question.getQuestionId())) {
                        questionSortList.add(question);
                        break;
                    }
                }
            }
            info.setQuestionList(questionSortList);
        }
        log.info("返回量表信息：{}", JSON.toJSONString(info));
        return Result.success(info);
    }

    /**
     * 删除量表
     *
     * @param scaleInfo
     * @return
     */
    public Result removeScaleInfo(ScaleInfo scaleInfo) {
        log.info("删除量表，请求参数：{}", JSON.toJSONString(scaleInfo));

        try {
            if (scaleInfo != null && StringUtils.isNotBlank(scaleInfo.getScaleId())) {
                scaleInfo.setStatus(StatusEnum.NO.getCode());
                /**
                 * 删除量表
                 */
                scaleInfoMapper.updateScaleInfo(scaleInfo);

                /**
                 * 删除题目
                 */
                questionService.removeQuestion(scaleInfo.getScaleId());
            }
        } catch (Exception e) {
            log.error("删除量表异常：", e);
            return Result.error();
        }
        log.info("删除量表成功");
        return Result.success();
    }


}
