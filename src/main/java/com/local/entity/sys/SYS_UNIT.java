package com.local.entity.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.List;

@ApiModel("用户表")//用在模型类上，对模型类做注释；
@Table("SYS_UNIT")
@Comment("用户表")
public class SYS_UNIT implements Serializable {
    @Name
//    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("单位id")//用在属性上，对属性做注释
    @Comment("单位id")//定义脚本中添加comment属性来添加注释
    @Column("id")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String id;

    @ApiModelProperty("组织机构编码")
    @Comment("组织机构编码")
    @Column("code")
    @ColDefine(type = ColType.VARCHAR,width = 24)
    private String code;

    @ApiModelProperty("单位名称")
    @Comment("单位名称")
    @Column("name")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String name;

    @ApiModelProperty("简称")
    @Comment("简称")
    @Column("simple_Name")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String simpleName;

    @ApiModelProperty("父单位ID")
    @Comment("父单位ID")
    @Column("parent_Id")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String parentId;

    @ApiModelProperty("所在地区")
    @Comment("所在地区")
    @Column("area")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String area;

    @ApiModelProperty("隶属关系")
    @Comment("隶属关系")
    @Column("affiliation")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String affiliation;

    @ApiModelProperty("机构类别")
    @Comment("机构类别")
    @Column("category")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String category;

    @ApiModelProperty("机构级别")
    @Comment("机构级别")
    @Column("level")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String level;

    @ApiModelProperty("正职领导数")
    @Comment("正职领导数")
    @Column("standing_Leader_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long standingLeaderNum;

    @ApiModelProperty("副职领导数")
    @Comment("副职领导数")
    @Column("voce_Leader_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long voceLeaderNum;

    @ApiModelProperty("正职非领导数")
    @Comment("正职非领导数")
    @Column("standing_Not_Leader_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long standingNotLeaderNum;

    @ApiModelProperty("副职非领导数")
    @Comment("副职非领导数")
    @Column("voce_Not_Leader_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long voceNotLeaderNum;

    @ApiModelProperty("行政编制数")
    @Comment("行政编制数")
    @Column("official_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long officialNum;

    @ApiModelProperty("参照公务员法管理事业单位编制数")
    @Comment("参照公务员法管理事业单位编制数")
    @Column("refer_Official_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long referOfficialNum;

    @ApiModelProperty("其他事业编制数")
    @Comment("其他事业编制数")
    @Column("enterprise_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long enterpriseNum;

    @ApiModelProperty("工勤编制数")
    @Comment("工勤编制数")
    @Column("worker_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long workerNum;

    @ApiModelProperty("其他编制数")
    @Comment("其他编制数")
    @Column("other_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long otherNum;

    @ApiModelProperty("内设机构应配领导正职")
    @Comment("内设机构应配领导正职")
    @Column("internal_Leader_Standing")
    @ColDefine(type = ColType.INT,width = 6)
    private long internalLeaderStanding;

    @ApiModelProperty("内设机构应配领导副职")
    @Comment("内设机构应配领导副职")
    @Column("internal_Leader_Voce")
    @ColDefine(type = ColType.INT,width = 6)
    private long internalLeaderVoce;

    @ApiModelProperty("内设机构应配正职非领导")
    @Comment("内设机构应配正职非领导")
    @Column("internal_Not_Leader_Standing")
    @ColDefine(type = ColType.INT,width = 6)
    private long internalNotLeaderStanding;

    @ApiModelProperty("内设机构应配副职非领导")
    @Comment("内设机构应配副职非领导")
    @Column("internal_Not_Leader_Voce")
    @ColDefine(type = ColType.INT,width = 6)
    private long internalNotLeaderVoce;

    @ApiModelProperty("机构状态")
    @Comment("机构状态")
    @Column("enabled")
    @ColDefine(type = ColType.INT,width = 2)
    private String enabled;


    private String[] areaStrs;
    private boolean hasChildren;
    private List<SYS_UNIT> children;
    private String value;

    private String label;

    public String[] getAreaStrs() {
        return areaStrs;
    }

    public void setAreaStrs(String[] areaStrs) {
        this.areaStrs = areaStrs;
    }

    public String getValue() {
        return id;
    }

    public void setValue(String value) {
        this.value = id;
    }

    public String getLabel() {
        return name;
    }

    public void setLabel(String label) {
        this.label = name;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public List<SYS_UNIT> getChildren() {
        return children;
    }

    public void setChildren(List<SYS_UNIT> children) {
        this.children = children;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStandingLeaderNum() {
        return standingLeaderNum;
    }

    public void setStandingLeaderNum(long standingLeaderNum) {
        this.standingLeaderNum = standingLeaderNum;
    }

    public long getVoceLeaderNum() {
        return voceLeaderNum;
    }

    public void setVoceLeaderNum(long voceLeaderNum) {
        this.voceLeaderNum = voceLeaderNum;
    }

    public long getStandingNotLeaderNum() {
        return standingNotLeaderNum;
    }

    public void setStandingNotLeaderNum(long standingNotLeaderNum) {
        this.standingNotLeaderNum = standingNotLeaderNum;
    }

    public long getVoceNotLeaderNum() {
        return voceNotLeaderNum;
    }

    public void setVoceNotLeaderNum(long voceNotLeaderNum) {
        this.voceNotLeaderNum = voceNotLeaderNum;
    }

    public long getOfficialNum() {
        return officialNum;
    }

    public void setOfficialNum(long officialNum) {
        this.officialNum = officialNum;
    }

    public long getReferOfficialNum() {
        return referOfficialNum;
    }

    public void setReferOfficialNum(long referOfficialNum) {
        this.referOfficialNum = referOfficialNum;
    }

    public long getEnterpriseNum() {
        return enterpriseNum;
    }

    public void setEnterpriseNum(long enterpriseNum) {
        this.enterpriseNum = enterpriseNum;
    }

    public long getWorkerNum() {
        return workerNum;
    }

    public void setWorkerNum(long workerNum) {
        this.workerNum = workerNum;
    }

    public long getOtherNum() {
        return otherNum;
    }

    public void setOtherNum(long otherNum) {
        this.otherNum = otherNum;
    }

    public long getInternalLeaderStanding() {
        return internalLeaderStanding;
    }

    public void setInternalLeaderStanding(long internalLeaderStanding) {
        this.internalLeaderStanding = internalLeaderStanding;
    }

    public long getInternalLeaderVoce() {
        return internalLeaderVoce;
    }

    public void setInternalLeaderVoce(long internalLeaderVoce) {
        this.internalLeaderVoce = internalLeaderVoce;
    }

    public long getInternalNotLeaderStanding() {
        return internalNotLeaderStanding;
    }

    public void setInternalNotLeaderStanding(long internalNotLeaderStanding) {
        this.internalNotLeaderStanding = internalNotLeaderStanding;
    }

    public long getInternalNotLeaderVoce() {
        return internalNotLeaderVoce;
    }

    public void setInternalNotLeaderVoce(long internalNotLeaderVoce) {
        this.internalNotLeaderVoce = internalNotLeaderVoce;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
}
