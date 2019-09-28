package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

@ApiModel("许可证申请单位许可证变更申请表")
@Table("TT_LMR_COMPANY")
@Comment("许可证申请单位许可证变更申请表")
public class TTLMRCOMPANY implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("许可证变更主键")
    @Comment("许可证变更主键")
    @Column("LMR_COMPANY_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String LMR_COMPANY_ID;

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

    @ApiModelProperty("旧单位名称")
    @Comment("旧单位名称")
    @Column("OLD_NAME")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String OLD_NAME;

    @ApiModelProperty("新单位名称")
    @Comment("新单位名称")
    @Column("NEW_NAME")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String NEW_NAME;

    @ApiModelProperty("旧单位地址")
    @Comment("旧单位地址")
    @Column("OLD_ADDRESS")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String OLD_ADDRESS;

    @ApiModelProperty("新单位地址")
    @Comment("新单位地址")
    @Column("NEW_ADDRESS")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String NEW_ADDRESS;

    @ApiModelProperty("旧法人")
    @Comment("旧法人")
    @Column("OLD_LEGAL_PERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String OLD_LEGAL_PERSON;

    @ApiModelProperty("旧法人证件类型")
    @Comment("旧法人证件类型")
    @Column("OLD_LEGAL_PERSONID_TYPE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String OLD_LEGAL_PERSONID_TYPE;

    @ApiModelProperty("旧法人证件号码")
    @Comment("旧法人证件号码")
    @Column("OLD_LEGAL_PERSONID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String OLD_LEGAL_PERSONID;

    @ApiModelProperty("新法人")
    @Comment("新法人")
    @Column("NEW_LEGAL")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String NEW_LEGAL;

    @ApiModelProperty("新法人证件类型")
    @Comment("新法人证件类型")
    @Column("NEW_LEGAL_PERSONID_TYPE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String NEW_LEGAL_PERSONID_TYPE;

    @ApiModelProperty("新法人证件号码")
    @Comment("新法人证件号码")
    @Column("NEW_LEGAL_PERSONID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String NEW_LEGAL_PERSONID;

    @ApiModelProperty("许可证号码")
    @Comment("许可证号码")
    @Column("LICENSE_NO")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String LICENSE_NO;

    @ApiModelProperty("范围及类型")
    @Comment("范围及类型")
    @Column("TYPE_AND_RANGE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String TYPE_AND_RANGE;

    @ApiModelProperty("许可证生效日期")
    @Comment("许可证生效日期")
    @Column("LICENSE_STARTDATE")
    @ColDefine(type = ColType.DATE)
    private Date LICENSE_STARTDATE;

    @ApiModelProperty("许可证失效日期")
    @Comment("许可证失效日期")
    @Column("LICENSE_EDNDATE")
    @ColDefine(type = ColType.DATE)
    private Date LICENSE_EDNDATE;

    @ApiModelProperty("单位经办人")
    @Comment("单位经办人")
    @Column("COMPANY_DOPERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String COMPANY_DOPERSON;

    @ApiModelProperty("单位经办人电话")
    @Comment("单位经办人电话")
    @Column("COMPANY_DOPERSON_PHONE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String COMPANY_DOPERSON_PHONE;

    @ApiModelProperty("受理状态")
    @Comment("受理状态")
    @Column("RESPONSE_STATUS")
    @ColDefine(type = ColType.INT,width = 2)
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

    @ApiModelProperty("单位id")
    @Comment("单位id")
    @Column("INSTANCEID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String INSTANCEID;

    @ApiModelProperty("变更地址区县区域code")
    @Comment("变更地址区县区域code")
    @Column("REGION_CODE_1")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String REGION_CODE_1;

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

    public String getLMR_COMPANY_ID() {
        return LMR_COMPANY_ID;
    }

    public void setLMR_COMPANY_ID(String LMR_COMPANY_ID) {
        this.LMR_COMPANY_ID = LMR_COMPANY_ID;
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

    public String getOLD_NAME() {
        return OLD_NAME;
    }

    public void setOLD_NAME(String OLD_NAME) {
        this.OLD_NAME = OLD_NAME;
    }

    public String getNEW_NAME() {
        return NEW_NAME;
    }

    public void setNEW_NAME(String NEW_NAME) {
        this.NEW_NAME = NEW_NAME;
    }

    public String getOLD_ADDRESS() {
        return OLD_ADDRESS;
    }

    public void setOLD_ADDRESS(String OLD_ADDRESS) {
        this.OLD_ADDRESS = OLD_ADDRESS;
    }

    public String getNEW_ADDRESS() {
        return NEW_ADDRESS;
    }

    public void setNEW_ADDRESS(String NEW_ADDRESS) {
        this.NEW_ADDRESS = NEW_ADDRESS;
    }

    public String getOLD_LEGAL_PERSON() {
        return OLD_LEGAL_PERSON;
    }

    public void setOLD_LEGAL_PERSON(String OLD_LEGAL_PERSON) {
        this.OLD_LEGAL_PERSON = OLD_LEGAL_PERSON;
    }

    public String getOLD_LEGAL_PERSONID_TYPE() {
        return OLD_LEGAL_PERSONID_TYPE;
    }

    public void setOLD_LEGAL_PERSONID_TYPE(String OLD_LEGAL_PERSONID_TYPE) {
        this.OLD_LEGAL_PERSONID_TYPE = OLD_LEGAL_PERSONID_TYPE;
    }

    public String getOLD_LEGAL_PERSONID() {
        return OLD_LEGAL_PERSONID;
    }

    public void setOLD_LEGAL_PERSONID(String OLD_LEGAL_PERSONID) {
        this.OLD_LEGAL_PERSONID = OLD_LEGAL_PERSONID;
    }

    public String getNEW_LEGAL() {
        return NEW_LEGAL;
    }

    public void setNEW_LEGAL(String NEW_LEGAL) {
        this.NEW_LEGAL = NEW_LEGAL;
    }

    public String getNEW_LEGAL_PERSONID_TYPE() {
        return NEW_LEGAL_PERSONID_TYPE;
    }

    public void setNEW_LEGAL_PERSONID_TYPE(String NEW_LEGAL_PERSONID_TYPE) {
        this.NEW_LEGAL_PERSONID_TYPE = NEW_LEGAL_PERSONID_TYPE;
    }

    public String getNEW_LEGAL_PERSONID() {
        return NEW_LEGAL_PERSONID;
    }

    public void setNEW_LEGAL_PERSONID(String NEW_LEGAL_PERSONID) {
        this.NEW_LEGAL_PERSONID = NEW_LEGAL_PERSONID;
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

    public Date getLICENSE_STARTDATE() {
        return LICENSE_STARTDATE;
    }

    public void setLICENSE_STARTDATE(Date LICENSE_STARTDATE) {
        this.LICENSE_STARTDATE = LICENSE_STARTDATE;
    }

    public Date getLICENSE_EDNDATE() {
        return LICENSE_EDNDATE;
    }

    public void setLICENSE_EDNDATE(Date LICENSE_EDNDATE) {
        this.LICENSE_EDNDATE = LICENSE_EDNDATE;
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

    public String getINSTANCEID() {
        return INSTANCEID;
    }

    public void setINSTANCEID(String INSTANCEID) {
        this.INSTANCEID = INSTANCEID;
    }

    public String getREGION_CODE_1() {
        return REGION_CODE_1;
    }

    public void setREGION_CODE_1(String REGION_CODE_1) {
        this.REGION_CODE_1 = REGION_CODE_1;
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
}
