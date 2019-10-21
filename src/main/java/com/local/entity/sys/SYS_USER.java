package com.local.entity.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel("用户表")//用在模型类上，对模型类做注释；
@Table("sys_user")
@Comment("用户表")
public class SYS_USER implements Serializable {
    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("用户id")//用在属性上，对属性做注释
    @Comment("用户id")//定义脚本中添加comment属性来添加注释
    @Column("id")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String id;

    @ApiModelProperty("头像地址")
    @Comment("头像地址")
    @Column("avatar")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String avatar;

    @ApiModelProperty("创建日期")
    @Comment("创建日期")
    @Column("create_time")
    @ColDefine(type = ColType.DATE)
    private Date createTime;

    @ApiModelProperty("账号")
    @Comment("账号")
    @NotEmpty(message = "请输入用户名!")
    @Column("user_account")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String userAccount;

    @ApiModelProperty("密码")
    @Comment("密码")
    @NotEmpty(message = "请输入密码!")
    @Column("user_password")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String userPassword;

    @ApiModelProperty("最后修改密码的日期")
    @Comment("最后修改密码的日期")
    @Column("last_password_reset_time")
    @ColDefine(type = ColType.DATE)
    private String lastTime;

    @ApiModelProperty("状态：1启用、0禁用")
    @Comment("状态：1启用、0禁用")
    @Column("enabled")
    @ColDefine(type = ColType.INT)
    private String enabled;

    @ApiModelProperty("人员ID")
    @Comment("人员ID")
    @Column("people_id")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String peopleId;

    @ApiModelProperty("单位ID")
    @Comment("单位ID")
    @Column("unit_id")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String unitId;

    @ApiModelProperty("验证码ID")
    private String uuid;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("用于系统免登陆")
    private String token;

    private SYS_UNIT unit;

//    @ApiModelProperty("用户关联所有菜单[已去重]")
//    private List<SYS_Menu> menus;


    public SYS_UNIT getUnit() {
        return unit;
    }

    public void setUnit(SYS_UNIT unit) {
        this.unit = unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(String peopleId) {
        this.peopleId = peopleId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
