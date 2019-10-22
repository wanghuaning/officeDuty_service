package com.local.entity.sys;


import com.local.util.StrUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Table("sys_log")
@Comment("操作日志表")
public class SYS_Log implements Serializable {
  private static final long serialVersionUID = 1L;
  @Column
  @Name
  private String id;

  @Column
  @Comment("日志类型，CRUD")
  @ColDefine(type = ColType.VARCHAR, width = 10)
  private String type;

  @Column
  @Comment("日志描述")
  @ColDefine(type = ColType.VARCHAR, width = 255)
  private String tag;

  @Column
  @Comment("执行类")
  @ColDefine(type = ColType.VARCHAR, width = 100)
  private String src;

  @Column
  @Comment("来源IP")
  @ColDefine(type = ColType.VARCHAR, width = 255)
  private String ip;

  @Column
  @Comment("请求参数")
  @ColDefine(type = ColType.TEXT)
  private String param;

  @Column("op_by")
  @Comment("操作人id")
  @ColDefine(type = ColType.VARCHAR, width = 128)
  private String opBy;

  @Column("op_name")
  @Comment("操作人名称")
  @ColDefine(type = ColType.VARCHAR, width = 32)
  private String opName;

  @Column("op_time")
  @Comment("操作时间")
  @Default("op_Time")
  @ColDefine(type = ColType.DATETIME)
  private Date opTime;

  @Column("del_flag")
  @Comment("删除标记 0正常 1删除")
  @Default("0")
  @ColDefine(type = ColType.CHAR, width = 1)
  private String delFlag;


  public SYS_Log() {
  }


  public SYS_Log(String type, String tag, String src, String ip, String opBy, String opName, String param) {
    this.type = type;
    this.tag = tag;
    this.src = src;
    this.ip = ip;
    this.opBy = opBy;
    this.opName = opName;
    this.param = param;
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

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getOpBy() {
    return opBy;
  }

  public void setOpBy(String opBy) {
    this.opBy = opBy;
  }

  public String getOpName() {
    return opName;
  }

  public void setOpName(String opName) {
    this.opName = opName;
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
    public static SYS_Log c(String type, String tag, String src,String ip, SYS_USER user, String param) {
        String uid = "";
        String uname = "";
        if(!StrUtils.isBlank(user)&&!StrUtils.isBlank(user.getId())){
            uid = user.getId();
        }
        if(!StrUtils.isBlank(user)&&!StrUtils.isBlank(user.getUserAccount())){
            uname = user.getUserAccount();
        }
        SYS_Log sysLog = new SYS_Log(type,tag,src,ip,uid,uname,param);
        sysLog.setId(UUID.randomUUID().toString().replace("-", ""));
        sysLog.setOpTime(new Date());
        return sysLog;
    }
}
