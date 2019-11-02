package com.local.entity.sys;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@ApiModel("职务信息表")//用在模型类上，对模型类做注释；
@Table("sys_duty")
@Comment("职务信息表")
public class SYS_Duty implements Serializable {
  @Name
  @ApiModelProperty("职务id")//用在属性上，对属性做注释
  @Comment("职务id")//定义脚本中添加comment属性来添加注释
  @Column("id")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String id;

  @ApiModelProperty("职务名称")
  @Comment("职务名称")
  @Column("name")
  @ColDefine(type = ColType.VARCHAR, width = 255)
  private String name;

  @ApiModelProperty("任职时间")
  @Comment("任职时间")
  @Column("create_Time")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @ColDefine(type = ColType.DATETIME)
  private Date createTime;

  @ApiModelProperty("人员ID")
  @Comment("人员ID")
  @Column("people_Id")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String peopleId;

  @ApiModelProperty("是否领导班子成员")
  @Comment("是否领导班子成员")
  @Column("leader")
  @ColDefine(type = ColType.VARCHAR, width = 8)
  private String leader;

  @ApiModelProperty("成员类别")
  @Comment("成员类别")
  @Column("leader_Type")
  @ColDefine(type = ColType.VARCHAR, width = 8)
  private String leaderType;

  @ApiModelProperty("选拔任用方式")
  @Comment("选拔任用方式")
  @Column("selection_Method")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String selectionMethod;

  @ApiModelProperty("任职状态")
  @Comment("任职状态")
  @Column("status")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String status;

  @ApiModelProperty("免职时间")
  @Comment("免职时间")
  @Column("serve_Time")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @ColDefine(type = ColType.DATETIME)
  private Date serveTime;

  @ApiModelProperty("免职文号")
  @Comment("免职文号")
  @Column("document_Number")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String documentNumber;

  @ApiModelProperty("是否兼任")
  @Comment("是否兼任")
  @Column("djunct")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String djunct;

  @ApiModelProperty("任职文号")
  @Comment("任职文号")
  @Column("document_Duty")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String documentduty;

  @ApiModelProperty("是否纳入实名制管理")
  @Comment("是否纳入实名制管理")
  @Column("real_Name")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String realName;

  public String getDjunct() {
    return djunct;
  }

  public void setDjunct(String djunct) {
    this.djunct = djunct;
  }

  public String getDocumentduty() {
    return documentduty;
  }

  public void setDocumentduty(String documentduty) {
    this.documentduty = documentduty;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLeader() {
    return leader;
  }

  public void setLeader(String leader) {
    this.leader = leader;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getCreateTime() {
    return createTime;
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

  public String getLeaderType() {
    return leaderType;
  }

  public void setLeaderType(String leaderType) {
    this.leaderType = leaderType;
  }

  public String getSelectionMethod() {
    return selectionMethod;
  }

  public void setSelectionMethod(String selectionMethod) {
    this.selectionMethod = selectionMethod;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getServeTime() {
    return serveTime;
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
}
