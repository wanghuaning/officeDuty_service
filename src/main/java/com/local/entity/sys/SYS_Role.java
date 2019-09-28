package com.local.entity.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;
import org.nutz.dao.pager.Pager;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel("角色")
@Table("sys_role")
@Comment("角色表")
public class SYS_Role implements Serializable {
    @Name
    @Prev(els = {@EL("uuid()")})
    @ApiModelProperty("角色id")
    @Comment("角色id")
    @Column("role_id")
    @ColDefine(type = ColType.VARCHAR, width = 128)
    private String roleId;

    @ApiModelProperty("系统id")
    @Comment("系统id")
    @Column("app_id")
    @ColDefine(type = ColType.VARCHAR, width = 128)
    private String appId;

    @ApiModelProperty("角色名称")
    @Comment("角色名称")
    @Column("role_name")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String roleName;

    @ApiModelProperty("角色编码")
    @Comment("角色编码")
    @Column("role_code")
    @ColDefine(type = ColType.VARCHAR, width = 128)
    private String roleCode;

    @ApiModelProperty("描述")
    @Comment("描述")
    @Column("role_note")
    @ColDefine(type = ColType.VARCHAR, width = 512)
    private String roleNote;

    @ApiModelProperty("状态 0:启用，1:未启用")
    @Comment("状态，0：启用；1：未启用")
    @Column("role_state")
    @Default("0")
    @ColDefine(type = ColType.CHAR, width = 1)
    private String roleState;

    @ApiModelProperty("管理员角色 0:不是，1:是")
    @Comment("管理员角色 0:不是，1:是")
    @Column("role_admin")
    @Default("0")
    @ColDefine(type = ColType.CHAR, width = 1)
    private String roleAdmin;

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

    @ApiModelProperty("角色关联菜单")
    @ManyMany(from = "role_id", relation = "sys_role_menu", to = "menu_id")
    private List<SYS_Menu> menus;

    @ApiModelProperty("用户关联角色")
    @ManyMany(from = "role_id", relation = "sys_user_role", to = "user_id")
    private List<SYS_USER> users;

//    @ApiModelProperty("用于数据权限设置，暂时不需要")
//    @ManyMany(from = "role_id", relation = "sys_org_role", to = "org_id")
//    private List<Org> orgs;

    private Pager pager;

    @ApiModelProperty("用于查询机构下用户角色")
    private String oid;

    public SYS_Role() {

    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleNote() {
        return roleNote;
    }

    public void setRoleNote(String roleNote) {
        this.roleNote = roleNote;
    }

    public String getRoleState() {
        return roleState;
    }

    public void setRoleState(String roleState) {
        this.roleState = roleState;
    }

    public String getRoleAdmin() {
        return roleAdmin;
    }

    public void setRoleAdmin(String roleAdmin) {
        this.roleAdmin = roleAdmin;
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

    public List<SYS_Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<SYS_Menu> menus) {
        this.menus = menus;
    }

    public List<SYS_USER> getUsers() {
        return users;
    }

    public void setUsers(List<SYS_USER> users) {
        this.users = users;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}
