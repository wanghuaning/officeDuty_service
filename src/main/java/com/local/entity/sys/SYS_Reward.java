package com.local.entity.sys;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
@ApiModel("奖惩信息表")//用在模型类上，对模型类做注释；
@Table("sys_reward")
@Comment("奖惩信息表")
public class SYS_Reward implements Serializable {

  @Name
  @ApiModelProperty("奖惩id")//用在属性上，对属性做注释
  @Comment("奖惩id")//定义脚本中添加comment属性来添加注释
  @Column("id")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String id;

  @ApiModelProperty("奖惩名称")
  @Comment("奖惩名称")
  @Column("name")
  @ColDefine(type = ColType.VARCHAR, width = 255)
  private String name;

  @ApiModelProperty("奖惩名称代码")
  @Comment("奖惩名称代码")
  @Column("name_Type")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String nameType;

  @ApiModelProperty("批准日期")
  @Comment("批准日期")
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

  @ApiModelProperty("批准机关")
  @Comment("批准机关")
  @Column("approval_unit")
  @ColDefine(type = ColType.VARCHAR, width = 255)
  private String approvalUnit;

  @ApiModelProperty("受奖惩时职务层次")
  @Comment("受奖惩时职务层次")
  @Column("duty")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String duty;

  @ApiModelProperty("撤销日期")
  @Comment("撤销日期")
  @Column("revocation_date")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @ColDefine(type = ColType.DATETIME)
  private Date revocationDate;

  @ApiModelProperty("批准机关性质")
  @Comment("批准机关性质")
  @Column("unit_Type")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String unitType;

  public String getNameType() {
    return nameType;
  }

  public void setNameType(String nameType) {
    this.nameType = nameType;
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

  public String getApprovalUnit() {
    return approvalUnit;
  }

  public void setApprovalUnit(String approvalUnit) {
    this.approvalUnit = approvalUnit;
  }

  public String getDuty() {
    return duty;
  }

  public void setDuty(String duty) {
    this.duty = duty;
  }

  public Date getRevocationDate() {
    return revocationDate;
  }

  public void setRevocationDate(Date revocationDate) {
    this.revocationDate = revocationDate;
  }

  public String getUnitType() {
    return unitType;
  }

  public void setUnitType(String unitType) {
    this.unitType = unitType;
  }
}
