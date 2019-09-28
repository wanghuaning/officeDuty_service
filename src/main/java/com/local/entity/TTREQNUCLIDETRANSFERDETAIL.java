package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@ApiModel("放射源转让明细表")
@Table("TT_REQ_NUCLIDE_TRANSFER_DETAIL")
@Comment("放射源转让明细表")
public class TTREQNUCLIDETRANSFERDETAIL implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("放射源转让明细主键")
    @Comment("放射源转让明细主键")
    @Column("DETAIL_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String DETAIL_ID;

    @ApiModelProperty("放射源转让主键")
    @Comment("放射源转让主键")
    @Column("TRANSFER_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String TRANSFER_ID;

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

    @ApiModelProperty("核素名称")
    @Comment("核素名称")
    @Column("NUCLIDE")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String NUCLIDE;

    @ApiModelProperty("出厂日期")
    @Comment("出厂日期")
    @Column("LEAVE_FACTORY_DATE")
    @ColDefine(type = ColType.DATE)
    private Timestamp LEAVE_FACTORY_DATE;

    @ApiModelProperty("出厂活度")
    @Comment("出厂活度")
    @Column("LEAVE_FACTORY_ACTIVITY")
    @ColDefine(type = ColType.INT,width = 38)
    private Integer LEAVE_FACTORY_ACTIVITY;

    @ApiModelProperty("标号")
    @Comment("标号")
    @Column("TAB")
    @ColDefine(type = ColType.VARCHAR,width = 16)
    private String TAB;

    @ApiModelProperty("编码")
    @Comment("编码")
    @Column("CODE")
    @ColDefine(type = ColType.VARCHAR,width = 16)
    private String CODE;

    @ApiModelProperty("类别")
    @Comment("类别")
    @Column("CATEGORY")
    @ColDefine(type = ColType.VARCHAR,width = 16)
    private String CATEGORY;

    @ApiModelProperty("用途")
    @Comment("用途")
    @Column("USE_TYPE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String USE_TYPE;

    @ApiModelProperty("场所")
    @Comment("场所")
    @Column("SITE")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String SITE;

    @ApiModelProperty("批量")
    @Comment("批量")
    @Column("BATCH")
    @ColDefine(type = ColType.INT,width = 10)
    private Integer BATCH;

    @ApiModelProperty("活度幂")
    @Comment("活度幂")
    @Column("POWER")
    @ColDefine(type = ColType.INT,width = 4)
    private Integer POWER;

    @ApiModelProperty("是否备份")
    @Comment("是否备份")
    @Column("BE_BACKUP")
    @ColDefine(type = ColType.INT,width = 2)
    private Integer BE_BACKUP;

    @ApiModelProperty("备份更新")
    @Comment("备份更新")
    @Column("BACK_UPDATE")
    @ColDefine(type = ColType.DATE,width = 7)
    private Date BACK_UPDATE;

    @ApiModelProperty("状态")
    @Comment("状态")
    @Column("STATUS")
    @ColDefine(type = ColType.INT,width = 4)
    private Integer STATUS;

    @ApiModelProperty("台账类型")
    @Comment("台账类型")
    @Column("RECORD_TYPE")
    @ColDefine(type = ColType.INT,width = 2)
    private Integer RECORD_TYPE;

    @ApiModelProperty("台账id")
    @Comment("台账id")
    @Column("RECORD_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String RECORD_ID;

    @ApiModelProperty("详细地址")
    @Comment("详细地址")
    @Column("ADDRESS")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String ADDRESS;

    @ApiModelProperty("是否发货")
    @Comment("是否发货")
    @Column("BE_REPLENISH_INFO")
    @ColDefine(type = ColType.INT,width = 1)
    private Integer BE_REPLENISH_INFO;

    @ApiModelProperty("其他用途")
    @Comment("其他用途")
    @Column("USE_TYPE_OTHER")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String USE_TYPE_OTHER;

    @ApiModelProperty("转入单位备案人")
    @Comment("转入单位备案人")
    @Column("INPERSON")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String INPERSON;

    @ApiModelProperty("转出单位备案人")
    @Comment("转出单位备案人")
    @Column("OUTPERSON")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String OUTPERSON;

    @ApiModelProperty("转入单位备案时间")
    @Comment("转入单位备案时间")
    @Column("INDATE")
    @ColDefine(type = ColType.VARCHAR,width = 30)
    private String INDATE;

    @ApiModelProperty("转出单位备案时间")
    @Comment("转出单位备案时间")
    @Column("OUTDATE")
    @ColDefine(type = ColType.VARCHAR,width = 30)
    private String OUTDATE;

    @ApiModelProperty("发货日期")
    @Comment("发货日期")
    @Column("SEND_DATE")
    @ColDefine(type = ColType.DATE,width = 7)
    private Date SEND_DATE;

    @ApiModelProperty("场所编码")
    @Comment("场所编码")
    @Column("SITE_CODE")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String SITE_CODE;

    public String getDETAIL_ID() {
        return DETAIL_ID;
    }

    public void setDETAIL_ID(String DETAIL_ID) {
        this.DETAIL_ID = DETAIL_ID;
    }

    public String getTRANSFER_ID() {
        return TRANSFER_ID;
    }

    public void setTRANSFER_ID(String TRANSFER_ID) {
        this.TRANSFER_ID = TRANSFER_ID;
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

    public String getNUCLIDE() {
        return NUCLIDE;
    }

    public void setNUCLIDE(String NUCLIDE) {
        this.NUCLIDE = NUCLIDE;
    }

    public Timestamp getLEAVE_FACTORY_DATE() {
        return LEAVE_FACTORY_DATE;
    }

    public void setLEAVE_FACTORY_DATE(Timestamp LEAVE_FACTORY_DATE) {
        this.LEAVE_FACTORY_DATE = LEAVE_FACTORY_DATE;
    }

    public Integer getLEAVE_FACTORY_ACTIVITY() {
        return LEAVE_FACTORY_ACTIVITY;
    }

    public void setLEAVE_FACTORY_ACTIVITY(Integer LEAVE_FACTORY_ACTIVITY) {
        this.LEAVE_FACTORY_ACTIVITY = LEAVE_FACTORY_ACTIVITY;
    }

    public String getTAB() {
        return TAB;
    }

    public void setTAB(String TAB) {
        this.TAB = TAB;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    public String getUSE_TYPE() {
        return USE_TYPE;
    }

    public void setUSE_TYPE(String USE_TYPE) {
        this.USE_TYPE = USE_TYPE;
    }

    public String getSITE() {
        return SITE;
    }

    public void setSITE(String SITE) {
        this.SITE = SITE;
    }

    public Integer getBATCH() {
        return BATCH;
    }

    public void setBATCH(Integer BATCH) {
        this.BATCH = BATCH;
    }

    public Integer getPOWER() {
        return POWER;
    }

    public void setPOWER(Integer POWER) {
        this.POWER = POWER;
    }

    public Integer getBE_BACKUP() {
        return BE_BACKUP;
    }

    public void setBE_BACKUP(Integer BE_BACKUP) {
        this.BE_BACKUP = BE_BACKUP;
    }

    public Date getBACK_UPDATE() {
        return BACK_UPDATE;
    }

    public void setBACK_UPDATE(Date BACK_UPDATE) {
        this.BACK_UPDATE = BACK_UPDATE;
    }

    public Integer getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(Integer STATUS) {
        this.STATUS = STATUS;
    }

    public Integer getRECORD_TYPE() {
        return RECORD_TYPE;
    }

    public void setRECORD_TYPE(Integer RECORD_TYPE) {
        this.RECORD_TYPE = RECORD_TYPE;
    }

    public String getRECORD_ID() {
        return RECORD_ID;
    }

    public void setRECORD_ID(String RECORD_ID) {
        this.RECORD_ID = RECORD_ID;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public Integer getBE_REPLENISH_INFO() {
        return BE_REPLENISH_INFO;
    }

    public void setBE_REPLENISH_INFO(Integer BE_REPLENISH_INFO) {
        this.BE_REPLENISH_INFO = BE_REPLENISH_INFO;
    }

    public String getUSE_TYPE_OTHER() {
        return USE_TYPE_OTHER;
    }

    public void setUSE_TYPE_OTHER(String USE_TYPE_OTHER) {
        this.USE_TYPE_OTHER = USE_TYPE_OTHER;
    }

    public String getINPERSON() {
        return INPERSON;
    }

    public void setINPERSON(String INPERSON) {
        this.INPERSON = INPERSON;
    }

    public String getOUTPERSON() {
        return OUTPERSON;
    }

    public void setOUTPERSON(String OUTPERSON) {
        this.OUTPERSON = OUTPERSON;
    }

    public String getINDATE() {
        return INDATE;
    }

    public void setINDATE(String INDATE) {
        this.INDATE = INDATE;
    }

    public String getOUTDATE() {
        return OUTDATE;
    }

    public void setOUTDATE(String OUTDATE) {
        this.OUTDATE = OUTDATE;
    }

    public Date getSEND_DATE() {
        return SEND_DATE;
    }

    public void setSEND_DATE(Date SEND_DATE) {
        this.SEND_DATE = SEND_DATE;
    }

    public String getSITE_CODE() {
        return SITE_CODE;
    }

    public void setSITE_CODE(String SITE_CODE) {
        this.SITE_CODE = SITE_CODE;
    }
}
