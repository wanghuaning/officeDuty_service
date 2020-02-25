package com.local.entity.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.local.model.DigestModel;
import com.local.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@ApiModel("超职级职数消化情况表")//用在模型类上，对模型类做注释；
@Table("sys_digest")
@Comment("超职级职数消化情况表")
@Data
public class SYS_Digest implements Serializable {
  @Name
  @ApiModelProperty("审批id")//用在属性上，对属性做注释
  @Comment("审批id")//定义脚本中添加comment属性来添加注释
  @Column("id")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String id;

  @ApiModelProperty("单位ID")
  @Comment("单位ID")
  @Column("unit_Id")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String unitId;

  @ApiModelProperty("单位名称")
  @Comment("单位名称")
  @Column("unit_Name")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String unitName;

  @ApiModelProperty("创建日期")
  @Comment("创建日期")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Column("create_time")
  @ColDefine(type = ColType.DATETIME)
  private Date createTime;

  private String createTimeStr;

  @ApiModelProperty("审批转态，0：未审批 1：已审批")
  @Comment("审批转态，0：未审批 1：已审批")
  @Column("flag")
  @ColDefine(type = ColType.VARCHAR, width = 10)
  private String flag;

  @ApiModelProperty("年份")
  @Comment("年份")
  @Column("years")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String years;

  @ApiModelProperty("季度")
  @Comment("季度")
  @Column("quarter")
  @ColDefine(type = ColType.VARCHAR,width = 16)
  private String quarter;

  private String yearAndQuarter;

  @ApiModelProperty("一、二级主任科员(职级职数核定情况)")
  @Comment("一、二级主任科员(职级职数核定情况)")
  @Column("one_Tow_Clerk_Approve")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String oneTowClerkApprove;

  @ApiModelProperty("三、四级主任科员(职级职数核定情况)")
  @Comment("三、四级主任科员(职级职数核定情况)")
  @Column("three_Four_Clerk_Approve")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String threeFourClerkApprove;

  @ApiModelProperty("二级主任科员(2019年6月套改情况)")
  @Comment("二级主任科员(2019年6月套改情况)")
  @Column("tow_Clerk_Arbitrage")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String towClerkArbitrage;

  @ApiModelProperty("四级主任科员(2019年6月套改情况)")
  @Comment("四级主任科员(2019年6月套改情况)")
  @Column("four_Clerk_Arbitrage")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String fourClerkArbitrage;

  @ApiModelProperty("一、二级主任科员(超职级职数情况)")
  @Comment("一、二级主任科员(超职级职数情况)")
  @Column("one_Tow_Clerk_Exceed")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String oneTowClerkExceed;

  @ApiModelProperty("三、四级主任科员(超职级职数情况)")
  @Comment("三、四级主任科员(超职级职数情况)")
  @Column("three_Four_Clerk_Exceed")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String threeFourClerkExceed;

  @ApiModelProperty("二级主任科员(消化计划)")
  @Comment("二级主任科员(消化计划)")
  @Column("one_Tow_Clerk_Remove")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String oneTowClerkRemove="0";

  @ApiModelProperty("四级主任科员(消化计划)")
  @Comment("四级主任科员(消化计划)")
  @Column("three_Four_Clerk_Remove")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String threeFourClerkRemove="0";

  @ApiModelProperty("退休(计划消化途径)")
  @Comment("退休(计划消化途径)")
  @Column("retire_Plan_Way")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String retirePlanWay="0";

  @ApiModelProperty("晋升(计划消化途径)")
  @Comment("晋升(计划消化途径)")
  @Column("up_Plan_Way")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String upPlanWay="0";//单位自己填写

  @ApiModelProperty("一、二级主任科员(消化情况)")
  @Comment("一、二级主任科员(消化情况)")
  @Column("one_Tow_Clerk_Situation")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String oneTowClerkSituation;

  @ApiModelProperty("三、四级主任科员(消化情况)")
  @Comment("三、四级主任科员(消化情况)")
  @Column("three_Four_Clerk_Situation")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String threeFourClerkSituation;

  @ApiModelProperty("职级晋升(消化途径)")
  @Comment("职级晋升(消化途径)")
  @Column("rank_Up_Way")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String rankUpWay;

  @ApiModelProperty("任领导职务(消化途径)")
  @Comment("任领导职务(消化途径)")
  @Column("leader_Duty_Way")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String leaderDutyWay;

  @ApiModelProperty("退休(消化途径)")
  @Comment("退休(消化途径)")
  @Column("retire_Way")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String retireWay;

  @ApiModelProperty("提前退休(消化途径)")
  @Comment("提前退休(消化途径)")
  @Column("early_Retire_Way")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String earlyRetireWay;

  @ApiModelProperty("调出(消化途径)")
  @Comment("调出(消化途径)")
  @Column("out_Way")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String outWay;

  @ApiModelProperty("其他(消化途径)")
  @Comment("其他(消化途径)")
  @Column("other_Way")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String otherWay;

  @ApiModelProperty("一、二级主任科员(消化后超职级职数情况)")
  @Comment("一、二级主任科员(消化后超职级职数情况)")
  @Column("one_Tow_Clerk_Result")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String oneTowClerkResult;

