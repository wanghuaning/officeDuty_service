package com.local.entity.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;
import org.nutz.dao.pager.Pager;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel("应用系统")
@Table("sys_app")
@Comment("应用系统")
public class SYS_App implements Serializable {
    @Name
    @Prev(els = {@EL("uuid()")})
    @ApiModelProperty("系统id")
    @Comment("系统id")
    @Column("app_id")
    @ColDefine(type = ColType.VARCHAR, width = 128)
    private String appId;

    @ApiModelProperty("系统名称")
    @Comment("系统名称")
    @Column("app_name")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String appName;

    @ApiModelProperty("系统编码")
    @Comment("系统编码")
    @Column("app_code")
    @ColDefine(type = ColType.VARCHAR, width = 128)
    private String appCode;

    @ApiModelProperty(value = "系统排序",example = "99")
    @Comment("系统排序")
    @Column("app_sort")
    @ColDefine(type = ColType.INT)
    private int appSort;

    @ApiModelProperty("系统图标")
    @Comment("系统图标")
    @Column("app_icon")
    @ColDefine(type = ColType.VARCHAR, width = 128)
    private String appIcon;

    @ApiModelProperty("系统简介")
    @Comment("系统简介")
    @Column("app_desc")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String appDesc;

    @ApiModelProperty("系统路径")
    @Comment("系统路径")
    @Column("app_path")
    @ColDefine(type = ColType.VARCHAR, width = 128)
    private String appPath;

    @ApiModelProperty("系统开发路径")
    @Comment("系统开发路径")
    @Column("app_dev_path")
    @ColDefine(type = ColType.VARCHAR, width = 128)
    private String appDevPath;

    @ApiModelProperty("是否有外部用户，0无，1有")
    @Comment("是否有外部用户，0无，1有")
    @Column("app_range")
    @Default("0")
    @ColDefine(type = ColType.CHAR, width = 1)
    private String appRange;

    @ApiModelProperty("状态 0:启用，1:未启用")
    @Comment("状态，0：启用；1：未启用")
    @Column("app_state")
    @Default("0")
    @ColDefine(type = ColType.CHAR, width = 1)
    private String appState;

    @ApiModelProperty("操作人id")
    @Comment("操作人id")
    @Column("opBy")
    @ColDefine(type = ColType.VARCHAR, width = 128)
    private String opBy;

    @ApiModelProperty("操作时间")
    @Comment("操作时间")
    @Column("opAt")
    @Prev(els = @EL("$me.now()"))
    @ColDefine(type = ColType.DATETIME)
    private Date opAt;

    @ApiModelProperty("具有本系统的用户列表")
    @ManyMany(from = "app_id", relation = "sys_user_app", to = "user_id")
    private List<SYS_USER> users;

    @ApiModelProperty("角色列表")
    @Many(field = "appId")
    private List<SYS_Role> roles;

    private Pager pager;

    public SYS_App() {

    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public int getAppSort() {
        return appSort;
    }

    public void setAppSort(int appSort) {
        this.appSort = appSort;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    public String getAppDevPath() {
        return appDevPath;
    }

    public void setAppDevPath(String appDevPath) {
        this.appDevPath = appDevPath;
    }

    public String getAppRange() {
        return appRange;
    }

    public void setAppRange(String appRange) {
        this.appRange = appRange;
    }

    public String getAppState() {
        return appState;
    }

    public void setAppState(String appState) {
        this.appState = appState;
    }

    public String getOpBy() {
        return opBy;
    }

    public void setOpBy(String opBy) {
        this.opBy = opBy;
    }

    public Date getOpAt() {
        return opAt;
    }

    public void setOpAt(Date opAt) {
        this.opAt = opAt;
    }

    public List<SYS_USER> getUsers() {
        return users;
    }

    public void setUsers(List<SYS_USER> users) {
        this.users = users;
    }

    public List<SYS_Role> getRoles() {
        return roles;
    }

    public void setRoles(List<SYS_Role> roles) {
        this.roles = roles;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }
}
