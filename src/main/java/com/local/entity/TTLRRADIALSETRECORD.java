package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

@ApiModel("许可证申请单位射线装置台帐表")
@Table("TT_LR_RADIALSET_RECORD")
@Comment("许可证申请单位射线装置台帐表")
public class TTLRRADIALSETRECORD implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("射线装置台帐主键")
    @Comment("射线装置台帐主键")
    @Column("RECORD_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String RECORD_ID;

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

    @ApiModelProperty("装置名称")
    @Comment("装置名称")
    @Column("SET_NAME")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String SET_NAME;

    @ApiModelProperty("装置名称详细")
    @Comment("装置名称详细")
    @Column("SET_NAME_INFO")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String SET_NAME_INFO;

    @ApiModelProperty("规格型号")
    @Comment("规格型号")
    @Column("SPEC_AND_MODEL")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String SPEC_AND_MODEL;

    @ApiModelProperty("射线装置类别")
    @Comment("射线装置类别")
    @Column("SET_TYPE")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String SET_TYPE;

    @ApiModelProperty("射线装置用途")
    @Comment("射线装置用途")
    @Column("USE_TYPE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String USE_TYPE;

    @ApiModelProperty("电压")
    @Comment("电压")
    @Column("VOLTAGE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String VOLTAGE;

    @ApiModelProperty("电流")
    @Comment("电流")
    @Column("AMPEREMETER")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String AMPEREMETER;

    @ApiModelProperty("功率")
    @Comment("功率")
    @Column("POWER")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String POWER;

    @ApiModelProperty("场所")
    @Comment("场所")
    @Column("SITE")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String SITE;

    @ApiModelProperty("来源")
    @Comment("来源")
    @Column("SOURCE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String SOURCE;

    @ApiModelProperty("来源审核人")
    @Comment("来源审核人")
    @Column("SRC_AUDITPERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String SRC_AUDITPERSON;

    @ApiModelProperty("来源审核日期")
    @Comment("来源审核日期")
    @Column("SRC_AUDITDATE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String SRC_AUDITDATE;

    @ApiModelProperty("去向")
    @Comment("去向")
    @Column("DESTINATION")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String DESTINATION;

    @ApiModelProperty("去向审核人")
    @Comment("去向审核人")
    @Column("DEST_AUDITPERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String DEST_AUDITPERSON;

    @ApiModelProperty("去向审核日期")
    @Comment("去向审核日期")
    @Column("DEST_AUDITDATE")
    @ColDefine(type = ColType.DATE)
    private java.sql.Timestamp DEST_AUDITDATE;

    @ApiModelProperty("是否历史")
    @Comment("是否历史")
    @Column("BE_HISTORY")
    @ColDefine(type = ColType.INT,width = 1)
    private Integer BE_HISTORY;

    @ApiModelProperty("粒子能量")
    @Comment("粒子能量")
    @Column("PARTICLE_ENERGY")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String PARTICLE_ENERGY;

    @ApiModelProperty("备注")
    @Comment("备注")
    @Column("REMARK")
    @ColDefine(type = ColType.VARCHAR,width = 512)
    private String REMARK;

    @ApiModelProperty("用户类型")
    @Comment("用户类型")
    @Column("USER_TYPE")
    @ColDefine(type = ColType.INT,width = 1)
    private Integer USER_TYPE;

    @ApiModelProperty("状态")
    @Comment("状态")
    @Column("STATUS")
    @ColDefine(type = ColType.VARCHAR,width = 16)
    private String STATUS;

    @ApiModelProperty("添加用户")
    @Comment("添加用户")
    @Column("INSERT_USER")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String INSERT_USER;

    @ApiModelProperty("添加时间")
    @Comment("添加时间")
    @Column("INSERT_DATE")
    @ColDefine(type = ColType.DATE,width = 7)
    private Date INSERT_DATE;

    @ApiModelProperty("其它用途")
    @Comment("其它用途")
    @Column("USE_TYPE_OTHER")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String USE_TYPE_OTHER;

    @ApiModelProperty("详细场所")
    @Comment("详细场所")
    @Column("SITE_DETAIL")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String SITE_DETAIL;

    @ApiModelProperty("场所编码")
    @Comment("场所编码")
    @Column("SITE_CODE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String SITE_CODE;

    @ApiModelProperty("标志")
    @Comment("标志")
    @Column("FLAG")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String FLAG;

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

    public String getRECORD_ID() {
        return RECORD_ID;
    }

    public void setRECORD_ID(String RECORD_ID) {
        this.RECORD_ID = RECORD_ID;
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

    public String getSET_NAME() {
        return SET_NAME;
    }

    public void setSET_NAME(String SET_NAME) {
        this.SET_NAME = SET_NAME;
    }

    public String getSET_NAME_INFO() {
        return SET_NAME_INFO;
    }

    public void setSET_NAME_INFO(String SET_NAME_INFO) {
        this.SET_NAME_INFO = SET_NAME_INFO;
    }

    public String getSPEC_AND_MODEL() {
        return SPEC_AND_MODEL;
    }

    public void setSPEC_AND_MODEL(String SPEC_AND_MODEL) {
        this.SPEC_AND_MODEL = SPEC_AND_MODEL;
    }

    public String getSET_TYPE() {
        return SET_TYPE;
    }

    public void setSET_TYPE(String SET_TYPE) {
        this.SET_TYPE = SET_TYPE;
    }

    public String getUSE_TYPE() {
        return USE_TYPE;
    }

    public void setUSE_TYPE(String USE_TYPE) {
        this.USE_TYPE = USE_TYPE;
    }

    public String getVOLTAGE() {
        return VOLTAGE;
    }

    public void setVOLTAGE(String VOLTAGE) {
        this.VOLTAGE = VOLTAGE;
    }

    public String getAMPEREMETER() {
        return AMPEREMETER;
    }

    public void setAMPEREMETER(String AMPEREMETER) {
        this.AMPEREMETER = AMPEREMETER;
    }

    public String getPOWER() {
        return POWER;
    }

    public void setPOWER(String POWER) {
        this.POWER = POWER;
    }

    public String getSITE() {
        return SITE;
    }

    public void setSITE(String SITE) {
        this.SITE = SITE;
    }

    public String getSOURCE() {
        return SOURCE;
    }

    public void setSOURCE(String SOURCE) {
        this.SOURCE = SOURCE;
    }

    public String getSRC_AUDITPERSON() {
        return SRC_AUDITPERSON;
    }

    public void setSRC_AUDITPERSON(String SRC_AUDITPERSON) {
        this.SRC_AUDITPERSON = SRC_AUDITPERSON;
    }

    public String getSRC_AUDITDATE() {
        return SRC_AUDITDATE;
    }

    public void setSRC_AUDITDATE(String SRC_AUDITDATE) {
        this.SRC_AUDITDATE = SRC_AUDITDATE;
    }

    public String getDESTINATION() {
        return DESTINATION;
    }

    public void setDESTINATION(String DESTINATION) {
        this.DESTINATION = DESTINATION;
    }

    public String getDEST_AUDITPERSON() {
        return DEST_AUDITPERSON;
    }

    public void setDEST_AUDITPERSON(String DEST_AUDITPERSON) {
        this.DEST_AUDITPERSON = DEST_AUDITPERSON;
    }

    public Timestamp getDEST_AUDITDATE() {
        return DEST_AUDITDATE;
    }

    public void setDEST_AUDITDATE(Timestamp DEST_AUDITDATE) {
        this.DEST_AUDITDATE = DEST_AUDITDATE;
    }

    public Integer getBE_HISTORY() {
        return BE_HISTORY;
    }

    public void setBE_HISTORY(Integer BE_HISTORY) {
        this.BE_HISTORY = BE_HISTORY;
    }

    public String getPARTICLE_ENERGY() {
        return PARTICLE_ENERGY;
    }

    public void setPARTICLE_ENERGY(String PARTICLE_ENERGY) {
        this.PARTICLE_ENERGY = PARTICLE_ENERGY;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public Integer getUSER_TYPE() {
        return USER_TYPE;
    }

    public void setUSER_TYPE(Integer USER_TYPE) {
        this.USER_TYPE = USER_TYPE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getINSERT_USER() {
        return INSERT_USER;
    }

    public void setINSERT_USER(String INSERT_USER) {
        this.INSERT_USER = INSERT_USER;
    }

    public Date getINSERT_DATE() {
        return INSERT_DATE;
    }

    public void setINSERT_DATE(Date INSERT_DATE) {
        this.INSERT_DATE = INSERT_DATE;
    }

    public String getUSE_TYPE_OTHER() {
        return USE_TYPE_OTHER;
    }

    public void setUSE_TYPE_OTHER(String USE_TYPE_OTHER) {
        this.USE_TYPE_OTHER = USE_TYPE_OTHER;
    }

    public String getSITE_DETAIL() {
        return SITE_DETAIL;
    }

    public void setSITE_DETAIL(String SITE_DETAIL) {
        this.SITE_DETAIL = SITE_DETAIL;
    }

    public String getSITE_CODE() {
        return SITE_CODE;
    }

    public void setSITE_CODE(String SITE_CODE) {
        this.SITE_CODE = SITE_CODE;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
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
