package com.local.entity.elsys;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel("部门表")//用在模型类上，对模型类做注释；
@Table("EL_SYS_DEPT")
@Comment("部门表")
public class ElSysDeptSmall implements Serializable {

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

}
