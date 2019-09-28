package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@ApiModel("放射源转让申请表")
@Table("TT_REQ_NUCLIDE_TRANSFER")
@Comment("放射源转让申请表")
public class TTREQNUCLIDETRANSFER implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
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

    @ApiModelProperty("申请文号")
    @Comment("申请文号")
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

    @ApiModelProperty("转出单位名称")
    @Comment("转出单位名称")
    @Column("OUT_COMPANY")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String OUT_COMPANY;

    @ApiModelProperty("转出单位许可证")
    @Comment("转出单位许可证")
    @Column("OUTCOMPANY_LICENSENO")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String OUTCOMPANY_LICENSENO;

    @ApiModelProperty("转出单位通讯地址")
    @Comment("转出单位通讯地址")
    @Column("OUTCOMPANY_CONTACTADDR")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String OUTCOMPANY_CONTACTADDR;

    @ApiModelProperty("转出单位经办人")
    @Comment("转出单位经办人")
    @Column("OUTCOMPANY_DOPERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String OUTCOMPANY_DOPERSON;

    @ApiModelProperty("转出单位电话传真")
    @Comment("转出单位电话传真")
    @Column("OUTCOMPANY_PHONEORFAX")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String OUTCOMPANY_PHONEORFAX;

    @ApiModelProperty("转出单位邮政编码")
    @Comment("转出单位邮政编码")
    @Column("OUTCOMPANY_POST")
    @ColDefine(type = ColType.VARCHAR,width = 6)
    private String OUTCOMPANY_POST;

    @ApiModelProperty("转入单位名称")
    @Comment("转入单位名称")
    @Column("INCOMPANY_NAME")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String INCOMPANY_NAME;

    @ApiModelProperty("转入单位许可证")
    @Comment("转入单位许可证")
    @Column("INCOMPANY_LICENSENO")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String INCOMPANY_LICENSENO;

    @ApiModelProperty("转入单位通讯地址")
    @Comment("转入单位通讯地址")
    @Column("INCOMPANY_CONTACTADDR")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String INCOMPANY_CONTACTADDR;

    @ApiModelProperty("转入单位经办人")
    @Comment("转入单位经办人")
    @Column("INCOMPANY_DOPERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String INCOMPANY_DOPERSON;

    @ApiModelProperty("转入单位电话传真")
    @Comment("转入单位电话传真")
    @Column("INCONPANY_PHONEORFAX")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String INCONPANY_PHONEORFAX;

    @ApiModelProperty("转入单位邮政编码")
    @Comment("转入单位邮政编码")
    @Column("INCOMPANY_POST")
    @ColDefine(type = ColType.VARCHAR,width = 6)
    private String INCOMPANY_POST;

    @ApiModelProperty("转入理由")
    @Comment("转入理由")
    @Column("TRANSFER_REASON")
    @ColDefine(type = ColType.INT,width = 2)
    private Integer TRANSFER_REASON;

    @ApiModelProperty("转入理由其他")
    @Comment("转入理由其他")
    @Column("TRANSFER_REASON_OTHERS")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String TRANSFER_REASON_OTHERS;

    @ApiModelProperty("附件")
    @Comment("附件")
    @Column("ATTACHMENT")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String ATTACHMENT;

    @ApiModelProperty("附件其他")
    @Comment("附件其他")
    @Column("ATTACHMENT_OTHERS")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String ATTACHMENT_OTHERS;

    @ApiModelProperty("受理状态")
    @Comment("受理状态")
    @Column("RESPONSE_STATUS")
    @ColDefine(type = ColType.INT,width = 2)
    private Integer RESPONSE_STATUS;

    @ApiModelProperty("审批是否通过")
    @Comment("审批是否通过")
    @Column("BE_PASS")
    @ColDefine(type = ColType.INT,width = 1)
    private Integer BE_PASS;

    @ApiModelProperty("审批人")
    @Comment("审批人")
    @Column("DO_PERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String DO_PERSON;

    @ApiModelProperty("审批时间")
    @Comment("审批时间")
    @Column("DO_DATE")
    @ColDefine(type = ColType.DATE,width = 7)
    private Date DO_DATE;

    @ApiModelProperty("需求来源")
    @Comment("需求来源")
    @Column("REQUEST_SOURCE")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String REQUEST_SOURCE;

    @ApiModelProperty("需求来源ID")
    @Comment("需求来源ID")
    @Column("REQUEST_SOURCE_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String REQUEST_SOURCE_ID;

    @ApiModelProperty("备份状态")
    @Comment("备份状态")
    @Column("BACKUP_STATUS")
    @ColDefine(type = ColType.INT,width = 2)
    private Integer BACKUP_STATUS;

    @ApiModelProperty("插入人")
    @Comment("插入人")
    @Column("INSERT_USER")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String INSERT_USER;

    @ApiModelProperty("插入时间")
    @Comment("插入时间")
    @Column("INSERT_DATE")
    @ColDefine(type = ColType.DATE)
    private Timestamp INSERT_DATE;

    @ApiModelProperty("修改人")
    @Comment("修改人")
    @Column("MODIFY_USER")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String MODIFY_USER;

    @ApiModelProperty("修改时间")
    @Comment("修改时间")
    @Column("MODIFY_DATE")
    @ColDefine(type = ColType.DATE,width = 6)
    private Timestamp MODIFY_DATE;

    @ApiModelProperty("审批不通过理由")
    @Comment("审批不通过理由")
    @Column("NOT_PASS_REASON")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String NOT_PASS_REASON;

    @ApiModelProperty("退回理由")
    @Comment("退回理由")
    @Column("RETREAT_REASON")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String RETREAT_REASON;

    @ApiModelProperty("用户ID")
    @Comment("用户ID")
    @Column("USER_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String USER_ID;

    @ApiModelProperty("撤销状态")
    @Comment("撤销状态")
    @Column("REVOKE_DECLARE")
    @ColDefine(type = ColType.VARCHAR,width = 8)
    private String REVOKE_DECLARE;

    @ApiModelProperty("撤销理由")
    @Comment("撤销理由")
    @Column("REVOKE_REASON")
    @ColDefine(type = ColType.VARCHAR,width = 512)
    private String REVOKE_REASON;

    @ApiModelProperty("转出单位instanceid")
    @Comment("转出单位instanceid")
    @Column("OUT_COMPANY_INSTANCEID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String OUT_COMPANY_INSTANCEID;

    @ApiModelProperty("转入单位instanceid")
    @Comment("转入单位instanceid")
    @Column("IN_COMPANY_INSTANCEID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String IN_COMPANY_INSTANCEID;

    @ApiModelProperty("转入单位区域编码")
    @Comment("转入单位区域编码")
    @Column("REGION_CODE_1")
    @ColDefine(type = ColType.VARCHAR,width = 30)
    private String REGION_CODE_1;

    @ApiModelProperty("转出单位区域编码")
    @Comment("转出单位区域编码")
    @Column("REGION_CODE_2")
    @ColDefine(type = ColType.VARCHAR,width = 30)
    private String REGION_CODE_2;

    @ApiModelProperty("是否是老数据")
    @Comment("是否是老数据")
    @Column("OLD_MARK")
    @ColDefine(type = ColType.VARCHAR,width = 16)
    private String OLD_MARK;

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

    public String getOUT_COMPANY() {
        return OUT_COMPANY;
    }

    public void setOUT_COMPANY(String OUT_COMPANY) {
        this.OUT_COMPANY = OUT_COMPANY;
    }

    public String getOUTCOMPANY_LICENSENO() {
        return OUTCOMPANY_LICENSENO;
    }

    public void setOUTCOMPANY_LICENSENO(String OUTCOMPANY_LICENSENO) {
        this.OUTCOMPANY_LICENSENO = OUTCOMPANY_LICENSENO;
    }

    public String getOUTCOMPANY_CONTACTADDR() {
        return OUTCOMPANY_CONTACTADDR;
    }

    public void setOUTCOMPANY_CONTACTADDR(String OUTCOMPANY_CONTACTADDR) {
        this.OUTCOMPANY_CONTACTADDR = OUTCOMPANY_CONTACTADDR;
    }

    public String getOUTCOMPANY_DOPERSON() {
        return OUTCOMPANY_DOPERSON;
    }

    public void setOUTCOMPANY_DOPERSON(String OUTCOMPANY_DOPERSON) {
        this.OUTCOMPANY_DOPERSON = OUTCOMPANY_DOPERSON;
    }

    public String getOUTCOMPANY_PHONEORFAX() {
        return OUTCOMPANY_PHONEORFAX;
    }

    public void setOUTCOMPANY_PHONEORFAX(String OUTCOMPANY_PHONEORFAX) {
        this.OUTCOMPANY_PHONEORFAX = OUTCOMPANY_PHONEORFAX;
    }

    public String getOUTCOMPANY_POST() {
        return OUTCOMPANY_POST;
    }

    public void setOUTCOMPANY_POST(String OUTCOMPANY_POST) {
        this.OUTCOMPANY_POST = OUTCOMPANY_POST;
    }

    public String getINCOMPANY_NAME() {
        return INCOMPANY_NAME;
    }

    public void setINCOMPANY_NAME(String INCOMPANY_NAME) {
        this.INCOMPANY_NAME = INCOMPANY_NAME;
    }

    public String getINCOMPANY_LICENSENO() {
        return INCOMPANY_LICENSENO;
    }

    public void setINCOMPANY_LICENSENO(String INCOMPANY_LICENSENO) {
        this.INCOMPANY_LICENSENO = INCOMPANY_LICENSENO;
    }

    public String getINCOMPANY_CONTACTADDR() {
        return INCOMPANY_CONTACTADDR;
    }

    public void setINCOMPANY_CONTACTADDR(String INCOMPANY_CONTACTADDR) {
        this.INCOMPANY_CONTACTADDR = INCOMPANY_CONTACTADDR;
    }

    public String getINCOMPANY_DOPERSON() {
        return INCOMPANY_DOPERSON;
    }

    public void setINCOMPANY_DOPERSON(String INCOMPANY_DOPERSON) {
        this.INCOMPANY_DOPERSON = INCOMPANY_DOPERSON;
    }

    public String getINCONPANY_PHONEORFAX() {
        return INCONPANY_PHONEORFAX;
    }

    public void setINCONPANY_PHONEORFAX(String INCONPANY_PHONEORFAX) {
        this.INCONPANY_PHONEORFAX = INCONPANY_PHONEORFAX;
    }

    public String getINCOMPANY_POST() {
        return INCOMPANY_POST;
    }

    public void setINCOMPANY_POST(String INCOMPANY_POST) {
        this.INCOMPANY_POST = INCOMPANY_POST;
    }

    public Integer getTRANSFER_REASON() {
        return TRANSFER_REASON;
    }

    public void setTRANSFER_REASON(Integer TRANSFER_REASON) {
        this.TRANSFER_REASON = TRANSFER_REASON;
    }

    public String getTRANSFER_REASON_OTHERS() {
        return TRANSFER_REASON_OTHERS;
    }

    public void setTRANSFER_REASON_OTHERS(String TRANSFER_REASON_OTHERS) {
        this.TRANSFER_REASON_OTHERS = TRANSFER_REASON_OTHERS;
    }

    public String getATTACHMENT() {
        return ATTACHMENT;
    }

    public void setATTACHMENT(String ATTACHMENT) {
        this.ATTACHMENT = ATTACHMENT;
    }

    public String getATTACHMENT_OTHERS() {
        return ATTACHMENT_OTHERS;
    }

    public void setATTACHMENT_OTHERS(String ATTACHMENT_OTHERS) {
        this.ATTACHMENT_OTHERS = ATTACHMENT_OTHERS;
    }

    public Integer getRESPONSE_STATUS() {
        return RESPONSE_STATUS;
    }

    public void setRESPONSE_STATUS(Integer RESPONSE_STATUS) {
        this.RESPONSE_STATUS = RESPONSE_STATUS;
    }

    public Integer getBE_PASS() {
        return BE_PASS;
    }

    public void setBE_PASS(Integer BE_PASS) {
        this.BE_PASS = BE_PASS;
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

    public String getREQUEST_SOURCE() {
        return REQUEST_SOURCE;
    }

    public void setREQUEST_SOURCE(String REQUEST_SOURCE) {
        this.REQUEST_SOURCE = REQUEST_SOURCE;
    }

    public String getREQUEST_SOURCE_ID() {
        return REQUEST_SOURCE_ID;
    }

    public void setREQUEST_SOURCE_ID(String REQUEST_SOURCE_ID) {
        this.REQUEST_SOURCE_ID = REQUEST_SOURCE_ID;
    }

    public Integer getBACKUP_STATUS() {
        return BACKUP_STATUS;
    }

    public void setBACKUP_STATUS(Integer BACKUP_STATUS) {
        this.BACKUP_STATUS = BACKUP_STATUS;
    }

    public String getINSERT_USER() {
        return INSERT_USER;
    }

    public void setINSERT_USER(String INSERT_USER) {
        this.INSERT_USER = INSERT_USER;
    }

    public Timestamp getINSERT_DATE() {
        return INSERT_DATE;
    }

    public void setINSERT_DATE(Timestamp INSERT_DATE) {
        this.INSERT_DATE = INSERT_DATE;
    }

    public String getMODIFY_USER() {
        return MODIFY_USER;
    }

    public void setMODIFY_USER(String MODIFY_USER) {
        this.MODIFY_USER = MODIFY_USER;
    }

    public Timestamp getMODIFY_DATE() {
        return MODIFY_DATE;
    }

    public void setMODIFY_DATE(Timestamp MODIFY_DATE) {
        this.MODIFY_DATE = MODIFY_DATE;
    }

    public String getNOT_PASS_REASON() {
        return NOT_PASS_REASON;
    }

    public void setNOT_PASS_REASON(String NOT_PASS_REASON) {
        this.NOT_PASS_REASON = NOT_PASS_REASON;
    }

    public String getRETREAT_REASON() {
        return RETREAT_REASON;
    }

    public void setRETREAT_REASON(String RETREAT_REASON) {
        this.RETREAT_REASON = RETREAT_REASON;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getREVOKE_DECLARE() {
        return REVOKE_DECLARE;
    }

    public void setREVOKE_DECLARE(String REVOKE_DECLARE) {
        this.REVOKE_DECLARE = REVOKE_DECLARE;
    }

    public String getREVOKE_REASON() {
        return REVOKE_REASON;
    }

    public void setREVOKE_REASON(String REVOKE_REASON) {
        this.REVOKE_REASON = REVOKE_REASON;
    }

    public String getOUT_COMPANY_INSTANCEID() {
        return OUT_COMPANY_INSTANCEID;
    }

    public void setOUT_COMPANY_INSTANCEID(String OUT_COMPANY_INSTANCEID) {
        this.OUT_COMPANY_INSTANCEID = OUT_COMPANY_INSTANCEID;
    }

    public String getIN_COMPANY_INSTANCEID() {
        return IN_COMPANY_INSTANCEID;
    }

    public void setIN_COMPANY_INSTANCEID(String IN_COMPANY_INSTANCEID) {
        this.IN_COMPANY_INSTANCEID = IN_COMPANY_INSTANCEID;
    }

    public String getREGION_CODE_1() {
        return REGION_CODE_1;
    }

    public void setREGION_CODE_1(String REGION_CODE_1) {
        this.REGION_CODE_1 = REGION_CODE_1;
    }

    public String getREGION_CODE_2() {
        return REGION_CODE_2;
    }

    public void setREGION_CODE_2(String REGION_CODE_2) {
        this.REGION_CODE_2 = REGION_CODE_2;
    }

    public String getOLD_MARK() {
        return OLD_MARK;
    }

    public void setOLD_MARK(String OLD_MARK) {
        this.OLD_MARK = OLD_MARK;
    }
}
