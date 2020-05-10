package com.local.entity.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.local.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@ApiModel("人员详情")
@Table("sys_Detail")
@Comment("人员详情")
public class SYS_Detail implements Serializable {
    @Name
    @ApiModelProperty("详情id")
    @Comment("详情id")
    @Column("id")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String id;

    @ApiModelProperty("详细")
    @Comment("详细")
    @Column("name")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String name;

    @ApiModelProperty("详情类型，0:人员转态，1:其他")
    @Comment("详情类型，0:人员转态，1:其他")
    @Column("flag")
    @ColDefine(type = ColType.VARCHAR, width = 1)
    private String flag;

    @ApiModelProperty("执行时间")
    @Comment("执行时间")
    @Column("createDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ColDefine(type = ColType.DATETIME)
    private Date createDate;

    private String createTimeStr;

    @ApiModelProperty("人员ID")
    @Comment("人员ID")
    @Column("peopleId")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String peopleId;

    @ApiModelProperty("单位ID")
    @Comment("单位ID")
    @Column("unitId")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String unitId;

    @ApiModelProperty("原单位Id")
    @Comment("原单位Id")
    @Column("oldUnitId")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String oldUnitId;

    @ApiModelProperty("原单位名称")
    @Comment("原单位名称")
    @Column("oldUnitName")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String oldUnitName;

    @ApiModelProperty("单位名称")
    @Comment("单位名称")
    @Column("unitName")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String unitName;

    @ApiModelProperty("人员姓名")
    @Comment("人员姓名")
    @Column("peopleName")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String peopleName;

    @ApiModelProperty("详细")
    @Comment("详细")
    @Column("detail")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String detail;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getOldUnitName() {
        return oldUnitName;
    }

    public void setOldUnitName(String oldUnitName) {
        this.oldUnitName = oldUnitName;
    }

    public String getOldUnitId() {
        return oldUnitId;
    }

    public void setOldUnitId(String oldUnitId) {
        this.oldUnitId = oldUnitId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public String getCreateTimeStr() {
        return DateUtil.dateToString(createDate);
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
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

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }
}
