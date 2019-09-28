package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;

@ApiModel("辐射安全许可信息表")
@Table("L_RAD_Resource")
public class LRADResource implements Serializable {

    @Name
    @Prev(els = {@EL("uuid()")})
    @ApiModelProperty("系统id")
    @Comment("系统id")
    @Column("ID")
    @ColDefine(type = ColType.VARCHAR, width = 50)
    private String ID;

    @ApiModelProperty("企业名称")
    @Comment("企业名称")
    @Column("NAME")
    @ColDefine(type = ColType.VARCHAR, width = 2000)
    private String NAME;

    @ApiModelProperty("统一社会信用代码")
    @Comment("统一社会信用代码")
    @Column("UNIFY_SOCIETY_CODE")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String UNIFY_SOCIETY_CODE;

    @ApiModelProperty("法人姓名")
    @Comment("法人姓名")
    @Column("LEGAL_PERSON")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String LEGAL_PERSON;

    @ApiModelProperty("辐射安全许可批复文号")
    @Comment("辐射安全许可批复文号")
    @Column("PASS_NO")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String PASS_NO;

    @ApiModelProperty("辐射安全许可批复结果")
    @Comment("辐射安全许可批复结果")
    @Column("PASS_RESULT")
    @ColDefine(type = ColType.VARCHAR, width = 2000)
    private String PASS_RESULT;

    @ApiModelProperty("批次号")
    @Comment("批次号")
    @Column("CD_BATCH")
    @ColDefine(type = ColType.VARCHAR, width = 30)
    private String CD_BATCH;

    @ApiModelProperty("状态标记")
    @Comment("状态标记")
    @Column("OPERSTATUS")
    @ColDefine(type = ColType.VARCHAR, width = 1)
    private String OPERSTATUS;

    @ApiModelProperty("物理ID")
    @Comment("物理ID")
    @Column("S_GUID")
    @ColDefine(type = ColType.VARCHAR, width = 38)
    private String S_GUID;

    @ApiModelProperty("传输时间")
    @Comment("传输时间")
    @Column("CTRTIME")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String CTRTIME;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getUNIFY_SOCIETY_CODE() {
        return UNIFY_SOCIETY_CODE;
    }

    public void setUNIFY_SOCIETY_CODE(String UNIFY_SOCIETY_CODE) {
        this.UNIFY_SOCIETY_CODE = UNIFY_SOCIETY_CODE;
    }

    public String getLEGAL_PERSON() {
        return LEGAL_PERSON;
    }

    public void setLEGAL_PERSON(String LEGAL_PERSON) {
        this.LEGAL_PERSON = LEGAL_PERSON;
    }

    public String getPASS_NO() {
        return PASS_NO;
    }

    public void setPASS_NO(String PASS_NO) {
        this.PASS_NO = PASS_NO;
    }

    public String getPASS_RESULT() {
        return PASS_RESULT;
    }

    public void setPASS_RESULT(String PASS_RESULT) {
        this.PASS_RESULT = PASS_RESULT;
    }

    public String getCD_BATCH() {
        return CD_BATCH;
    }

    public void setCD_BATCH(String CD_BATCH) {
        this.CD_BATCH = CD_BATCH;
    }

    public String getOPERSTATUS() {
        return OPERSTATUS;
    }

    public void setOPERSTATUS(String OPERSTATUS) {
        this.OPERSTATUS = OPERSTATUS;
    }

    public String getS_GUID() {
        return S_GUID;
    }

    public void setS_GUID(String s_GUID) {
        S_GUID = s_GUID;
    }

    public String getCTRTIME() {
        return CTRTIME;
    }

    public void setCTRTIME(String CTRTIME) {
        this.CTRTIME = CTRTIME;
    }
}
