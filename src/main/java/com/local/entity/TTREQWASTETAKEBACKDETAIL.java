package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

@ApiModel("放射源收贮备案明细表")
@Table("TT_REQ_WASTE_TAKEBACK_DETAIL")
@Comment("放射源收贮备案明细表")
public class TTREQWASTETAKEBACKDETAIL implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("主键")
    @Comment("主键")
    @Column("DETAIL_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String DETAIL_ID;

    @ApiModelProperty("外键")
    @Comment("外键")
    @Column("TAKEBACK_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String TAKEBACK_ID;

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
    @Column("LEAVEFACTORY_DATE")
    @ColDefine(type = ColType.DATE)
    private Date LEAVEFACTORY_DATE;

    @ApiModelProperty("出厂活度")
    @Comment("出厂活度")
    @Column("LEAVEFACTORY_ACTIVITY")
    @ColDefine(type = ColType.INT,width = 38)
    private Integer LEAVEFACTORY_ACTIVITY;

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

    @ApiModelProperty("备注")
    @Comment("备注")
    @Column("REMARK")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String REMARK;

    @ApiModelProperty("活度幂")
    @Comment("活度幂")
    @Column("POWER")
    @ColDefine(type = ColType.INT,width = 4)
    private Integer POWER;

    @ApiModelProperty("状态")
    @Comment("状态")
    @Column("STATUS")
    @ColDefine(type = ColType.VARCHAR,width = 4)
    private String STATUS;

    @ApiModelProperty("台账id")
    @Comment("台账id")
    @Column("RECORD_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String RECORD_ID;

    @ApiModelProperty("台账类型")
    @Comment("台账类型")
    @Column("RECORD_TYPE")
    @ColDefine(type = ColType.INT,width = 2)
    private Integer RECORD_TYPE;

    @ApiModelProperty("接受单位备案人")
    @Comment("接受单位备案人")
    @Column("INPERSON")
    @ColDefine(type = ColType.VARCHAR,width = 50)
    private String INPERSON;

    @ApiModelProperty("送交单位备案")
    @Comment("送交单位备案")
    @Column("OUTPERSON")
    @ColDefine(type = ColType.VARCHAR,width = 50)
    private String OUTPERSON;

    @ApiModelProperty("接受单位备案时间")
    @Comment("接受单位备案时间")
    @Column("INDATE")
    @ColDefine(type = ColType.VARCHAR,width = 30)
    private String INDATE;

    @ApiModelProperty("送交单位备案时间")
    @Comment("送交单位备案时间")
    @Column("OUTDATE")
    @ColDefine(type = ColType.VARCHAR,width = 30)
    private String OUTDATE;

    @ApiModelProperty("用途")
    @Comment("用途")
    @Column("USE_TYPE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String USE_TYPE;

    @ApiModelProperty("其他用途")
    @Comment("其他用途")
    @Column("OTHER_USE_TYPE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String OTHER_USE_TYPE;

    public String getDETAIL_ID() {
        return DETAIL_ID;
    }

    public void setDETAIL_ID(String DETAIL_ID) {
        this.DETAIL_ID = DETAIL_ID;
    }

    public String getTAKEBACK_ID() {
        return TAKEBACK_ID;
    }

    public void setTAKEBACK_ID(String TAKEBACK_ID) {
        this.TAKEBACK_ID = TAKEBACK_ID;
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

    public Date getLEAVEFACTORY_DATE() {
        return LEAVEFACTORY_DATE;
    }

    public void setLEAVEFACTORY_DATE(Date LEAVEFACTORY_DATE) {
        this.LEAVEFACTORY_DATE = LEAVEFACTORY_DATE;
    }

    public Integer getLEAVEFACTORY_ACTIVITY() {
        return LEAVEFACTORY_ACTIVITY;
    }

    public void setLEAVEFACTORY_ACTIVITY(Integer LEAVEFACTORY_ACTIVITY) {
        this.LEAVEFACTORY_ACTIVITY = LEAVEFACTORY_ACTIVITY;
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

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public Integer getPOWER() {
        return POWER;
    }

    public void setPOWER(Integer POWER) {
        this.POWER = POWER;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getRECORD_ID() {
        return RECORD_ID;
    }

    public void setRECORD_ID(String RECORD_ID) {
        this.RECORD_ID = RECORD_ID;
    }

    public Integer getRECORD_TYPE() {
        return RECORD_TYPE;
    }

    public void setRECORD_TYPE(Integer RECORD_TYPE) {
        this.RECORD_TYPE = RECORD_TYPE;
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

    public String getUSE_TYPE() {
        return USE_TYPE;
    }

    public void setUSE_TYPE(String USE_TYPE) {
        this.USE_TYPE = USE_TYPE;
    }

    public String getOTHER_USE_TYPE() {
        return OTHER_USE_TYPE;
    }

    public void setOTHER_USE_TYPE(String OTHER_USE_TYPE) {
        this.OTHER_USE_TYPE = OTHER_USE_TYPE;
    }
}
