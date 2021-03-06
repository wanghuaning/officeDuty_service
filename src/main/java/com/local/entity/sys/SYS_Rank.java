package com.local.entity.sys;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.local.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@ApiModel("职级信息表")//用在模型类上，对模型类做注释；
@Table("sys_rank")
@Comment("职级信息表")
public class SYS_Rank implements Serializable {

  @Name
  @ApiModelProperty("职级id")//用在属性上，对属性做注释
  @Comment("职级id")//定义脚本中添加comment属性来添加注释
  @Column("id")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String id;

  @ApiModelProperty("职级层次")
  @Comment("职级层次")
  @Column("name")
  @ColDefine(type = ColType.VARCHAR, width = 255)
  private String name;

  @ApiModelProperty("任职日期")
  @Comment("任职日期")
  @Column("create_Time")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @ColDefine(type = ColType.DATETIME)
  private Date createTime=new Date();

  private String createTimeStr;

  @ApiModelProperty("人员ID")
  @Comment("人员ID")
  @Column("people_Id")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String peopleId;

  @ApiModelProperty("状态")
  @Comment("状态")
  @Column("status")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String status;

  @ApiModelProperty("批准文号")
  @Comment("批准文号")
  @Column("document_Number")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String documentNumber;

  @ApiModelProperty("批次")
  @Comment("批次")
  @Column("batch")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String batch;

  @ApiModelProperty("民主测评结果")
  @Comment("民主测评结果")
  @Column("democracy")
  @ColDefine(type = ColType.VARCHAR, width = 255)
  private String democracy;

  @ApiModelProperty("任职级事由")
  @Comment("任职级事由")
  @Column("detail")
  @ColDefine(type = ColType.VARCHAR, width = 255)
  private String detail;

  @ApiModelProperty("审批时间")
  @Comment("审批时间")
  @Column("approval_Time")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @ColDefine(type = ColType.DATETIME)
  private Date approvalTime;

  private String approvalTimeStr;

  @ApiModelProperty("任同职务时间")
  @Comment("任同职务时间")
  @Column("duty_Time")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @ColDefine(type = ColType.DATETIME)
  private Date dutyTime;

  private String dutyTimeStr;
  @ApiModelProperty("人员姓名")
  @Comment("人员姓名")
  @Column("people_Name")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String peopleName;

  @ApiModelProperty("单位ID")
  @Comment("单位ID")
  @Column("unit_Id")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String unitId;

  @ApiModelProperty("是否军转干部首次套转不占职数")
  @Comment("是否军转干部首次套转不占职数")
  @Column("leaders")
  @ColDefine(type = ColType.VARCHAR, width = 8)
  private String leaders;

  @ApiModelProperty("任同职级时间")
  @Comment("任同职级时间")
  @Column("rank_Time")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @ColDefine(type = ColType.DATETIME)
  private Date rankTime;

  private String rankTimeStr;


  @ApiModelProperty("免职级事由")
  @Comment("免职级事由")
  @Column("depose_Rank")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String deposeRank;

  @ApiModelProperty("免职级时间")
  @Comment("免职级时间")
  @Column("depose_Time")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @ColDefine(type = ColType.DATETIME)
  private Date deposeTime;

  private String deposeTimeStr;

  @ApiModelProperty("套转")
  @Comment("套转")
  @Column("flag")
  @Default("否")
  @ColDefine(type = ColType.VARCHAR, width = 8)
  private String flag;

  @ApiModelProperty("免职审批时间")
  @Comment("免职审批时间")
  @Column("serve_Approval_Time")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @ColDefine(type = ColType.DATETIME)
  private Date serveApprovalTime;

  private String serveApprovalTimeStr;

  @ApiModelProperty("任职级文号")
  @Comment("任职级文号")
  @Column("serveNumber")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String serveNumber;

  @ApiModelProperty("免职级文号")
  @Comment("免职级文号")
  @Column("deposeNumber")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String deposeNumber;
  @ApiModelProperty("任职部门")
  @Comment("任职部门")
  @Column("dutyType")
  @ColDefine(type = ColType.VARCHAR, width = 32)
  private String dutyType;

  @ApiModelProperty("任职职务")
  @Comment("任职职务")
  @Column("serveDuty")
  @ColDefine(type = ColType.VARCHAR, width = 32)
  private String serveDuty;