  @ApiModelProperty("三、四级主任科员(消化后超职级职数情况)")
  @Comment("三、四级主任科员(消化后超职级职数情况)")
  @Column("three_Four_Clerk_Result")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String threeFourClerkResult;

  private DigestModel model;

  public DigestModel getModel() {
    return model;
  }

  public void setModel(DigestModel model) {
    this.model = model;
  }

  public String getCreateTimeStr() {
    return DateUtil.dateToString(createTime);
  }

  public void setCreateTimeStr(String createTimeStr) {
    this.createTimeStr = createTimeStr;
  }

  public String getYearAndQuarter() {
    return years+"/"+quarter;
  }

  public void setYearAndQuarter(String yearAndQuarter) {
    this.yearAndQuarter = yearAndQuarter;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public String getUnitName() {
    return unitName;
  }

  public void setUnitName(String unitName) {
    this.unitName = unitName;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public String getYears() {
    return years;
  }

  public void setYears(String years) {
    this.years = years;
  }

  public String getQuarter() {
    return quarter;
  }

  public void setQuarter(String quarter) {
    this.quarter = quarter;
  }

  public String getOneTowClerkApprove() {
    return oneTowClerkApprove;
  }

  public void setOneTowClerkApprove(String oneTowClerkApprove) {
    this.oneTowClerkApprove = oneTowClerkApprove;
  }

  public String getThreeFourClerkApprove() {
    return threeFourClerkApprove;
  }

  public void setThreeFourClerkApprove(String threeFourClerkApprove) {
    this.threeFourClerkApprove = threeFourClerkApprove;
  }

  public String getTowClerkArbitrage() {
    return towClerkArbitrage;
  }

  public void setTowClerkArbitrage(String towClerkArbitrage) {
    this.towClerkArbitrage = towClerkArbitrage;
  }

  public String getFourClerkArbitrage() {
    return fourClerkArbitrage;
  }

  public void setFourClerkArbitrage(String fourClerkArbitrage) {
    this.fourClerkArbitrage = fourClerkArbitrage;
  }

  public String getOneTowClerkExceed() {
    return oneTowClerkExceed;
  }

  public void setOneTowClerkExceed(String oneTowClerkExceed) {
    this.oneTowClerkExceed = oneTowClerkExceed;
  }

  public String getThreeFourClerkExceed() {
    return threeFourClerkExceed;
  }

  public void setThreeFourClerkExceed(String threeFourClerkExceed) {
    this.threeFourClerkExceed = threeFourClerkExceed;
  }

  public String getOneTowClerkRemove() {
    return oneTowClerkRemove;
  }

  public void setOneTowClerkRemove(String oneTowClerkRemove) {
    this.oneTowClerkRemove = oneTowClerkRemove;
  }

  public String getThreeFourClerkRemove() {
    return threeFourClerkRemove;
  }

  public void setThreeFourClerkRemove(String threeFourClerkRemove) {
    this.threeFourClerkRemove = threeFourClerkRemove;
  }

  public String getRetirePlanWay() {
    return retirePlanWay;
  }

  public void setRetirePlanWay(String retirePlanWay) {
    this.retirePlanWay = retirePlanWay;
  }

  public String getUpPlanWay() {
    return upPlanWay;
  }

  public void setUpPlanWay(String upPlanWay) {
    this.upPlanWay = upPlanWay;
  }

  public String getOneTowClerkSituation() {
    return oneTowClerkSituation;
  }

  public void setOneTowClerkSituation(String oneTowClerkSituation) {
    this.oneTowClerkSituation = oneTowClerkSituation;
  }

  public String getThreeFourClerkSituation() {
    return threeFourClerkSituation;
  }

  public void setThreeFourClerkSituation(String threeFourClerkSituation) {
    this.threeFourClerkSituation = threeFourClerkSituation;
  }

  public String getRankUpWay() {
    return rankUpWay;
  }

  public void setRankUpWay(String rankUpWay) {
    this.rankUpWay = rankUpWay;
  }

  public String getLeaderDutyWay() {
    return leaderDutyWay;
  }

  public void setLeaderDutyWay(String leaderDutyWay) {
    this.leaderDutyWay = leaderDutyWay;
  }

  public String getRetireWay() {
    return retireWay;
  }

  public void setRetireWay(String retireWay) {
    this.retireWay = retireWay;
  }

  public String getEarlyRetireWay() {
    return earlyRetireWay;
  }

  public void setEarlyRetireWay(String earlyRetireWay) {
    this.earlyRetireWay = earlyRetireWay;
  }

  public String getOutWay() {
    return outWay;
  }

  public void setOutWay(String outWay) {
    this.outWay = outWay;
  }

  public String getOtherWay() {
    return otherWay;
  }

  public void setOtherWay(String otherWay) {
    this.otherWay = otherWay;
  }

  public String getOneTowClerkResult() {
    return oneTowClerkResult;
  }

  public void setOneTowClerkResult(String oneTowClerkResult) {
    this.oneTowClerkResult = oneTowClerkResult;
  }

  public String getThreeFourClerkResult() {
    return threeFourClerkResult;
  }

  public void setThreeFourClerkResult(String threeFourClerkResult) {
    this.threeFourClerkResult = threeFourClerkResult;
  }
}
