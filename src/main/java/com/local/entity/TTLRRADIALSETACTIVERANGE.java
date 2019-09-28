package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;

@ApiModel("射线装置活动种类及范围表")
@Table("TT_LR_RADIALSET_ACTIVERANGE")
@Comment("射线装置活动种类及范围表")
public class TTLRRADIALSETACTIVERANGE implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("射线装置活动种类及范围主键")
    @Comment("射线装置活动种类及范围主键")
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

    @ApiModelProperty("场所名称")
    @Comment("场所名称")
    @Column("NAME")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String NAME;

    @ApiModelProperty("装置名称")
    @Comment("装置名称")
    @Column("SET_NAME")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String SET_NAME;

    @ApiModelProperty("装置类别")
    @Comment("装置类别")
    @Column("SET_TYPE")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String SET_TYPE;

    @ApiModelProperty("装置数量")
    @Comment("装置数量")
    @Column("SET_COUNT")
    @ColDefine(type = ColType.INT,width = 3)
    private Integer SET_COUNT;

    @ApiModelProperty("活动种类")
    @Comment("活动种类")
    @Column("ACTIVE_TYPE")
    @ColDefine(type = ColType.VARCHAR,width = 32)
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

    public String getSET_NAME() {
        return SET_NAME;
    }

    public void setSET_NAME(String SET_NAME) {
        this.SET_NAME = SET_NAME;
    }

    public String getSET_TYPE() {
        return SET_TYPE;
    }

    public void setSET_TYPE(String SET_TYPE) {
        this.SET_TYPE = SET_TYPE;
    }

    public Integer getSET_COUNT() {
        return SET_COUNT;
    }

    public void setSET_COUNT(Integer SET_COUNT) {
        this.SET_COUNT = SET_COUNT;
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
