package com.local.entity.elsys;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.List;

@ApiModel("菜单表")//用在模型类上，对模型类做注释；
@Table("EL_SYS_MENU")
@Comment("菜单表")
public class ElSysMenu implements Serializable {

  @Id
  @ApiModelProperty("ID")//用在属性上，对属性做注释
  @Comment("ID")//定义脚本中添加comment属性来添加注释
  @Column("ID")
  @ColDefine(type = ColType.INT)
  private long id;

  @ApiModelProperty("创建日期")
  @Comment("创建日期")
  @Column("CREATE_TIME")
  @ColDefine(type = ColType.DATE)
  private java.sql.Timestamp createTime;

  @ApiModelProperty("是否外链")
  @Comment("是否外链")
  @Column("I_FRAME")
  @ColDefine(type = ColType.INT)
  private long iFrame;

  @ApiModelProperty("菜单名称")
  @Comment("菜单名称")
  @Column("NAME")
  @ColDefine(type = ColType.VARCHAR,width = 255)
  private String name;

  @ApiModelProperty("组件")
  @Comment("组件")
  @Column("COMPONENT")
  @ColDefine(type = ColType.VARCHAR,width = 255)
  private String component;

  @ApiModelProperty("上级菜单ID")
  @Comment("上级菜单ID")
  @Column("PID")
  @ColDefine(type = ColType.INT)
  private long pid;

  @ApiModelProperty("排序")
  @Comment("排序")
  @Column("SORT")
  @ColDefine(type = ColType.INT)
  private long sort;

  @ApiModelProperty("图标")
  @Comment("图标")
  @Column("ICON")
  @ColDefine(type = ColType.VARCHAR,width = 255)
  private String icon;

  @ApiModelProperty("链接地址")
  @Comment("链接地址")
  @Column("PATH")
  @ColDefine(type = ColType.VARCHAR,width = 255)
  private String path;

  private List<ElSysMenu> children;

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

  public List<ElSysMenu> getChildren() {
    return children;
  }

  public void setChildren(List<ElSysMenu> children) {
    this.children = children;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }


  public long getIFrame() {
    return iFrame;
  }

  public void setIFrame(long iFrame) {
    this.iFrame = iFrame;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getComponent() {
    return component;
  }

  public void setComponent(String component) {
    this.component = component;
  }


  public long getPid() {
    return pid;
  }

  public void setPid(long pid) {
    this.pid = pid;
  }


  public long getSort() {
    return sort;
  }

  public void setSort(long sort) {
    this.sort = sort;
  }


  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }


  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

}
