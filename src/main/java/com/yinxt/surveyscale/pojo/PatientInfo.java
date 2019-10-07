package com.yinxt.surveyscale.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 病人信息
 */

@Data
public class PatientInfo {
    /**
     * 病人编号
     */
    private String patientId;
    /**
     * 医生编号
     */
    private String doctorId;
    /**
     * 病人姓名
     */
    private String name;
    /**
     * 生日
     */
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
//    @JSONField(format = "yyyy-MM-dd")
    private Date birthday;
    /**
     * 性别
     */
    private String gender;
    /**
     * 民族
     */
    private String nation;
    /**
     * 婚姻状况
     */
    private String marriageStatus;
    /**
     * 工作类型
     */
    private String workStatus;
    /**
     *
     */
    private String inServiceJob;
    private String educationLevel;
    private Integer educationYears;
    private String familyAddress;
    private String telephoneNumber;
    private String isSnoring;
    private String livingWay;
    private String medicalHistory;
    private String otherMedicalHistory;
    private String smokingHistory;
    /**
     * 每天吸烟数量（支）
     */
    private Integer smokingNumEachDay;
    /**
     * 吸烟年数
     */
    private Integer smokingYears;
    /**
     * 有无精神疾病家族史
     */
    private String isMentalDiseaseFamilyHistory;
    /**
     * 精神疾病家族史
     */
    private String mentalDiseaseFamilyHistory;
    /**
     * 其他精神疾病家族史
     */
    private String otherMentalDiseaseFamilyHistory;
    /**
     * 现病史（有无记忆下降）
     */
    private String currentMedicalHistoryMemoryLoss;
    /**
     * 记忆力下降多久
     */
    private String memoryLossTime;
    /**
     * 体格检查
     */
    private String physicalExamination;
    /**
     * 是否合并使用促认知药物
     */
    private String isUseCognitiveDrugs;
    /**
     * 具体药物及剂量
     */
    private String drugsDosage;
    /**
     * 利手
     */
    private String hand;
    /**
     * 喝酒史
     */
    private String drinkingHistory;
    /**
     * 饮酒类型
     */
    private String drinkingType;
    /**
     * 一天几两
     */
    private String drinkingNumEachDay;
    /**
     * 喝酒年数
     */
    private Integer drinkingYears;
    /**
     * 具体认知药物
     */
    private String drugsType;

    /**
     * 状态（是否有效）
     */
    private String status;

}
