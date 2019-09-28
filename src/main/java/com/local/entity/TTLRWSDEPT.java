package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;


@ApiModel("涉源部门表")
@Table("TT_LR_WSDEPT")
@Comment("涉源部门表")
public class TTLRWSDEPT implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("主键")
    @Comment("主键")
    @Column("ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String ID;

    @ApiModelProperty("涉源部门名称")
    @Comment("涉源部门名称")
    @Column("DEPTNAME")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String DEPTNAME;

    @ApiModelProperty("所在省份")
    @Comment("所在省份")
    @Column("PROVINCE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String PROVINCE;

    @ApiModelProperty("所在市")
    @Comment("所在市")
    @Column("CITY")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String CITY;

    @ApiModelProperty("所在区")
    @Comment("所在区")
    @Column("COUNTRY")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String COUNTRY;

    @ApiModelProperty("所在区区域code")
    @Comment("所在区区域code")
    @Column("REGION_CODE")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String REGION_CODE;

    @ApiModelProperty("详细地址")
    @Comment("详细地址")
    @Column("DETAIL_ADDR")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String DETAIL_ADDR;

    @ApiModelProperty("更新时间")
    @Comment("更新时间")
    @Column("UPDATETIME")
    @ColDefine(type = ColType.DATE)
    private Timestamp UPDATETIME;

    @ApiModelProperty("插入人")
    @Comment("插入人")
    @Column("CREATEPERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String CREATEPERSON;

    @ApiModelProperty("许可证申请单位主键")
    @Comment("许可证申请单位主键")
    @Column("COMPANY_ID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String COMPANY_ID;

    @ApiModelProperty("涉源部门负责人")
    @Comment("涉源部门负责人")
    @Column("PRINCIPAL")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String PRINCIPAL;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDEPTNAME() {
        return DEPTNAME;
    }

    public void setDEPTNAME(String DEPTNAME) {
        this.DEPTNAME = DEPTNAME;
    }

    public String getPROVINCE() {
        return PROVINCE;
    }

    public void setPROVINCE(String PROVINCE) {
        this.PROVINCE = PROVINCE;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getCOUNTRY() {
        return COUNTRY;
    }

    public void setCOUNTRY(String COUNTRY) {
        this.COUNTRY = COUNTRY;
    }

    public String getREGION_CODE() {
        return REGION_CODE;
    }

    public void setREGION_CODE(String REGION_CODE) {
        this.REGION_CODE = REGION_CODE;
    }

    public String getDETAIL_ADDR() {
        return DETAIL_ADDR;
    }

    public void setDETAIL_ADDR(String DETAIL_ADDR) {
        this.DETAIL_ADDR = DETAIL_ADDR;
    }

    public Timestamp getUPDATETIME() {
        return UPDATETIME;
    }

    public void setUPDATETIME(Timestamp UPDATETIME) {
        this.UPDATETIME = UPDATETIME;
    }

    public String getCREATEPERSON() {
        return CREATEPERSON;
    }

    public void setCREATEPERSON(String CREATEPERSON) {
        this.CREATEPERSON = CREATEPERSON;
    }

    public String getCOMPANY_ID() {
        return COMPANY_ID;
    }

    public void setCOMPANY_ID(String COMPANY_ID) {
        this.COMPANY_ID = COMPANY_ID;
    }

    public String getPRINCIPAL() {
        return PRINCIPAL;
    }

    public void setPRINCIPAL(String PRINCIPAL) {
        this.PRINCIPAL = PRINCIPAL;
    }
}
