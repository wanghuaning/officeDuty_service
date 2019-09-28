package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * POLLUTE_CONTENT
 *
 * @author yangbing
 * 实现序列化
 */
@ApiModel("备案表")
@Table("L_REG_registration")
@Comment("备案表")
public class REG_Registration implements Serializable {
    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("编号")
    @Comment("编号")
    @Column("id")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String id;

    @ApiModelProperty("user编号")
    @Comment("user编号")
    @Column("User_Id")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String userId;

    @ApiModelProperty("项目名称")
    @Comment("项目名称")
    @Column("Project_Name")
    @ColDefine(type = ColType.VARCHAR, width = 2000)
    private String projectName;

    @ApiModelProperty("建设地点省")
    @Comment("建设地点省")
    @Column("Build_Province")
    @ColDefine(type = ColType.VARCHAR, width = 10)
    private String buildProvince;

    @ApiModelProperty("建设地点市")
    @Comment("建设地点市")
    @Column("Build_City")
    @ColDefine(type = ColType.VARCHAR, width = 10)
    private String buildCity;

    @ApiModelProperty("建设地点县")
    @Comment("建设地点县")
    @Column("Build_County")
    @ColDefine(type = ColType.VARCHAR, width = 10)
    private String buildCounty;

    @ApiModelProperty("建设地点详细地址")
    @Comment("建设地点详细地址")
    @Column("Build_Address")
    @ColDefine(type = ColType.VARCHAR, width = 2000)
    private String buildAddress;

    @ApiModelProperty("建设单位")
    @Comment("建设单位")
    @Column("Construction_Company_Name")
    @ColDefine(type = ColType.VARCHAR, width = 2000)
    private String constructionCompanyName;

    @ApiModelProperty("占地面积")
    @Comment("占地面积")
    @Column("Area_Covered")
    @ColDefine(type = ColType.VARCHAR, width = 18)
    private String areaCovered;

    @ApiModelProperty("法人代表")
    @Comment("法人代表")
    @Column("Legal_Representative")
    @ColDefine(type = ColType.VARCHAR, width = 128)
    private String legalRepresentative;

    @ApiModelProperty("联系人")
    @Comment("联系人")
    @Column("Linkman_Name")
    @ColDefine(type = ColType.VARCHAR, width = 128)
    private String linkmanName;

    @ApiModelProperty("联系电话")
    @Comment("联系电话")
    @Column("Contact_Number")
    @ColDefine(type = ColType.VARCHAR, width = 256)
    private String contactNumber;

    @ApiModelProperty("项目投资")
    @Comment("项目投资")
    @Column("Project_Investment")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String projectInvestment;

    @ApiModelProperty("环保投资")
    @Comment("环保投资")
    @Column("Env_Protection_Pnvestment")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String envProtectionPnvestment;

    @ApiModelProperty("拟投入生产运营日期")
    @Comment("拟投入生产运营日期")
    @Column("Planned_Start_Date")
    @ColDefine(type = ColType.DATE)
    private java.util.Date plannedStartDate;

    @ApiModelProperty("建设性质")
    @Comment("建设性质")
    @Column("Construction_Property")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String constructionProperty;

    @ApiModelProperty("建设内容及规模")
    @Comment("建设内容及规模")
    @Column("Construction_Content_Scale")
    @ColDefine(type = ColType.TEXT)
    private String constructionContentScale;


    @ApiModelProperty("环境影响评价分类2级(备案依据)")
    @Comment("环境影响评价分类2级(备案依据)")
    @Column("Environmental_Type2")
    @ColDefine(type = ColType.VARCHAR, width = 256)
    private String environmentalType2;

    @ApiModelProperty("环境影响评价分类3级(备案依据)")
    @Comment("环境影响评价分类3级(备案依据)")
    @Column("Environmental_Type3")
    @ColDefine(type = ColType.VARCHAR, width = 256)
    private String environmentalType3;

    @ApiModelProperty("备案号")
    @Comment("备案号")
    @Column("Record_Number")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String recordNumber;

    @ApiModelProperty("备案号流水号")
    @Comment("备案号流水号")
    @Column("Record_Number_Serial")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String recordNumberSerial;

    @ApiModelProperty("申请日期")
    @Comment("申请日期")
    @Column("Application_Date")
    @ColDefine(type = ColType.DATE)
    private java.util.Date applicationDate;

    @ApiModelProperty("备案日期")
    @Comment("备案日期")
    @Column("Record_Date")
    @ColDefine(type = ColType.DATE)
    private java.util.Date recordDate;

    @ApiModelProperty("删除标记")
    @Comment("删除标记")
    @Column("Del_Flag")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String delFlag;

    @ApiModelProperty("面积类型")
    @Comment("面积类型")
    @Column("Area_Type")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String areaType;

    @ApiModelProperty("是否可用")
    @Comment("是否可用")
    @Column("Project_Active")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String projectActive;

    @ApiModelProperty("公示状态")
    @Comment("公示状态")
    @Column("Announcement_Status")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String announcementStatus;

    @ApiModelProperty("公示时间")
    @Comment("公示时间")
    @Column("Announcement_Date")
    @ColDefine(type = ColType.DATE)
    private java.util.Date announcementDate;

