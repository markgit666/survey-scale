package com.yinxt.surveyscale.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinxt.surveyscale.dto.ListDataReqDTO;
import com.yinxt.surveyscale.mapper.ScaleInfoMapper;
import com.yinxt.surveyscale.pojo.Question;
import com.yinxt.surveyscale.pojo.ScaleInfo;
import com.yinxt.surveyscale.util.enums.StatusEnum;
import com.yinxt.surveyscale.util.page.PageBean;
import com.yinxt.surveyscale.util.result.Result;
import com.yinxt.surveyscale.util.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private PatientInfoService patientInfoService;

    /**
     * 添加量表
     *
     * @param scaleInfo
     * @return
     */
    @Transactional
    public Result saveScaleInfo(ScaleInfo scaleInfo) {
        log.info("添加量表：请求参数：{}", JSON.toJSONString(scaleInfo));
        try {
            String scaleId = "S_" + UUID.randomUUID().toString().substring(0, 8);
            scaleInfo.setScaleId(scaleId);
            /**
             * 处理题目
             */
            StringBuffer stringBuffer = new StringBuffer();
            List<Question> questionList = scaleInfo.getQuestionList();
            if (questionList != null) {
                for (Question question : questionList) {

                    question.setScaleId(scaleInfo.getScaleId());

                    questionService.addQuestion(question);
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
            scaleInfoMapper.insertScaleInfo(scaleInfo);

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
            List<ScaleInfo> scaleInfos = scaleInfoMapper.selectScaleInfoList(listDataReqDTO.getData());
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
        } else {
            info.setQuestionList(questionList);
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
