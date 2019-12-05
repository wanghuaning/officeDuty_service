package com.local.entity.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.local.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@ApiModel("人员基础信息表")//用在模型类上，对模型类做注释；
@Table("sys_people")
@Comment("人员基础信息表")
public class SYS_People implements Serializable {
  @Name
  @ApiModelProperty("人员id")//用在属性上，对属性做注释
  @Comment("人员id")//定义脚本中添加comment属性来添加注释
  @Column("id")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String id;

  @ApiModelProperty("姓名")
  @Comment("姓名")
  @Column("name")
  @ColDefine(type = ColType.VARCHAR, width = 255)
  private String name;

  @ApiModelProperty("身份证号")
  @Comment("身份证号")
  @Column("idcard")
  @ColDefine(type = ColType.VARCHAR, width = 24)
  private String idcard;

  @ApiModelProperty("出生年月")
  @Comment("出生年月")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Column("birthday")
  @ColDefine(type = ColType.DATETIME)
  private Date birthday;

  private String birthdayStr;

  @ApiModelProperty("单位ID")
  @Comment("单位ID")
  @Column("unit_Id")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String unitId;

  @ApiModelProperty("性别")
  @Comment("性别")
  @Column("sex")
  @ColDefine(type = ColType.VARCHAR, width = 8)
  private String sex;

  @ApiModelProperty("籍贯")
  @Comment("籍贯")
  @Column("birthplace")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String birthplace;

  @ApiModelProperty("民族")
  @Comment("民族")
  @Column("nationality")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String nationality;