    @ApiModelProperty("建设地点省(名称)")
    @Comment("建设地点省(名称)")
    @Column("Build_Province_Name")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String buildProvinceName;

    @ApiModelProperty("建设地点市(名称)")
    @Comment("建设地点市(名称)")
    @Column("Build_City_Name")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String buildCityName;

    @ApiModelProperty("建设地点县(名称)")
    @Comment("建设地点县(名称)")
    @Column("Build_County_Name")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String buildCountyName;

    @ApiModelProperty("更新时间")
    @Comment("更新时间")
    @Column("Update_Time")
    @ColDefine(type = ColType.DATE)
    private java.util.Date updateTime;

    @ApiModelProperty("批次号")
    @Comment("批次号")
    @Column("Batch_Number")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String batchNumber;

    @ApiModelProperty("推送批次")
    @Comment("推送批次")
    @Column("Push_Batch")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String pushBatch;

    @ApiModelProperty("接收批次")
    @Comment("接收批次")
    @Column("C_Batch")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String CBatch;

    private String buildAddressAll;

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getPushBatch() {
        return pushBatch;
    }

    public void setPushBatch(String pushBatch) {
        this.pushBatch = pushBatch;
    }

    public String getCBatch() {
        return CBatch;
    }

    public void setCBatch(String CBatch) {
        this.CBatch = CBatch;
    }

    public String getBuildAddressAll() {
        return this.buildProvinceName+ this.buildCityName+this.buildCountyName;
    }

    public void setBuildAddressAll(String buildAddressAll) {
        this.buildAddressAll =this.buildProvince+ this.buildCity+this.buildCounty;
    }

    public String getContactNumber() {
        String str="****";
        StringBuilder sb=new StringBuilder(contactNumber);
        return sb.replace(3,7,str).toString();
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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


    public String getBuildAddress() {
        return buildAddress;
    }

    public void setBuildAddress(String buildAddress) {
        this.buildAddress = buildAddress;
    }


    public String getConstructionCompanyName() {
        return constructionCompanyName;
    }

    public void setConstructionCompanyName(String constructionCompanyName) {
        this.constructionCompanyName = constructionCompanyName;
    }


    public String getAreaCovered() {
        return areaCovered;
    }

    public void setAreaCovered(String areaCovered) {
        this.areaCovered = areaCovered;
    }


    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }


    public String getLinkmanName() {
        return linkmanName;
    }

    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName;
    }


    public String getProjectInvestment() {
        return projectInvestment;
    }

    public void setProjectInvestment(String projectInvestment) {
        this.projectInvestment = projectInvestment;
    }


    public String getEnvProtectionPnvestment() {
        return envProtectionPnvestment;
    }

    public void setEnvProtectionPnvestment(String envProtectionPnvestment) {
        this.envProtectionPnvestment = envProtectionPnvestment;
    }


    public java.util.Date getPlannedStartDate() {
        return plannedStartDate;
    }

    public void setPlannedStartDate(java.util.Date plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }


    public String getConstructionProperty() {
        return constructionProperty;
    }

    public void setConstructionProperty(String constructionProperty) {
        this.constructionProperty = constructionProperty;
    }


    public String getConstructionContentScale() {
        return constructionContentScale;
    }

    public void setConstructionContentScale(String constructionContentScale) {
        this.constructionContentScale = constructionContentScale;
    }

    public String getEnvironmentalType2() {
        return environmentalType2;
    }

    public void setEnvironmentalType2(String environmentalType2) {
        this.environmentalType2 = environmentalType2;
    }


    public String getEnvironmentalType3() {
        return environmentalType3;
    }

    public void setEnvironmentalType3(String environmentalType3) {
        this.environmentalType3 = environmentalType3;
    }


    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }


    public String getRecordNumberSerial() {
        return recordNumberSerial;
    }

    public void setRecordNumberSerial(String recordNumberSerial) {
        this.recordNumberSerial = recordNumberSerial;
    }


    public java.util.Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(java.util.Date applicationDate) {
        this.applicationDate = applicationDate;
    }


    public java.util.Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(java.util.Date recordDate) {
        this.recordDate = recordDate;
    }


    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }


    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }


    public String getProjectActive() {
        return projectActive;
    }

    public void setProjectActive(String projectActive) {
        this.projectActive = projectActive;
    }


    public String getAnnouncementStatus() {
        return announcementStatus;
    }

    public void setAnnouncementStatus(String announcementStatus) {
        this.announcementStatus = announcementStatus;
    }


    public java.util.Date getAnnouncementDate() {
        return announcementDate;
    }

    public void setAnnouncementDate(java.util.Date announcementDate) {
        this.announcementDate = announcementDate;
    }


    public String getBuildProvinceName() {
        return buildProvinceName;
    }

    public void setBuildProvinceName(String buildProvinceName) {
        this.buildProvinceName = buildProvinceName;
    }


    public String getBuildCityName() {
        return buildCityName;
    }

    public void setBuildCityName(String buildCityName) {
        this.buildCityName = buildCityName;
    }


    public String getBuildCountyName() {
        return buildCountyName;
    }

    public void setBuildCountyName(String buildCountyName) {
        this.buildCountyName = buildCountyName;
    }
}
