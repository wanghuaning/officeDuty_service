package com.local.entity.sys;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;

@ApiModel("部门表")//用在模型类上，对模型类做注释；
@Table("EL_SYS_DEPT")
@Comment("部门表")
public class SYS_Dept implements Serializable {

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

    @ApiModelProperty("上级部门")
    @Comment("上级部门")
    @Column("PID")
    @ColDefine(type = ColType.INT)
    private long pid;

    @ApiModelProperty("创建日期")
    @Comment("创建日期")
    @Column("CREATE_TIME")
    @ColDefine(type = ColType.DATE)
    private java.sql.Timestamp createTime;

    @NotNull
    @ApiModelProperty("是否启用")
    @Comment("是否启用")
    @Column("ENABLED")
    @ColDefine(type = ColType.INT)
    private long enabled;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SYS_Dept> children;

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

    public List<SYS_Dept> getChildren() {
        return children;
    }

    public void setChildren(List<SYS_Dept> children) {
        this.children = children;
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

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }


    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }


    public long getEnabled() {
        return enabled;
    }

    public void setEnabled(long enabled) {
        this.enabled = enabled;
    }

}

