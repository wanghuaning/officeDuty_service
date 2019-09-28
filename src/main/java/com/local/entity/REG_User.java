package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Timestamp;

@ApiModel("用户表")
@Table("L_REG_sys_user")
@Comment("用户表")
public class REG_User implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("id")//用在属性上，对属性做注释
    @Comment("id")//定义脚本中添加comment属性来添加注释
    @Column("ID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String id;

    @ApiModelProperty("用户类型")
    @Comment("用户类型")
    @Column("User_Type")
    @ColDefine(type = ColType.VARCHAR,width = 128)
    private String userType;

    @ApiModelProperty("登录名")
    @Comment("登录名")
    @NotEmpty(message = "请输入登录名!")
    @Column("Login_Name")
    @ColDefine(type = ColType.VARCHAR,width = 128)
    private String loginName;

    @ApiModelProperty("密码")
    @Comment("密码")
    @NotEmpty(message = "请输入密码!")
    @Column("Password")
    @ColDefine(type = ColType.VARCHAR,width = 128)
    private String password;

    @ApiModelProperty("姓名")
    @Comment("姓名")
    @Column("Name")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String name;


    @ApiModelProperty("证书名称")
    @Comment("证书名称")
    @Column("Certificate_Name")
    @ColDefine(type = ColType.CHAR,width = 1)
    private String certificateName;

    @ApiModelProperty("证书编码")
    @Comment("证书编码")
    @Column("Certificate_Code")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String certificateCode;

    @ApiModelProperty("法人代表")
    @Comment("法人代表")
    @Column("Legal_Representative")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String legalRepresentative;

    @ApiModelProperty("身份证号")
    @Comment("身份证号")
    @Column("Identity_Number")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String identityNumber;

    @ApiModelProperty("移动电话")
    @Comment("移动电话")
    @Column("phone")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String phone;

    @ApiModelProperty("法人电话")
    @Comment("法人电话")
    @Column("Legal_Phone")
    @ColDefine(type = ColType.VARCHAR,width =128)
    private String legalPhone;

    @ApiModelProperty("邮箱")
    @Comment("邮箱")
    @Column("email")
    @ColDefine(type = ColType.VARCHAR,width = 256)
    private String email;

    @ApiModelProperty("是否可登录")
    @Comment("是否可登录")
    @Column("Login_Flag")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String loginFlag;

    @ApiModelProperty("修改时间")
    @Comment("修改时间")
    @Column("Update_Date")
    @ColDefine(type = ColType.DATE)
    private java.sql.Timestamp updateDate;

    @ApiModelProperty("创建时间")
    @Comment("创建时间")
    @Column("Create_Date")
    @ColDefine(type = ColType.DATE)
    private java.sql.Timestamp createDate;


    @ApiModelProperty("删除标记")
    @Comment("删除标记")
    @Column("Delete_Flag")
    @ColDefine(type = ColType.CHAR,width = 1)
    private String deleteFlag;


    @ApiModelProperty("建设单位名称")
    @Comment("建设单位名称")
    @Column("Construction_Unit_Name")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String constructionUnitName;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getCertificateCode() {
        return certificateCode;
    }

    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLegalPhone() {
        return legalPhone;
    }

    public void setLegalPhone(String legalPhone) {
        this.legalPhone = legalPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getConstructionUnitName() {
        return constructionUnitName;
    }

    public void setConstructionUnitName(String constructionUnitName) {
        this.constructionUnitName = constructionUnitName;
    }
}
