package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;

@ApiModel("放射源收贮备案表")
@Table("TT_REQ_WASTE_NUCLIDE_TAKEBACK")
@Comment("放射源收贮备案表")
public class TTREQWASTENUCLIDETAKEBACK implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("放射源回收备案主键")
    @Comment("放射源回收备案主键")
    @Column("TAKEBACK_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String TAKEBACK_ID;

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

    @ApiModelProperty("送贮单位名称")
    @Comment("送贮单位名称")
    @Column("DELIVERY_COMPANY_NAME")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String DELIVERY_COMPANY_NAME;

    @ApiModelProperty("送贮单位许可证")
    @Comment("送贮单位许可证")
    @Column("DELIVERY_COMPANY_LICENSENO")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String DELIVERY_COMPANY_LICENSENO;

    @ApiModelProperty("送贮单位通讯地址")
    @Comment("送贮单位通讯地址")
    @Column("DELIVERY_COMPANY_CONTACTADDR")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String DELIVERY_COMPANY_CONTACTADDR;

    @ApiModelProperty("送贮单位经办人")
    @Comment("送贮单位经办人")
    @Column("DELIVERY_COMPANY_DOPERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String DELIVERY_COMPANY_DOPERSON;

    @ApiModelProperty("送贮单位电话传真")
    @Comment("送贮单位电话传真")
    @Column("DELIVERY_COMPANY_PHONEORFAX")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String DELIVERY_COMPANY_PHONEORFAX;

    @ApiModelProperty("送贮单位邮编")
    @Comment("送贮单位邮编")
    @Column("DELIVERY_COMPANY_POST")
    @ColDefine(type = ColType.VARCHAR,width = 6)
    private String DELIVERY_COMPANY_POST;

    @ApiModelProperty("接受单位名称")
    @Comment("接受单位名称")
    @Column("RECEIVE_COMPANY_NAME")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String RECEIVE_COMPANY_NAME;

    @ApiModelProperty("接收单位许可证")
    @Comment("接收单位许可证")
    @Column("RECEIVECOMPANY_LICENSENO")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String RECEIVECOMPANY_LICENSENO;

    @ApiModelProperty("接收单位通讯地址")
    @Comment("接收单位通讯地址")
    @Column("RECEIVECOMPANY_CONTACTADDR")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String RECEIVECOMPANY_CONTACTADDR;

    @ApiModelProperty("接受单位经办人")
    @Comment("接受单位经办人")
    @Column("RECEIVECOMPANY_DOPERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String RECEIVECOMPANY_DOPERSON;

    @ApiModelProperty("接受单位电话传真")
    @Comment("接受单位电话传真")
    @Column("RECEIVECOMPANY_PHONEORFAX")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String RECEIVECOMPANY_PHONEORFAX;

    @ApiModelProperty("接受单位邮编")
    @Comment("接受单位邮编")
    @Column("RECEIVECOMPANY_POST")
    @ColDefine(type = ColType.VARCHAR,width = 6)
    private String RECEIVECOMPANY_POST;

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

    @ApiModelProperty("收贮日期")
    @Comment("收贮日期")
    @Column("STORE_DATE")
    @ColDefine(type = ColType.DATE)
    private Timestamp STORE_DATE;

    @ApiModelProperty("贮存设施")
    @Comment("贮存设施")
    @Column("STORE_FACILITY")
    @ColDefine(type = ColType.VARCHAR,width = 128)
    private String STORE_FACILITY;

    @ApiModelProperty("区号")
    @Comment("区号")
    @Column("ZONE_NO")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String ZONE_NO;

    @ApiModelProperty("坑号")
    @Comment("坑号")
    @Column("PIT_NO")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String PIT_NO;

    @ApiModelProperty("盖号")
    @Comment("盖号")
    @Column("COVER_NO")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String COVER_NO;

    @ApiModelProperty("架号")
    @Comment("架号")
    @Column("SHELF_NO")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String SHELF_NO;

    @ApiModelProperty("桶号")
    @Comment("桶号")
    @Column("BARREL_NO")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String BARREL_NO;

    @ApiModelProperty("罐号")
    @Comment("罐号")
    @Column("CONTAINTER_NO")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String CONTAINTER_NO;

    @ApiModelProperty("表面剂量率")
    @Comment("表面剂量率")
    @Column("SURFACE_DOSE_RATE")
    @ColDefine(type = ColType.VARCHAR,width = 38)
    private Integer SURFACE_DOSE_RATE;

    @ApiModelProperty("一米处剂量率")
    @Comment("一米处剂量率")
    @Column("METER_DOSE_RATE")
    @ColDefine(type = ColType.INT,width = 38)
    private Integer METER_DOSE_RATE;

    @ApiModelProperty("表面α污染水平")
    @Comment("表面α污染水平")
    @Column("A_POLLUTE_RATE")
    @ColDefine(type = ColType.INT,width = 38)
    private Integer A_POLLUTE_RATE;

    @ApiModelProperty("表面β污染水平")
    @Comment("表面β污染水平")
    @Column("B_POLLUTE_RATE")
    @ColDefine(type = ColType.INT,width = 38)
    private Integer B_POLLUTE_RATE;

    @ApiModelProperty("包装体积")
    @Comment("包装体积")
    @Column("PACK_VOLUME")
    @ColDefine(type = ColType.INT,width = 38)
    private Integer PACK_VOLUME;

    @ApiModelProperty("重量")
    @Comment("重量")
    @Column("WEIGHT")
    @ColDefine(type = ColType.VARCHAR,width = 38)
    private Integer WEIGHT;

    @ApiModelProperty("测量人")
    @Comment("测量人")
    @Column("MEASURE_PERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String MEASURE_PERSON;

    @ApiModelProperty("送贮人")
    @Comment("送贮人")
    @Column("DELIVERY_PERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String DELIVERY_PERSON;

    @ApiModelProperty("收贮人")
    @Comment("收贮人")
    @Column("STORE_PERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String STORE_PERSON;

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

    @ApiModelProperty("受理状态")
    @Comment("受理状态")
    @Column("RESPONSE_STATUS")
    @ColDefine(type = ColType.INT,width = 2)
    private Integer RESPONSE_STATUS;

    @ApiModelProperty("表面剂量率幂")
    @Comment("表面剂量率幂")
    @Column("SURFACE_POWER")
    @ColDefine(type = ColType.INT,width = 4)
    private Integer SURFACE_POWER;

    @ApiModelProperty("一米处剂量率幂")
    @Comment("一米处剂量率幂")
    @Column("METER_POWER")
    @ColDefine(type = ColType.INT,width = 4)
    private Integer METER_POWER;

    @ApiModelProperty("表面α污染水平幂")
    @Comment("表面α污染水平幂")
    @Column("A_POWER")
    @ColDefine(type = ColType.INT,width = 4)
    private Integer A_POWER;

    @ApiModelProperty("表面β污染水平幂")
    @Comment("表面β污染水平幂")
    @Column("B_POWER")
    @ColDefine(type = ColType.INT,width = 4)
    private Integer B_POWER;

    @ApiModelProperty("收贮日期")
    @Comment("收贮日期")
    @Column("TAKEBACK_DATE")
    @ColDefine(type = ColType.DATE)
    private Timestamp TAKEBACK_DATE;

    @ApiModelProperty("退回理由")
    @Comment("退回理由")
    @Column("RETREAT_REASON")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String RETREAT_REASON;

    @ApiModelProperty("接收单位是否备案")
    @Comment("接收单位是否备案")
    @Column("RECEIVECOMPANY_BE_RECORD")
    @ColDefine(type = ColType.INT,width = 2)
    private Integer RECEIVECOMPANY_BE_RECORD;

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

    @ApiModelProperty("送交单位唯一id")
    @Comment("送交单位唯一id")
    @Column("DELIVERY_COMPANY_INSTANCEID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String DELIVERY_COMPANY_INSTANCEID;

    @ApiModelProperty("接受单位唯一id")
    @Comment("接受单位唯一id")
    @Column("RECEIVE_COMPANY_INSTANCEID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String RECEIVE_COMPANY_INSTANCEID;

    @ApiModelProperty("送交单位区域边编码")
    @Comment("送交单位区域边编码")
    @Column("REGION_CODE_1")
    @ColDefine(type = ColType.VARCHAR,width = 30)
    private String REGION_CODE_1;

    @ApiModelProperty("接受单位区域编码")
    @Comment("接受单位区域编码")
    @Column("REGION_CODE_2")
    @ColDefine(type = ColType.VARCHAR,width = 30)
    private String REGION_CODE_2;

    @ApiModelProperty("送交单位备案时间")
    @Comment("送交单位备案时间")
    @Column("OUT_DATE")
    @ColDefine(type = ColType.VARCHAR,width = 25)
    private String OUT_DATE;

    @ApiModelProperty("接受单位备案时间")
    @Comment("接受单位备案时间")
    @Column("IN_DATE")
    @ColDefine(type = ColType.VARCHAR,width = 25)
    private String IN_DATE;

    @ApiModelProperty("送交单位备案人")
    @Comment("送交单位备案人")
    @Column("OUT_PERSON")
    @ColDefine(type = ColType.VARCHAR,width = 50)
    private String OUT_PERSON;

    @ApiModelProperty("接受单位备案人")
    @Comment("接受单位备案人")
    @Column("IN_PERSON")
    @ColDefine(type = ColType.VARCHAR,width = 50)
    private String IN_PERSON;

    @ApiModelProperty("老数据标志")
    @Comment("老数据标志")
    @Column("OLD_MARK")
    @ColDefine(type = ColType.VARCHAR,width = 5)
    private String OLD_MARK;

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

    public String getDELIVERY_COMPANY_NAME() {
        return DELIVERY_COMPANY_NAME;
    }

    public void setDELIVERY_COMPANY_NAME(String DELIVERY_COMPANY_NAME) {
        this.DELIVERY_COMPANY_NAME = DELIVERY_COMPANY_NAME;
    }

    public String getDELIVERY_COMPANY_LICENSENO() {
        return DELIVERY_COMPANY_LICENSENO;
    }

    public void setDELIVERY_COMPANY_LICENSENO(String DELIVERY_COMPANY_LICENSENO) {
        this.DELIVERY_COMPANY_LICENSENO = DELIVERY_COMPANY_LICENSENO;
    }

    public String getDELIVERY_COMPANY_CONTACTADDR() {
        return DELIVERY_COMPANY_CONTACTADDR;
    }

    public void setDELIVERY_COMPANY_CONTACTADDR(String DELIVERY_COMPANY_CONTACTADDR) {
        this.DELIVERY_COMPANY_CONTACTADDR = DELIVERY_COMPANY_CONTACTADDR;
    }

    public String getDELIVERY_COMPANY_DOPERSON() {
        return DELIVERY_COMPANY_DOPERSON;
    }

    public void setDELIVERY_COMPANY_DOPERSON(String DELIVERY_COMPANY_DOPERSON) {
        this.DELIVERY_COMPANY_DOPERSON = DELIVERY_COMPANY_DOPERSON;
    }

    public String getDELIVERY_COMPANY_PHONEORFAX() {
        return DELIVERY_COMPANY_PHONEORFAX;
    }

    public void setDELIVERY_COMPANY_PHONEORFAX(String DELIVERY_COMPANY_PHONEORFAX) {
        this.DELIVERY_COMPANY_PHONEORFAX = DELIVERY_COMPANY_PHONEORFAX;
    }

    public String getDELIVERY_COMPANY_POST() {
        return DELIVERY_COMPANY_POST;
    }

    public void setDELIVERY_COMPANY_POST(String DELIVERY_COMPANY_POST) {
        this.DELIVERY_COMPANY_POST = DELIVERY_COMPANY_POST;
    }

    public String getRECEIVE_COMPANY_NAME() {
        return RECEIVE_COMPANY_NAME;
    }

    public void setRECEIVE_COMPANY_NAME(String RECEIVE_COMPANY_NAME) {
        this.RECEIVE_COMPANY_NAME = RECEIVE_COMPANY_NAME;
    }

    public String getRECEIVECOMPANY_LICENSENO() {
        return RECEIVECOMPANY_LICENSENO;
    }

    public void setRECEIVECOMPANY_LICENSENO(String RECEIVECOMPANY_LICENSENO) {
        this.RECEIVECOMPANY_LICENSENO = RECEIVECOMPANY_LICENSENO;
    }

    public String getRECEIVECOMPANY_CONTACTADDR() {
        return RECEIVECOMPANY_CONTACTADDR;
    }

    public void setRECEIVECOMPANY_CONTACTADDR(String RECEIVECOMPANY_CONTACTADDR) {
        this.RECEIVECOMPANY_CONTACTADDR = RECEIVECOMPANY_CONTACTADDR;
    }

    public String getRECEIVECOMPANY_DOPERSON() {
        return RECEIVECOMPANY_DOPERSON;
    }

    public void setRECEIVECOMPANY_DOPERSON(String RECEIVECOMPANY_DOPERSON) {
        this.RECEIVECOMPANY_DOPERSON = RECEIVECOMPANY_DOPERSON;
    }

    public String getRECEIVECOMPANY_PHONEORFAX() {
        return RECEIVECOMPANY_PHONEORFAX;
    }

    public void setRECEIVECOMPANY_PHONEORFAX(String RECEIVECOMPANY_PHONEORFAX) {
        this.RECEIVECOMPANY_PHONEORFAX = RECEIVECOMPANY_PHONEORFAX;
    }

    public String getRECEIVECOMPANY_POST() {
        return RECEIVECOMPANY_POST;
    }

    public void setRECEIVECOMPANY_POST(String RECEIVECOMPANY_POST) {
        this.RECEIVECOMPANY_POST = RECEIVECOMPANY_POST;
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

    public Timestamp getSTORE_DATE() {
        return STORE_DATE;
    }

    public void setSTORE_DATE(Timestamp STORE_DATE) {
        this.STORE_DATE = STORE_DATE;
    }

    public String getSTORE_FACILITY() {
        return STORE_FACILITY;
    }

    public void setSTORE_FACILITY(String STORE_FACILITY) {
        this.STORE_FACILITY = STORE_FACILITY;
    }

    public String getZONE_NO() {
        return ZONE_NO;
    }

    public void setZONE_NO(String ZONE_NO) {
        this.ZONE_NO = ZONE_NO;
    }

    public String getPIT_NO() {
        return PIT_NO;
    }

    public void setPIT_NO(String PIT_NO) {
        this.PIT_NO = PIT_NO;
    }

    public String getCOVER_NO() {
        return COVER_NO;
    }

    public void setCOVER_NO(String COVER_NO) {
        this.COVER_NO = COVER_NO;
    }

    public String getSHELF_NO() {
        return SHELF_NO;
    }

    public void setSHELF_NO(String SHELF_NO) {
        this.SHELF_NO = SHELF_NO;
    }

    public String getBARREL_NO() {
        return BARREL_NO;
    }

    public void setBARREL_NO(String BARREL_NO) {
        this.BARREL_NO = BARREL_NO;
    }

    public String getCONTAINTER_NO() {
        return CONTAINTER_NO;
    }

    public void setCONTAINTER_NO(String CONTAINTER_NO) {
        this.CONTAINTER_NO = CONTAINTER_NO;
    }

    public Integer getSURFACE_DOSE_RATE() {
        return SURFACE_DOSE_RATE;
    }

    public void setSURFACE_DOSE_RATE(Integer SURFACE_DOSE_RATE) {
        this.SURFACE_DOSE_RATE = SURFACE_DOSE_RATE;
    }

    public Integer getMETER_DOSE_RATE() {
        return METER_DOSE_RATE;
    }

    public void setMETER_DOSE_RATE(Integer METER_DOSE_RATE) {
        this.METER_DOSE_RATE = METER_DOSE_RATE;
    }

    public Integer getA_POLLUTE_RATE() {
        return A_POLLUTE_RATE;
    }

    public void setA_POLLUTE_RATE(Integer a_POLLUTE_RATE) {
        A_POLLUTE_RATE = a_POLLUTE_RATE;
    }

    public Integer getB_POLLUTE_RATE() {
        return B_POLLUTE_RATE;
    }

    public void setB_POLLUTE_RATE(Integer b_POLLUTE_RATE) {
        B_POLLUTE_RATE = b_POLLUTE_RATE;
    }

    public Integer getPACK_VOLUME() {
        return PACK_VOLUME;
    }

    public void setPACK_VOLUME(Integer PACK_VOLUME) {
        this.PACK_VOLUME = PACK_VOLUME;
    }

    public Integer getWEIGHT() {
        return WEIGHT;
    }

    public void setWEIGHT(Integer WEIGHT) {
        this.WEIGHT = WEIGHT;
    }

    public String getMEASURE_PERSON() {
        return MEASURE_PERSON;
    }

    public void setMEASURE_PERSON(String MEASURE_PERSON) {
        this.MEASURE_PERSON = MEASURE_PERSON;
    }

    public String getDELIVERY_PERSON() {
        return DELIVERY_PERSON;
    }

    public void setDELIVERY_PERSON(String DELIVERY_PERSON) {
        this.DELIVERY_PERSON = DELIVERY_PERSON;
    }

    public String getSTORE_PERSON() {
        return STORE_PERSON;
    }

    public void setSTORE_PERSON(String STORE_PERSON) {
        this.STORE_PERSON = STORE_PERSON;
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

    public Integer getRESPONSE_STATUS() {
        return RESPONSE_STATUS;
    }

    public void setRESPONSE_STATUS(Integer RESPONSE_STATUS) {
        this.RESPONSE_STATUS = RESPONSE_STATUS;
    }

    public Integer getSURFACE_POWER() {
        return SURFACE_POWER;
    }

    public void setSURFACE_POWER(Integer SURFACE_POWER) {
        this.SURFACE_POWER = SURFACE_POWER;
    }

    public Integer getMETER_POWER() {
        return METER_POWER;
    }

    public void setMETER_POWER(Integer METER_POWER) {
        this.METER_POWER = METER_POWER;
    }

    public Integer getA_POWER() {
        return A_POWER;
    }

    public void setA_POWER(Integer a_POWER) {
        A_POWER = a_POWER;
    }

    public Integer getB_POWER() {
        return B_POWER;
    }

    public void setB_POWER(Integer b_POWER) {
        B_POWER = b_POWER;
    }

    public Timestamp getTAKEBACK_DATE() {
        return TAKEBACK_DATE;
    }

    public void setTAKEBACK_DATE(Timestamp TAKEBACK_DATE) {
        this.TAKEBACK_DATE = TAKEBACK_DATE;
    }

    public String getRETREAT_REASON() {
        return RETREAT_REASON;
    }

    public void setRETREAT_REASON(String RETREAT_REASON) {
        this.RETREAT_REASON = RETREAT_REASON;
    }

    public Integer getRECEIVECOMPANY_BE_RECORD() {
        return RECEIVECOMPANY_BE_RECORD;
    }

    public void setRECEIVECOMPANY_BE_RECORD(Integer RECEIVECOMPANY_BE_RECORD) {
        this.RECEIVECOMPANY_BE_RECORD = RECEIVECOMPANY_BE_RECORD;
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

    public String getDELIVERY_COMPANY_INSTANCEID() {
        return DELIVERY_COMPANY_INSTANCEID;
    }

    public void setDELIVERY_COMPANY_INSTANCEID(String DELIVERY_COMPANY_INSTANCEID) {
        this.DELIVERY_COMPANY_INSTANCEID = DELIVERY_COMPANY_INSTANCEID;
    }

    public String getRECEIVE_COMPANY_INSTANCEID() {
        return RECEIVE_COMPANY_INSTANCEID;
    }

    public void setRECEIVE_COMPANY_INSTANCEID(String RECEIVE_COMPANY_INSTANCEID) {
        this.RECEIVE_COMPANY_INSTANCEID = RECEIVE_COMPANY_INSTANCEID;
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

    public String getOUT_DATE() {
        return OUT_DATE;
    }

    public void setOUT_DATE(String OUT_DATE) {
        this.OUT_DATE = OUT_DATE;
    }

    public String getIN_DATE() {
        return IN_DATE;
    }

    public void setIN_DATE(String IN_DATE) {
        this.IN_DATE = IN_DATE;
    }

    public String getOUT_PERSON() {
        return OUT_PERSON;
    }

    public void setOUT_PERSON(String OUT_PERSON) {
        this.OUT_PERSON = OUT_PERSON;
    }

    public String getIN_PERSON() {
        return IN_PERSON;
    }

    public void setIN_PERSON(String IN_PERSON) {
        this.IN_PERSON = IN_PERSON;
    }

    public String getOLD_MARK() {
        return OLD_MARK;
    }

    public void setOLD_MARK(String OLD_MARK) {
        this.OLD_MARK = OLD_MARK;
    }
}
