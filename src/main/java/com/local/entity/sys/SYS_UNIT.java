package com.local.entity.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@ApiModel("单位表")//用在模型类上，对模型类做注释；
@Table("SYS_UNIT")
@Comment("单位表")
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

    @ApiModelProperty("所属序列")
    @Comment("所属序列")
    @Column("category")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String category;

    @ApiModelProperty("机构级别")
    @Comment("机构级别")
    @Column("level")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String level;

    @ApiModelProperty("参照公务员法管理审批文号")
    @Comment("参照公务员法管理审批文号")
    @Column("refer_Official_Document")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String referOfficialDocument;

    @ApiModelProperty("参照公务员法管理审批时间")
    @Comment("参照公务员法管理审批时间")
    @Column("refer_Official_Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ColDefine(type = ColType.DATETIME)
    private Date referOfficialDate;

    @ApiModelProperty("行政编制数")
    @Comment("行政编制数")
    @Column("official_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long officialNum;

    @ApiModelProperty("事业编制数（参公）")
    @Comment("事业编制数（参公）")
    @Column("refer_Official_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long referOfficialNum;

    @ApiModelProperty("县处级正职领导职数")
    @Comment("县处级正职领导职数")
    @Column("right_Place_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long rightPlaceNum;

    @ApiModelProperty("县处级副职领导职数")
    @Comment("县处级副职领导职数")
    @Column("deputy_Place_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long deputyPlaceNum;

    @ApiModelProperty("乡科级正职领导职数")
    @Comment("乡科级正职领导职数")
    @Column("main_Hall_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long mainHallNum;

    @ApiModelProperty("乡科级副职领导职数")
    @Comment("乡科级副职领导职数")
    @Column("deputy_Hall_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long deputyHallNum;

    @ApiModelProperty("一级巡视员职数")
    @Comment("一级巡视员职数")
    @Column("one_Inspector_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long oneInspectorNum;

    @ApiModelProperty("二级巡视员职数")
    @Comment("二级巡视员职数")
    @Column("tow_Inspector_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long towInspectorNum;

    @ApiModelProperty("一级和二级调研员职数")
    @Comment("一级和二级调研员职数")
    @Column("one_Tow_Researcher_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long oneTowResearcherNum;


    @ApiModelProperty("三级和四级调研员职数")
    @Comment("三级和四级调研员职数")
    @Column("three_Four_Researcher_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long threeFourResearcherNum;

    @ApiModelProperty("一级和二级主任科员职数")
    @Comment("一级和二级主任科员职数")
    @Column("one_Tow_Clerk_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long oneTowClerkNum;


    @ApiModelProperty("三级和四级主任科员职数")
    @Comment("三级和四级主任科员职数")
    @Column("three_Four_Clerk_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long threeFourClerkNum;


    @ApiModelProperty("一级调研员职数")
    @Comment("一级调研员职数")
    @Column("one_Researcher_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long oneResearcherNum;

    @ApiModelProperty("二级调研员职数")
    @Comment("二级调研员职数")
    @Column("tow_Researcher_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long towResearcherNum;


    @ApiModelProperty("三级调研员职数")
    @Comment("三级调研员职数")
    @Column("three_Researcher_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long threeResearcherNum;

    @ApiModelProperty("四级调研员职数")
    @Comment("四级调研员职数")
    @Column("four_Researcher_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long fourResearcherNum;

    @ApiModelProperty("一级主任科员职数")
    @Comment("一级主任科员职数")
    @Column("one_Clerk_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long oneClerkNum;

    @ApiModelProperty("二级主任科员职数")
    @Comment("二级主任科员职数")
    @Column("tow_Clerk_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long towClerkNum;

    @ApiModelProperty("三级主任科员职数")
    @Comment("三级主任科员职数")
    @Column("three_Clerk_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long threeClerkNum;

    @ApiModelProperty("四级主任科员职数")
    @Comment("四级主任科员职数")
    @Column("four_Clerk_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private long fourClerkNum;

    @ApiModelProperty("机构状态0:可用 1：停用 2：导入单位没有找到上级")
    @Comment("机构状态")
    @Column("enabled")
    @ColDefine(type = ColType.INT,width = 2)
    private String enabled;

    @ApiModelProperty("上级单位名称")
    @Comment("上级单位名称")
    @Column("parent_Name")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String parentName;

    @ApiModelProperty("备注")
    @Comment("备注")
    @Column("detail")
    @ColDefine(type = ColType.VARCHAR,width = 2000)
    private String detail;

    @ApiModelProperty("排序")
    @Comment("排序")
    @Column("unit_Order")
    @ColDefine(type = ColType.INT,width = 6)
    private String unitOrder;

    @ApiModelProperty("省名称")
    @Comment("省名称")
    @Column("build_Province")
    @ColDefine(type = ColType.VARCHAR,width = 10)
    private String buildProvince;

    @ApiModelProperty("市名称")
    @Comment("市名称")
    @Column("build_City")
    @ColDefine(type = ColType.VARCHAR,width = 10)
    private String buildCity;

    @ApiModelProperty("县名称")
    @Comment("县名称")
    @Column("build_County")
    @ColDefine(type = ColType.VARCHAR,width = 10)
    private String buildCounty;

    @ApiModelProperty("联系人")
    @Comment("联系人")
    @Column("contact")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String contact;

    @ApiModelProperty("联系电话")
    @Comment("联系电话")
    @Column("contact_Number")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String contactNumber;

    private String[] areaStrs;
    private boolean hasChildren;
    private List<SYS_UNIT> children;
    private String value;
    private String label;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public long getOneResearcherNum() {
        return oneResearcherNum;
    }

    public void setOneResearcherNum(long oneResearcherNum) {
        this.oneResearcherNum = oneResearcherNum;
    }

    public long getTowResearcherNum() {
        return towResearcherNum;
    }

    public void setTowResearcherNum(long towResearcherNum) {
        this.towResearcherNum = towResearcherNum;
    }

    public long getThreeResearcherNum() {
        return threeResearcherNum;
    }

    public void setThreeResearcherNum(long threeResearcherNum) {
        this.threeResearcherNum = threeResearcherNum;
    }

    public long getFourResearcherNum() {
        return fourResearcherNum;
    }

    public void setFourResearcherNum(long fourResearcherNum) {
        this.fourResearcherNum = fourResearcherNum;
    }

    public long getOneClerkNum() {
        return oneClerkNum;
    }

    public void setOneClerkNum(long oneClerkNum) {
        this.oneClerkNum = oneClerkNum;
    }

    public long getTowClerkNum() {
        return towClerkNum;
    }

    public void setTowClerkNum(long towClerkNum) {
        this.towClerkNum = towClerkNum;
    }

    public long getThreeClerkNum() {
        return threeClerkNum;
    }

    public void setThreeClerkNum(long threeClerkNum) {
        this.threeClerkNum = threeClerkNum;
    }

    public long getFourClerkNum() {
        return fourClerkNum;
    }

    public void setFourClerkNum(long fourClerkNum) {
        this.fourClerkNum = fourClerkNum;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public String getReferOfficialDocument() {
        return referOfficialDocument;
    }

    public void setReferOfficialDocument(String referOfficialDocument) {
        this.referOfficialDocument = referOfficialDocument;
    }

    public Date getReferOfficialDate() {
        return referOfficialDate;
    }

    public void setReferOfficialDate(Date referOfficialDate) {
        this.referOfficialDate = referOfficialDate;
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

    public long getMainHallNum() {
        return mainHallNum;
    }

    public void setMainHallNum(long mainHallNum) {
        this.mainHallNum = mainHallNum;
    }

    public long getDeputyHallNum() {
        return deputyHallNum;
    }

    public void setDeputyHallNum(long deputyHallNum) {
        this.deputyHallNum = deputyHallNum;
    }

    public long getRightPlaceNum() {
        return rightPlaceNum;
    }

    public void setRightPlaceNum(long rightPlaceNum) {
        this.rightPlaceNum = rightPlaceNum;
    }

    public long getDeputyPlaceNum() {
        return deputyPlaceNum;
    }

    public void setDeputyPlaceNum(long deputyPlaceNum) {
        this.deputyPlaceNum = deputyPlaceNum;
    }

    public long getOneInspectorNum() {
        return oneInspectorNum;
    }

    public void setOneInspectorNum(long oneInspectorNum) {
        this.oneInspectorNum = oneInspectorNum;
    }

    public long getTowInspectorNum() {
        return towInspectorNum;
    }

    public void setTowInspectorNum(long towInspectorNum) {
        this.towInspectorNum = towInspectorNum;
    }

    public long getOneTowResearcherNum() {
        return oneTowResearcherNum;
    }

    public void setOneTowResearcherNum(long oneTowResearcherNum) {
        this.oneTowResearcherNum = oneTowResearcherNum;
    }

    public long getThreeFourResearcherNum() {
        return threeFourResearcherNum;
    }

    public void setThreeFourResearcherNum(long threeFourResearcherNum) {
        this.threeFourResearcherNum = threeFourResearcherNum;
    }

    public long getOneTowClerkNum() {
        return oneTowClerkNum;
    }

    public void setOneTowClerkNum(long oneTowClerkNum) {
        this.oneTowClerkNum = oneTowClerkNum;
    }

    public long getThreeFourClerkNum() {
        return threeFourClerkNum;
    }

    public void setThreeFourClerkNum(long threeFourClerkNum) {
        this.threeFourClerkNum = threeFourClerkNum;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUnitOrder() {
        return unitOrder;
    }

    public void setUnitOrder(String unitOrder) {
        this.unitOrder = unitOrder;
    }

    public String getBuildProvince() {
        return buildProvince;
    }

    public void setBuildProvince(String buildProvince) {
        this.buildProvince = buildProvince;
    }

    public String getBuildCity() {
        return buildCity;
    }

    public void setBuildCity(String buildCity) {
        this.buildCity = buildCity;
    }

    public String getBuildCounty() {
        return buildCounty;
    }

    public void setBuildCounty(String buildCounty) {
        this.buildCounty = buildCounty;
    }

    public String[] getAreaStrs() {
        return areaStrs;
    }

    public void setAreaStrs(String[] areaStrs) {
        this.areaStrs = areaStrs;
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

    public String getValue() {
        return name;
    }

    public void setValue(String value) {
        this.value = name;
    }

    public String getLabel() {
        return name;
    }

    public void setLabel(String label) {
        this.label = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SYS_UNIT)) return false;
        SYS_UNIT unit = (SYS_UNIT) o;
        return officialNum == unit.officialNum &&
                referOfficialNum == unit.referOfficialNum &&
                mainHallNum == unit.mainHallNum &&
                deputyHallNum == unit.deputyHallNum &&
                rightPlaceNum == unit.rightPlaceNum &&
                deputyPlaceNum == unit.deputyPlaceNum &&
                oneInspectorNum == unit.oneInspectorNum &&
                towInspectorNum == unit.towInspectorNum &&
                oneTowResearcherNum == unit.oneTowResearcherNum &&
                threeFourResearcherNum == unit.threeFourResearcherNum &&
                oneTowClerkNum== unit.oneTowClerkNum &&
                threeFourClerkNum == unit.threeFourClerkNum &&
                hasChildren == unit.hasChildren &&
                Objects.equals(id, unit.id) &&
                Objects.equals(code, unit.code) &&
                Objects.equals(name, unit.name) &&
                Objects.equals(parentId, unit.parentId) &&
                Objects.equals(area, unit.area) &&
                Objects.equals(affiliation, unit.affiliation) &&
                Objects.equals(category, unit.category) &&
                Objects.equals(level, unit.level) &&
                Objects.equals(referOfficialDocument, unit.referOfficialDocument) &&
                Objects.equals(referOfficialDate, unit.referOfficialDate) &&
                Objects.equals(enabled, unit.enabled) &&
                Objects.equals(parentName, unit.parentName) &&
                Objects.equals(detail, unit.detail) &&
                Objects.equals(unitOrder, unit.unitOrder) &&
                Objects.equals(buildProvince, unit.buildProvince) &&
                Objects.equals(buildCity, unit.buildCity) &&
                Objects.equals(buildCounty, unit.buildCounty) &&
                Arrays.equals(areaStrs, unit.areaStrs) &&
                Objects.equals(children, unit.children) &&
                Objects.equals(value, unit.value) &&
                Objects.equals(label, unit.label);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, code, name, parentId, area, affiliation, category, level, referOfficialDocument, referOfficialDate, officialNum, referOfficialNum, mainHallNum, deputyHallNum, rightPlaceNum, deputyPlaceNum, oneInspectorNum, towInspectorNum, oneTowResearcherNum, threeFourResearcherNum, oneTowClerkNum, threeFourClerkNum, enabled, parentName, detail, unitOrder, buildProvince, buildCity, buildCounty, hasChildren, children, value, label);
        result = 31 * result + Arrays.hashCode(areaStrs);
        return result;
    }
}
