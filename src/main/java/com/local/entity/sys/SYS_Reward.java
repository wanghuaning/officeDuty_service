package com.local.entity.sys;


import java.io.Serializable;

public class SYS_Reward implements Serializable {

  private String id;
  private String name;
  private java.sql.Timestamp createTime;
  private String peopleId;
  private String approvalUnit;
  private String duty;
  private java.sql.Timestamp revocationDate;
  private String unitType;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }


  public String getPeopleId() {
    return peopleId;
  }

  public void setPeopleId(String peopleId) {
    this.peopleId = peopleId;
  }


  public String getApprovalUnit() {
    return approvalUnit;
  }

  public void setApprovalUnit(String approvalUnit) {
    this.approvalUnit = approvalUnit;
  }


  public String getDuty() {
    return duty;
  }

  public void setDuty(String duty) {
    this.duty = duty;
  }


  public java.sql.Timestamp getRevocationDate() {
    return revocationDate;
  }

  public void setRevocationDate(java.sql.Timestamp revocationDate) {
    this.revocationDate = revocationDate;
  }


  public String getUnitType() {
    return unitType;
  }

  public void setUnitType(String unitType) {
    this.unitType = unitType;
  }

}
