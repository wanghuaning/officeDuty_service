package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@ApiModel("放射源进口审批表")
@Table("TT_REQ_NUCLIDE_IMPORT")
@Comment("放射源进口审批表")
public class TTREQNUCLIDEIMPORT implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("放射源进口审批主键")
    @Comment("放射源进口审批主键")
    @Column("IMPORT_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String IMPORT_ID;

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

    @ApiModelProperty("进口单位名称")
    @Comment("进口单位名称")
    @Column("IMPORTCOMPANY_NAME")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String IMPORTCOMPANY_NAME;

    @ApiModelProperty("进口单位许可证")
    @Comment("进口单位许可证")
    @Column("IMPORTCOMPANY_LICENSENO")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String IMPORTCOMPANY_LICENSENO;

    @ApiModelProperty("进口单位通讯地址")
    @Comment("进口单位通讯地址")
    @Column("IMPORTCOMPANY_CONTACTADDR")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String IMPORTCOMPANY_CONTACTADDR;

    @ApiModelProperty("进口单位经办人")
    @Comment("进口单位经办人")
    @Column("IMPORTCOMPANY_DOPERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String IMPORTCOMPANY_DOPERSON;

    @ApiModelProperty("进口单位电话传真")
    @Comment("进口单位电话传真")
    @Column("IMPORTCOMPANY_PHONEORFAX")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String IMPORTCOMPANY_PHONEORFAX;

    @ApiModelProperty("进口单位邮编")
    @Comment("进口单位邮编")
    @Column("IMPORTCOMPANY_POST")
    @ColDefine(type = ColType.VARCHAR,width = 6)
    private String IMPORTCOMPANY_POST;

    @ApiModelProperty("使用单位名称")
    @Comment("使用单位名称")
    @Column("USECOMPANY_NAME")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String USECOMPANY_NAME;

    @ApiModelProperty("使用单位许可证")
    @Comment("使用单位许可证")
    @Column("USECOMPANY_LICENSENO")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String USECOMPANY_LICENSENO;

    @ApiModelProperty("使用单位通讯地址")
    @Comment("使用单位通讯地址")
    @Column("USECPMPANY_CONTACTADDR")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String USECPMPANY_CONTACTADDR;

    @ApiModelProperty("使用单位邮编")
    @Comment("使用单位邮编")
    @Column("USECOMPANY_POST")
    @ColDefine(type = ColType.VARCHAR,width = 6)
    private String USECOMPANY_POST;

    @ApiModelProperty("使用单位经办人")
    @Comment("使用单位经办人")
    @Column("USECOMPANY_DOPERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String USECOMPANY_DOPERSON;

    @ApiModelProperty("使用单位电话传真")
    @Comment("使用单位电话传真")
    @Column("USECOMPANY_PHONEORFAX")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String USECOMPANY_PHONEORFAX;

    @ApiModelProperty("出口国家")
    @Comment("出口国家")
    @Column("NUCLIDE_EXPORTCOUNTRY")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String NUCLIDE_EXPORTCOUNTRY;

    @ApiModelProperty("产源国家")
    @Comment("产源国家")
    @Column("NUCLIDE_PRODUCE_COUNTRY")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String NUCLIDE_PRODUCE_COUNTRY;

    @ApiModelProperty("产源单位名称")
    @Comment("产源单位名称")
    @Column("NUCLIDE_MANUFACTURE")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String NUCLIDE_MANUFACTURE;

    @ApiModelProperty("进口理由")
    @Comment("进口理由")
    @Column("IMPORT_REASON")
    @ColDefine(type = ColType.INT,width = 2)
    private Integer IMPORT_REASON;

    @ApiModelProperty("进口其他理由")
    @Comment("进口其他理由")
    @Column("IMPORT_REASON_OTHERS")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String IMPORT_REASON_OTHERS;

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

    @ApiModelProperty("新增人")
    @Comment("新增人")
    @Column("INSERT_USER")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String INSERT_USER;

    @ApiModelProperty("新增时间")
    @Comment("新增时间")
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
    @ColDefine(type = ColType.DATE)
    private Timestamp MODIFY_DATE;

    @ApiModelProperty("审批不通过理由")
    @Comment("审批不通过理由")
    @Column("NOT_PASS_REASON")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String NOT_PASS_REASON;

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

    @ApiModelProperty("审批人")
    @Comment("审批人")
    @Column("DO_PERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String DO_PERSON;

    @ApiModelProperty("审批是否通过")
    @Comment("审批是否通过")
    @Column("BE_PASS")
    @ColDefine(type = ColType.INT,width = 8)
    private Integer BE_PASS;

    @ApiModelProperty("退回理由")
    @Comment("退回理由")
    @Column("RETREAT_REASON")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String RETREAT_REASON;

    @ApiModelProperty("货物编码")
    @Comment("货物编码")
    @Column("GOODS_CODE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String GOODS_CODE;

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

    @ApiModelProperty("进口单位instanceid")
    @Comment("进口单位instanceid")
    @Column("IMPORT_COMPANY_INSTANCEID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String IMPORT_COMPANY_INSTANCEID;

    @ApiModelProperty("用户单位instanceid")
    @Comment("用户单位instanceid")
    @Column("USE_COMPANY_INSTANCEID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String USE_COMPANY_INSTANCEID;

    @ApiModelProperty("进口单位是否备案")
    @Comment("进口单位是否备案")
    @Column("IMPORT_IS_RECORD")
    @ColDefine(type = ColType.VARCHAR,width = 2)
    private String IMPORT_IS_RECORD;

    @ApiModelProperty("进口单位区域编码")
    @Comment("进口单位区域编码")
    @Column("REGION_CODE_1")
    @ColDefine(type = ColType.VARCHAR,width = 30)
    private String REGION_CODE_1;

    @ApiModelProperty("用户单位区域编码")
    @Comment("用户单位区域编码")
    @Column("REGION_CODE_2")
    @ColDefine(type = ColType.VARCHAR,width = 30)
    private String REGION_CODE_2;

    @ApiModelProperty("老数据标志")
    @Comment("老数据标志")
    @Column("OLD_MARK")
    @ColDefine(type = ColType.VARCHAR,width = 16)
    private String OLD_MARK;

    public String getIMPORT_ID() {
        return IMPORT_ID;
    }

    public void setIMPORT_ID(String IMPORT_ID) {
        this.IMPORT_ID = IMPORT_ID;
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

    public String getIMPORTCOMPANY_NAME() {
        return IMPORTCOMPANY_NAME;
    }

    public void setIMPORTCOMPANY_NAME(String IMPORTCOMPANY_NAME) {
        this.IMPORTCOMPANY_NAME = IMPORTCOMPANY_NAME;
    }

    public String getIMPORTCOMPANY_LICENSENO() {
        return IMPORTCOMPANY_LICENSENO;
    }

    public void setIMPORTCOMPANY_LICENSENO(String IMPORTCOMPANY_LICENSENO) {
        this.IMPORTCOMPANY_LICENSENO = IMPORTCOMPANY_LICENSENO;
    }

    public String getIMPORTCOMPANY_CONTACTADDR() {
        return IMPORTCOMPANY_CONTACTADDR;
    }

    public void setIMPORTCOMPANY_CONTACTADDR(String IMPORTCOMPANY_CONTACTADDR) {
        this.IMPORTCOMPANY_CONTACTADDR = IMPORTCOMPANY_CONTACTADDR;
    }

    public String getIMPORTCOMPANY_DOPERSON() {
        return IMPORTCOMPANY_DOPERSON;
    }

    public void setIMPORTCOMPANY_DOPERSON(String IMPORTCOMPANY_DOPERSON) {
        this.IMPORTCOMPANY_DOPERSON = IMPORTCOMPANY_DOPERSON;
    }

    public String getIMPORTCOMPANY_PHONEORFAX() {
        return IMPORTCOMPANY_PHONEORFAX;
    }

    public void setIMPORTCOMPANY_PHONEORFAX(String IMPORTCOMPANY_PHONEORFAX) {
        this.IMPORTCOMPANY_PHONEORFAX = IMPORTCOMPANY_PHONEORFAX;
    }

    public String getIMPORTCOMPANY_POST() {
        return IMPORTCOMPANY_POST;
    }

    public void setIMPORTCOMPANY_POST(String IMPORTCOMPANY_POST) {
        this.IMPORTCOMPANY_POST = IMPORTCOMPANY_POST;
    }

    public String getUSECOMPANY_NAME() {
        return USECOMPANY_NAME;
    }

    public void setUSECOMPANY_NAME(String USECOMPANY_NAME) {
        this.USECOMPANY_NAME = USECOMPANY_NAME;
    }

    public String getUSECOMPANY_LICENSENO() {
        return USECOMPANY_LICENSENO;
    }

    public void setUSECOMPANY_LICENSENO(String USECOMPANY_LICENSENO) {
        this.USECOMPANY_LICENSENO = USECOMPANY_LICENSENO;
    }

    public String getUSECPMPANY_CONTACTADDR() {
        return USECPMPANY_CONTACTADDR;
    }

    public void setUSECPMPANY_CONTACTADDR(String USECPMPANY_CONTACTADDR) {
        this.USECPMPANY_CONTACTADDR = USECPMPANY_CONTACTADDR;
    }

    public String getUSECOMPANY_POST() {
        return USECOMPANY_POST;
    }

    public void setUSECOMPANY_POST(String USECOMPANY_POST) {
        this.USECOMPANY_POST = USECOMPANY_POST;
    }

    public String getUSECOMPANY_DOPERSON() {
        return USECOMPANY_DOPERSON;
    }

    public void setUSECOMPANY_DOPERSON(String USECOMPANY_DOPERSON) {
        this.USECOMPANY_DOPERSON = USECOMPANY_DOPERSON;
    }

    public String getUSECOMPANY_PHONEORFAX() {
        return USECOMPANY_PHONEORFAX;
    }

    public void setUSECOMPANY_PHONEORFAX(String USECOMPANY_PHONEORFAX) {
        this.USECOMPANY_PHONEORFAX = USECOMPANY_PHONEORFAX;
    }

    public String getNUCLIDE_EXPORTCOUNTRY() {
        return NUCLIDE_EXPORTCOUNTRY;
    }

    public void setNUCLIDE_EXPORTCOUNTRY(String NUCLIDE_EXPORTCOUNTRY) {
        this.NUCLIDE_EXPORTCOUNTRY = NUCLIDE_EXPORTCOUNTRY;
    }

    public String getNUCLIDE_PRODUCE_COUNTRY() {
        return NUCLIDE_PRODUCE_COUNTRY;
    }

    public void setNUCLIDE_PRODUCE_COUNTRY(String NUCLIDE_PRODUCE_COUNTRY) {
        this.NUCLIDE_PRODUCE_COUNTRY = NUCLIDE_PRODUCE_COUNTRY;
    }

    public String getNUCLIDE_MANUFACTURE() {
        return NUCLIDE_MANUFACTURE;
    }

    public void setNUCLIDE_MANUFACTURE(String NUCLIDE_MANUFACTURE) {
        this.NUCLIDE_MANUFACTURE = NUCLIDE_MANUFACTURE;
    }

    public Integer getIMPORT_REASON() {
        return IMPORT_REASON;
    }

    public void setIMPORT_REASON(Integer IMPORT_REASON) {
        this.IMPORT_REASON = IMPORT_REASON;
    }

    public String getIMPORT_REASON_OTHERS() {
        return IMPORT_REASON_OTHERS;
    }

    public void setIMPORT_REASON_OTHERS(String IMPORT_REASON_OTHERS) {
        this.IMPORT_REASON_OTHERS = IMPORT_REASON_OTHERS;
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

    public String getDO_PERSON() {
        return DO_PERSON;
    }

    public void setDO_PERSON(String DO_PERSON) {
        this.DO_PERSON = DO_PERSON;
    }

    public Integer getBE_PASS() {
        return BE_PASS;
    }

    public void setBE_PASS(Integer BE_PASS) {
        this.BE_PASS = BE_PASS;
    }

    public String getRETREAT_REASON() {
        return RETREAT_REASON;
    }

    public void setRETREAT_REASON(String RETREAT_REASON) {
        this.RETREAT_REASON = RETREAT_REASON;
    }

    public String getGOODS_CODE() {
        return GOODS_CODE;
    }

    public void setGOODS_CODE(String GOODS_CODE) {
        this.GOODS_CODE = GOODS_CODE;
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

    public String getIMPORT_COMPANY_INSTANCEID() {
        return IMPORT_COMPANY_INSTANCEID;
    }

    public void setIMPORT_COMPANY_INSTANCEID(String IMPORT_COMPANY_INSTANCEID) {
        this.IMPORT_COMPANY_INSTANCEID = IMPORT_COMPANY_INSTANCEID;
    }

    public String getUSE_COMPANY_INSTANCEID() {
        return USE_COMPANY_INSTANCEID;
    }

    public void setUSE_COMPANY_INSTANCEID(String USE_COMPANY_INSTANCEID) {
        this.USE_COMPANY_INSTANCEID = USE_COMPANY_INSTANCEID;
    }

    public String getIMPORT_IS_RECORD() {
        return IMPORT_IS_RECORD;
    }

    public void setIMPORT_IS_RECORD(String IMPORT_IS_RECORD) {
        this.IMPORT_IS_RECORD = IMPORT_IS_RECORD;
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
