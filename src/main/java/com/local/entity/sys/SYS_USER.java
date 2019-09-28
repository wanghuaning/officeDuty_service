package com.local.entity.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@ApiModel("用户表")//用在模型类上，对模型类做注释；
@Table("sys_user")
@Comment("用户表")
public class SYS_USER implements Serializable {
    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("用户id")//用在属性上，对属性做注释
    @Comment("用户id")//定义脚本中添加comment属性来添加注释
    @Column("user_id")
    @ColDefine(type = ColType.VARCHAR,width = 128)
    private String userId;

    @ApiModelProperty("账号")
    @Comment("账号")
    @NotEmpty(message = "请输入用户名!")
    @Column("user_account")
    @ColDefine(type = ColType.VARCHAR,width = 128)
    private String userAccount;

    @ApiModelProperty("姓名")
    @Comment("姓名")
    @Column("user_name")
    @ColDefine(type = ColType.VARCHAR,width = 225)
    private String userName;

    @ApiModelProperty("密码")
    @Comment("密码")
    @NotEmpty(message = "请输入密码!")
    @Column("user_password")
    @ColDefine(type = ColType.VARCHAR,width = 128)
    private String userPassword;

    @ApiModelProperty("邮箱")
    @Comment("邮箱")
    @Column("user_email")
    @ColDefine(type = ColType.VARCHAR,width = 128)
    private String userEmail;

    @ApiModelProperty("编号")
    @Comment("编号")
    @Column("user_salt")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String userSalt;

    @ApiModelProperty("手机")
    @Comment("手机")
    @Column("user_phone")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String userPhone;

    @ApiModelProperty("头像")
    @Comment("头像")
    @Column("USER_AVATAR")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String avatar;

    @ApiModelProperty("验证码ID")
    private String uuid;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("用于系统免登陆")
    private String token;

    @ApiModelProperty("用于系统免登陆获取当前系统权限")
    private String appId;

//    @ApiModelProperty("用户关联所有菜单[已去重]")
//    private List<SYS_Menu> menus;

    @ApiModelProperty("用户关联角色")
    private List<SYS_Role> roles;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<SYS_Role> getRoles() {
        return roles;
    }

    public void setRoles(List<SYS_Role> roles) {
        this.roles = roles;
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserSalt() {
        return userSalt;
    }

    public void setUserSalt(String userSalt) {
        this.userSalt = userSalt;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
