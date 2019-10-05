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
  parent_id        int  comment '名称',
  code_order  int  comment '排序',
  delete_flag varchar(1) default 0 comment '0:启用，1：删除'
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
-- ==============================================================
-- Table: 单位信息表(SYS_UNIT)
-- ==============================================================
create table SYS_UNIT(
  id varchar(64) not null primary key ,
  code varchar(24) unique comment '组织机构编码',
  simple_Name varchar(64) comment '简称',
  area varchar(64) comment '所在地区',
  affiliation varchar(64) comment '隶属关系',
  category varchar(64) comment '机构类别',
  level varchar(64) comment '机构级别',
  standing_Position_Num bigint(6) default 0 comment '正职领导数',
  voce_Position_Num bigint(6) DEFAULT 0 comment '副职领导数',
  official_Num bigint(6) default 0 comment '行政编制数',
  refer_Official_Num bigint(6) default 0 comment '参照公务员法管理事业单位编制数',
  enterprise_Num bigint(6) default 0 comment '其他事业编制数',
  worker_Num bigint(6) default 0 comment '工勤编制数',
  other_Num bigint(6) default 0 comment '其他编制数',
  internal_Leader_Standing bigint(6) default 0 comment '内设机构应配领导正职',
  internal_Leader_Voce bigint(6) default 0 comment '内设机构应配领导副职',
  internal_Not_Leader_Standing bigint(6) default 0 comment '内设机构应配正职非领导',
  internal_Not_Leader_Voce bigint(6) default 0 comment '内设机构应配副职非领导',
  flag bigint(2) default 0 comment '机构状态'
)comment '单位信息表' charset = utf8;