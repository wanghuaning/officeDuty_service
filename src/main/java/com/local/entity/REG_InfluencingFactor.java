package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

@ApiModel("污染类型表")//用在模型类上，对模型类做注释；
@Table("L_REG_influencing_factor")
@Comment("污染类型表")
public class REG_InfluencingFactor implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("ID")
    @Comment("ID")
    @Column("id")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String id;


    @ApiModelProperty("备案ID")
    @Comment("备案ID")
    @Column("Registration_Id")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String registrationId;

    @ApiModelProperty("污染类型")
    @Comment("污染类型")
    @Column("Factor")
    @ColDefine(type = ColType.VARCHAR,width = 2000)
    private String factor;

    @ApiModelProperty("排序")
    @Comment("排序")
    @Column("Factor_Sort")
    @ColDefine(type = ColType.INT,width = 4)
    private Integer factorSort;

    @ApiModelProperty("更新时间")
    @Comment("更新时间")
    @Column("Update_Time")
    @ColDefine(type = ColType.DATE)
    private java.util.Date updateTime;

    @ApiModelProperty("批次号")
    @Comment("批次号")
    @Column("Batch_Number")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String batchNumber;

    @ApiModelProperty("推送批次")
    @Comment("推送批次")
    @Column("Push_Batch")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String pushBatch;

    @ApiModelProperty("接收批次")
    @Comment("接收批次")
    @Column("C_Batch")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String CBatch;

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getPushBatch() {
        return pushBatch;
    }

    public void setPushBatch(String pushBatch) {
        this.pushBatch = pushBatch;
    }

    public String getCBatch() {
        return CBatch;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setCBatch(String CBatch) {
        this.CBatch = CBatch;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public Integer getFactorSort() {
        return factorSort;
    }

    public void setFactorSort(Integer factorSort) {
        this.factorSort = factorSort;
    }
}
