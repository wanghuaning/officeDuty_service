package com.local.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel("推送记录表")
@Table("L_REG_push_record")
@Comment("推送记录表")
public class REG_PushRecord implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("id")
    @Comment("id")
    @Column("Id")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String id;

    @ApiModelProperty("项目时间")
    @Comment("推送时间")
    @Column("Push_Date")
    @ColDefine(type = ColType.DATE)
    private java.util.Date projectDate;

    @ApiModelProperty("开始时间")
    @Comment("开始时间")
    @Column("Start_Date")
    @ColDefine(type = ColType.DATE)
    private Date startDate;

    @ApiModelProperty("结束时间")
    @Comment("结束时间")
    @Column("End_Date")
    @ColDefine(type = ColType.DATE)
    private Date endDate;

    @ApiModelProperty("推送或接收")
    @Comment("推送或接收")
    @Column("Push_Receive")
    @ColDefine(type = ColType.VARCHAR, width = 16)
    private String pushReceive;

    @ApiModelProperty("记录条数")
    @Comment("记录条数")
    @Column("Records_Num")
    @ColDefine(type = ColType.INT, width = 12)
    private Integer recordsNum;

    @ApiModelProperty("表名")
    @Comment("表名")
    @Column("Data_Table")
    @ColDefine(type = ColType.VARCHAR, width = 16)
    private String dataTable;

    @ApiModelProperty("批次号")
    @Comment("批次号")
    @Column("Batch_Number")
    @ColDefine(type = ColType.VARCHAR, width = 16)
    private String batchNum;

    @ApiModelProperty("父Id")
    @Comment("父Id")
    @Column("Parent_Id")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String parentId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<REG_PushRecord> children;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<REG_PushRecord> getChildren() {
        return children;
    }

    public void setChildren(List<REG_PushRecord> children) {
        this.children = children;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }
    public String getDataTable() {
        return dataTable;
    }

    public void setDataTable(String dataTable) {
        this.dataTable = dataTable;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getProjectDate() {
        return projectDate;
    }

    public void setProjectDate(Date projectDate) {
        this.projectDate = projectDate;
    }

    public String getPushReceive() {
        return pushReceive;
    }

    public void setPushReceive(String pushReceive) {
        this.pushReceive = pushReceive;
    }


    public double getRecordsNum() {
        return recordsNum;
    }

    public void setRecordsNum(Integer recordsNum) {
        this.recordsNum = recordsNum;
    }
}
