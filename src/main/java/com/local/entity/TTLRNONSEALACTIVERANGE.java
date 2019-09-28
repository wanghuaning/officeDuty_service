package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;

@ApiModel("非密封放射性物质活动种类及范围表")
@Table("TT_LR_NONSEAL_ACTIVE_RANGE")
@Comment("非密封放射性物质活动种类及范围表")
public class TTLRNONSEALACTIVERANGE implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("非密封放射性物质活动种类和范围主键")
    @Comment("非密封放射性物质活动种类和范围主键")
    @Column("ACTIVE_RANGE_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String ACTIVE_RANGE_ID;

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

    @ApiModelProperty("工作场所")
    @Comment("工作场所")
    @Column("ANAME")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String ANAME;

    @ApiModelProperty("场所等级")
    @Comment("场所等级")
    @Column("ALEVEL")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String ALEVEL;

    @ApiModelProperty("核素")
    @Comment("核素")
    @Column("NUCLIDE_NAME")
    @ColDefine(type = ColType.VARCHAR,width = 1024)
    private String NUCLIDE_NAME;

    @ApiModelProperty("日等效最大操作量")
    @Comment("日等效最大操作量")
    @Column("DAYMAX_COUNT")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String DAYMAX_COUNT;

    @ApiModelProperty("年最大用量")
    @Comment("年最大用量")
    @Column("YEARMAX_COUNT")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String YEARMAX_COUNT;

    @ApiModelProperty("活动种类")
    @Comment("活动种类")
    @Column("ACTIVE_TYPE")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String ACTIVE_TYPE;

    @ApiModelProperty("场所code")
    @Comment("场所code")
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

    public String getACTIVE_RANGE_ID() {
        return ACTIVE_RANGE_ID;
    }

    public void setACTIVE_RANGE_ID(String ACTIVE_RANGE_ID) {
        this.ACTIVE_RANGE_ID = ACTIVE_RANGE_ID;
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

    public String getANAME() {
        return ANAME;
    }

    public void setANAME(String ANAME) {
        this.ANAME = ANAME;
    }

    public String getALEVEL() {
        return ALEVEL;
    }

    public void setALEVEL(String ALEVEL) {
        this.ALEVEL = ALEVEL;
    }

    public String getNUCLIDE_NAME() {
        return NUCLIDE_NAME;
    }

    public void setNUCLIDE_NAME(String NUCLIDE_NAME) {
        this.NUCLIDE_NAME = NUCLIDE_NAME;
    }

    public String getDAYMAX_COUNT() {
        return DAYMAX_COUNT;
    }

    public void setDAYMAX_COUNT(String DAYMAX_COUNT) {
        this.DAYMAX_COUNT = DAYMAX_COUNT;
    }

    public String getYEARMAX_COUNT() {
        return YEARMAX_COUNT;
    }

    public void setYEARMAX_COUNT(String YEARMAX_COUNT) {
        this.YEARMAX_COUNT = YEARMAX_COUNT;
    }

    public String getACTIVE_TYPE() {
        return ACTIVE_TYPE;
    }

    public void setACTIVE_TYPE(String ACTIVE_TYPE) {
        this.ACTIVE_TYPE = ACTIVE_TYPE;
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