  @ApiModelProperty("分类1：当前，0：已免")
  @Comment("分类1：当前，0：已免")
  @Column("rankOrder")
  @ColDefine(type = ColType.INT, width = 1)
  private Integer rankOrder=0;

  @ApiModelProperty("单位名称")
  @Comment("单位名称")
  @Column("unitName")
  @ColDefine(type = ColType.VARCHAR, width = 128)
  private String unitName;


  public String getUnitName() {
    return unitName;
  }

  public void setUnitName(String unitName) {
    this.unitName = unitName;
  }

  public Integer getRankOrder() {
    return rankOrder;
  }

  public void setRankOrder(Integer rankOrder) {
    this.rankOrder = rankOrder;
  }

  public String getDutyType() {
    return dutyType;
  }

  public void setDutyType(String dutyType) {
    this.dutyType = dutyType;
  }

  public String getServeDuty() {
    return serveDuty;
  }

  public void setServeDuty(String serveDuty) {
    this.serveDuty = serveDuty;
  }

  public String getServeNumber() {
    return serveNumber;
  }

  public void setServeNumber(String serveNumber) {
    this.serveNumber = serveNumber;
  }

  public String getDeposeNumber() {
    return deposeNumber;
  }

  public void setDeposeNumber(String deposeNumber) {
    this.deposeNumber = deposeNumber;
  }

  public Date getServeApprovalTime() {
    return serveApprovalTime;
  }

  public void setServeApprovalTime(Date serveApprovalTime) {
    this.serveApprovalTime = serveApprovalTime;
  }

  public String getServeApprovalTimeStr() {
    return DateUtil.dateToString(serveApprovalTime);
  }

  public void setServeApprovalTimeStr(String serveApprovalTimeStr) {
    this.serveApprovalTimeStr = serveApprovalTimeStr;
  }
  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public String getDeposeRank() {
    return deposeRank;
  }

  public void setDeposeRank(String deposeRank) {
    this.deposeRank = deposeRank;
  }

  public Date getDeposeTime() {
    return deposeTime;
  }

  public void setDeposeTime(Date deposeTime) {
    this.deposeTime = deposeTime;
  }

  public String getDeposeTimeStr() {
    return DateUtil.dateToString(deposeTime);
  }

  public void setDeposeTimeStr(String deposeTimeStr) {
    this.deposeTimeStr = deposeTimeStr;
  }

  public String getLeaders() {
    return leaders;
  }

  public void setLeaders(String leaders) {
    this.leaders = leaders;
  }

  public Date getRankTime() {
    return rankTime;
  }

  public void setRankTime(Date rankTime) {
    this.rankTime = rankTime;
  }

  public String getRankTimeStr() {
    return DateUtil.dateToString(rankTime);
  }

  public void setRankTimeStr(String rankTimeStr) {
    this.rankTimeStr = rankTimeStr;
  }

  public String getPeopleName() {
    return peopleName;
  }

  public void setPeopleName(String peopleName) {
    this.peopleName = peopleName;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public String getCreateTimeStr() {
    return DateUtil.dateToString(createTime);
  }

  public void setCreateTimeStr(String createTimeStr) {
    this.createTimeStr = createTimeStr;
  }


  public String getApprovalTimeStr() {
    return DateUtil.dateToString(approvalTime);
  }

  public void setApprovalTimeStr(String approvalTimeStr) {
    this.approvalTimeStr = approvalTimeStr;
  }

  public String getDutyTimeStr() {
    return DateUtil.dateToString(dutyTime);
  }

  public void setDutyTimeStr(String dutyTimeStr) {
    this.dutyTimeStr = dutyTimeStr;
  }

  public Date getDutyTime() {
    return DateUtil.parseDateYMD(dutyTime);
  }

  public void setDutyTime(Date dutyTime) {
    this.dutyTime = dutyTime;
  }

  public String getDemocracy() {
    return democracy;
  }

  public void setDemocracy(String democracy) {
    this.democracy = democracy;
  }

  public Date getApprovalTime() {
    return DateUtil.parseDateYMD(approvalTime);
  }

  public void setApprovalTime(Date approvalTime) {
    this.approvalTime = approvalTime;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
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

  public Date getCreateTime() {
    return DateUtil.parseDateYMD(createTime);
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getPeopleId() {
    return peopleId;
  }

  public void setPeopleId(String peopleId) {
    this.peopleId = peopleId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDocumentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }

  public String getBatch() {
    return batch;
  }

  public void setBatch(String batch) {
    this.batch = batch;
  }
}
