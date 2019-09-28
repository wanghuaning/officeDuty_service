package com.local.entity.elsys;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.List;

@ApiModel("权限表")//用在模型类上，对模型类做注释；
@Table("EL_SYS_PERMISSION")
@Comment("权限表")
public class ElSysPermission implements Serializable {

  @Id
  @ApiModelProperty("ID")//用在属性上，对属性做注释
  @Comment("ID")//定义脚本中添加comment属性来添加注释
  @Column("ID")
  @ColDefine(type = ColType.INT)
  private long id;

  @ApiModelProperty("别名")
  @Comment("别名")
  @Column("ALIAS")
  @ColDefine(type = ColType.VARCHAR,width = 255)
  private String alias;

  @ApiModelProperty("创建日期")
  @Comment("创建日期")
  @Column("CREATE_TIME")
  @ColDefine(type = ColType.DATE)
  private java.sql.Timestamp createTime;

  @ApiModelProperty("名称")
  @Comment("名称")
  @Column("NAME")
  @ColDefine(type = ColType.VARCHAR,width = 255)
  private String name;

  @ApiModelProperty("上级权限")
  @Comment("上级权限")
  @Column("PID")
  @ColDefine(type = ColType.INT)
  private long pid;

  @ApiModelProperty("角色ID")
  @Comment("角色ID")
  @Column("ROLE_ID")
  @ColDefine(type = ColType.INT)
  private long role_Id;

  public long getRole_Id() {
    return role_Id;
  }

  public void setRole_Id(long role_Id) {
    this.role_Id = role_Id;
  }

  private List<ElSysPermission>  children;

  public List<ElSysPermission> getChildren() {
    return children;
  }

  public void setChildren(List<ElSysPermission> children) {
    this.children = children;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public long getPid() {
    return pid;
  }

  public void setPid(long pid) {
    this.pid = pid;
  }

}
