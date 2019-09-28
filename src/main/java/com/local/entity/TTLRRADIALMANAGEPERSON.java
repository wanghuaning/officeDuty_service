package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;

@ApiModel("单位辐射管理机构人员表")
@Table("TT_LR_RADIAL_MANAGE_PERSON")
@Comment("单位辐射管理机构人员表")
public class TTLRRADIALMANAGEPERSON implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("辐射管理机构人员主键")
    @Comment("辐射管理机构人员主键")
    @Column("MANAGE_PERSON_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String MANAGE_PERSON_ID;

    @ApiModelProperty("业务id")
    @Comment("业务id")
    @Column("YWID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String YWID;

    @ApiModelProperty("业务主键id")
    @Comment("业务主键id")
    @Column("REQUEIR_YWID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String REQUEIR_YWID;

    @ApiModelProperty("许可证申请单位主键")
    @Comment("许可证申请单位主键")
    @Column("COMPANY_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String COMPANY_ID;

    @ApiModelProperty("职责")
    @Comment("职责")
    @Column("DUTY")
    @ColDefine(type = ColType.VARCHAR,width = 16)
    private String DUTY;

    @ApiModelProperty("姓名")
    @Comment("姓名")
    @Column("PERSON_NAME")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String PERSON_NAME;

    @ApiModelProperty("性别")
    @Comment("性别")
    @Column("SEX")
    @ColDefine(type = ColType.VARCHAR,width = 16)
    private String SEX;

    @ApiModelProperty("职务或职称")
    @Comment("职务或职称")
    @Column("PERSON_JOB")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String PERSON_JOB;

    @ApiModelProperty("工作部门")
    @Comment("工作部门")
    @Column("DEPARTMENT")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String DEPARTMENT;

    @ApiModelProperty("是否专兼职")
    @Comment("是否专兼职")
    @Column("FULL_OR_PART")
    @ColDefine(type = ColType.VARCHAR,width = 16)
    private String FULL_OR_PART;

    @ApiModelProperty("专业")
    @Comment("专业")
    @Column("MAJOR")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String MAJOR;

    @ApiModelProperty("是否注册核安全工程师 ")
    @Comment("是否注册核安全工程师 ")
    @Column("NUCLEUS_ENGINEER")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String NUCLEUS_ENGINEER;

    @ApiModelProperty("身份证号")
    @Comment("身份证号")
    @Column("CARD")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String CARD;

    @ApiModelProperty("更改人")
    @Comment("更改人")
    @Column("UPDATEUSER")
    @ColDefine(type = ColType.VARCHAR,width = 30)
    private String UPDATEUSER;

    @ApiModelProperty("更新时间")
    @Comment("更新时间")
    @Column("UPDATETIME")
    @ColDefine(type = ColType.DATE)
    private Timestamp UPDATETIME;

    public String getMANAGE_PERSON_ID() {
        return MANAGE_PERSON_ID;
    }

    public void setMANAGE_PERSON_ID(String MANAGE_PERSON_ID) {
        this.MANAGE_PERSON_ID = MANAGE_PERSON_ID;
    }

    public String getYWID() {
        return YWID;
    }

    public void setYWID(String YWID) {
        this.YWID = YWID;
    }

    public String getREQUEIR_YWID() {
        return REQUEIR_YWID;
    }

    public void setREQUEIR_YWID(String REQUEIR_YWID) {
        this.REQUEIR_YWID = REQUEIR_YWID;
    }

    public String getCOMPANY_ID() {
        return COMPANY_ID;
    }

    public void setCOMPANY_ID(String COMPANY_ID) {
        this.COMPANY_ID = COMPANY_ID;
    }

    public String getDUTY() {
        return DUTY;
    }

    public void setDUTY(String DUTY) {
        this.DUTY = DUTY;
    }

    public String getPERSON_NAME() {
        return PERSON_NAME;
    }

    public void setPERSON_NAME(String PERSON_NAME) {
        this.PERSON_NAME = PERSON_NAME;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }

    public String getPERSON_JOB() {
        return PERSON_JOB;
    }

    public void setPERSON_JOB(String PERSON_JOB) {
        this.PERSON_JOB = PERSON_JOB;
    }

    public String getDEPARTMENT() {
        return DEPARTMENT;
    }

    public void setDEPARTMENT(String DEPARTMENT) {
        this.DEPARTMENT = DEPARTMENT;
    }

    public String getFULL_OR_PART() {
        return FULL_OR_PART;
    }

    public void setFULL_OR_PART(String FULL_OR_PART) {
        this.FULL_OR_PART = FULL_OR_PART;
    }

    public String getMAJOR() {
        return MAJOR;
    }

    public void setMAJOR(String MAJOR) {
        this.MAJOR = MAJOR;
    }

    public String getNUCLEUS_ENGINEER() {
        return NUCLEUS_ENGINEER;
    }

    public void setNUCLEUS_ENGINEER(String NUCLEUS_ENGINEER) {
        this.NUCLEUS_ENGINEER = NUCLEUS_ENGINEER;
    }

    public String getCARD() {
        return CARD;
    }

    public void setCARD(String CARD) {
        this.CARD = CARD;
    }

    public String getUPDATEUSER() {
        return UPDATEUSER;
    }

    public void setUPDATEUSER(String UPDATEUSER) {
        this.UPDATEUSER = UPDATEUSER;
    }

    public Timestamp getUPDATETIME() {
        return UPDATETIME;
    }

    public void setUPDATETIME(Timestamp UPDATETIME) {
        this.UPDATETIME = UPDATETIME;
    }
}
