package com.local.entity.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;

@ApiModel("应用系统")
@Table("SYS_AREA")
public class SYS_AREA implements Serializable {

    @Name
    @Prev(els = {@EL("uuid()")})
    @ApiModelProperty("系统id")
    @Comment("系统id")
    @Column("AREA_CODE")
    @ColDefine(type = ColType.VARCHAR, width = 128)
    private String AREA_CODE;

    @ApiModelProperty("系统名称")
    @Comment("系统名称")
    @Column("AREA_PARENT_CODE")
    @ColDefine(type = ColType.VARCHAR, width = 128)
    private String AREA_PARENT_CODE;

    @ApiModelProperty("系统编码")
    @Comment("系统编码")
    @Column("AREA_NAME")
    @ColDefine(type = ColType.VARCHAR, width = 128)
    private String AREA_NAME;

    public String getAREA_CODE() {
        return AREA_CODE;
    }

    public void setAREA_CODE(String AREA_CODE) {
        this.AREA_CODE = AREA_CODE;
    }

    public String getAREA_PARENT_CODE() {
        return AREA_PARENT_CODE;
    }

    public void setAREA_PARENT_CODE(String AREA_PARENT_CODE) {
        this.AREA_PARENT_CODE = AREA_PARENT_CODE;
    }

    public String getAREA_NAME() {
        return AREA_NAME;
    }

    public void setAREA_NAME(String AREA_NAME) {
        this.AREA_NAME = AREA_NAME;
    }

}
