package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

@ApiModel("辐射安全与防护培训记录")
@Table("TT_LR_TRAIN_RECORD")
@Comment("辐射安全与防护培训记录")
public class TTLRTRAINRECORD implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("培训主键")
    @Comment("培训主键")
    @Column("TRAIN_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String TRAIN_ID;

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

    @ApiModelProperty("辐射安全和防护培训开始时间")
    @Comment("辐射安全和防护培训开始时间")
    @Column("BEGIN_TRAIN_DATE")
    @ColDefine(type = ColType.DATE)
    private Date BEGIN_TRAIN_DATE;

    @ApiModelProperty("辐射安全和防护培训结束时间")
    @Comment("辐射安全和防护培训结束时间")
    @Column("END_TRAIN_DATE")
    @ColDefine(type = ColType.DATE)
    private Date END_TRAIN_DATE;

    @ApiModelProperty("培训等级")
    @Comment("培训等级")
    @Column("TRAIN_LEVEL")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String TRAIN_LEVEL;

    @ApiModelProperty("培训性质")
    @Comment("培训性质")
    @Column("TRAIN_NATURE")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String TRAIN_NATURE;

    @ApiModelProperty("学时(小时)")
    @Comment("学时(小时)")
    @Column("PERIOD")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String PERIOD;

    @ApiModelProperty("培训证号")
    @Comment("培训证号")
    @Column("TRAIN_NO")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String TRAIN_NO;

    @ApiModelProperty("培训机构名称")
    @Comment("培训机构名称")
    @Column("TRAIN_ORG")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String TRAIN_ORG;

    @ApiModelProperty("培训内容")
    @Comment("培训内容")
    @Column("_CONTENT")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String _CONTENT;

    @ApiModelProperty("培训时间")
    @Comment("培训时间")
    @Column("TRAINDATETIME")
    @ColDefine(type = ColType.VARCHAR,width = 300)
    private String TRAINDATETIME;

    public String getTRAIN_ID() {
        return TRAIN_ID;
    }

    public void setTRAIN_ID(String TRAIN_ID) {
        this.TRAIN_ID = TRAIN_ID;
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

    public Date getBEGIN_TRAIN_DATE() {
        return BEGIN_TRAIN_DATE;
    }

    public void setBEGIN_TRAIN_DATE(Date BEGIN_TRAIN_DATE) {
        this.BEGIN_TRAIN_DATE = BEGIN_TRAIN_DATE;
    }

    public Date getEND_TRAIN_DATE() {
        return END_TRAIN_DATE;
    }

    public void setEND_TRAIN_DATE(Date END_TRAIN_DATE) {
        this.END_TRAIN_DATE = END_TRAIN_DATE;
    }

    public String getTRAIN_LEVEL() {
        return TRAIN_LEVEL;
    }

    public void setTRAIN_LEVEL(String TRAIN_LEVEL) {
        this.TRAIN_LEVEL = TRAIN_LEVEL;
    }

    public String getTRAIN_NATURE() {
        return TRAIN_NATURE;
    }

    public void setTRAIN_NATURE(String TRAIN_NATURE) {
        this.TRAIN_NATURE = TRAIN_NATURE;
    }

    public String getPERIOD() {
        return PERIOD;
    }

    public void setPERIOD(String PERIOD) {
        this.PERIOD = PERIOD;
    }

    public String getTRAIN_NO() {
        return TRAIN_NO;
    }

    public void setTRAIN_NO(String TRAIN_NO) {
        this.TRAIN_NO = TRAIN_NO;
    }

    public String getTRAIN_ORG() {
        return TRAIN_ORG;
    }

    public void setTRAIN_ORG(String TRAIN_ORG) {
        this.TRAIN_ORG = TRAIN_ORG;
    }

    public String get_CONTENT() {
        return _CONTENT;
    }

    public void set_CONTENT(String _CONTENT) {
        this._CONTENT = _CONTENT;
    }

    public String getTRAINDATETIME() {
        return TRAINDATETIME;
    }

    public void setTRAINDATETIME(String TRAINDATETIME) {
        this.TRAINDATETIME = TRAINDATETIME;
    }
}
