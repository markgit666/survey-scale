package com.yinxt.surveyscale.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 被试者信息
 */

@Data
public class PatientInfo {
    /**
     * 被试者编号
     */
    private String patientId;
    /**
     * 病例号
     */
    private String medicalRecordNum;
    /**
     * 组别
     */
    private String patientGroup;
    /**
     * 医生编号
     */
    private String doctorId;
    /**
     * 被试者姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 生日
     */
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
    /**
     * 受教育程度
     */
    private String educationLevel;
    /**
     * 教育年数
     */
    private String educationYears;
    /**
     * 家庭地址
     */
    private String familyAddress;
    /**
     * 电话
     */
    private String telephoneNumber;
    /**
     * 是否打鼾
     */
    private String isSnoring;
    /**
     * 居住方式
     */
    private String livingWay;
    /**
     * 用药史
     */
    private String medicalHistory;
    /**
     * 其他用药史
     */
    private String otherMedicalHistory;
    /**
     * 抽烟史
     */
    private String smokingHistory;
    /**
     * 每天吸烟数量（支）
     */
    private String smokingNumEachDay;
    /**
     * 吸烟年数
     */
    private String smokingYears;
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
    private String drinkingYears;
    /**
     * 状态（是否有效）
     */
    private String status;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;

}
