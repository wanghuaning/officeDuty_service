package com.local.entity.sys;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

import java.io.Serializable;
import java.util.Date;

@ApiModel("学历学位信息表")//用在模型类上，对模型类做注释；
@Table("sys_education")
@Comment("学历学位信息表")
public class SYS_Education implements Serializable {

  @Name
  @ApiModelProperty("学历id")//用在属性上，对属性做注释
  @Comment("学历id")//定义脚本中添加comment属性来添加注释
  @Column("id")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String id;

  @ApiModelProperty("学历名称")
  @Comment("学历名称")
  @Column("name")
  @ColDefine(type = ColType.VARCHAR, width = 255)
  private String name;

  @ApiModelProperty("入学时间")
  @Comment("入学时间")
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

  @ApiModelProperty("学位名称")
  @Comment("学位名称")
  @Column("degree")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String degree;

  @ApiModelProperty("毕（肄）业时间")
  @Comment("毕（肄）业时间")
  @Column("end_Time")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @ColDefine(type = ColType.DATETIME)
  private Date endTime;

  @ApiModelProperty("学位授予时间")
  @Comment("学位授予时间")
  @Column("degree_Time")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @ColDefine(type = ColType.DATETIME)
  private String degreeTime;

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

  public String getDegree() {
    return degree;
  }

  public void setDegree(String degree) {
    this.degree = degree;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public String getDegreeTime() {
    return degreeTime;
  }

  public void setDegreeTime(String degreeTime) {
    this.degreeTime = degreeTime;
  }
}
