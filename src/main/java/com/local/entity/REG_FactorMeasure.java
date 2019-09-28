package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;

@ApiModel("环保措施表")//用在模型类上，对模型类做注释；
@Table("L_REG_factor_measure")
@Comment("环保措施表")
public class REG_FactorMeasure implements Serializable {
    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("id")//用在属性上，对属性做注释
    @Comment("id")//定义脚本中添加comment属性来添加注释
    @Column("ID")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String id;

    @ApiModelProperty("污染类型Id")
    @Comment("污染类型Id")
    @Column("Factor_Id")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String factorId;


    @ApiModelProperty("创建时间")
    @Comment("创建时间")
    @Column("Create_Date")
    @ColDefine(type = ColType.DATE)
    private java.sql.Timestamp createDate;

    @ApiModelProperty("修改时间")
    @Comment("修改时间")
    @Column("Update_Date")
    @ColDefine(type = ColType.DATE)
    private java.sql.Timestamp updateDate;

    @ApiModelProperty("有无环保措施标示")
    @Comment("有无环保措施标示")
    @Column("Yn_Flag")
    @ColDefine(type = ColType.VARCHAR,width = 32)
    private String ynFlag;

    @ApiModelProperty("措施")
    @Comment("措施")
    @Column("Measure")
    @ColDefine(type = ColType.TEXT,width = 0)
    private String measure;

    @ApiModelProperty("删除标记")
    @Comment("删除标记")
    @Column("Delete_Flag")
    @ColDefine(type = ColType.CHAR,width = 1)
    private String deleteFlag;

    @ApiModelProperty("排序'")
    @Comment("排序'")
    @Column("Measure_Sort")
    @ColDefine(type = ColType.INT,width = 255)
    private Integer measureSort;

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

    public void setCBatch(String CBatch) {
        this.CBatch = CBatch;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFactorId() {
        return factorId;
    }

    public void setFactorId(String factorId) {
        this.factorId = factorId;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getYnFlag() {
        return ynFlag;
    }

    public void setYnFlag(String ynFlag) {
        this.ynFlag = ynFlag;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    public Integer getMeasureSort() {
        return measureSort;
    }
    public void setMeasureSort(Integer measureSort) {
        this.measureSort = measureSort;
    }
}
