package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

@ApiModel("检测仪器表")
@Table("TT_LR_MONITOR_INSTRUMENT")
@Comment("检测仪器表")
public class TTLRMONITORINSTRUMENT implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("监测仪器主键")
    @Comment("监测仪器主键")
    @Column("MONITOR_INSTRUMENT_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String MONITOR_INSTRUMENT_ID;

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

    @ApiModelProperty("仪器名称")
    @Comment("仪器名称")
    @Column("NAME")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String NAME;

    @ApiModelProperty("型号")
    @Comment("型号")
    @Column("STYLE")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String STYLE;

    @ApiModelProperty("购置时间")
    @Comment("购置时间")
    @Column("BUY_DATE")
    @ColDefine(type = ColType.DATE)
    private Date BUY_DATE;

    @ApiModelProperty("仪器状态")
    @Comment("仪器状态")
    @Column("STATUS")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String STATUS;

    @ApiModelProperty("数量")
    @Comment("数量")
    @Column("AMOUNT")
    @ColDefine(type = ColType.INT,width = 3)
    private Integer AMOUNT;

    @ApiModelProperty("备注")
    @Comment("备注")
    @Column("REMARK")
    @ColDefine(type = ColType.VARCHAR,width = 1024)
    private String REMARK;

    public String getMONITOR_INSTRUMENT_ID() {
        return MONITOR_INSTRUMENT_ID;
    }

    public void setMONITOR_INSTRUMENT_ID(String MONITOR_INSTRUMENT_ID) {
        this.MONITOR_INSTRUMENT_ID = MONITOR_INSTRUMENT_ID;
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

    public String getSTYLE() {
        return STYLE;
    }

    public void setSTYLE(String STYLE) {
        this.STYLE = STYLE;
    }

    public Date getBUY_DATE() {
        return BUY_DATE;
    }

    public void setBUY_DATE(Date BUY_DATE) {
        this.BUY_DATE = BUY_DATE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public Integer getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(Integer AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }
}
