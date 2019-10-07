package com.local.entity.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.List;

@ApiModel("地区标识表")//用在模型类上，对模型类做注释；
@Table("sys_area")
@Comment("地区标识表")
public class SYS_AREA implements Serializable {
    @Name
    @ApiModelProperty("代码")
    @Comment("代码")
    @Column("Code")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String code;

    @ApiModelProperty("父级代码")
    @Comment("父级代码")
    @Column("Up_Code")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String upCode;

    @ApiModelProperty("区域名字")
    @Comment("区域名字")
    @Column("Area_Name")
    @ColDefine(type = ColType.VARCHAR,width = 128)
    private String areaName;

    @ApiModelProperty("类型")
    @Comment("类型")
    @Column("type")
    @ColDefine(type = ColType.CHAR,width = 1)
    private String type;

    @ApiModelProperty("可用标示")
    @Comment("可用标示")
    @Column("Available_Flag")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String availableFlag;

    private String value;

    private String label;
    private List<SYS_AREA> children;

    public String getValue() {
        return code;
    }

    public void setValue(String value) {
        this.value = code;
    }

    public String getLabel() {
        return areaName;
    }

    public void setLabel(String label) {
        this.label = areaName;
    }

    public List<SYS_AREA> getChildren() {
        return children;
    }

    public void setChildren(List<SYS_AREA> children) {
        this.children = children;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUpCode() {
        return upCode;
    }

    public void setUpCode(String upCode) {
        this.upCode = upCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvailableFlag() {
        return availableFlag;
    }

    public void setAvailableFlag(String availableFlag) {
        this.availableFlag = availableFlag;
    }
}
