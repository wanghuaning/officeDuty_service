package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;

@ApiModel("放射源活动种类及范围表")
@Table("TT_LR_NUCLIDE_ACTIVE_RANGE")
@Comment("放射源活动种类及范围表")
public class TTLRNUCLIDEACTIVERANGE implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("放射源活动种类和范围主键")
    @Comment("放射源活动种类和范围主键")
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

    @ApiModelProperty("工作场所code")
    @Comment("工作场所code")
    @Column("NAME")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String NAME;

    @ApiModelProperty("核素")
    @Comment("核素")
    @Column("NUCLIDE_NAME")
    @ColDefine(type = ColType.VARCHAR,width = 16)
    private String NUCLIDE_NAME;

    @ApiModelProperty("类别")
    @Comment("类别")
    @Column("NUCLIDE_CATEGORY")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String NUCLIDE_CATEGORY;

    @ApiModelProperty("总活度")
    @Comment("总活度")
    @Column("TOTAL_ACTIVITY")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String TOTAL_ACTIVITY;

    @ApiModelProperty("活动类别")
    @Comment("活动类别")
    @Column("ACTIVE_TYPE")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String ACTIVE_TYPE;

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

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getNUCLIDE_NAME() {
        return NUCLIDE_NAME;
    }

    public void setNUCLIDE_NAME(String NUCLIDE_NAME) {
        this.NUCLIDE_NAME = NUCLIDE_NAME;
    }

    public String getNUCLIDE_CATEGORY() {
        return NUCLIDE_CATEGORY;
    }

    public void setNUCLIDE_CATEGORY(String NUCLIDE_CATEGORY) {
        this.NUCLIDE_CATEGORY = NUCLIDE_CATEGORY;
    }

    public String getTOTAL_ACTIVITY() {
        return TOTAL_ACTIVITY;
    }

    public void setTOTAL_ACTIVITY(String TOTAL_ACTIVITY) {
        this.TOTAL_ACTIVITY = TOTAL_ACTIVITY;
    }

    public String getACTIVE_TYPE() {
        return ACTIVE_TYPE;
    }

    public void setACTIVE_TYPE(String ACTIVE_TYPE) {
        this.ACTIVE_TYPE = ACTIVE_TYPE;
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
