package com.local.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UnitModel {
    private String id;
    private String code;//组织机构编码
    private String name;//单位名称
    private String parentId;//父单位ID
    private String area;//所在地区
    private String affiliation;//隶属关系
    private String category;//所属序列
    private String level;//机构级别
    private String parentName;//上级单位名称
    private String detail;//备注
    private String contact;//联系人
    private String contactNumber;//联系电话
    private String referOfficialDocument;//参照公务员法管理审批文号
    private Date referOfficialDate;//参照公务员法管理审批时间
    private Integer officialNum=0;//行政编制数
    private Integer referOfficialNum=0;//事业编制数（参公）
    private Integer officialRealNum=0;//行政实有数
    private Integer referOfficialRealNum=0;//事业实有数（参公）

    private Integer realNameNumTurn=0;//套转实名制管理
    private Integer militaryNumTurn=0;//套转军转干部
    private Integer rightPlaceNumTurn=0;//套转县处级正职领导职数
    private Integer deputyPlaceNumTurn=0;//套转县处级副职领导职数
    private Integer mainHallNumTurn=0;//套转乡科级正职领导职数
    private Integer deputyHallNumTurn=0;//套转乡科级副职领导职数
    private Integer oneTowResearcherNumTurn=0;//套转一级和二级调研员职数
    private Integer oneResearcherNumTurn=0;//套转一级调研员职数
    private Integer towResearcherNumTurn=0;//套转二级调研员职数
    private Integer threeFourResearcherNumTurn=0;//套转三级和四级调研员职数
    private Integer threeResearcherNumTurn=0;//套转三级调研员职数
    private Integer fourResearcherNumTurn=0;//套转四级调研员职数
    private Integer oneTowClerkNumTurn=0;//套转一级和二级主任科员职数
    private Integer oneClerkNumTurn=0;//套转一级主任科员职数
    private Integer towClerkNumTurn=0;//套转二级主任科员职数
    private Integer threeFourClerkNumTurn=0;//套转三级和四级主任科员职数
    private Integer threeClerkNumTurn=0;//套转三级主任科员职数
    private Integer fourClerkNumTurn=0;//套转四级主任科员职数
    private Integer oneClerkTurn=0;//套转一级科员
    private Integer towClerkTurn=0;//套转二级科员
    private Integer probationTurn=0;//套转试用期

    private Integer realNameNumNow=0;//现有实名制管理
    private Integer militaryNumNow=0;//现有军转干部
    private Integer rightPlaceNumNow=0;//现有县处级正职领导职数
    private Integer deputyPlaceNumNow=0;//现有县处级副职领导职数
    private Integer mainHallNumNow=0;//现有乡科级正职领导职数
    private Integer deputyHallNumNow=0;//现有乡科级副职领导职数
    private Integer oneTowResearcherNumNow=0;//现有一级和二级调研员职数
    private Integer oneResearcherNumNow=0;//现有一级调研员职数
    private Integer towResearcherNumNow=0;//现有二级调研员职数
    private Integer threeFourResearcherNumNow=0;//现有三级和四级调研员职数
    private Integer threeResearcherNumNow=0;//现有三级调研员职数
    private Integer fourResearcherNumNow=0;//现有四级调研员职数
    private Integer oneTowClerkNumNow=0;//现有一级和二级主任科员职数
    private Integer oneClerkNumNow=0;//现有一级主任科员职数
    private Integer towClerkNumNow=0;//现有二级主任科员职数
    private Integer threeFourClerkNumNow=0;//现有三级和四级主任科员职数
    private Integer threeClerkNumNow=0;//现有三级主任科员职数
    private Integer fourClerkNumNow=0;//现有四级主任科员职数
    private Integer oneClerkNow=0;//现有一级科员
    private Integer towClerkNow=0;//现有二级科员
    private Integer probationNow=0;//现有试用期

    private Long oneTowResearcherNumLave=0l;//剩余一级和二级调研员职数
    private Long oneResearcherNumLave=0l;//剩余一级调研员职数
    private Long towResearcherNumLave=0l;//剩余二级调研员职数
    private Long threeFourResearcherNumLave=0l;//剩余三级和四级调研员职数
    private Long threeResearcherNumLave=0l;//剩余三级调研员职数
    private Long fourResearcherNumLave=0l;//剩余四级调研员职数
    private Long oneTowClerkNumLave=0l;//剩余一级和二级主任科员职数
    private Long oneClerkNumLave=0l;//剩余一级主任科员职数
    private Long towClerkNumLave=0l;//剩余二级主任科员职数
    private Long threeFourClerkNumLave=0l;//剩余三级和四级主任科员职数
    private Long threeClerkNumLave=0l;//剩余三级主任科员职数
    private Long fourClerkNumLave=0l;//剩余四级主任科员职数

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

    public Integer getOfficialNum() {
        return officialNum;
    }

    public void setOfficialNum(Integer officialNum) {
        this.officialNum = officialNum;
    }

    public Integer getReferOfficialNum() {
        return referOfficialNum;
    }

    public void setReferOfficialNum(Integer referOfficialNum) {
        this.referOfficialNum = referOfficialNum;
    }

    public Integer getOfficialRealNum() {
        return officialRealNum;
    }

    public void setOfficialRealNum(Integer officialRealNum) {
        this.officialRealNum = officialRealNum;
    }

    public Integer getReferOfficialRealNum() {
        return referOfficialRealNum;
    }

    public void setReferOfficialRealNum(Integer referOfficialRealNum) {
        this.referOfficialRealNum = referOfficialRealNum;
    }

    public Integer getRealNameNumTurn() {
        return realNameNumTurn;
    }

    public void setRealNameNumTurn(Integer realNameNumTurn) {
        this.realNameNumTurn = realNameNumTurn;
    }

    public Integer getMilitaryNumTurn() {
        return militaryNumTurn;
    }

    public void setMilitaryNumTurn(Integer militaryNumTurn) {
        this.militaryNumTurn = militaryNumTurn;
    }

    public Integer getRightPlaceNumTurn() {
        return rightPlaceNumTurn;
    }

    public void setRightPlaceNumTurn(Integer rightPlaceNumTurn) {
        this.rightPlaceNumTurn = rightPlaceNumTurn;
    }

    public Integer getDeputyPlaceNumTurn() {
        return deputyPlaceNumTurn;
    }

    public void setDeputyPlaceNumTurn(Integer deputyPlaceNumTurn) {
        this.deputyPlaceNumTurn = deputyPlaceNumTurn;
    }

    public Integer getMainHallNumTurn() {
        return mainHallNumTurn;
    }

    public void setMainHallNumTurn(Integer mainHallNumTurn) {
        this.mainHallNumTurn = mainHallNumTurn;
    }

    public Integer getDeputyHallNumTurn() {
        return deputyHallNumTurn;
    }

    public void setDeputyHallNumTurn(Integer deputyHallNumTurn) {
        this.deputyHallNumTurn = deputyHallNumTurn;
    }

    public Integer getOneTowResearcherNumTurn() {
        return oneTowResearcherNumTurn;
    }

    public void setOneTowResearcherNumTurn(Integer oneTowResearcherNumTurn) {
        this.oneTowResearcherNumTurn = oneTowResearcherNumTurn;
    }

    public Integer getOneResearcherNumTurn() {
        return oneResearcherNumTurn;
    }

    public void setOneResearcherNumTurn(Integer oneResearcherNumTurn) {
        this.oneResearcherNumTurn = oneResearcherNumTurn;
    }

    public Integer getTowResearcherNumTurn() {
        return towResearcherNumTurn;
    }

    public void setTowResearcherNumTurn(Integer towResearcherNumTurn) {
        this.towResearcherNumTurn = towResearcherNumTurn;
    }

    public Integer getThreeFourResearcherNumTurn() {
        return threeFourResearcherNumTurn;
    }

    public void setThreeFourResearcherNumTurn(Integer threeFourResearcherNumTurn) {
        this.threeFourResearcherNumTurn = threeFourResearcherNumTurn;
    }

    public Integer getThreeResearcherNumTurn() {
        return threeResearcherNumTurn;
    }

    public void setThreeResearcherNumTurn(Integer threeResearcherNumTurn) {
        this.threeResearcherNumTurn = threeResearcherNumTurn;
    }

    public Integer getFourResearcherNumTurn() {
        return fourResearcherNumTurn;
    }

    public void setFourResearcherNumTurn(Integer fourResearcherNumTurn) {
        this.fourResearcherNumTurn = fourResearcherNumTurn;
    }

    public Integer getOneTowClerkNumTurn() {
        return oneTowClerkNumTurn;
    }

    public void setOneTowClerkNumTurn(Integer oneTowClerkNumTurn) {
        this.oneTowClerkNumTurn = oneTowClerkNumTurn;
    }

    public Integer getOneClerkNumTurn() {
        return oneClerkNumTurn;
    }

    public void setOneClerkNumTurn(Integer oneClerkNumTurn) {
        this.oneClerkNumTurn = oneClerkNumTurn;
    }

    public Integer getTowClerkNumTurn() {
        return towClerkNumTurn;
    }

    public void setTowClerkNumTurn(Integer towClerkNumTurn) {
        this.towClerkNumTurn = towClerkNumTurn;
    }

    public Integer getThreeFourClerkNumTurn() {
        return threeFourClerkNumTurn;
    }

    public void setThreeFourClerkNumTurn(Integer threeFourClerkNumTurn) {
        this.threeFourClerkNumTurn = threeFourClerkNumTurn;
    }

    public Integer getThreeClerkNumTurn() {
        return threeClerkNumTurn;
    }

    public void setThreeClerkNumTurn(Integer threeClerkNumTurn) {
        this.threeClerkNumTurn = threeClerkNumTurn;
    }

    public Integer getFourClerkNumTurn() {
        return fourClerkNumTurn;
    }

    public void setFourClerkNumTurn(Integer fourClerkNumTurn) {
        this.fourClerkNumTurn = fourClerkNumTurn;
    }

    public Integer getOneClerkTurn() {
        return oneClerkTurn;
    }

    public void setOneClerkTurn(Integer oneClerkTurn) {
        this.oneClerkTurn = oneClerkTurn;
    }

    public Integer getTowClerkTurn() {
        return towClerkTurn;
    }

    public void setTowClerkTurn(Integer towClerkTurn) {
        this.towClerkTurn = towClerkTurn;
    }

    public Integer getProbationTurn() {
        return probationTurn;
    }

    public void setProbationTurn(Integer probationTurn) {
        this.probationTurn = probationTurn;
    }

    public Integer getRealNameNumNow() {
        return realNameNumNow;
    }

    public void setRealNameNumNow(Integer realNameNumNow) {
        this.realNameNumNow = realNameNumNow;
    }

    public Integer getMilitaryNumNow() {
        return militaryNumNow;
    }

    public void setMilitaryNumNow(Integer militaryNumNow) {
        this.militaryNumNow = militaryNumNow;
    }

    public Integer getRightPlaceNumNow() {
        return rightPlaceNumNow;
    }

    public void setRightPlaceNumNow(Integer rightPlaceNumNow) {
        this.rightPlaceNumNow = rightPlaceNumNow;
    }

    public Integer getDeputyPlaceNumNow() {
        return deputyPlaceNumNow;
    }

    public void setDeputyPlaceNumNow(Integer deputyPlaceNumNow) {
        this.deputyPlaceNumNow = deputyPlaceNumNow;
    }

    public Integer getMainHallNumNow() {
        return mainHallNumNow;
    }

    public void setMainHallNumNow(Integer mainHallNumNow) {
        this.mainHallNumNow = mainHallNumNow;
    }

    public Integer getDeputyHallNumNow() {
        return deputyHallNumNow;
    }

    public void setDeputyHallNumNow(Integer deputyHallNumNow) {
        this.deputyHallNumNow = deputyHallNumNow;
    }

    public Integer getOneTowResearcherNumNow() {
        return oneTowResearcherNumNow;
    }

    public void setOneTowResearcherNumNow(Integer oneTowResearcherNumNow) {
        this.oneTowResearcherNumNow = oneTowResearcherNumNow;
    }

    public Integer getOneResearcherNumNow() {
        return oneResearcherNumNow;
    }

    public void setOneResearcherNumNow(Integer oneResearcherNumNow) {
        this.oneResearcherNumNow = oneResearcherNumNow;
    }

    public Integer getTowResearcherNumNow() {
        return towResearcherNumNow;
    }

    public void setTowResearcherNumNow(Integer towResearcherNumNow) {
        this.towResearcherNumNow = towResearcherNumNow;
    }

    public Integer getThreeFourResearcherNumNow() {
        return threeFourResearcherNumNow;
    }

    public void setThreeFourResearcherNumNow(Integer threeFourResearcherNumNow) {
        this.threeFourResearcherNumNow = threeFourResearcherNumNow;
    }

    public Integer getThreeResearcherNumNow() {
        return threeResearcherNumNow;
    }

    public void setThreeResearcherNumNow(Integer threeResearcherNumNow) {
        this.threeResearcherNumNow = threeResearcherNumNow;
    }

    public Integer getFourResearcherNumNow() {
        return fourResearcherNumNow;
    }

    public void setFourResearcherNumNow(Integer fourResearcherNumNow) {
        this.fourResearcherNumNow = fourResearcherNumNow;
    }

    public Integer getOneTowClerkNumNow() {
        return oneTowClerkNumNow;
    }

    public void setOneTowClerkNumNow(Integer oneTowClerkNumNow) {
        this.oneTowClerkNumNow = oneTowClerkNumNow;
    }

    public Integer getOneClerkNumNow() {
        return oneClerkNumNow;
    }

    public void setOneClerkNumNow(Integer oneClerkNumNow) {
        this.oneClerkNumNow = oneClerkNumNow;
    }

    public Integer getTowClerkNumNow() {
        return towClerkNumNow;
    }

    public void setTowClerkNumNow(Integer towClerkNumNow) {
        this.towClerkNumNow = towClerkNumNow;
    }

    public Integer getThreeFourClerkNumNow() {
        return threeFourClerkNumNow;
    }

    public void setThreeFourClerkNumNow(Integer threeFourClerkNumNow) {
        this.threeFourClerkNumNow = threeFourClerkNumNow;
    }

    public Integer getThreeClerkNumNow() {
        return threeClerkNumNow;
    }

    public void setThreeClerkNumNow(Integer threeClerkNumNow) {
        this.threeClerkNumNow = threeClerkNumNow;
    }

    public Integer getFourClerkNumNow() {
        return fourClerkNumNow;
    }

    public void setFourClerkNumNow(Integer fourClerkNumNow) {
        this.fourClerkNumNow = fourClerkNumNow;
    }

    public Integer getOneClerkNow() {
        return oneClerkNow;
    }

    public void setOneClerkNow(Integer oneClerkNow) {
        this.oneClerkNow = oneClerkNow;
    }

    public Integer getTowClerkNow() {
        return towClerkNow;
    }

    public void setTowClerkNow(Integer towClerkNow) {
        this.towClerkNow = towClerkNow;
    }

    public Integer getProbationNow() {
        return probationNow;
    }

    public void setProbationNow(Integer probationNow) {
        this.probationNow = probationNow;
    }

    public Long getOneTowResearcherNumLave() {
        return oneTowResearcherNumLave;
    }

    public void setOneTowResearcherNumLave(Long oneTowResearcherNumLave) {
        this.oneTowResearcherNumLave = oneTowResearcherNumLave;
    }

    public Long getOneResearcherNumLave() {
        return oneResearcherNumLave;
    }

    public void setOneResearcherNumLave(Long oneResearcherNumLave) {
        this.oneResearcherNumLave = oneResearcherNumLave;
    }

    public Long getTowResearcherNumLave() {
        return towResearcherNumLave;
    }

    public void setTowResearcherNumLave(Long towResearcherNumLave) {
        this.towResearcherNumLave = towResearcherNumLave;
    }

    public Long getThreeFourResearcherNumLave() {
        return threeFourResearcherNumLave;
    }

    public void setThreeFourResearcherNumLave(Long threeFourResearcherNumLave) {
        this.threeFourResearcherNumLave = threeFourResearcherNumLave;
    }

    public Long getThreeResearcherNumLave() {
        return threeResearcherNumLave;
    }

    public void setThreeResearcherNumLave(Long threeResearcherNumLave) {
        this.threeResearcherNumLave = threeResearcherNumLave;
    }

    public Long getFourResearcherNumLave() {
        return fourResearcherNumLave;
    }

    public void setFourResearcherNumLave(Long fourResearcherNumLave) {
        this.fourResearcherNumLave = fourResearcherNumLave;
    }

    public Long getOneTowClerkNumLave() {
        return oneTowClerkNumLave;
    }

    public void setOneTowClerkNumLave(Long oneTowClerkNumLave) {
        this.oneTowClerkNumLave = oneTowClerkNumLave;
    }

    public Long getOneClerkNumLave() {
        return oneClerkNumLave;
    }

    public void setOneClerkNumLave(Long oneClerkNumLave) {
        this.oneClerkNumLave = oneClerkNumLave;
    }

    public Long getTowClerkNumLave() {
        return towClerkNumLave;
    }

    public void setTowClerkNumLave(Long towClerkNumLave) {
        this.towClerkNumLave = towClerkNumLave;
    }

    public Long getThreeFourClerkNumLave() {
        return threeFourClerkNumLave;
    }

    public void setThreeFourClerkNumLave(Long threeFourClerkNumLave) {
        this.threeFourClerkNumLave = threeFourClerkNumLave;
    }

    public Long getThreeClerkNumLave() {
        return threeClerkNumLave;
    }

    public void setThreeClerkNumLave(Long threeClerkNumLave) {
        this.threeClerkNumLave = threeClerkNumLave;
    }

    public Long getFourClerkNumLave() {
        return fourClerkNumLave;
    }

    public void setFourClerkNumLave(Long fourClerkNumLave) {
        this.fourClerkNumLave = fourClerkNumLave;
    }
}
