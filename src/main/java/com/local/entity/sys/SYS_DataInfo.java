package com.local.entity.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@ApiModel("数据备份详情表")
@Table("sys_dataInfo")
@Comment("数据备份详情表")
public class SYS_DataInfo implements Serializable {
  private static final long serialVersionUID = 1L;
  @Name
  @ApiModelProperty("id")//用在属性上，对属性做注释
  @Comment("id")//定义脚本中添加comment属性来添加注释
  @Column("id")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String id;

  @ApiModelProperty("数据备份表ID")
  @Comment("数据备份表ID")
  @Column("data_Id")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String dataId;

  @ApiModelProperty("数据类型；上行、下行")
  @Comment("数据类型；上行、下行")
  @Column("type")
  @ColDefine(type = ColType.VARCHAR, width = 10)
  private String type;

  @ApiModelProperty("单位ID")
  @Comment("单位ID")
  @Column("unit_Id")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String unitId;

  @ApiModelProperty("数据参数")
  @Comment("数据参数")
  @Column("param")
  @ColDefine(type = ColType.TEXT)
  private String param;

  @ApiModelProperty("审批前数据参数")
  @Comment("审批前数据参数")
  @Column("before_Param")
  @ColDefine(type = ColType.TEXT)
  private String beforeParam;

  @ApiModelProperty("操作时间")
  @Comment("操作时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Column("op_Time")
  @ColDefine(type = ColType.DATETIME)
  private Date opTime;

  @Column("user_Flag")
  @Comment("应用标记 0未使用 1已应用")
  @Default("0")
  @ColDefine(type = ColType.CHAR, width = 1)
  private String delFlag;

  @ApiModelProperty("数据表名称")
  @Comment("数据表名称")
  @Column("table_Name")
  @ColDefine(type = ColType.VARCHAR, width = 32)
  private String tableName;

  public SYS_DataInfo() {
  }

  public String getBeforeParam() {
    return beforeParam;
  }

  public void setBeforeParam(String beforeParam) {
    this.beforeParam = beforeParam;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getDataId() {
    return dataId;
  }

  public void setDataId(String dataId) {
    this.dataId = dataId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public String getParam() {
    return param;
  }

  public void setParam(String param) {
    this.param = param;
  }

  public Date getOpTime() {
    return opTime;
  }

  public void setOpTime(Date opTime) {
    this.opTime = opTime;
  }

  public String getDelFlag() {
    return delFlag;
  }

  public void setDelFlag(String delFlag) {
    this.delFlag = delFlag;
  }
}
