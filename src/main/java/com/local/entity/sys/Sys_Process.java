package com.local.entity.sys;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.local.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel("审批管理表")//用在模型类上，对模型类做注释；
@Table("sys_process")
@Comment("审批管理表")
public class Sys_Process implements Serializable {
  @Name
  @ApiModelProperty("审批id")//用在属性上，对属性做注释
  @Comment("审批id")//定义脚本中添加comment属性来添加注释
  @Column("id")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String id;

  @ApiModelProperty("审批表类型，0：备案表 1：职数审批表")
  @Comment("审批表类型，0：备案表 1：职数审批表")
  @Column("flag")
  @ColDefine(type = ColType.VARCHAR, width = 10)
  private String flag;

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

  @ApiModelProperty("数据参数")
  @Comment("数据参数")
  @Column("param")
  @ColDefine(type = ColType.TEXT)
  private String param;

  @ApiModelProperty("单位名称及人员")
  @Comment("单位名称及人员")
  @Column("people")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String people;

  @ApiModelProperty("创建日期")
  @Comment("创建日期")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Column("create_time")
  @ColDefine(type = ColType.DATETIME)
  private Date createTime;
  private String createTimeStr;

  @ApiModelProperty("审批日期")
  @Comment("审批日期")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Column("process_time")
  @ColDefine(type = ColType.DATETIME)
  private Date processTime;
  private String processTimeStr;

  @ApiModelProperty("审批状态")
  @Comment("审批状态")
  @Column("states")
  @ColDefine(type = ColType.VARCHAR, width = 8)
  private String states;

  @ApiModelProperty("父ID")
  @Comment("父ID")
  @Column("parent_Id")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String parentId;

  private String processFlag;

  public String getProcessFlag() {
    if ("0".equals(flag)){
      return "晋升职级人员备案名册";
    }else {
      return "职级职数使用审批表";
    }
  }

  private List<Sys_Process> children;

    public List<Sys_Process> getChildren() {
        return children;
    }

    public void setChildren(List<Sys_Process> children) {
        this.children = children;
    }

    public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public void setProcessFlag(String processFlag) {
    this.processFlag = processFlag;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public String getProcessTimeStr() {
    return DateUtil.dateToString(processTime);
  }

  public void setProcessTimeStr(String processTimeStr) {
    this.processTimeStr = processTimeStr;
  }

  public String getCreateTimeStr() {
    return DateUtil.dateToString(createTime);
  }

  public void setCreateTimeStr(String createTimeStr) {
    this.createTimeStr = createTimeStr;
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

  public String getParam() {
    return param;
  }

  public void setParam(String param) {
    this.param = param;
  }

  public String getPeople() {
    return people;
  }

  public void setPeople(String people) {
    this.people = people;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getProcessTime() {
    return processTime;
  }

  public void setProcessTime(Date processTime) {
    this.processTime = processTime;
  }

  public String getStates() {
    return states;
  }

  public void setStates(String states) {
    this.states = states;
  }
}