  @ApiModelProperty("参加工作时间")
  @Comment("参加工作时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Column("workday")
  @ColDefine(type = ColType.DATETIME)
  private Date workday;

  private String workdayStr;

  @ApiModelProperty("政治面貌")
  @Comment("政治面貌")
  @Column("party")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String party;

  @ApiModelProperty("入党时间")
  @Comment("入党时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Column("partyTime")
  @ColDefine(type = ColType.DATETIME)
  private Date partyTime;

  private String partyTimeStr;

  @ApiModelProperty("第二党派")
  @Comment("第二党派")
  @Column("second_party")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String secondParty;

  @ApiModelProperty("第三党派")
  @Comment("第三党派")
  @Column("third_party")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String thirdParty;

  @ApiModelProperty("现职务层次")
  @Comment("现职务层次")
  @Column("position")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String position;

  @ApiModelProperty("任现职务层次时间")
  @Comment("任现职务层次时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Column("position_Time")
  @ColDefine(type = ColType.DATETIME)
  private Date positionTime;

  private String positionTimeStr;

  @ApiModelProperty("现职级")
  @Comment("现职级")
  @Column("position_Level")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String positionLevel;

  @ApiModelProperty("现职级时间")
  @Comment("现职级时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Column("position_Level_Time")
  @ColDefine(type = ColType.DATETIME)
  private Date positionLevelTime;

  private String positionLevelTimeStr;

  @ApiModelProperty("套转职级")
  @Comment("套转职级")
  @Column("turn_Rank")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String turnRank;

  @ApiModelProperty("套转职级时间")
  @Comment("套转职级时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Column("turn_Rank_Time")
  @ColDefine(type = ColType.DATETIME)
  private Date turnRankTime;

  private String turnRankTimeStr;

  @ApiModelProperty("是否具有两年以上基层工作经历")
  @Comment("是否具有两年以上基层工作经历")
  @Column("base_Worker")
  @ColDefine(type = ColType.VARCHAR, width = 8)
  private String baseWorker;

  @ApiModelProperty("编制类型")
  @Comment("编制类型")
  @Column("political_Status")
  @ColDefine(type = ColType.VARCHAR, width = 32)
  private String politicalStatus;

  @ApiModelProperty("公务员登记时间")
  @Comment("公务员登记时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Column("create_Time")
  @ColDefine(type = ColType.DATETIME)
  private Date createTime;

  private String createTimeStr;

  @ApiModelProperty("特殊人员")
  @Comment("特殊人员")
  @Column("detail")
  @ColDefine(type = ColType.VARCHAR, width = 2000)
  private String detail;

  @ApiModelProperty("单位名称")
  @Comment("单位名称")
  @Column("unit_Name")
  @ColDefine(type = ColType.VARCHAR, width = 255)
  private String unitName;

  @ApiModelProperty("最新学历")
  @Comment("最新学历")
  @Column("education")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String education;

  @ApiModelProperty("是否兼职")
  @Comment("是否兼职")
  @Column("isEnable")
  @ColDefine(type = ColType.VARCHAR, width = 8)
  private String isEnable;

  @ApiModelProperty("排序")
  @Comment("排序")
  @Column("people_Order")
  @ColDefine(type = ColType.INT, width = 6)
  private Integer peopleOrder;

  private String value;
  private String label;

  @ApiModelProperty("单列管理事由")
  @Comment("单列管理事由")
  @Column("real_Name")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String realName;

  public String getIsEnable() {
    return isEnable;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getBirthdayStr() {
    return DateUtil.dateToString(birthday);
  }

  public void setBirthdayStr(String birthdayStr) {
    this.birthdayStr = birthdayStr;
  }

  public String getWorkdayStr() {
    return DateUtil.dateToString(workday);
  }

  public void setWorkdayStr(String workdayStr) {
    this.workdayStr = workdayStr;
  }

  public String getPartyTimeStr() {
    return DateUtil.dateToString(partyTime);
  }

  public void setPartyTimeStr(String partyTimeStr) {
    this.partyTimeStr = partyTimeStr;
  }

  public String getPositionTimeStr() {
    return DateUtil.dateToString(positionTime);
  }

  public void setPositionTimeStr(String positionTimeStr) {
    this.positionTimeStr = positionTimeStr;
  }

  public String getPositionLevelTimeStr() {
    return DateUtil.dateToString(positionLevelTime);
  }

  public void setPositionLevelTimeStr(String positionLevelTimeStr) {
    this.positionLevelTimeStr = positionLevelTimeStr;
  }

  public String getTurnRankTimeStr() {
    return DateUtil.dateToString(turnRankTime);
  }

  public void setTurnRankTimeStr(String turnRankTimeStr) {
    this.turnRankTimeStr = turnRankTimeStr;
  }

  public String getCreateTimeStr() {
    return DateUtil.dateToString(createTime);
  }

  public void setCreateTimeStr(String createTimeStr) {
    this.createTimeStr = createTimeStr;
  }

  public String getTurnRank() {
    return turnRank;
  }

  public void setTurnRank(String turnRank) {
    this.turnRank = turnRank;
  }

  public Date getTurnRankTime() {
    return DateUtil.parseDateYMD(turnRankTime);
  }

  public void setTurnRankTime(Date turnRankTime) {
    this.turnRankTime = turnRankTime;
  }

  public String getValue() {
    return id;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getLabel() {
    return name;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getEducation() {
    return education;
  }

  public void setEducation(String education) {
    this.education = education;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIdcard() {
    return idcard;
  }

  public void setIdcard(String idcard) {
    this.idcard = idcard;
  }

  public Date getBirthday(){
    return DateUtil.parseDateYMD(birthday);
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getBirthplace() {
    return birthplace;
  }

  public void setBirthplace(String birthplace) {
    this.birthplace = birthplace;
  }

  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  public Date getWorkday() {
    return DateUtil.parseDateYMD(workday);
  }

  public void setWorkday(Date workday) {
    this.workday = workday;
  }

  public String getParty() {
    return party;
  }

  public void setParty(String party) {
    this.party = party;
  }

  public Date getPartyTime() {
    return DateUtil.parseDateYMD(partyTime);
  }

  public void setPartyTime(Date partyTime) {
    this.partyTime = partyTime;
  }

  public String getSecondParty() {
    return secondParty;
  }

  public void setSecondParty(String secondParty) {
    this.secondParty = secondParty;
  }

  public String getThirdParty() {
    return thirdParty;
  }

  public void setThirdParty(String thirdParty) {
    this.thirdParty = thirdParty;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public Date getPositionTime() {
    return DateUtil.parseDateYMD(positionTime);
  }

  public void setPositionTime(Date positionTime) {
    this.positionTime = positionTime;
  }

  public String getPositionLevel() {
    return positionLevel;
  }

  public void setPositionLevel(String positionLevel) {
    this.positionLevel = positionLevel;
  }

  public Date getPositionLevelTime() {
    return DateUtil.parseDateYMD(positionLevelTime);
  }

  public void setPositionLevelTime(Date positionLevelTime) {
    this.positionLevelTime = positionLevelTime;
  }

  public String getBaseWorker() {
    return baseWorker;
  }

  public void setBaseWorker(String baseWorker) {
    this.baseWorker = baseWorker;
  }

  public String getPoliticalStatus() {
    return politicalStatus;
  }

  public void setPoliticalStatus(String politicalStatus) {
    this.politicalStatus = politicalStatus;
  }

  public Date getCreateTime() {
    return DateUtil.parseDateYMD(createTime);
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getUnitName() {
    return unitName;
  }

  public void setUnitName(String unitName) {
    this.unitName = unitName;
  }

  public Integer getPeopleOrder() {
    return peopleOrder;
  }

  public void setPeopleOrder(Integer peopleOrder) {
    this.peopleOrder = peopleOrder;
  }

  public void setIsEnable(String isEnable) {
    this.isEnable = isEnable;
  }
}

