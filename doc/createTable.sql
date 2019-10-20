-- ==============================================================
-- officeDuty(mysql)
-- ==============================================================
-- ==============================================================
-- Table: 行政区划表(sys_area)
-- ==============================================================
create table sys_area
(
  Code nvarchar(32) PRIMARY KEY NOT NULL comment '编码',
  Up_Code nvarchar(32)  comment '父编码',
  Area_Name nvarchar(128) comment '名称',
  Type nchar(1) comment '类型',
  Available_Flag nvarchar(1)
)
  comment '行政区划表'
  charset = utf8;
-- ==============================================================
-- Table: 系统字典表(sys_code)
-- ==============================================================
create table sys_code
(
  id        int not null  comment '主键ID'    primary key,
  code_name varchar(128)  comment '名称',
  parent_id        int  comment '父Id',
  code_order  int  comment '排序',
  delete_flag varchar(1) default '0' comment '0:启用，1：删除'
)
  comment '系统字典表'
  charset = utf8;

-- ==============================================================
-- Table: 用户信息表(sys_user)
-- ==============================================================
create table sys_user
(
  id                      varchar(64) not null
  comment 'ID'
    primary key,
  avatar                   varchar(255) null
  comment '头像地址',
  create_time              datetime     null
  comment '创建日期',
  user_password                 varchar(255) null
  comment '密码',
  user_account                 varchar(255) null
  comment '用户名',
  last_password_reset_time datetime     null
  comment '最后修改密码的日期',
  enabled                  bigint       null
  comment '状态：1启用、0禁用' default 1,
  people_id                  varchar(64)       null comment '人员ID',
  unit_id                  varchar(64)       null comment '单位ID',
  constraint user_account
  unique (user_account)
)
  comment '用户信息表'
  charset = utf8;

-- ==============================================================
-- Table: 菜单表(SYS_MENU)
-- ==============================================================
CREATE TABLE SYS_MENU
(
  MENU_ID varchar(128) PRIMARY KEY NOT NULL,
  MENU_CREATE_TIME timestamp comment '创建时间',
  MENU_STATE char(1) DEFAULT '0'  comment '状态 0:不禁用，1:禁用',
  MENU_NAME varchar(255)  comment '菜单名称',
  MENU_COMPONENT varchar(255)  comment '组件',
  MENU_PARENT_ID varchar(128)  comment '菜单父id',
  MENU_SORT numeric(18) DEFAULT 99  comment '排序字段',
  MENU_ICON varchar(64)  comment '菜单图标',
  MENU_PATH varchar(255)  comment '连接地址'
)
  comment '菜单表表'
  charset = utf8;
 -- d21adc3949ba59abbe56e057f20f456e	53000	"云南省昆明市

-- ==============================================================
-- Table: 单位信息表(SYS_UNIT)
-- ==============================================================
create table SYS_UNIT(
  id varchar(64) not null primary key ,
  code varchar(24) unique comment '组织机构编码',
  name varchar(255) comment '单位名称',
  parent_Id varchar(64) comment '父单位ID',
  area varchar(64) comment '所在地区',
  affiliation varchar(64) comment '隶属关系',
  category varchar(64) comment '机构类别',
  level varchar(64) comment '机构级别',
  official_Num bigint(6) default 0 comment '行政编制数',
  refer_Official_Num bigint(6) default 0 comment '事业编制数（参公）',
  refer_Official_Date datetime comment '参照公务员法管理审批时间',
  refer_Official_Document varchar(64) comment '参照公务员法管理审批文号',
  main_Hall_Num bigint(6) default 0 comment '厅局级正职领导职数',
  deputy_Hall_Num bigint(6) DEFAULT 0 comment '厅局级副职领导职数',
  right_Place_Num bigint(6) default 0 comment '县处级正职领导职数',
  deputy_Place_Num bigint(6) DEFAULT 0 comment '县处级副职领导职数',
  one_Inspector_Num bigint(6) default 0 comment '一级巡视员职数',
  tow_Inspector_Num bigint(6) default 0 comment '二级巡视员职数',
  one_Researcher_Num bigint(6) default 0 comment '一级调研员职数',
  tow_Researcher_Num bigint(6) default 0 comment '二级调研员职数',
  three_Researcher_Num bigint(6) default 0 comment '三级调研员职数',
  four_Researcher_Num bigint(6) default 0 comment '四级调研员职数',
  one_Clerk_Num bigint(6) default 0 comment '一级主任科员职数',
  tow_Clerk_Num bigint(6) default 0 comment '二级主任科员职数',
  three_Clerk_Num bigint(6) default 0 comment '三级主任科员职数',
  four_Clerk_Num bigint(6) default 0 comment '四级主任科员职数',
  parent_Name nvarchar(255) comment '上级单位名称',
  detail nvarchar(2000) comment '备注',
  enabled varchar(1) default 0 comment '机构状态1:禁用；0：在用',
  unit_Order bigint(6) comment '排序',
)comment '单位信息表' charset = utf8;

