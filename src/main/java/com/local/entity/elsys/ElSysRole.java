package com.local.entity.elsys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@ApiModel("权限表")//用在模型类上，对模型类做注释；
@Table("EL_SYS_ROLE")
@Comment("权限表")
public class ElSysRole implements Serializable {

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

  @ApiModelProperty("名称")
  @Comment("名称")
  @Column("NAME")
  @ColDefine(type = ColType.VARCHAR,width = 255)
  private String name;

  @ApiModelProperty("描述")
  @Comment("描述")
  @Column("REMARK")
  @ColDefine(type = ColType.VARCHAR,width = 255)
  private String remark;

  @ApiModelProperty("数据权限")
  @Comment("数据权限")
  @Column("DATA_SCOPE")
  @ColDefine(type = ColType.VARCHAR,width = 255)
  private String dataScope;

  @ApiModelProperty("角色级别")
  @Comment("角色级别")
  @Column("LEVEL")
  @ColDefine(type = ColType.INT)
  private long level;

  // 中间表多对多关联信息
  @ManyMany(target = ElSysPermission.class, relation = "EL_SYS_ROLES_PERMISSIONS", from = "ROLE_ID", to = "PERMISSION_ID")
  private List<ElSysPermission> permissions;

  // 中间表多对多关联信息
  @ManyMany(target = ElSysMenu.class, relation = "EL_SYS_ROLES_MENUS", from = "ROLE_ID", to = "MENU_ID")
  private List<ElSysMenu> menus;

  // 中间表多对多关联信息
  @ManyMany(target = ElSysDept.class, relation = "EL_SYS_ROLES_DEPTS", from = "ROLE_ID", to = "DEPT_ID")
  private List<ElSysDept> depts;

  public List<ElSysPermission> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<ElSysPermission> permissions) {
    this.permissions = permissions;
  }

  public List<ElSysMenu> getMenus() {
    return menus;
  }

  public void setMenus(List<ElSysMenu> menus) {
    this.menus = menus;
  }

  public List<ElSysDept> getDepts() {
    return depts;
  }

  public void setDepts(List<ElSysDept> depts) {
    this.depts = depts;
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


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }


  public String getDataScope() {
    return dataScope;
  }

  public void setDataScope(String dataScope) {
    this.dataScope = dataScope;
  }


  public long getLevel() {
    return level;
  }

  public void setLevel(long level) {
    this.level = level;
  }

}
