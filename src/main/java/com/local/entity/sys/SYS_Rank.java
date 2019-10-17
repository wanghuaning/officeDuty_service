package com.local.entity.sys;


import com.fasterxml.jackson.annotation.JsonFormat;
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

  public String getBatch() {
    return batch;
  }

  public void setBatch(String batch) {
    this.batch = batch;
  }
}
