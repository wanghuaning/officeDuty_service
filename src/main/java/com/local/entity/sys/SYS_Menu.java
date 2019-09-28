package com.local.entity.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel("菜单")
@Table("sys_menu")
@Comment("菜单表")
public class SYS_Menu implements Serializable {
    @Name
    @Prev(els = {@EL("uuid()")})
    @ApiModelProperty("菜单id")
    @Column("menu_id")
    @Comment("菜单id")
    @ColDefine(type = ColType.VARCHAR, width = 128)
    private String menuId;

    @ApiModelProperty("菜单父id")
    @Column("menu_parent_id")
    @Comment("菜单父id")
    @ColDefine(type = ColType.VARCHAR, width = 128)
    private String menuParentId;

    @ApiModelProperty("创建时间")
    @Comment("创建时间")
    @Column("MENU_CREATE_TIME")
    @ColDefine(type = ColType.DATETIME)
    private Date createTime;

    @ApiModelProperty("状态 0:不禁用，1:禁用")
    @Column("menu_state")
    @Comment("状态 0:不禁用，1:禁用")
    @Default("0")
    @ColDefine(type = ColType.CHAR,width = 1)
    private String menuState;

    @ApiModelProperty("菜单名称")
    @Column("menu_name")
    @Comment("菜单名称")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String name;

    @ApiModelProperty("组件")
    @Column("MENU_COMPONENT")
    @Comment("组件")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String component;

    @ApiModelProperty(value = "排序字段",example = "0")
    @Column("menu_sort")
    @Comment("排序字段")
    @Default("99")
    @ColDefine(type = ColType.INT)
    private Integer menuSort;

    @ApiModelProperty("菜单图标")
    @Column("menu_icon")
    @Comment("菜单图标")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String icon;

    @ApiModelProperty("连接地址")
    @Comment("连接地址")
    @Column("MENU_PATH")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String path;

    private List<SYS_Menu> children;

    private boolean alwaysShow;

    private Map meta;

    public SYS_Menu() {

    }

    public String getMenuIcon() {
        if(icon==null){
            icon = "";
        }
        return icon;
    }

    public Map getMeta() {
        Map tmp = new HashMap();
        tmp.put("title",name);
        tmp.put("icon",icon);
        return tmp;
    }

    public boolean isAlwaysShow() {
        if ("1".equals(this.menuState)){
            alwaysShow=false;
        }else {
            alwaysShow=true;
        }
        return alwaysShow;
    }

    public void setAlwaysShow(boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(String menuParentId) {
        this.menuParentId = menuParentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMenuState() {
        return menuState;
    }

    public void setMenuState(String menuState) {
        this.menuState = menuState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Integer getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(Integer menuSort) {
        this.menuSort = menuSort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<SYS_Menu> getChildren() {
        return children;
    }

    public void setChildren(List<SYS_Menu> children) {
        this.children = children;
    }

    public void setMeta(Map meta) {
        this.meta = meta;
    }
}
