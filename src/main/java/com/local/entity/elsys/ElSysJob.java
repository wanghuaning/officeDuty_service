package com.local.entity.elsys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;
import java.io.Serializable;
import java.util.Date;


@ApiModel("岗位表")//用在模型类上，对模型类做注释；
@Table("EL_SYS_JOB")
@Comment("岗位表")
public class ElSysJob implements Serializable {

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

  @ApiModelProperty("是否启用")
  @Comment("是否启用")
  @Column("ENABLED")
  @ColDefine(type = ColType.INT)
  private long enabled;

  @ApiModelProperty("创建日期")
  @Comment("创建日期")
  @Column("CREATE_TIME")
  @ColDefine(type = ColType.DATE)
  private java.sql.Timestamp createTime;

  @ApiModelProperty("排序")
  @Comment("排序")
  @Column("SORT")
  @ColDefine(type = ColType.INT)
  private long sort;

  @ApiModelProperty("部门ID")
  @Comment("部门ID")
  @Column("DEPT_ID")
  @ColDefine(type = ColType.INT)
  private long deptId;

  private ElSysDept dept;
  /**
   * 如果分公司存在相同部门，则显示上级部门名称
   */
  private String deptSuperiorName;

  public String getDeptSuperiorName() {
    return deptSuperiorName;
  }

  public void setDeptSuperiorName(String deptSuperiorName) {
    this.deptSuperiorName = deptSuperiorName;
  }

  public ElSysDept getDept() {
    return dept;
  }

  public void setDept(ElSysDept dept) {
    this.dept = dept;
  }

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

  public long getEnabled() {
    return enabled;
  }

  public void setEnabled(long enabled) {
    this.enabled = enabled;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }

  public long getSort() {
    return sort;
  }

  public void setSort(long sort) {
    this.sort = sort;
  }

  public long getDeptId() {
    return deptId;
  }

  public void setDeptId(long deptId) {
    this.deptId = deptId;
  }
}
