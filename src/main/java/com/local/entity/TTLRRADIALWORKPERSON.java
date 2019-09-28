package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

@ApiModel("单位辐射工作人员明细信息表")
@Table("TT_LR_RADIAL_WORK_PERSON")
@Comment("单位辐射工作人员明细信息表")
public class TTLRRADIALWORKPERSON implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("辐射工作人员主键")
    @Comment("辐射工作人员主键")
    @Column("WORK_PERSON_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String WORK_PERSON_ID;

    @ApiModelProperty("许可证申请单位主键")
    @Comment("许可证申请单位主键")
    @Column("COMPANY_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String COMPANY_ID;

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

    @ApiModelProperty("姓名")
    @Comment("姓名")
    @Column("NAME")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String NAME;

    @ApiModelProperty("性别")
    @Comment("性别")
    @Column("SEX")
    @ColDefine(type = ColType.VARCHAR,width = 16)
    private String SEX;

    @ApiModelProperty("出生日期")
    @Comment("出生日期")
    @Column("BIRTHDAY")
    @ColDefine(type = ColType.DATE)
    private Date BIRTHDAY;

    @ApiModelProperty("毕业学校")
    @Comment("毕业学校")
    @Column("GRADE_SCHOOL")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String GRADE_SCHOOL;

    @ApiModelProperty("学历")
    @Comment("学历")
    @Column("DEGREE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String DEGREE;

    @ApiModelProperty("专业")
    @Comment("专业")
    @Column("DEPARTMENT")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String DEPARTMENT;

    @ApiModelProperty("证件类型")
    @Comment("证件类型")
    @Column("PERSON_ID_TYPE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String PERSON_ID_TYPE;

    @ApiModelProperty("证件编码")
    @Comment("证件编码")
    @Column("PERSON_ID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String PERSON_ID;

    @ApiModelProperty("工作岗位")
    @Comment("工作岗位")
    @Column("WORK_STATION")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String WORK_STATION;

    @ApiModelProperty("备注")
    @Comment("备注")
    @Column("REMARK")
    @ColDefine(type = ColType.VARCHAR,width = 1024)
    private String REMARK;

    public String getWORK_PERSON_ID() {
        return WORK_PERSON_ID;
    }

    public void setWORK_PERSON_ID(String WORK_PERSON_ID) {
        this.WORK_PERSON_ID = WORK_PERSON_ID;
    }

    public String getCOMPANY_ID() {
        return COMPANY_ID;
    }

    public void setCOMPANY_ID(String COMPANY_ID) {
        this.COMPANY_ID = COMPANY_ID;
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

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }

    public Date getBIRTHDAY() {
        return BIRTHDAY;
    }

    public void setBIRTHDAY(Date BIRTHDAY) {
        this.BIRTHDAY = BIRTHDAY;
    }

    public String getGRADE_SCHOOL() {
        return GRADE_SCHOOL;
    }

    public void setGRADE_SCHOOL(String GRADE_SCHOOL) {
        this.GRADE_SCHOOL = GRADE_SCHOOL;
    }

    public String getDEGREE() {
        return DEGREE;
    }

    public void setDEGREE(String DEGREE) {
        this.DEGREE = DEGREE;
    }

    public String getDEPARTMENT() {
        return DEPARTMENT;
    }

    public void setDEPARTMENT(String DEPARTMENT) {
        this.DEPARTMENT = DEPARTMENT;
    }

    public String getPERSON_ID_TYPE() {
        return PERSON_ID_TYPE;
    }

    public void setPERSON_ID_TYPE(String PERSON_ID_TYPE) {
        this.PERSON_ID_TYPE = PERSON_ID_TYPE;
    }

    public String getPERSON_ID() {
        return PERSON_ID;
    }

    public void setPERSON_ID(String PERSON_ID) {
        this.PERSON_ID = PERSON_ID;
    }

    public String getWORK_STATION() {
        return WORK_STATION;
    }

    public void setWORK_STATION(String WORK_STATION) {
        this.WORK_STATION = WORK_STATION;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }
}
