package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@ApiModel("许可证延续申请表")
@Table("TT_LCR_COMPANY")
@Comment("许可证延续申请表")
public class TTLCRCOMPANY implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("许可证延期主键")
    @Comment("许可证延期主键")
    @Column("LCR_COMPANY_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String LCR_COMPANY_ID;

    @ApiModelProperty("业务id")
    @Comment("业务id")
    @Column("YWID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String YWID;

    @ApiModelProperty("申请编号")
    @Comment("申请编号")
    @Column("REQUEST_NO")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String REQUEST_NO;

    @ApiModelProperty("受理编号")
    @Comment("受理编号")
    @Column("RESPONSE_NO")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String RESPONSE_NO;

    @ApiModelProperty("批准文号")
    @Comment("批准文号")
    @Column("PASS_NO")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String PASS_NO;

    @ApiModelProperty("单位名称")
    @Comment("单位名称")
    @Column("NAME")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String NAME;

    @ApiModelProperty("单位地址")
    @Comment("单位地址")
    @Column("ADDRESS")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String ADDRESS;

    @ApiModelProperty("法人")
    @Comment("法人")
    @Column("LEGAL_PERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String LEGAL_PERSON;

    @ApiModelProperty("法人电话")
    @Comment("法人电话")
    @Column("LEGAL_PERSON_PHONE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String LEGAL_PERSON_PHONE;

    @ApiModelProperty("法人证件类型")
    @Comment("法人证件类型")
    @Column("LEGAL_PERSON_TYPE")
    @ColDefine(type = ColType.INT,width = 2)
    private Integer LEGAL_PERSON_TYPE;

    @ApiModelProperty("法人证件号码")
    @Comment("法人证件号码")
    @Column("LEGAL_PERSON_ID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String LEGAL_PERSON_ID;

    @ApiModelProperty("许可证号码")
    @Comment("许可证号码")
    @Column("LICENSE_NO")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String LICENSE_NO;

    @ApiModelProperty("类型及范围")
    @Comment("类型及范围")
    @Column("TYPE_AND_RANGE")
    @ColDefine(type = ColType.VARCHAR,width = 512)
    private String TYPE_AND_RANGE;

    @ApiModelProperty("许可证生效日期")
    @Comment("许可证生效日期")
    @Column("LICENSE_STARTDATE")
    @ColDefine(type = ColType.DATE)
    private Timestamp LICENSE_STARTDATE;

    @ApiModelProperty("许可证失效日期")
    @Comment("许可证失效日期")
    @Column("LICENSE_ENDDATE")
    @ColDefine(type = ColType.DATE)
    private Timestamp LICENSE_ENDDATE;

    @ApiModelProperty("经办人")
    @Comment("经办人")
    @Column("COMPANY_DOPERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String COMPANY_DOPERSON;

    @ApiModelProperty("联系电话")
    @Comment("联系电话")
    @Column("COMPANY_DOPERSON_PHONE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String COMPANY_DOPERSON_PHONE;

    @ApiModelProperty("受理状态")
    @Comment("受理状态")
    @Column("RESPONSE_STATUS")
    @ColDefine(type = ColType.INT,width = 3)
    private Integer RESPONSE_STATUS;

    @ApiModelProperty("不批准理由")
    @Comment("不批准理由")
    @Column("NOT_PASS_REASON")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String NOT_PASS_REASON;

    @ApiModelProperty("经办人")
    @Comment("经办人")
    @Column("DO_PERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String DO_PERSON;

    @ApiModelProperty("经办人日期")
    @Comment("经办人日期")
    @Column("DO_DATE")
    @ColDefine(type = ColType.DATE)
    private Date DO_DATE;

    @ApiModelProperty("许可证延期日期")
    @Comment("许可证延期日期")
    @Column("LCR_DATE")
    @ColDefine(type = ColType.DATE)
    private Date LCR_DATE;

    @ApiModelProperty("单位id")
    @Comment("单位id")
    @Column("INSTANCEID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String INSTANCEID;

    @ApiModelProperty("许可证审批机关")
    @Comment("许可证审批机关")
    @Column("LICENSE_ISSUE_ORGAN")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String LICENSE_ISSUE_ORGAN;

    @ApiModelProperty("单位company_id")
    @Comment("单位company_id")
    @Column("COMPANY_ID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String COMPANY_ID;

    @ApiModelProperty("提交日期")
    @Comment("提交日期")
    @Column("SUBMIT_DATE")
    @ColDefine(type = ColType.DATE)
    private Date SUBMIT_DATE;

    @ApiModelProperty("提交人")
    @Comment("提交人")
    @Column("SUBMIT_USER")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String SUBMIT_USER;

    @ApiModelProperty("发证日期")
    @Comment("发证日期")
    @Column("CERTIFICATE_DATE")
    @ColDefine(type = ColType.DATE)
    private Date CERTIFICATE_DATE;

    @ApiModelProperty("新增人")
    @Comment("新增人")
    @Column("INSERT_USER")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String INSERT_USER;

    @ApiModelProperty("新增时间")
    @Comment("新增时间")
    @Column("INSERT_DATE")
    @ColDefine(type = ColType.DATE)
    private Date INSERT_DATE;

    @ApiModelProperty("修改人")
    @Comment("修改人")
    @Column("MODIFY_USER")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String MODIFY_USER;

    @ApiModelProperty("修改时间")
    @Comment("修改时间")
    @Column("MODIFY_DATE")
    @ColDefine(type = ColType.DATE)
    private Date MODIFY_DATE;

    public String getLCR_COMPANY_ID() {
        return LCR_COMPANY_ID;
    }

    public void setLCR_COMPANY_ID(String LCR_COMPANY_ID) {
        this.LCR_COMPANY_ID = LCR_COMPANY_ID;
    }

    public String getYWID() {
        return YWID;
    }

    public void setYWID(String YWID) {
        this.YWID = YWID;
    }

    public String getREQUEST_NO() {
        return REQUEST_NO;
    }

    public void setREQUEST_NO(String REQUEST_NO) {
        this.REQUEST_NO = REQUEST_NO;
    }

    public String getRESPONSE_NO() {
        return RESPONSE_NO;
    }

    public void setRESPONSE_NO(String RESPONSE_NO) {
        this.RESPONSE_NO = RESPONSE_NO;
    }

    public String getPASS_NO() {
        return PASS_NO;
    }

    public void setPASS_NO(String PASS_NO) {
        this.PASS_NO = PASS_NO;
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

    public String getLEGAL_PERSON() {
        return LEGAL_PERSON;
    }

    public void setLEGAL_PERSON(String LEGAL_PERSON) {
        this.LEGAL_PERSON = LEGAL_PERSON;
    }

    public String getLEGAL_PERSON_PHONE() {
        return LEGAL_PERSON_PHONE;
    }

    public void setLEGAL_PERSON_PHONE(String LEGAL_PERSON_PHONE) {
        this.LEGAL_PERSON_PHONE = LEGAL_PERSON_PHONE;
    }

    public Integer getLEGAL_PERSON_TYPE() {
        return LEGAL_PERSON_TYPE;
    }

    public void setLEGAL_PERSON_TYPE(Integer LEGAL_PERSON_TYPE) {
        this.LEGAL_PERSON_TYPE = LEGAL_PERSON_TYPE;
    }

    public String getLEGAL_PERSON_ID() {
        return LEGAL_PERSON_ID;
    }

    public void setLEGAL_PERSON_ID(String LEGAL_PERSON_ID) {
        this.LEGAL_PERSON_ID = LEGAL_PERSON_ID;
    }

    public String getLICENSE_NO() {
        return LICENSE_NO;
    }

    public void setLICENSE_NO(String LICENSE_NO) {
        this.LICENSE_NO = LICENSE_NO;
    }

    public String getTYPE_AND_RANGE() {
        return TYPE_AND_RANGE;
    }

    public void setTYPE_AND_RANGE(String TYPE_AND_RANGE) {
        this.TYPE_AND_RANGE = TYPE_AND_RANGE;
    }

    public Timestamp getLICENSE_STARTDATE() {
        return LICENSE_STARTDATE;
    }

    public void setLICENSE_STARTDATE(Timestamp LICENSE_STARTDATE) {
        this.LICENSE_STARTDATE = LICENSE_STARTDATE;
    }

    public Timestamp getLICENSE_ENDDATE() {
        return LICENSE_ENDDATE;
    }

    public void setLICENSE_ENDDATE(Timestamp LICENSE_ENDDATE) {
        this.LICENSE_ENDDATE = LICENSE_ENDDATE;
    }

    public String getCOMPANY_DOPERSON() {
        return COMPANY_DOPERSON;
    }

    public void setCOMPANY_DOPERSON(String COMPANY_DOPERSON) {
        this.COMPANY_DOPERSON = COMPANY_DOPERSON;
    }

    public String getCOMPANY_DOPERSON_PHONE() {
        return COMPANY_DOPERSON_PHONE;
    }

    public void setCOMPANY_DOPERSON_PHONE(String COMPANY_DOPERSON_PHONE) {
        this.COMPANY_DOPERSON_PHONE = COMPANY_DOPERSON_PHONE;
    }

    public Integer getRESPONSE_STATUS() {
        return RESPONSE_STATUS;
    }

    public void setRESPONSE_STATUS(Integer RESPONSE_STATUS) {
        this.RESPONSE_STATUS = RESPONSE_STATUS;
    }

    public String getNOT_PASS_REASON() {
        return NOT_PASS_REASON;
    }

    public void setNOT_PASS_REASON(String NOT_PASS_REASON) {
        this.NOT_PASS_REASON = NOT_PASS_REASON;
    }

    public String getDO_PERSON() {
        return DO_PERSON;
    }

    public void setDO_PERSON(String DO_PERSON) {
        this.DO_PERSON = DO_PERSON;
    }

    public Date getDO_DATE() {
        return DO_DATE;
    }

    public void setDO_DATE(Date DO_DATE) {
        this.DO_DATE = DO_DATE;
    }

    public Date getLCR_DATE() {
        return LCR_DATE;
    }

    public void setLCR_DATE(Date LCR_DATE) {
        this.LCR_DATE = LCR_DATE;
    }

    public String getINSTANCEID() {
        return INSTANCEID;
    }

    public void setINSTANCEID(String INSTANCEID) {
        this.INSTANCEID = INSTANCEID;
    }

    public String getLICENSE_ISSUE_ORGAN() {
        return LICENSE_ISSUE_ORGAN;
    }

    public void setLICENSE_ISSUE_ORGAN(String LICENSE_ISSUE_ORGAN) {
        this.LICENSE_ISSUE_ORGAN = LICENSE_ISSUE_ORGAN;
    }

    public String getCOMPANY_ID() {
        return COMPANY_ID;
    }

    public void setCOMPANY_ID(String COMPANY_ID) {
        this.COMPANY_ID = COMPANY_ID;
    }

    public Date getSUBMIT_DATE() {
        return SUBMIT_DATE;
    }

    public void setSUBMIT_DATE(Date SUBMIT_DATE) {
        this.SUBMIT_DATE = SUBMIT_DATE;
    }

    public String getSUBMIT_USER() {
        return SUBMIT_USER;
    }

    public void setSUBMIT_USER(String SUBMIT_USER) {
        this.SUBMIT_USER = SUBMIT_USER;
    }

    public Date getCERTIFICATE_DATE() {
        return CERTIFICATE_DATE;
    }

    public void setCERTIFICATE_DATE(Date CERTIFICATE_DATE) {
        this.CERTIFICATE_DATE = CERTIFICATE_DATE;
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

    public String getMODIFY_USER() {
        return MODIFY_USER;
    }

    public void setMODIFY_USER(String MODIFY_USER) {
        this.MODIFY_USER = MODIFY_USER;
    }

    public Date getMODIFY_DATE() {
        return MODIFY_DATE;
    }

    public void setMODIFY_DATE(Date MODIFY_DATE) {
        this.MODIFY_DATE = MODIFY_DATE;
    }
}
