package com.local.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;


@ApiModel("工作场所表")
@Table("TT_LR_SITE")
@Comment("工作场所表")
public class TTLRSITE implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("主键")
    @Comment("主键")
    @Column("SITE_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String SITE_ID;

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

    @ApiModelProperty("场所名称")
    @Comment("场所名称")
    @Column("NAME")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String NAME;

    @ApiModelProperty("场所地址")
    @Comment("场所地址")
    @Column("ADDRESS")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String ADDRESS;

    @ApiModelProperty("负责人")
    @Comment("负责人")
    @Column("PRINCIPAL")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String PRINCIPAL;

    @ApiModelProperty("部门名称")
    @Comment("部门名称")
    @Column("DEPARTMENT")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String DEPARTMENT;

    @ApiModelProperty("部门ID")
    @Comment("部门ID")
    @Column("WSDEPT_ID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String WSDEPT_ID;

    @ApiModelProperty("场所编码")
    @Comment("场所编码")
    @Column("SITE_CODE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String SITE_CODE;

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

    public String getSITE_ID() {
        return SITE_ID;
    }

    public void setSITE_ID(String SITE_ID) {
        this.SITE_ID = SITE_ID;
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

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getPRINCIPAL() {
        return PRINCIPAL;
    }

    public void setPRINCIPAL(String PRINCIPAL) {
        this.PRINCIPAL = PRINCIPAL;
    }

    public String getDEPARTMENT() {
        return DEPARTMENT;
    }

    public void setDEPARTMENT(String DEPARTMENT) {
        this.DEPARTMENT = DEPARTMENT;
    }

    public String getWSDEPT_ID() {
        return WSDEPT_ID;
    }

    public void setWSDEPT_ID(String WSDEPT_ID) {
        this.WSDEPT_ID = WSDEPT_ID;
    }

    public String getSITE_CODE() {
        return SITE_CODE;
    }

    public void setSITE_CODE(String SITE_CODE) {
        this.SITE_CODE = SITE_CODE;
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
