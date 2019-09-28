package com.local.entity.elsys;


import com.local.entity.sys.SYS_Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.List;

@ApiModel("用户表")//用在模型类上，对模型类做注释；
@Table("EL_SYS_USER")
@Comment("用户表")
public class ElSysUser implements Serializable {
  @Name
  @Prev(els = {@EL("uuid")})
  @ApiModelProperty("ID")//用在属性上，对属性做注释
  @Comment("ID")//定义脚本中添加comment属性来添加注释
  @Column("ID")
  @ColDefine(type = ColType.INT)
  private long id;

  @ApiModelProperty("验证码ID")
  private String uuid;
  @ApiModelProperty("验证码")
  private String code;

  @ApiModelProperty("用于系统免登陆")
  private String token;

  @ApiModelProperty("用于系统免登陆获取当前系统权限")
  private String appId;

  @ApiModelProperty("用户关联角色")
  private List<SYS_Role> roles;

  @ApiModelProperty("头像地址")
  @Comment("头像地址")
  @Column("AVATAR")
  @ColDefine(type = ColType.VARCHAR,width = 255)
  private String avatar;

  @ApiModelProperty("创建日期")
  @Comment("创建日期")
  @Column("CREATE_TIME")
  @ColDefine(type = ColType.DATE)
  private java.sql.Timestamp createTime;

  @ApiModelProperty("邮箱")
  @Comment("邮箱")
  @Column("EMAIL")
  @ColDefine(type = ColType.VARCHAR,width = 128)
  private String email;

  @ApiModelProperty("邮箱")
  @Comment("邮箱")
  @Column("ENABLED")
  @ColDefine(type = ColType.INT,width = 32)
  private long enabled;

  @ApiModelProperty("密码")
  @Comment("密码")
  @Column("PASSWORD")
  @ColDefine(type = ColType.VARCHAR,width = 128)
  private String password;

  @ApiModelProperty("用户名")
  @Comment("用户名")
  @Column("USERNAME")
  @ColDefine(type = ColType.VARCHAR,width = 128)
  private String username;

  @ApiModelProperty("上一次密码重置日期")
  @Comment("上一次密码重置日期日期")
  @Column("LAST_PASSWORD_RESET_TIME")
  @ColDefine(type = ColType.DATE)
  private java.sql.Timestamp lastPasswordResetTime;

  @ApiModelProperty("部门")
  @Comment("部门")
  @Column("DEPT_ID")
  @ColDefine(type = ColType.INT,width = 64)
  private long deptId;

  @ApiModelProperty("电话")
  @Comment("电话")
  @Column("PHONE")
  @ColDefine(type = ColType.VARCHAR,width = 128)
  private String phone;

  @ApiModelProperty("工作")
  @Comment("工作")
  @Column("JOB_ID")
  @ColDefine(type = ColType.INT,width = 64)
  private long jobId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public long getEnabled() {
    return enabled;
  }

  public void setEnabled(long enabled) {
    this.enabled = enabled;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public java.sql.Timestamp getLastPasswordResetTime() {
    return lastPasswordResetTime;
  }

  public void setLastPasswordResetTime(java.sql.Timestamp lastPasswordResetTime) {
    this.lastPasswordResetTime = lastPasswordResetTime;
  }


  public long getDeptId() {
    return deptId;
  }

  public void setDeptId(long deptId) {
    this.deptId = deptId;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public long getJobId() {
    return jobId;
  }

  public void setJobId(long jobId) {
    this.jobId = jobId;
  }

}
