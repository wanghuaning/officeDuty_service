package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;

@ApiModel("辐射防护用品表")
@Table("TT_LR_PROTECT_GOODS")
@Comment("辐射防护用品表")
public class TTLRPROTECTGOODS implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("辐射防护物品主键")
    @Comment("辐射防护物品主键")
    @Column("PROTECT_GOODS_ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String PROTECT_GOODS_ID;

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

    @ApiModelProperty("业务主键id")
    @Comment("业务主键id")
    @Column("REQUEIR_YWID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String REQUEIR_YWID;

    @ApiModelProperty("铅衣")
    @Comment("铅衣")
    @Column("QIAN_YI")
    @ColDefine(type = ColType.INT,width = 11)
    private Integer QIAN_YI;

    @ApiModelProperty("铅帽")
    @Comment("铅帽")
    @Column("QIAN_MAO")
    @ColDefine(type = ColType.INT,width = 11)
    private Integer QIAN_MAO;

    @ApiModelProperty("铅手套")
    @Comment("铅手套")
    @Column("QIAN_SHOU_TAO")
    @ColDefine(type = ColType.INT,width = 11)
    private Integer QIAN_SHOU_TAO;

    @ApiModelProperty("铅眼睛")
    @Comment("铅眼睛")
    @Column("QIAN_YAN_JING")
    @ColDefine(type = ColType.INT,width = 11)
    private Integer QIAN_YAN_JING;

    @ApiModelProperty("铅围裙")
    @Comment("铅围裙")
    @Column("QIAN_WEI_QUN")
    @ColDefine(type = ColType.INT,width = 11)
    private Integer QIAN_WEI_QUN;

    @ApiModelProperty("铅围脖")
    @Comment("铅围脖")
    @Column("QIAN_WEI_BO")
    @ColDefine(type = ColType.INT,width = 11)
    private Integer QIAN_WEI_BO;

    @ApiModelProperty("铅屏风")
    @Comment("铅屏风")
    @Column("QIAN_PING_FENG")
    @ColDefine(type = ColType.INT,width = 11)
    private Integer QIAN_PING_FENG;

    @ApiModelProperty("个人剂量计")
    @Comment("个人剂量计")
    @Column("GEREN_JILIANG")
    @ColDefine(type = ColType.INT,width = 11)
    private Integer GEREN_JILIANG;

    @ApiModelProperty("其他")
    @Comment("其他")
    @Column("OTHERS")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String OTHERS;

    @ApiModelProperty("更改人")
    @Comment("更改人")
    @Column("UPDATEUSER")
    @ColDefine(type = ColType.VARCHAR,width = 30)
    private String UPDATEUSER;

    @ApiModelProperty("更新时间")
    @Comment("更新时间")
    @Column("UPDATETIME")
    @ColDefine(type = ColType.DATE)
    private Timestamp UPDATETIME;

    @ApiModelProperty("个人剂量计")
    @Comment("个人剂量计")
    @Column("GEREN_JILIANG_JI")
    @ColDefine(type = ColType.INT,width = 11)
    private Integer GEREN_JILIANG_JI;

    public String getPROTECT_GOODS_ID() {
        return PROTECT_GOODS_ID;
    }

    public void setPROTECT_GOODS_ID(String PROTECT_GOODS_ID) {
        this.PROTECT_GOODS_ID = PROTECT_GOODS_ID;
    }

    public String getCOMPANY_ID() {
        return COMPANY_ID;
    }

    public void setCOMPANY_ID(String COMPANY_ID) {
        this.COMPANY_ID = COMPANY_ID;
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

    public Integer getQIAN_YI() {
        return QIAN_YI;
    }

    public void setQIAN_YI(Integer QIAN_YI) {
        this.QIAN_YI = QIAN_YI;
    }

    public Integer getQIAN_MAO() {
        return QIAN_MAO;
    }

    public void setQIAN_MAO(Integer QIAN_MAO) {
        this.QIAN_MAO = QIAN_MAO;
    }

    public Integer getQIAN_SHOU_TAO() {
        return QIAN_SHOU_TAO;
    }

    public void setQIAN_SHOU_TAO(Integer QIAN_SHOU_TAO) {
        this.QIAN_SHOU_TAO = QIAN_SHOU_TAO;
    }

    public Integer getQIAN_YAN_JING() {
        return QIAN_YAN_JING;
    }

    public void setQIAN_YAN_JING(Integer QIAN_YAN_JING) {
        this.QIAN_YAN_JING = QIAN_YAN_JING;
    }

    public Integer getQIAN_WEI_QUN() {
        return QIAN_WEI_QUN;
    }

    public void setQIAN_WEI_QUN(Integer QIAN_WEI_QUN) {
        this.QIAN_WEI_QUN = QIAN_WEI_QUN;
    }

    public Integer getQIAN_WEI_BO() {
        return QIAN_WEI_BO;
    }

    public void setQIAN_WEI_BO(Integer QIAN_WEI_BO) {
        this.QIAN_WEI_BO = QIAN_WEI_BO;
    }

    public Integer getQIAN_PING_FENG() {
        return QIAN_PING_FENG;
    }

    public void setQIAN_PING_FENG(Integer QIAN_PING_FENG) {
        this.QIAN_PING_FENG = QIAN_PING_FENG;
    }

    public Integer getGEREN_JILIANG() {
        return GEREN_JILIANG;
    }

    public void setGEREN_JILIANG(Integer GEREN_JILIANG) {
        this.GEREN_JILIANG = GEREN_JILIANG;
    }

    public String getOTHERS() {
        return OTHERS;
    }

    public void setOTHERS(String OTHERS) {
        this.OTHERS = OTHERS;
    }

    public String getUPDATEUSER() {
        return UPDATEUSER;
    }

    public void setUPDATEUSER(String UPDATEUSER) {
        this.UPDATEUSER = UPDATEUSER;
    }

    public Timestamp getUPDATETIME() {
        return UPDATETIME;
    }

    public void setUPDATETIME(Timestamp UPDATETIME) {
        this.UPDATETIME = UPDATETIME;
    }

    public Integer getGEREN_JILIANG_JI() {
        return GEREN_JILIANG_JI;
    }

    public void setGEREN_JILIANG_JI(Integer GEREN_JILIANG_JI) {
        this.GEREN_JILIANG_JI = GEREN_JILIANG_JI;
    }
}
