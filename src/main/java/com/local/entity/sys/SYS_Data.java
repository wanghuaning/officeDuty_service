package com.local.entity.sys;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;

@ApiModel("数据备份表")
@Table("sys_data")
@Comment("数据备份表")
public class SYS_Data implements Serializable {
  private static final long serialVersionUID = 1L;
  @Name
  @ApiModelProperty("id")//用在属性上，对属性做注释
  @Comment("id")//定义脚本中添加comment属性来添加注释
  @Column("id")
  @ColDefine(type = ColType.VARCHAR, width = 64)
  private String id;

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

  public SYS_Data() {
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
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
