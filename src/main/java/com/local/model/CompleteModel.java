package com.local.model;

import lombok.Data;

@Data
public class CompleteModel {
    private String name;
    private String taoUnitNum;
    private String firstBatch;
    private String secondBatch;
    private String thirdBatch;
    private String fourBatch;
    private String fiveBatch;
    private String sexBatch;
    private String ganBuNum;
    private String total;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaoUnitNum() {
        return taoUnitNum;
    }

    public void setTaoUnitNum(String taoUnitNum) {
        this.taoUnitNum = taoUnitNum;
    }

    public String getFirstBatch() {
        return firstBatch;
    }

    public void setFirstBatch(String firstBatch) {
        this.firstBatch = firstBatch;
    }

    public String getSecondBatch() {
        return secondBatch;
    }

    public void setSecondBatch(String secondBatch) {
        this.secondBatch = secondBatch;
    }

    public String getThirdBatch() {
        return thirdBatch;
    }

    public void setThirdBatch(String thirdBatch) {
        this.thirdBatch = thirdBatch;
    }

    public String getFourBatch() {
        return fourBatch;
    }

    public void setFourBatch(String fourBatch) {
        this.fourBatch = fourBatch;
    }

    public String getFiveBatch() {
        return fiveBatch;
    }

    public void setFiveBatch(String fiveBatch) {
        this.fiveBatch = fiveBatch;
    }

    public String getSexBatch() {
        return sexBatch;
    }

    public void setSexBatch(String sexBatch) {
        this.sexBatch = sexBatch;
    }

    public String getGanBuNum() {
        return ganBuNum;
    }

    public void setGanBuNum(String ganBuNum) {
        this.ganBuNum = ganBuNum;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
