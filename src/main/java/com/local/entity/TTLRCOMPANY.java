package com.local.entity;

import java.io.Serializable;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.util.Date;
import java.sql.Timestamp;


@ApiModel("单位基本信息表")
@Table("TT_LR_COMPANY")
@Comment("单位基本信息表")
public class TTLRCOMPANY implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
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

    @ApiModelProperty("所属省份")
    @Comment("所属省份")
    @Column("PROVINCE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String PROVINCE;

    @ApiModelProperty("所属市区")
    @Comment("所属市区")
    @Column("CITY")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String CITY;

    @ApiModelProperty("所属区县")
    @Comment("所属区县")
    @Column("DISTRICT")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String DISTRICT;

    @ApiModelProperty("注册地址")
    @Comment("注册地址")
    @Column("REGIST_ADDR")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String REGIST_ADDR;

    @ApiModelProperty("注册地址邮编")
    @Comment("注册地址邮编")
    @Column("REGIST_ADDR_POST")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String REGIST_ADDR_POST;

    @ApiModelProperty("通讯地址")
    @Comment("通讯地址")
    @Column("CONTACT_ADDR")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String CONTACT_ADDR;

    @ApiModelProperty("通讯地址邮编")
    @Comment("通讯地址邮编")
    @Column("CONTACT_ADDR_POST")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String CONTACT_ADDR_POST;

    @ApiModelProperty("联系人")
    @Comment("联系人")
    @Column("CONTACT_PERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String CONTACT_PERSON;

    @ApiModelProperty("联系电话")
    @Comment("联系电话")
    @Column("CONTACT_PHONE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String CONTACT_PHONE;

    @ApiModelProperty("经度度")
    @Comment("经度度")
    @Column("LONGITUDE")
    @ColDefine(type = ColType.INT,width = 3)
    private Integer LONGITUDE;

    @ApiModelProperty("经度分")
    @Comment("经度分")
    @Column("LONGITUDE_HOUR")
    @ColDefine(type = ColType.INT,width = 2)
    private Integer LONGITUDE_HOUR;

    @ApiModelProperty("经度秒")
    @Comment("经度秒")
    @Column("LONGITUDE_SECOND")
    @ColDefine(type = ColType.INT,width = 2)
    private Integer LONGITUDE_SECOND;

    @ApiModelProperty("纬度度")
    @Comment("纬度度")
    @Column("LATITUDE")
    @ColDefine(type = ColType.INT,width = 3)
    private Integer LATITUDE;

    @ApiModelProperty("纬度分")
    @Comment("纬度分")
    @Column("LATITUDE_HOUR")
    @ColDefine(type = ColType.INT,width = 2)
    private Integer LATITUDE_HOUR;

    @ApiModelProperty("纬度秒")
    @Comment("纬度秒")
    @Column("LATITUDE_SECOND")
    @ColDefine(type = ColType.INT,width = 2)
    private Integer LATITUDE_SECOND;

    @ApiModelProperty("法定代表人")
    @Comment("法定代表人")
    @Column("LEGAL_PERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String LEGAL_PERSON;

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

    @ApiModelProperty("法人联系电话")
    @Comment("法人联系电话")
    @Column("LEGAL_PERSON_PHONE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String LEGAL_PERSON_PHONE;

    @ApiModelProperty("单位性质")
    @Comment("单位性质")
    @Column("KIND")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String KIND;

    @ApiModelProperty("行业分类")
    @Comment("行业分类")
    @Column("INDUSTRY_TYPE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String INDUSTRY_TYPE;

    @ApiModelProperty("放射源生产活动种类")
    @Comment("放射源生产活动种类")
    @Column("NUCLIDE_PRODUCE_ACTIVE")
    @ColDefine(type = ColType.VARCHAR,width = 2)
    private String NUCLIDE_PRODUCE_ACTIVE;

    @ApiModelProperty("放射源销售活动种类")
    @Comment("放射源销售活动种类")
    @Column("NUCLIDE_SALE_ACTIVE")
    @ColDefine(type = ColType.VARCHAR,width = 2)
    private String NUCLIDE_SALE_ACTIVE;

    @ApiModelProperty("放射源使用活动种类")
    @Comment("放射源使用活动种类")
    @Column("NUCLIDE_USE_ACTIVE")
    @ColDefine(type = ColType.VARCHAR,width = 2)
    private String NUCLIDE_USE_ACTIVE;

    @ApiModelProperty("射线装置生产活动种类")
    @Comment("射线装置生产活动种类")
    @Column("RADIAL_PRODUCE_ACTIVE")
    @ColDefine(type = ColType.VARCHAR,width = 2)
    private String RADIAL_PRODUCE_ACTIVE;

    @ApiModelProperty("射线装置销售活动种类")
    @Comment("射线装置销售活动种类")
    @Column("RADIAL_SALE_ACTIVE")
    @ColDefine(type = ColType.VARCHAR,width = 2)
    private String RADIAL_SALE_ACTIVE;

    @ApiModelProperty("射线装置使用活动种类")
    @Comment("射线装置使用活动种类")
    @Column("RADIAL_USE_ACTIVE")
    @ColDefine(type = ColType.VARCHAR,width = 2)
    private String RADIAL_USE_ACTIVE;

    @ApiModelProperty("射线装置使用(含建造)I类")
    @Comment("射线装置使用(含建造)I类")
    @Column("RADIAL_SALE_AND_PRODUCE")
    @ColDefine(type = ColType.VARCHAR,width = 2)
    private String RADIAL_SALE_AND_PRODUCE;

    @ApiModelProperty("非密封放射物质活动种类")
    @Comment("非密封放射物质活动种类")
    @Column("NON_SEAL_ACTIVE")
    @ColDefine(type = ColType.VARCHAR,width = 2)
    private String NON_SEAL_ACTIVE;

    @ApiModelProperty("非密封放射物质活动场所")
    @Comment("非密封放射物质活动场所")
    @Column("NON_SEAL_TYPE")
    @ColDefine(type = ColType.VARCHAR,width = 2)
    private String NON_SEAL_TYPE;

    @ApiModelProperty("使用(含收贮)")
    @Comment("使用(含收贮)")
    @Column("USE_AND_COLLECTION")
    @ColDefine(type = ColType.VARCHAR,width = 2)
    private String USE_AND_COLLECTION;

    @ApiModelProperty("辐射管理机构名称")
    @Comment("辐射管理机构名称")
    @Column("RADIAL_ORGNAME")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String RADIAL_ORGNAME;

    @ApiModelProperty("辐射管理机构联系人")
    @Comment("辐射管理机构联系人")
    @Column("RADIAL_ORGCONTACT_PERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String RADIAL_ORGCONTACT_PERSON;

    @ApiModelProperty("辐射管理机构负责人电话")
    @Comment("辐射管理机构负责人电话")
    @Column("RADIAL_ORGCONTACT_PHONE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String RADIAL_ORGCONTACT_PHONE;

    @ApiModelProperty("辐射管理机构联系人手机")
    @Comment("辐射管理机构联系人手机")
    @Column("RADIAL_ORGCONTACT_MOBILE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String RADIAL_ORGCONTACT_MOBILE;

    @ApiModelProperty("辐射管理机构传真")
    @Comment("辐射管理机构传真")
    @Column("RADIAL_ORGCONTACT_FAX")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String RADIAL_ORGCONTACT_FAX;

    @ApiModelProperty("辐射管理机构联系人邮件")
    @Comment("辐射管理机构联系人邮件")
    @Column("RADIAL_ORGCONTACT_EMAIL")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String RADIAL_ORGCONTACT_EMAIL;

    @ApiModelProperty("组织机构代码")
    @Comment("组织机构代码")
    @Column("ORGANIZATION_CODE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String ORGANIZATION_CODE;

    @ApiModelProperty("统一社会信用编码")
    @Comment("统一社会信用编码")
    @Column("UNIFY_SOCIETY_CODE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String UNIFY_SOCIETY_CODE;

    @ApiModelProperty("备注")
    @Comment("备注")
    @Column("REMARK")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String REMARK;

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
    private java.sql.Timestamp INSERT_DATE;

    @ApiModelProperty("修改人")
    @Comment("修改人")
    @Column("MODIFY_USER")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String MODIFY_USER;

    @ApiModelProperty("修改时间")
    @Comment("修改时间")
    @Column("MODIFY_DATE")
    @ColDefine(type = ColType.DATE)
    private java.sql.Timestamp MODIFY_DATE;

    @ApiModelProperty("许可证号")
    @Comment("许可证号")
    @Column("LICENSE_NO")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String LICENSE_NO;

    @ApiModelProperty("许可证生效日期")
    @Comment("许可证生效日期")
    @Column("LICENSE_START_DATE")
    @ColDefine(type = ColType.DATE)
    private java.sql.Timestamp LICENSE_START_DATE;

    @ApiModelProperty("许可证失效日期")
    @Comment("许可证失效日期")
    @Column("LICENSE_END_DATE")
    @ColDefine(type = ColType.DATE)
    private java.sql.Timestamp LICENSE_END_DATE;

    @ApiModelProperty("许可证颁发条件")
    @Comment("许可证颁发条件")
    @Column("LICENSE_CONDITION")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String LICENSE_CONDITION;

    @ApiModelProperty("许可证审批机关")
    @Comment("许可证审批机关")
    @Column("LICENSE_ISSUE_ORGAN")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String LICENSE_ISSUE_ORGAN;

    @ApiModelProperty("审批状态")
    @Comment("审批状态")
    @Column("STATUS")
    @ColDefine(type = ColType.VARCHAR,width = 8)
    private String STATUS;

    @ApiModelProperty("是否通过")
    @Comment("是否通过")
    @Column("BE_PASS")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String BE_PASS;

    @ApiModelProperty("不通过理由")
    @Comment("不通过理由")
    @Column("NOPASS_REASON")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String NOPASS_REASON;

    @ApiModelProperty("是否重新申领")
    @Comment("是否重新申领")
    @Column("IS_APPLY")
    @ColDefine(type = ColType.VARCHAR,width = 1)
    private String IS_APPLY;

    @ApiModelProperty("单位id")
    @Comment("单位id")
    @Column("INSTANCEID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String INSTANCEID;

    @ApiModelProperty("单位类型")
    @Comment("单位类型")
    @Column("COMPANY_TYPE")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String COMPANY_TYPE;

    @ApiModelProperty("许可证发证机关")
    @Comment("许可证发证机关")
    @Column("LICENCE_ISSUING_AUTHORITY")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String LICENCE_ISSUING_AUTHORITY;

    @ApiModelProperty("办理人")
    @Comment("办理人")
    @Column("DO_PERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String DO_PERSON;

    @ApiModelProperty("办理时间")
    @Comment("办理时间")
    @Column("DO_DATE")
    @ColDefine(type = ColType.DATE,width = 7)
    private Date DO_DATE;

    @ApiModelProperty("注册地址最后一级code")
    @Comment("注册地址最后一级code")
    @Column("REGION_CODE_1")
    @ColDefine(type = ColType.VARCHAR,width = 30)
    private String REGION_CODE_1;

    @ApiModelProperty("通讯地址最后一级code")
    @Comment("通讯地址最后一级code")
    @Column("REGION_CODE_2")
    @ColDefine(type = ColType.VARCHAR,width = 30)
    private String REGION_CODE_2;

    @ApiModelProperty("受理时间")
    @Comment("受理时间")
    @Column("ACCEPT_DATE")
    @ColDefine(type = ColType.DATE,width = 7)
    private Date ACCEPT_DATE;

    @ApiModelProperty("受理人")
    @Comment("受理人")
    @Column("ACCEPT_PERSON")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String ACCEPT_PERSON;

    @ApiModelProperty("提交时间")
    @Comment("提交时间")
    @Column("SUBMIT_DATE")
    @ColDefine(type = ColType.DATE,width = 7)
    private Date SUBMIT_DATE;

    @ApiModelProperty("提交人")
    @Comment("提交人")
    @Column("SUBMIT_USER")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String SUBMIT_USER;

    @ApiModelProperty("单位编号类型")
    @Comment("单位编号类型")
    @Column("COMPANY_CODE_TYPE")
    @ColDefine(type = ColType.INT,width = 2)
    private Integer COMPANY_CODE_TYPE;

    public String getCOMPANY_ID() {
        return COMPANY_ID;
    }

    public void setCOMPANY_ID(String COMPANY_ID) {
        this.COMPANY_ID = COMPANY_ID;
    }

    public String getREQUEST_NO() {
        return REQUEST_NO;
    }

    public void setREQUEST_NO(String REQUEST_NO) {
        this.REQUEST_NO = REQUEST_NO;
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

    public String getRESPONSE_NO() {
        return RESPONSE_NO;
    }

    public void setRESPONSE_NO(String RESPONSE_NO) {
        this.RESPONSE_NO = RESPONSE_NO;
    }

    public String getPROVINCE() {
        return PROVINCE;
    }

    public void setPROVINCE(String PROVINCE) {
        this.PROVINCE = PROVINCE;
    }

    public String getREGIST_ADDR() {
        return REGIST_ADDR;
    }

    public void setREGIST_ADDR(String REGIST_ADDR) {
        this.REGIST_ADDR = REGIST_ADDR;
    }

    public String getCONTACT_ADDR_POST() {
        return CONTACT_ADDR_POST;
    }

    public void setCONTACT_ADDR_POST(String CONTACT_ADDR_POST) {
        this.CONTACT_ADDR_POST = CONTACT_ADDR_POST;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getDISTRICT() {
        return DISTRICT;
    }

    public void setDISTRICT(String DISTRICT) {
        this.DISTRICT = DISTRICT;
    }


    public String getREGIST_ADDR_POST() {
        return REGIST_ADDR_POST;
    }

    public void setREGIST_ADDR_POST(String REGIST_ADDR_POST) {
        this.REGIST_ADDR_POST = REGIST_ADDR_POST;
    }

    public String getCONTACT_ADDR() {
        return CONTACT_ADDR;
    }

    public void setCONTACT_ADDR(String CONTACT_ADDR) {
        this.CONTACT_ADDR = CONTACT_ADDR;
    }

    public String getCONTACT_PERSON() {
        return CONTACT_PERSON;
    }

    public void setCONTACT_PERSON(String CONTACT_PERSON) {
        this.CONTACT_PERSON = CONTACT_PERSON;
    }

    public String getCONTACT_PHONE() {
        return CONTACT_PHONE;
    }

    public void setCONTACT_PHONE(String CONTACT_PHONE) {
        this.CONTACT_PHONE = CONTACT_PHONE;
    }

    public Integer getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(Integer LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public Integer getLONGITUDE_HOUR() {
        return LONGITUDE_HOUR;
    }

    public void setLONGITUDE_HOUR(Integer LONGITUDE_HOUR) {
        this.LONGITUDE_HOUR = LONGITUDE_HOUR;
    }

    public Integer getLONGITUDE_SECOND() {
        return LONGITUDE_SECOND;
    }

    public void setLONGITUDE_SECOND(Integer LONGITUDE_SECOND) {
        this.LONGITUDE_SECOND = LONGITUDE_SECOND;
    }

    public Integer getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(Integer LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public Integer getLATITUDE_HOUR() {
        return LATITUDE_HOUR;
    }

    public void setLATITUDE_HOUR(Integer LATITUDE_HOUR) {
        this.LATITUDE_HOUR = LATITUDE_HOUR;
    }

    public Integer getLATITUDE_SECOND() {
        return LATITUDE_SECOND;
    }

    public void setLATITUDE_SECOND(Integer LATITUDE_SECOND) {
        this.LATITUDE_SECOND = LATITUDE_SECOND;
    }

    public String getLEGAL_PERSON() {
        return LEGAL_PERSON;
    }

    public void setLEGAL_PERSON(String LEGAL_PERSON) {
        this.LEGAL_PERSON = LEGAL_PERSON;
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

    public String getLEGAL_PERSON_PHONE() {
        return LEGAL_PERSON_PHONE;
    }

    public void setLEGAL_PERSON_PHONE(String LEGAL_PERSON_PHONE) {
        this.LEGAL_PERSON_PHONE = LEGAL_PERSON_PHONE;
    }

    public String getKIND() {
        return KIND;
    }

    public void setKIND(String KIND) {
        this.KIND = KIND;
    }

    public String getINDUSTRY_TYPE() {
        return INDUSTRY_TYPE;
    }

    public void setINDUSTRY_TYPE(String INDUSTRY_TYPE) {
        this.INDUSTRY_TYPE = INDUSTRY_TYPE;
    }

    public String getNUCLIDE_PRODUCE_ACTIVE() {
        return NUCLIDE_PRODUCE_ACTIVE;
    }

    public void setNUCLIDE_PRODUCE_ACTIVE(String NUCLIDE_PRODUCE_ACTIVE) {
        this.NUCLIDE_PRODUCE_ACTIVE = NUCLIDE_PRODUCE_ACTIVE;
    }

    public String getNUCLIDE_SALE_ACTIVE() {
        return NUCLIDE_SALE_ACTIVE;
    }

    public void setNUCLIDE_SALE_ACTIVE(String NUCLIDE_SALE_ACTIVE) {
        this.NUCLIDE_SALE_ACTIVE = NUCLIDE_SALE_ACTIVE;
    }

    public String getNUCLIDE_USE_ACTIVE() {
        return NUCLIDE_USE_ACTIVE;
    }

    public void setNUCLIDE_USE_ACTIVE(String NUCLIDE_USE_ACTIVE) {
        this.NUCLIDE_USE_ACTIVE = NUCLIDE_USE_ACTIVE;
    }

    public String getRADIAL_PRODUCE_ACTIVE() {
        return RADIAL_PRODUCE_ACTIVE;
    }

    public void setRADIAL_PRODUCE_ACTIVE(String RADIAL_PRODUCE_ACTIVE) {
        this.RADIAL_PRODUCE_ACTIVE = RADIAL_PRODUCE_ACTIVE;
    }

    public String getRADIAL_SALE_ACTIVE() {
        return RADIAL_SALE_ACTIVE;
    }

    public void setRADIAL_SALE_ACTIVE(String RADIAL_SALE_ACTIVE) {
        this.RADIAL_SALE_ACTIVE = RADIAL_SALE_ACTIVE;
    }

    public String getRADIAL_USE_ACTIVE() {
        return RADIAL_USE_ACTIVE;
    }

    public void setRADIAL_USE_ACTIVE(String RADIAL_USE_ACTIVE) {
        this.RADIAL_USE_ACTIVE = RADIAL_USE_ACTIVE;
    }

    public String getRADIAL_SALE_AND_PRODUCE() {
        return RADIAL_SALE_AND_PRODUCE;
    }

    public void setRADIAL_SALE_AND_PRODUCE(String RADIAL_SALE_AND_PRODUCE) {
        this.RADIAL_SALE_AND_PRODUCE = RADIAL_SALE_AND_PRODUCE;
    }

    public String getNON_SEAL_ACTIVE() {
        return NON_SEAL_ACTIVE;
    }

    public void setNON_SEAL_ACTIVE(String NON_SEAL_ACTIVE) {
        this.NON_SEAL_ACTIVE = NON_SEAL_ACTIVE;
    }

    public String getNON_SEAL_TYPE() {
        return NON_SEAL_TYPE;
    }

    public void setNON_SEAL_TYPE(String NON_SEAL_TYPE) {
        this.NON_SEAL_TYPE = NON_SEAL_TYPE;
    }

    public String getUSE_AND_COLLECTION() {
        return USE_AND_COLLECTION;
    }

    public void setUSE_AND_COLLECTION(String USE_AND_COLLECTION) {
        this.USE_AND_COLLECTION = USE_AND_COLLECTION;
    }

    public String getRADIAL_ORGNAME() {
        return RADIAL_ORGNAME;
    }

    public void setRADIAL_ORGNAME(String RADIAL_ORGNAME) {
        this.RADIAL_ORGNAME = RADIAL_ORGNAME;
    }

    public String getRADIAL_ORGCONTACT_PERSON() {
        return RADIAL_ORGCONTACT_PERSON;
    }

    public void setRADIAL_ORGCONTACT_PERSON(String RADIAL_ORGCONTACT_PERSON) {
        this.RADIAL_ORGCONTACT_PERSON = RADIAL_ORGCONTACT_PERSON;
    }

    public String getRADIAL_ORGCONTACT_PHONE() {
        return RADIAL_ORGCONTACT_PHONE;
    }

    public void setRADIAL_ORGCONTACT_PHONE(String RADIAL_ORGCONTACT_PHONE) {
        this.RADIAL_ORGCONTACT_PHONE = RADIAL_ORGCONTACT_PHONE;
    }

    public String getRADIAL_ORGCONTACT_MOBILE() {
        return RADIAL_ORGCONTACT_MOBILE;
    }

    public void setRADIAL_ORGCONTACT_MOBILE(String RADIAL_ORGCONTACT_MOBILE) {
        this.RADIAL_ORGCONTACT_MOBILE = RADIAL_ORGCONTACT_MOBILE;
    }

    public String getRADIAL_ORGCONTACT_FAX() {
        return RADIAL_ORGCONTACT_FAX;
    }

    public void setRADIAL_ORGCONTACT_FAX(String RADIAL_ORGCONTACT_FAX) {
        this.RADIAL_ORGCONTACT_FAX = RADIAL_ORGCONTACT_FAX;
    }

    public String getRADIAL_ORGCONTACT_EMAIL() {
        return RADIAL_ORGCONTACT_EMAIL;
    }

    public void setRADIAL_ORGCONTACT_EMAIL(String RADIAL_ORGCONTACT_EMAIL) {
        this.RADIAL_ORGCONTACT_EMAIL = RADIAL_ORGCONTACT_EMAIL;
    }

    public String getORGANIZATION_CODE() {
        return ORGANIZATION_CODE;
    }

    public void setORGANIZATION_CODE(String ORGANIZATION_CODE) {
        this.ORGANIZATION_CODE = ORGANIZATION_CODE;
    }

    public String getUNIFY_SOCIETY_CODE() {
        return UNIFY_SOCIETY_CODE;
    }

    public void setUNIFY_SOCIETY_CODE(String UNIFY_SOCIETY_CODE) {
        this.UNIFY_SOCIETY_CODE = UNIFY_SOCIETY_CODE;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
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

    public String getLICENSE_NO() {
        return LICENSE_NO;
    }

    public void setLICENSE_NO(String LICENSE_NO) {
        this.LICENSE_NO = LICENSE_NO;
    }

    public Timestamp getLICENSE_START_DATE() {
        return LICENSE_START_DATE;
    }

    public void setLICENSE_START_DATE(Timestamp LICENSE_START_DATE) {
        this.LICENSE_START_DATE = LICENSE_START_DATE;
    }

    public Timestamp getLICENSE_END_DATE() {
        return LICENSE_END_DATE;
    }

    public void setLICENSE_END_DATE(Timestamp LICENSE_END_DATE) {
        this.LICENSE_END_DATE = LICENSE_END_DATE;
    }

    public String getLICENSE_CONDITION() {
        return LICENSE_CONDITION;
    }

    public void setLICENSE_CONDITION(String LICENSE_CONDITION) {
        this.LICENSE_CONDITION = LICENSE_CONDITION;
    }

    public String getLICENSE_ISSUE_ORGAN() {
        return LICENSE_ISSUE_ORGAN;
    }

    public void setLICENSE_ISSUE_ORGAN(String LICENSE_ISSUE_ORGAN) {
        this.LICENSE_ISSUE_ORGAN = LICENSE_ISSUE_ORGAN;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getBE_PASS() {
        return BE_PASS;
    }

    public void setBE_PASS(String BE_PASS) {
        this.BE_PASS = BE_PASS;
    }

    public String getNOPASS_REASON() {
        return NOPASS_REASON;
    }

    public void setNOPASS_REASON(String NOPASS_REASON) {
        this.NOPASS_REASON = NOPASS_REASON;
    }

    public String getIS_APPLY() {
        return IS_APPLY;
    }

    public void setIS_APPLY(String IS_APPLY) {
        this.IS_APPLY = IS_APPLY;
    }

    public String getINSTANCEID() {
        return INSTANCEID;
    }

    public void setINSTANCEID(String INSTANCEID) {
        this.INSTANCEID = INSTANCEID;
    }

    public String getCOMPANY_TYPE() {
        return COMPANY_TYPE;
    }

    public void setCOMPANY_TYPE(String COMPANY_TYPE) {
        this.COMPANY_TYPE = COMPANY_TYPE;
    }

    public String getLICENCE_ISSUING_AUTHORITY() {
        return LICENCE_ISSUING_AUTHORITY;
    }

    public void setLICENCE_ISSUING_AUTHORITY(String LICENCE_ISSUING_AUTHORITY) {
        this.LICENCE_ISSUING_AUTHORITY = LICENCE_ISSUING_AUTHORITY;
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

    public String getACCEPT_PERSON() {
        return ACCEPT_PERSON;
    }

    public void setACCEPT_PERSON(String ACCEPT_PERSON) {
        this.ACCEPT_PERSON = ACCEPT_PERSON;
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

    public String getYWID() {
        return YWID;
    }

    public void setYWID(String YWID) {
        this.YWID = YWID;
    }

    public Date getACCEPT_DATE() {
        return ACCEPT_DATE;
    }

    public void setACCEPT_DATE(Date ACCEPT_DATE) {
        this.ACCEPT_DATE = ACCEPT_DATE;
    }

    public Integer getCOMPANY_CODE_TYPE() {
        return COMPANY_CODE_TYPE;
    }

    public void setCOMPANY_CODE_TYPE(Integer COMPANY_CODE_TYPE) {
        this.COMPANY_CODE_TYPE = COMPANY_CODE_TYPE;
    }
}
