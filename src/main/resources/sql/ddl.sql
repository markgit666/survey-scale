create schema if not exists questionnaire collate utf8mb4_general_ci;

create table if not exists tb_answer
(
	id int auto_increment
		primary key,
	answer_id varchar(45) not null,
	qeustion_id varchar(45) not null,
	examination_paper_id varchar(45) not null,
	content varchar(45) not null,
	score int null,
	create_time datetime not null,
	update_time datetime null,
	constraint answer_id_UNIQUE
		unique (answer_id)
);

create index index_question_id
	on tb_answer (qeustion_id);

create table if not exists tb_doctor_info
(
	id int auto_increment
		primary key,
	doctor_id varchar(45) not null,
	login_name varchar(45) not null,
	password varchar(45) not null,
	salt varchar(45) not null,
	doctor_name varchar(45) null,
	create_time varchar(45) not null,
	update_time varchar(45) null,
	constraint doctor_id_UNIQUE
		unique (doctor_id)
);

create index index_login_name
	on tb_doctor_info (login_name);

create table if not exists tb_examination_paper
(
	id int auto_increment
		primary key,
	examination_paper_id varchar(45) not null,
	patient_id varchar(45) not null,
	scale_id varchar(45) not null,
	use_time int not null,
	judge_status varchar(8) null comment '评分状态',
	create_time datetime not null,
	update_time datetime null,
	constraint examination_papaer_id_UNIQUE
		unique (examination_paper_id)
);

create table if not exists tb_file_info
(
	id int auto_increment
		primary key,
	file_no varchar(45) not null,
	file_name varchar(45) not null,
	file_type tinyint(2) not null,
	create_time datetime not null,
	update_time datetime null,
	constraint file_no_UNIQUE
		unique (file_no)
);

create table if not exists tb_judge_info
(
	id int auto_increment
		primary key,
	judge_id varchar(45) not null,
	examination_paper_id varchar(45) not null,
	check_user varchar(45) not null,
	check_time datetime not null,
	total_score varchar(45) null,
	create_time datetime null,
	update_time datetime null,
	constraint examination_paper_id_UNIQUE
		unique (examination_paper_id),
	constraint judge_id_UNIQUE
		unique (judge_id)
);

create table if not exists tb_patient_info
(
	id int auto_increment comment '自增主键'
		primary key,
	patient_id varchar(50) not null comment '用户编号',
	doctor_id varchar(24) not null comment '医生ID',
	patient_name varchar(50) not null comment '姓名',
	gender tinyint(2) not null comment '性别',
	birthday date null comment '出生日期',
	telephone_number varchar(20) null comment '手机号码',
	family_address varchar(50) null comment '地址',
	status tinyint(2) default 1 not null comment '用户状态 有效：1；无效：0',
	create_time datetime null comment '创建时间',
	update_time datetime null comment '修改时间',
	nation varchar(8) null comment '名族',
	marriage_status varchar(8) null comment '婚姻状况',
	work_status varchar(8) null comment '工作类型',
	in_service_job varchar(8) null,
	education_level varchar(8) null comment '学历',
	education_years varchar(45) null comment '受教育年数',
	is_snoring varchar(8) null comment '是否打鼾',
	living_way varchar(45) null comment '居住方式',
	medical_history varchar(45) null comment '用药史',
	other_medical_history varchar(45) null comment '其用药史',
	smoking_history varchar(45) null comment '吸烟史',
	smoking_num_eachday varchar(45) null comment '每天吸烟数量（支）',
	smoking_years varchar(45) null comment '吸烟年数',
	is_mental_disease_family_history varchar(45) null comment '有无精神疾病家族史',
	mental_disease_family_history varchar(45) null comment '精神疾病家族史',
	other_mental_disease_family_history varchar(45) null comment '其他家族精神病史',
	current_medical_history_memory_loss varchar(45) null comment '现病史（有无记忆下降）',
	memory_loss_time varchar(45) null comment '记忆力下降多久',
	physical_examination varchar(45) null comment '体格检查',
	is_use_cognitive_drugs varchar(45) null comment '是否合并使用促认知药物',
	drugs_dosage varchar(45) null comment '具体药物及剂量',
	hand varchar(45) null comment '利手',
	drinking_history varchar(45) null comment '喝酒史',
	drinking_type varchar(45) null comment '饮酒类型',
	drinking_num_eachday varchar(45) null comment '每天饮酒量（两）',
	drinking_years varchar(45) null comment '喝酒年数',
	drugs_type varchar(45) null comment '具体认知药物',
	constraint user_id_UNIQUE
		unique (patient_id)
);

create index index_doctor_id
	on tb_patient_info (doctor_id);

create table if not exists tb_question
(
	id int auto_increment comment '主键id'
		primary key,
	question_id varchar(45) not null comment '问题id',
	scale_id varchar(45) not null comment '量表id',
	question_type varchar(24) not null comment '问题类型',
	title text null comment '标题',
	items varchar(512) null comment '选择题选项',
	attachment varchar(512) null comment '附件',
	status tinyint(2) default 1 not null comment '状态',
	create_time datetime not null comment '创建时间',
	update_time datetime null comment '更新时间',
	constraint question_id_UNIQUE
		unique (question_id)
);

create index index_scale_id
	on tb_question (scale_id);

create table if not exists tb_scale_info
(
	id int auto_increment
		primary key,
	scale_id varchar(64) not null,
	doctor_id varchar(64) not null,
	scale_name varchar(256) not null,
	question_sort varchar(1024) default '' null,
	question_count int null comment '真实题目数量',
	status tinyint(2) default 1 not null,
	create_time datetime null,
	update_time datetime null,
	constraint scale_id_UNIQUE
		unique (scale_id)
);

create index index_doctor_id
	on tb_scale_info (doctor_id);

