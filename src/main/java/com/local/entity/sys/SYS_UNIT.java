package com.local.entity.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.local.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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

    @ApiModelProperty("单位性质")
    @Comment("单位性质")
    @Column("unit_Type")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String unitType;

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

    private String referOfficialDateStr;

    @ApiModelProperty("行政编制数")
    @Comment("行政编制数")
    @Column("official_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long officialNum;

    @ApiModelProperty("事业编制数（参公）")
    @Comment("事业编制数（参公）")
    @Column("refer_Official_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long referOfficialNum;

    @ApiModelProperty("行政实有数")
    @Comment("行政实有数")
    @Column("official_Real_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long officialRealNum;

    @ApiModelProperty("事业实有数（参公）")
    @Comment("事业实有数（参公）")
    @Column("refer_Official_Real_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long referOfficialRealNum;

    @ApiModelProperty("县处级正职领导职数")
    @Comment("县处级正职领导职数")
    @Column("right_Place_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long rightPlaceNum;

    @ApiModelProperty("县处级副职领导职数")
    @Comment("县处级副职领导职数")
    @Column("deputy_Place_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long deputyPlaceNum;

    @ApiModelProperty("乡科级正职领导职数")
    @Comment("乡科级正职领导职数")
    @Column("main_Hall_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long mainHallNum;

    @ApiModelProperty("乡科级副职领导职数")
    @Comment("乡科级副职领导职数")
    @Column("deputy_Hall_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long deputyHallNum;

    @ApiModelProperty("一级巡视员职数")
    @Comment("一级巡视员职数")
    @Column("one_Inspector_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long oneInspectorNum;

    @ApiModelProperty("二级巡视员职数")
    @Comment("二级巡视员职数")
    @Column("tow_Inspector_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long towInspectorNum;

    @ApiModelProperty("一级和二级调研员职数")
    @Comment("一级和二级调研员职数")
    @Column("one_Tow_Researcher_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long oneTowResearcherNum;


    @ApiModelProperty("三级和四级调研员职数")
    @Comment("三级和四级调研员职数")
    @Column("three_Four_Researcher_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long threeFourResearcherNum;

    @ApiModelProperty("一级和二级主任科员职数")
    @Comment("一级和二级主任科员职数")
    @Column("one_Tow_Clerk_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long oneTowClerkNum;


    @ApiModelProperty("三级和四级主任科员职数")
    @Comment("三级和四级主任科员职数")
    @Column("three_Four_Clerk_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long threeFourClerkNum;


    @ApiModelProperty("一级调研员职数")
    @Comment("一级调研员职数")
    @Column("one_Researcher_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long oneResearcherNum;

    @ApiModelProperty("二级调研员职数")
    @Comment("二级调研员职数")
    @Column("tow_Researcher_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long towResearcherNum;


    @ApiModelProperty("三级调研员职数")
    @Comment("三级调研员职数")
    @Column("three_Researcher_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long threeResearcherNum;

    @ApiModelProperty("四级调研员职数")
    @Comment("四级调研员职数")
    @Column("four_Researcher_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long fourResearcherNum;

    @ApiModelProperty("一级主任科员职数")
    @Comment("一级主任科员职数")
    @Column("one_Clerk_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long oneClerkNum;

    @ApiModelProperty("二级主任科员职数")
    @Comment("二级主任科员职数")
    @Column("tow_Clerk_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long towClerkNum;

    @ApiModelProperty("三级主任科员职数")
    @Comment("三级主任科员职数")
    @Column("three_Clerk_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long threeClerkNum;

    @ApiModelProperty("四级主任科员职数")
    @Comment("四级主任科员职数")
    @Column("four_Clerk_Num")
    @ColDefine(type = ColType.INT,width = 6)
    private Long fourClerkNum;

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
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String buildCity;

    @ApiModelProperty("县名称")
    @Comment("县名称")
    @Column("build_County")
    @ColDefine(type = ColType.VARCHAR,width = 64)
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

    @ApiModelProperty("注册码")
    @Comment("注册码")
    @Column("reg_Code")
    @ColDefine(type = ColType.TEXT)
    private String regCode;

    @ApiModelProperty("是否有子单位0:无，1：有")
    @Comment("是否有子单位0:无，1：有")
    @Column("hasChild")
    @Default("0")
    @ColDefine(type = ColType.VARCHAR,width = 1)
    private String hasChild;

    @ApiModelProperty("是否可修改0:可，1：否")
    @Comment("是否可修改0:可，1：否")
    @Column("isEdit")
    @Default("0")
    @ColDefine(type = ColType.VARCHAR,width = 1)
    private String isEdit;
    private String[] areaStrs;
    private boolean hasChildren;

    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SYS_UNIT> children;
    private String value;
    private String label;


    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }

    public String getHasChild() {
        return hasChild;
    }

    public void setHasChild(String hasChild) {
        this.hasChild = hasChild;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

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

    public Long getOneResearcherNum() {
        return oneResearcherNum;
    }

    public void setOneResearcherNum(Long oneResearcherNum) {
        this.oneResearcherNum = oneResearcherNum;
    }

    public Long getTowResearcherNum() {
        return towResearcherNum;
    }

    public void setTowResearcherNum(Long towResearcherNum) {
        this.towResearcherNum = towResearcherNum;
    }

    public Long getThreeResearcherNum() {
        return threeResearcherNum;
    }

    public void setThreeResearcherNum(Long threeResearcherNum) {
        this.threeResearcherNum = threeResearcherNum;
    }

    public Long getFourResearcherNum() {
        return fourResearcherNum;
    }

    public void setFourResearcherNum(Long fourResearcherNum) {
        this.fourResearcherNum = fourResearcherNum;
    }

    public Long getOneClerkNum() {
        return oneClerkNum;
    }

    public void setOneClerkNum(Long oneClerkNum) {
        this.oneClerkNum = oneClerkNum;
    }

    public Long getTowClerkNum() {
        return towClerkNum;
    }

    public void setTowClerkNum(Long towClerkNum) {
        this.towClerkNum = towClerkNum;
    }

    public Long getThreeClerkNum() {
        return threeClerkNum;
    }

    public void setThreeClerkNum(Long threeClerkNum) {
        this.threeClerkNum = threeClerkNum;
    }

    public Long getFourClerkNum() {
        return fourClerkNum;
    }

    public void setFourClerkNum(Long fourClerkNum) {
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

    public String getReferOfficialDateStr() {
        return DateUtil.dateToString(referOfficialDate);
    }

    public void setReferOfficialDateStr(String referOfficialDateStr) {
        this.referOfficialDateStr = referOfficialDateStr;
    }

    public Date getReferOfficialDate() {
        return DateUtil.parseDateYMD(referOfficialDate);
    }

    public void setReferOfficialDate(Date referOfficialDate) {
        this.referOfficialDate = referOfficialDate;
    }

    public Long getOfficialNum() {
        return officialNum;
    }

    public void setOfficialNum(Long officialNum) {
        this.officialNum = officialNum;
    }

    public Long getReferOfficialNum() {
        return referOfficialNum;
    }

    public void setReferOfficialNum(Long referOfficialNum) {
        this.referOfficialNum = referOfficialNum;
    }

    public Long getMainHallNum() {
        return mainHallNum;
    }

    public void setMainHallNum(Long mainHallNum) {
        this.mainHallNum = mainHallNum;
    }

    public Long getDeputyHallNum() {
        return deputyHallNum;
    }

    public void setDeputyHallNum(Long deputyHallNum) {
        this.deputyHallNum = deputyHallNum;
    }

    public Long getRightPlaceNum() {
        return rightPlaceNum;
    }

    public void setRightPlaceNum(Long rightPlaceNum) {
        this.rightPlaceNum = rightPlaceNum;
    }

    public Long getDeputyPlaceNum() {
        return deputyPlaceNum;
    }

    public void setDeputyPlaceNum(Long deputyPlaceNum) {
        this.deputyPlaceNum = deputyPlaceNum;
    }

    public Long getOneInspectorNum() {
        return oneInspectorNum;
    }

    public void setOneInspectorNum(Long oneInspectorNum) {
        this.oneInspectorNum = oneInspectorNum;
    }

    public Long getTowInspectorNum() {
        return towInspectorNum;
    }

    public void setTowInspectorNum(Long towInspectorNum) {
        this.towInspectorNum = towInspectorNum;
    }

    public Long getOneTowResearcherNum() {
        return oneTowResearcherNum;
    }

    public void setOneTowResearcherNum(Long oneTowResearcherNum) {
        this.oneTowResearcherNum = oneTowResearcherNum;
    }

    public Long getThreeFourResearcherNum() {
        return threeFourResearcherNum;
    }

    public void setThreeFourResearcherNum(Long threeFourResearcherNum) {
        this.threeFourResearcherNum = threeFourResearcherNum;
    }

    public Long getOneTowClerkNum() {
        return oneTowClerkNum;
    }

    public void setOneTowClerkNum(Long oneTowClerkNum) {
        this.oneTowClerkNum = oneTowClerkNum;
    }

    public Long getThreeFourClerkNum() {
        return threeFourClerkNum;
    }

    public void setThreeFourClerkNum(Long threeFourClerkNum) {
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

    public Long getOfficialRealNum() {
        return officialRealNum;
    }

    public void setOfficialRealNum(Long officialRealNum) {
        this.officialRealNum = officialRealNum;
    }

    public Long getReferOfficialRealNum() {
        return referOfficialRealNum;
    }

    public void setReferOfficialRealNum(Long referOfficialRealNum) {
        this.referOfficialRealNum = referOfficialRealNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SYS_UNIT)) return false;
        SYS_UNIT unit = (SYS_UNIT) o;
        return hasChildren == unit.hasChildren &&
                Objects.equals(id, unit.id) &&
                Objects.equals(code, unit.code) &&
                Objects.equals(name, unit.name) &&
                Objects.equals(parentId, unit.parentId) &&
                Objects.equals(area, unit.area) &&
                Objects.equals(affiliation, unit.affiliation) &&
                Objects.equals(unitType, unit.unitType) &&
                Objects.equals(category, unit.category) &&
                Objects.equals(level, unit.level) &&
                Objects.equals(referOfficialDocument, unit.referOfficialDocument) &&
                Objects.equals(referOfficialDate, unit.referOfficialDate) &&
                Objects.equals(referOfficialDateStr, unit.referOfficialDateStr) &&
                Objects.equals(officialNum, unit.officialNum) &&
                Objects.equals(referOfficialNum, unit.referOfficialNum) &&
                Objects.equals(officialRealNum, unit.officialRealNum) &&
                Objects.equals(referOfficialRealNum, unit.referOfficialRealNum) &&
                Objects.equals(rightPlaceNum, unit.rightPlaceNum) &&
                Objects.equals(deputyPlaceNum, unit.deputyPlaceNum) &&
                Objects.equals(mainHallNum, unit.mainHallNum) &&
                Objects.equals(deputyHallNum, unit.deputyHallNum) &&
                Objects.equals(oneInspectorNum, unit.oneInspectorNum) &&
                Objects.equals(towInspectorNum, unit.towInspectorNum) &&
                Objects.equals(oneTowResearcherNum, unit.oneTowResearcherNum) &&
                Objects.equals(threeFourResearcherNum, unit.threeFourResearcherNum) &&
                Objects.equals(oneTowClerkNum, unit.oneTowClerkNum) &&
                Objects.equals(threeFourClerkNum, unit.threeFourClerkNum) &&
                Objects.equals(oneResearcherNum, unit.oneResearcherNum) &&
                Objects.equals(towResearcherNum, unit.towResearcherNum) &&
                Objects.equals(threeResearcherNum, unit.threeResearcherNum) &&
                Objects.equals(fourResearcherNum, unit.fourResearcherNum) &&
                Objects.equals(oneClerkNum, unit.oneClerkNum) &&
                Objects.equals(towClerkNum, unit.towClerkNum) &&
                Objects.equals(threeClerkNum, unit.threeClerkNum) &&
                Objects.equals(fourClerkNum, unit.fourClerkNum) &&
                Objects.equals(parentName, unit.parentName) &&
                Objects.equals(detail, unit.detail) &&
                Objects.equals(unitOrder, unit.unitOrder) &&
                Objects.equals(buildProvince, unit.buildProvince) &&
                Objects.equals(buildCity, unit.buildCity) &&
                Objects.equals(buildCounty, unit.buildCounty) &&
                Objects.equals(contact, unit.contact) &&
                Objects.equals(contactNumber, unit.contactNumber) &&
                Objects.equals(regCode, unit.regCode) &&
                Arrays.equals(areaStrs, unit.areaStrs) &&
                Objects.equals(children, unit.children) &&
                Objects.equals(value, unit.value) &&
                Objects.equals(label, unit.label);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, code, name, parentId, area, affiliation, unitType, category, level, referOfficialDocument, referOfficialDate, referOfficialDateStr, officialNum, referOfficialNum, officialRealNum, referOfficialRealNum, rightPlaceNum, deputyPlaceNum, mainHallNum, deputyHallNum, oneInspectorNum, towInspectorNum, oneTowResearcherNum, threeFourResearcherNum, oneTowClerkNum, threeFourClerkNum, oneResearcherNum, towResearcherNum, threeResearcherNum, fourResearcherNum, oneClerkNum, towClerkNum, threeClerkNum, fourClerkNum, parentName, detail, unitOrder, buildProvince, buildCity, buildCounty, contact, contactNumber, regCode, hasChildren, children, value, label);
        result = 31 * result + Arrays.hashCode(areaStrs);
        return result;
    }
}
