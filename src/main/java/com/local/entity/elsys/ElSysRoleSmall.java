package com.local.entity.elsys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.List;

@ApiModel("权限表")//用在模型类上，对模型类做注释；
@Table("EL_SYS_ROLE")
@Comment("权限表")
public class ElSysRoleSmall implements Serializable {

  @Id
  @ApiModelProperty("ID")//用在属性上，对属性做注释
  @Comment("ID")//定义脚本中添加comment属性来添加注释
  @Column("ID")
  @ColDefine(type = ColType.INT)
  private long id;

  @ApiModelProperty("名称")
  @Comment("名称")
  @Column("NAME")
  @ColDefine(type = ColType.VARCHAR,width = 255)
  private String name;

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


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
