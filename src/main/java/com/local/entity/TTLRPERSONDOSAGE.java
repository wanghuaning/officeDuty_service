package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;

@ApiModel("职业照射个人剂量检测档案")
@Table("TT_LR_PERSON_DOSAGE")
@Comment("职业照射个人剂量检测档案")
public class TTLRPERSONDOSAGE implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("照射个人剂量检测档案主键")
    @Comment("照射个人剂量检测档案主键")
    @Column("PERSON_DOSAGE_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String PERSON_DOSAGE_ID;

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

    @ApiModelProperty("辐射工作人员主键")
    @Comment("辐射工作人员主键")
    @Column("WORK_PERSON_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String WORK_PERSON_ID;

    @ApiModelProperty("监测年份")
    @Comment("监测年份")
    @Column("MONITOR_YEAR")
    @ColDefine(type = ColType.INT,width = 4)
    private Integer MONITOR_YEAR;

    @ApiModelProperty("Hp（0.07）")
    @Comment("Hp（0.07）")
    @Column("DOSAGE_SEVEN")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String DOSAGE_SEVEN;

    @ApiModelProperty("Hp（3）")
    @Comment("Hp（3）")
    @Column("DOSAGE_THREE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String DOSAGE_THREE;

    @ApiModelProperty("Hp（10）")
    @Comment("Hp（10）")
    @Column("DOSAGE_TEN")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String DOSAGE_TEN;

    @ApiModelProperty("内照射待积有效剂量")
    @Comment("内照射待积有效剂量")
    @Column("IN_DOSAGE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String IN_DOSAGE;

    @ApiModelProperty("总有效剂量E")
    @Comment("总有效剂量E")
    @Column("TOTAL_DOSAGE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String TOTAL_DOSAGE;

    @ApiModelProperty("工作单位")
    @Comment("工作单位")
    @Column("WORK_COMPANY")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String WORK_COMPANY;

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


    public String getPERSON_DOSAGE_ID() {
        return PERSON_DOSAGE_ID;
    }

    public void setPERSON_DOSAGE_ID(String PERSON_DOSAGE_ID) {
        this.PERSON_DOSAGE_ID = PERSON_DOSAGE_ID;
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

    public String getWORK_PERSON_ID() {
        return WORK_PERSON_ID;
    }

    public void setWORK_PERSON_ID(String WORK_PERSON_ID) {
        this.WORK_PERSON_ID = WORK_PERSON_ID;
    }

    public Integer getMONITOR_YEAR() {
        return MONITOR_YEAR;
    }

    public void setMONITOR_YEAR(Integer MONITOR_YEAR) {
        this.MONITOR_YEAR = MONITOR_YEAR;
    }

    public String getDOSAGE_SEVEN() {
        return DOSAGE_SEVEN;
    }

    public void setDOSAGE_SEVEN(String DOSAGE_SEVEN) {
        this.DOSAGE_SEVEN = DOSAGE_SEVEN;
    }

    public String getDOSAGE_THREE() {
        return DOSAGE_THREE;
    }

    public void setDOSAGE_THREE(String DOSAGE_THREE) {
        this.DOSAGE_THREE = DOSAGE_THREE;
    }

    public String getDOSAGE_TEN() {
        return DOSAGE_TEN;
    }

    public void setDOSAGE_TEN(String DOSAGE_TEN) {
        this.DOSAGE_TEN = DOSAGE_TEN;
    }

    public String getIN_DOSAGE() {
        return IN_DOSAGE;
    }

    public void setIN_DOSAGE(String IN_DOSAGE) {
        this.IN_DOSAGE = IN_DOSAGE;
    }

    public String getTOTAL_DOSAGE() {
        return TOTAL_DOSAGE;
    }

    public void setTOTAL_DOSAGE(String TOTAL_DOSAGE) {
        this.TOTAL_DOSAGE = TOTAL_DOSAGE;
    }

    public String getWORK_COMPANY() {
        return WORK_COMPANY;
    }

    public void setWORK_COMPANY(String WORK_COMPANY) {
        this.WORK_COMPANY = WORK_COMPANY;
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
