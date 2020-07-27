-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 121.199.56.115    Database: survey_scale
-- ------------------------------------------------------
-- Server version	5.7.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_answer`
--

CREATE SCHEMA `survey_scale` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;

USE survey_scale;

DROP TABLE IF EXISTS `tb_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `answer_id` varchar(45) NOT NULL,
  `scale_paper_id` varchar(45) DEFAULT NULL COMMENT '量表答卷id',
  `question_id` varchar(45) NOT NULL,
  `content` varchar(225) NOT NULL,
  `score` double DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `answer_id_UNIQUE` (`answer_id`),
  KEY `idx_question_id` (`question_id`),
  KEY `idx_scale_paper_id` (`scale_paper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_doctor`
--

DROP TABLE IF EXISTS `tb_doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_doctor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doctor_id` varchar(45) NOT NULL,
  `login_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `salt` varchar(45) NOT NULL,
  `doctor_name` varchar(45) DEFAULT NULL,
  `identity` varchar(8) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `doctor_id_UNIQUE` (`doctor_id`),
  KEY `idx_login_name` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_doctor_report`
--

DROP TABLE IF EXISTS `tb_doctor_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_doctor_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `doctor_id` varchar(45) DEFAULT NULL COMMENT '医生编号',
  `report_id` varchar(45) DEFAULT NULL COMMENT '报告编号',
  `status` varchar(45) DEFAULT '1' COMMENT '状态 1-有效 0-无效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_eligible`
--

DROP TABLE IF EXISTS `tb_eligible`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_eligible` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `eligible_id` varchar(45) NOT NULL COMMENT '条件编号',
  `group_name` varchar(45) DEFAULT NULL COMMENT '组名（AD/MCI/EXP）',
  `type` varchar(45) DEFAULT NULL COMMENT '标准类型（入组标准join/排除标准except）',
  `content` varchar(1024) DEFAULT NULL COMMENT '条件内容',
  `default_answer` varchar(8) DEFAULT NULL COMMENT '默认答案',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_eligible_eligible_id_uindex` (`eligible_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='参与实验的条件';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_eligible_patient`
--

DROP TABLE IF EXISTS `tb_eligible_patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_eligible_patient` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `eligible_id` varchar(45) NOT NULL COMMENT '条件编号',
  `patient_id` varchar(45) NOT NULL COMMENT '病人编号',
  `answer` varchar(8) DEFAULT NULL COMMENT '条件答案',
  `remarks` varchar(1024) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_eligible_id` (`eligible_id`),
  KEY `idx_patient_id` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='病人实验条件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_examination_paper`
--

DROP TABLE IF EXISTS `tb_examination_paper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_examination_paper` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `examination_paper_id` varchar(45) NOT NULL COMMENT '答卷编号',
  `patient_id` varchar(45) NOT NULL COMMENT '病人编号',
  `report_id` varchar(45) NOT NULL COMMENT '报告编号',
  `effective_status` tinyint(4) DEFAULT '1',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `examination_papaer_id_UNIQUE` (`examination_paper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_file`
--

DROP TABLE IF EXISTS `tb_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_no` varchar(45) NOT NULL,
  `file_name` varchar(45) NOT NULL,
  `file_type` tinyint(2) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `file_no_UNIQUE` (`file_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_judge`
--

DROP TABLE IF EXISTS `tb_judge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_judge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `judge_id` varchar(45) NOT NULL,
  `scale_paper_id` varchar(45) NOT NULL,
  `check_user` varchar(45) NOT NULL,
  `check_time` datetime NOT NULL,
  `total_score` varchar(45) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `judge_id_UNIQUE` (`judge_id`),
  UNIQUE KEY `scale_paper_id_UNIQUE` (`scale_paper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_patient`
--

DROP TABLE IF EXISTS `tb_patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_patient` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `patient_id` varchar(45) NOT NULL COMMENT '用户编号',
  `doctor_id` varchar(24) NOT NULL COMMENT '医生ID',
  `patient_name` varchar(16) NOT NULL COMMENT '姓名',
  `id_card` varchar(45) DEFAULT NULL COMMENT '身份证号',
  `gender` tinyint(2) NOT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `telephone_number` varchar(24) DEFAULT NULL COMMENT '手机号码',
  `family_address` varchar(128) DEFAULT NULL COMMENT '地址',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '用户状态 有效：1；无效：0',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `nation` varchar(8) DEFAULT NULL COMMENT '名族',
  `marriage_status` varchar(8) DEFAULT NULL COMMENT '婚姻状况',
  `work_status` varchar(8) DEFAULT NULL COMMENT '工作类型',
  `in_service_job` varchar(8) DEFAULT NULL,
  `education_level` varchar(8) DEFAULT NULL COMMENT '学历',
  `education_years` varchar(45) DEFAULT NULL COMMENT '受教育年数',
  `is_snoring` varchar(8) DEFAULT NULL COMMENT '是否打鼾',
  `living_way` varchar(45) DEFAULT NULL COMMENT '居住方式',
  `medical_history` varchar(45) DEFAULT NULL COMMENT '用药史',
  `other_medical_history` varchar(45) DEFAULT NULL COMMENT '其用药史',
  `smoking_history` varchar(45) DEFAULT NULL COMMENT '吸烟史',
  `smoking_num_eachday` varchar(45) DEFAULT NULL COMMENT '每天吸烟数量（支）',
  `smoking_years` varchar(45) DEFAULT NULL COMMENT '吸烟年数',
  `is_mental_disease_family_history` varchar(45) DEFAULT NULL COMMENT '有无精神疾病家族史',
  `mental_disease_family_history` varchar(45) DEFAULT NULL COMMENT '精神疾病家族史',
  `other_mental_disease_family_history` varchar(45) DEFAULT NULL COMMENT '其他家族精神病史',
  `current_medical_history_memory_loss` varchar(45) DEFAULT NULL COMMENT '现病史（有无记忆下降）',
  `memory_loss_time` varchar(45) DEFAULT NULL COMMENT '记忆力下降多久',
  `physical_examination` varchar(45) DEFAULT NULL COMMENT '体格检查',
  `is_use_cognitive_drugs` varchar(45) DEFAULT NULL COMMENT '是否合并使用促认知药物',
  `drugs_dosage` varchar(45) DEFAULT NULL COMMENT '具体药物及剂量',
  `hand` varchar(45) DEFAULT NULL COMMENT '利手',
  `drinking_history` varchar(45) DEFAULT NULL COMMENT '喝酒史',
  `drinking_type` varchar(45) DEFAULT NULL COMMENT '饮酒类型',
  `drinking_num_eachday` varchar(45) DEFAULT NULL COMMENT '每天饮酒量（两）',
  `drinking_years` varchar(45) DEFAULT NULL COMMENT '喝酒年数',
  `drugs_type` varchar(45) DEFAULT NULL COMMENT '具体认知药物',
  `sign_status` varchar(8) DEFAULT '0' COMMENT '是否签约合同',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_UNIQUE` (`patient_id`),
  KEY `idx_doctor_id` (`doctor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='病人信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_question`
--

DROP TABLE IF EXISTS `tb_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `question_id` varchar(45) NOT NULL COMMENT '问题id',
  `scale_id` varchar(45) NOT NULL COMMENT '量表id',
  `question_type` varchar(24) NOT NULL COMMENT '问题类型',
  `title` text COMMENT '标题',
  `items` varchar(512) DEFAULT NULL COMMENT '选择题选项',
  `attachment` varchar(512) DEFAULT NULL COMMENT '附件',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态',
  `record_score` tinyint(1) DEFAULT '1' COMMENT '是否记录总分',
  `group_type` varchar(8) DEFAULT NULL COMMENT '分组类型',
  `display` tinyint(1) DEFAULT '1' COMMENT '是否展示',
  `insert_content` varchar(256) DEFAULT NULL COMMENT '插入内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `question_id_UNIQUE` (`question_id`),
  KEY `idx_scale_id` (`scale_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='题目表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_report`
--

DROP TABLE IF EXISTS `tb_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `report_id` varchar(45) NOT NULL COMMENT '报告编号',
  `report_name` varchar(90) DEFAULT NULL COMMENT '报告名称',
  `create_user` varchar(45) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `report_id_UNIQUE` (`report_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_report_scale`
--

DROP TABLE IF EXISTS `tb_report_scale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_report_scale` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `report_id` varchar(45) NOT NULL COMMENT '报告编号',
  `scale_id` varchar(45) NOT NULL COMMENT '量表编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_scale`
--

DROP TABLE IF EXISTS `tb_scale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_scale` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `scale_id` varchar(64) NOT NULL,
  `doctor_id` varchar(64) NOT NULL,
  `scale_name` varchar(256) NOT NULL,
  `question_sort` varchar(1024) DEFAULT '',
  `question_count` int(11) DEFAULT NULL COMMENT '真实题目数量',
  `status` tinyint(2) NOT NULL DEFAULT '1',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `scale_id_UNIQUE` (`scale_id`),
  KEY `idx_doctor_id` (`doctor_id`),
  KEY `idx_scale_name` (`scale_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_scale_paper`
--

DROP TABLE IF EXISTS `tb_scale_paper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_scale_paper` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `scale_paper_id` varchar(45) NOT NULL COMMENT '量表答卷编号',
  `paper_id` varchar(45) NOT NULL COMMENT '试卷编号',
  `scale_id` varchar(45) NOT NULL COMMENT '量表编号',
  `use_time` varchar(45) DEFAULT NULL COMMENT '量表答题耗时',
  `judge_status` varchar(8) DEFAULT NULL COMMENT '评定状态',
  `effective_status` tinyint(4) DEFAULT '1',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `paper_scacle_id_UNIQUE` (`scale_paper_id`),
  KEY `idx_paper_id` (`paper_id`),
  KEY `idx_scale_id` (`scale_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='量表答卷';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-30 23:13:44



alter table tb_judge add frequency_total_score varchar(45) null comment '频率总分';
alter table tb_judge add serious_total_score varchar(45) null comment '严重程度总分';
alter table tb_judge add frequency_serious_total_score varchar(45) null comment '频率*严重程度总分';
alter table tb_judge add distress_total_score varchar(45) null comment '严重程度总分';


alter table tb_patient
	add medical_record_num varchar(45) null comment '病例号' after patient_id;

alter table tb_patient modify patient_group varchar(8) null comment '病人组别';


create unique index idx_patient_medical_record_num
	on tb_patient (medical_record_num);

alter table tb_patient drop column drugs_type;

alter table tb_patient drop column sign_status;

alter table tb_examination_paper
	add adverse_reactions varchar(1024) null comment '不良反应' after effective_status;

alter table tb_examination_paper
	add medication varchar(1024) null comment '用药情况' after adverseReactions;





