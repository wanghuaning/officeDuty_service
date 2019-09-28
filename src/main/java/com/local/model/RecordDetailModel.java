package com.local.model;

import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * @author 陈铁
 * @Api_注释
 */
@Table
public class RecordDetailModel {
    private String name;
    private String id;
    private String parentId;
    private java.util.Date updateTime;
    private String batchNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }
}
