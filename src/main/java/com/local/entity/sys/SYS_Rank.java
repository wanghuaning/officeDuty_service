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
  private Date createTime;

  private String createTimeStr;

  @ApiModelProperty("人员ID")
  @Comment("人员ID")
  @Column("people_Id")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String peopleId;

  @ApiModelProperty("类别（职级标志）")
  @Comment("类别（职级标志）")
  @Column("rank_Type")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String rankType;

  @ApiModelProperty("状态")
  @Comment("状态")
  @Column("status")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String status;

  @ApiModelProperty("终止日期")
  @Comment("终止日期")
  @Column("serve_Time")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @ColDefine(type = ColType.DATETIME)
  private Date serveTime;

  private String serveTimeStr;

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

  @ApiModelProperty("备注")
  @Comment("备注")
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

  public String getCreateTimeStr() {
    return DateUtil.dateToString(createTime);
  }

  public void setCreateTimeStr(String createTimeStr) {
    this.createTimeStr = createTimeStr;
  }

  public String getServeTimeStr() {
    return DateUtil.dateToString(serveTime);
  }

  public void setServeTimeStr(String serveTimeStr) {
    this.serveTimeStr = serveTimeStr;
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

  public String getRankType() {
    return rankType;
  }

  public void setRankType(String rankType) {
    this.rankType = rankType;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getServeTime() {
    return DateUtil.parseDateYMD(serveTime);
  }

  public void setServeTime(Date serveTime) {
    this.serveTime = serveTime;
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