alter table SYS_UNIT add build_Province nvarchar(10) comment '省名称';
alter table SYS_UNIT add build_City nvarchar(10) comment '市名称';
alter table SYS_UNIT add build_County nvarchar(10) comment '县名称';
alter table SYS_UNIT add contact nvarchar(64) comment '联系人';
alter table SYS_UNIT add contact_Number nvarchar(64) comment '联系电话';
-- mysql 设置自增序列
-- 设置key
alter table SYS_UNIT ADD KEY key_t_bd_extension_show_id(unit_Order);
-- 改为自增id字段，自增的值默认是从1开始累加，每次+1
ALTER TABLE SYS_UNIT MODIFY unit_Order BIGINT auto_increment;
-- 4、修改表，当前自增id值
-- 4.1 先查出目前最大值：150628
select max(unit_Order) from SYS_UNIT;
-- 4.2 修改自增的目前起始值，等于目前的最大值，确保后续id连续增长
alter table SYS_UNIT auto_increment=1;

-- ==============================================================
-- Table: 人员基本信息表(sys_people)
-- ==============================================================
create table sys_people (
  id                  varchar(64) not null primary key,
  name                varchar(255) comment '姓名',
  idcard              varchar(24) unique
  comment '身份证号',
  birthday            datetime comment '出生年月',
  unit_Id             varchar(64) comment '单位ID',
  sex                 varchar(8) comment '性别',
  birthplace          varchar(64) comment '籍贯',
  nationality         varchar(64) comment '民族',
  workday             datetime comment '参加工作时间',
  party    varchar(64) comment '政治面貌',
  party_Time          datetime comment '入党时间',
  second_party        varchar(64) comment '第二党派',
  third_party         varchar(64) comment '第三党派',
  position            varchar(64) comment '现职务层次',
  position_Time       datetime comment '任现职务层次时间',
  position_Level      varchar(64) comment '现职级',
  position_Level_Time datetime comment '现职级时间',
  base_Worker varchar(8) comment '是否具有两年以上基层工作经历',
  political_Status varchar(32) comment '编制类型',
  create_Time datetime comment '公务员登记时间',
  detail varchar(2000) comment '备注',
  unit_Name nvarchar(255) comment '单位名称',
  enabled varchar(1) default 0 comment '人员状态1:禁用；0：在用',
  people_Order bigint(6) comment '排序',
  foreign key (unit_Id) references SYS_UNIT(id) on DELETE cascade,
  index (unit_Id)
)comment '人员基本信息表' charset = utf8;
alter table sys_people add education nvarchar(64) comment '最新学历';
alter table sys_people add partyTime datetime comment '入党时间';
-- mysql 设置自增序列
-- 设置key
alter table sys_people ADD KEY key_t_bd_extension_show_id(people_Order);
-- 改为自增id字段，自增的值默认是从1开始累加，每次+1
ALTER TABLE sys_people MODIFY people_Order BIGINT auto_increment;
-- 4、修改表，当前自增id值
-- 4.2 修改自增的目前起始值，等于目前的最大值，确保后续id连续增长
alter table sys_people auto_increment=1;

create table sys_duty (
  id                  varchar(64) not null primary key,
  name                varchar(255) comment '职务名称',
  create_Time            datetime comment '任职时间',
  people_Id             varchar(64) comment '人员ID',
  leader_Type             varchar(8) comment '成员类别',
  selection_Method          varchar(64) comment '选拔任用方式',
  status         varchar(64) comment '任职状态',
  serve_Time          datetime comment '免职时间',
  document_Number        varchar(64) comment '免职文号',
  foreign key (people_Id) references sys_people(id) on DELETE cascade,
  index (people_Id)
)comment '职务信息表' charset = utf8;

create table sys_rank (
  id                  varchar(64) not null primary key,
  name                varchar(255) comment '职级层次',
  create_Time            datetime comment '任职日期',
  people_Id             varchar(64) comment '人员ID',
  rank_Type             varchar(8) comment '类别（职级标志）',
  status         varchar(64) comment '状态',
  serve_Time          datetime comment '终止日期',
  document_Number        varchar(64) comment '批准文号',
  batch        varchar(64) comment '批次',
  foreign key (people_Id) references sys_people(id) on DELETE cascade,
  index (people_Id)
)comment '职级信息表' charset = utf8;

create table sys_education (
  id                  varchar(64) not null primary key,
  name                varchar(255) comment '学历名称',
  create_Time            datetime comment '入学时间',
  people_Id             varchar(64) comment '人员ID',
  degree             varchar(8) comment '学位名称',
  end_Time          datetime comment '毕（肄）业时间',
  degree_Time        varchar(64) comment '学位授予时间',
  foreign key (people_Id) references sys_people(id) on DELETE cascade,
  index (people_Id)
)comment '学历学位信息表' charset = utf8;

create table sys_reward (
  id                  varchar(64) not null primary key,
  name                varchar(255) comment '奖惩名称',
  name_Type varchar(64) comment '奖惩名称代码',
  create_Time            datetime comment '批准日期',
  people_Id             varchar(64) comment '人员ID',
  approval_unit             varchar(255) comment '批准机关',
  duty          varchar(64) comment '受奖惩时职务层次',
  revocation_date          datetime comment '撤销日期',
  unit_Type        varchar(64) comment '批准机关性质',
  foreign key (people_Id) references sys_people(id) on DELETE cascade,
  index (people_Id)
)comment '奖惩信息表' charset = utf8;

create table sys_assessment (
  id                  varchar(64) not null primary key,
  name                varchar(255) comment '考核结论',
  year            int comment '考核年度',
  people_Id             varchar(64) comment '人员ID',
  foreign key (people_Id) references sys_people(id) on DELETE cascade,
  index (people_Id)
)comment '考核信息表' charset = utf8;