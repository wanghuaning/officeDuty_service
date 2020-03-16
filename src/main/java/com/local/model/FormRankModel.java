package com.local.model;

import lombok.Data;

@Data
public class FormRankModel {

    private String unitName;
    private String id;

    private Long oneResearcherNum=0l;//核准一级调研员职数
    private Long towResearcherNum=0l;//核准二级调研员职数
    private Long threeResearcherNum=0l;//核准三级调研员职数
    private Long fourResearcherNum=0l;//核准四级调研员职数
    private Long oneClerkNum=0l;//核准一级主任科员职数
    private Long towClerkNum=0l;//核准二级主任科员职数
    private Long threeClerkNum=0l;//核准三级主任科员职数
    private Long fourClerkNum=0l;//核准四级主任科员职数
    private Long oneClerk=0l;//核准一级科员
    private Long towClerk=0l;//核准二级科员
    private Long probation=0l;//核准试用期
    private Long heZhunTotal=0l;

    private Integer oneResearcherNumTurn=0;//套转一级调研员职数
    private Integer towResearcherNumTurn=0;//套转二级调研员职数
    private Integer threeResearcherNumTurn=0;//套转三级调研员职数
    private Integer fourResearcherNumTurn=0;//套转四级调研员职数
    private Integer oneClerkNumTurn=0;//套转一级主任科员职数
    private Integer towClerkNumTurn=0;//套转二级主任科员职数
    private Integer threeClerkNumTurn=0;//套转三级主任科员职数
    private Integer fourClerkNumTurn=0;//套转四级主任科员职数
    private Integer oneClerkTurn=0;//套转一级科员
    private Integer towClerkTurn=0;//套转二级科员
    private Integer probationTurn=0;//套转试用期
    private Integer taoZhuanTotal=0;

    private Long oneResearcherNumNow=0l;//现有一级调研员职数
    private Long towResearcherNumNow=0l;//现有二级调研员职数
    private Long threeResearcherNumNow=0l;//现有三级调研员职数
    private Long fourResearcherNumNow=0l;//现有四级调研员职数
    private Long oneClerkNumNow=0l;//现有一级主任科员职数
    private Long towClerkNumNow=0l;//现有二级主任科员职数
    private Long threeClerkNumNow=0l;//现有三级主任科员职数
    private Long fourClerkNumNow=0l;//现有四级主任科员职数
    private Long oneClerkNow=0l;//现有一级科员
    private Long towClerkNow=0l;//现有二级科员
    private Long probationNow=0l;//现有试用期
    private Long total=0l;

    private Long oneResearcherNumLave=0l;//剩余一级调研员职数
    private Long towResearcherNumLave=0l;//剩余二级调研员职数
    private Long threeResearcherNumLave=0l;//剩余三级调研员职数
    private Long fourResearcherNumLave=0l;//剩余四级调研员职数
    private Long oneClerkNumLave=0l;//剩余一级主任科员职数
    private Long towClerkNumLave=0l;//剩余二级主任科员职数
    private Long threeClerkNumLave=0l;//剩余三级主任科员职数
    private Long fourClerkNumLave=0l;//剩余四级主任科员职数
    private Long laveTotal=0l;

    private Long oneResearcherDraftingNum=0l;//拟使用一级调研员职数
    private Long towResearcherDraftingNum=0l;//拟使用二级调研员职数
    private Long threeResearcherDraftingNum=0l;//拟使用三级调研员职数
    private Long fourResearcherDraftingNum=0l;//拟使用四级调研员职数
    private Long oneClerkDraftingNum=0l;//一级主任科员职数拟定
    private Long towClerkDraftingNum=0l;//二级主任科员职数拟定
    private Long threeClerkDraftingNum=0l;//三级主任科员职数拟定
    private Long fourClerkDraftingNum=0l;//四级主任科员职数拟定


    public Long getHeZhunTotal() {
        return heZhunTotal;
    }

    public void setHeZhunTotal(Long heZhunTotal) {
        this.heZhunTotal = heZhunTotal;
    }

    public Integer getTaoZhuanTotal() {
        return taoZhuanTotal;
    }

    public void setTaoZhuanTotal(Integer taoZhuanTotal) {
        this.taoZhuanTotal = taoZhuanTotal;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getLaveTotal() {
        return laveTotal;
    }

    public void setLaveTotal(Long laveTotal) {
        this.laveTotal = laveTotal;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOneResearcherNum() {
        return oneResearcherNum;
    }

    public void setOneResearcherNum(Long oneResearcherNum) {
        this.oneResearcherNum = oneResearcherNum;
    }

    public Long getTowResearcherNum() {
        return towResearcherNum;
    }

    public void setTowResearcherNum(Long towResearcherNum) {
        this.towResearcherNum = towResearcherNum;
    }

    public Long getThreeResearcherNum() {
        return threeResearcherNum;
    }

    public void setThreeResearcherNum(Long threeResearcherNum) {
        this.threeResearcherNum = threeResearcherNum;
    }

    public Long getFourResearcherNum() {
        return fourResearcherNum;
    }

    public void setFourResearcherNum(Long fourResearcherNum) {
        this.fourResearcherNum = fourResearcherNum;
    }

    public Long getOneClerkNum() {
        return oneClerkNum;
    }

    public void setOneClerkNum(Long oneClerkNum) {
        this.oneClerkNum = oneClerkNum;
    }

    public Long getTowClerkNum() {
        return towClerkNum;
    }

    public void setTowClerkNum(Long towClerkNum) {
        this.towClerkNum = towClerkNum;
    }

    public Long getThreeClerkNum() {
        return threeClerkNum;
    }

    public void setThreeClerkNum(Long threeClerkNum) {
        this.threeClerkNum = threeClerkNum;
    }

    public Long getFourClerkNum() {
        return fourClerkNum;
    }

    public void setFourClerkNum(Long fourClerkNum) {
        this.fourClerkNum = fourClerkNum;
    }

    public Long getOneClerk() {
        return oneClerk;
    }

    public void setOneClerk(Long oneClerk) {
        this.oneClerk = oneClerk;
    }

    public Long getTowClerk() {
        return towClerk;
    }

    public void setTowClerk(Long towClerk) {
        this.towClerk = towClerk;
    }

    public Long getProbation() {
        return probation;
    }

    public void setProbation(Long probation) {
        this.probation = probation;
    }

    public Integer getOneResearcherNumTurn() {
        return oneResearcherNumTurn;
    }

    public void setOneResearcherNumTurn(Integer oneResearcherNumTurn) {
        this.oneResearcherNumTurn = oneResearcherNumTurn;
    }

    public Integer getTowResearcherNumTurn() {
        return towResearcherNumTurn;
    }

    public void setTowResearcherNumTurn(Integer towResearcherNumTurn) {
        this.towResearcherNumTurn = towResearcherNumTurn;
    }

    public Integer getThreeResearcherNumTurn() {
        return threeResearcherNumTurn;
    }

    public void setThreeResearcherNumTurn(Integer threeResearcherNumTurn) {
        this.threeResearcherNumTurn = threeResearcherNumTurn;
    }

    public Integer getFourResearcherNumTurn() {
        return fourResearcherNumTurn;
    }

    public void setFourResearcherNumTurn(Integer fourResearcherNumTurn) {
        this.fourResearcherNumTurn = fourResearcherNumTurn;
    }

    public Integer getOneClerkNumTurn() {
        return oneClerkNumTurn;
    }

    public void setOneClerkNumTurn(Integer oneClerkNumTurn) {
        this.oneClerkNumTurn = oneClerkNumTurn;
    }

    public Integer getTowClerkNumTurn() {
        return towClerkNumTurn;
    }

    public void setTowClerkNumTurn(Integer towClerkNumTurn) {
        this.towClerkNumTurn = towClerkNumTurn;
    }

    public Integer getThreeClerkNumTurn() {
        return threeClerkNumTurn;
    }

    public void setThreeClerkNumTurn(Integer threeClerkNumTurn) {
        this.threeClerkNumTurn = threeClerkNumTurn;
    }

    public Integer getFourClerkNumTurn() {
        return fourClerkNumTurn;
    }

    public void setFourClerkNumTurn(Integer fourClerkNumTurn) {
        this.fourClerkNumTurn = fourClerkNumTurn;
    }

    public Integer getOneClerkTurn() {
        return oneClerkTurn;
    }

    public void setOneClerkTurn(Integer oneClerkTurn) {
        this.oneClerkTurn = oneClerkTurn;
    }

    public Integer getTowClerkTurn() {
        return towClerkTurn;
    }

    public void setTowClerkTurn(Integer towClerkTurn) {
        this.towClerkTurn = towClerkTurn;
    }

    public Integer getProbationTurn() {
        return probationTurn;
    }

    public void setProbationTurn(Integer probationTurn) {
        this.probationTurn = probationTurn;
    }

    public Long getOneResearcherNumNow() {
        return oneResearcherNumNow;
    }

    public void setOneResearcherNumNow(Long oneResearcherNumNow) {
        this.oneResearcherNumNow = oneResearcherNumNow;
    }

    public Long getTowResearcherNumNow() {
        return towResearcherNumNow;
    }

    public void setTowResearcherNumNow(Long towResearcherNumNow) {
        this.towResearcherNumNow = towResearcherNumNow;
    }

    public Long getThreeResearcherNumNow() {
        return threeResearcherNumNow;
    }

    public void setThreeResearcherNumNow(Long threeResearcherNumNow) {
        this.threeResearcherNumNow = threeResearcherNumNow;
    }

    public Long getFourResearcherNumNow() {
        return fourResearcherNumNow;
    }

    public void setFourResearcherNumNow(Long fourResearcherNumNow) {
        this.fourResearcherNumNow = fourResearcherNumNow;
    }

    public Long getOneClerkNumNow() {
        return oneClerkNumNow;
    }

    public void setOneClerkNumNow(Long oneClerkNumNow) {
        this.oneClerkNumNow = oneClerkNumNow;
    }

    public Long getTowClerkNumNow() {
        return towClerkNumNow;
    }

    public void setTowClerkNumNow(Long towClerkNumNow) {
        this.towClerkNumNow = towClerkNumNow;
    }

    public Long getThreeClerkNumNow() {
        return threeClerkNumNow;
    }

    public void setThreeClerkNumNow(Long threeClerkNumNow) {
        this.threeClerkNumNow = threeClerkNumNow;
    }

    public Long getFourClerkNumNow() {
        return fourClerkNumNow;
    }

    public void setFourClerkNumNow(Long fourClerkNumNow) {
        this.fourClerkNumNow = fourClerkNumNow;
    }

    public Long getOneClerkNow() {
        return oneClerkNow;
    }

    public void setOneClerkNow(Long oneClerkNow) {
        this.oneClerkNow = oneClerkNow;
    }

    public Long getTowClerkNow() {
        return towClerkNow;
    }

    public void setTowClerkNow(Long towClerkNow) {
        this.towClerkNow = towClerkNow;
    }

    public Long getProbationNow() {
        return probationNow;
    }

    public void setProbationNow(Long probationNow) {
        this.probationNow = probationNow;
    }

    public Long getOneResearcherNumLave() {
        return oneResearcherNumLave;
    }

    public void setOneResearcherNumLave(Long oneResearcherNumLave) {
        this.oneResearcherNumLave = oneResearcherNumLave;
    }

    public Long getTowResearcherNumLave() {
        return towResearcherNumLave;
    }

    public void setTowResearcherNumLave(Long towResearcherNumLave) {
        this.towResearcherNumLave = towResearcherNumLave;
    }

    public Long getThreeResearcherNumLave() {
        return threeResearcherNumLave;
    }

    public void setThreeResearcherNumLave(Long threeResearcherNumLave) {
        this.threeResearcherNumLave = threeResearcherNumLave;
    }

    public Long getFourResearcherNumLave() {
        return fourResearcherNumLave;
    }

    public void setFourResearcherNumLave(Long fourResearcherNumLave) {
        this.fourResearcherNumLave = fourResearcherNumLave;
    }

    public Long getOneClerkNumLave() {
        return oneClerkNumLave;
    }

    public void setOneClerkNumLave(Long oneClerkNumLave) {
        this.oneClerkNumLave = oneClerkNumLave;
    }

    public Long getTowClerkNumLave() {
        return towClerkNumLave;
    }

    public void setTowClerkNumLave(Long towClerkNumLave) {
        this.towClerkNumLave = towClerkNumLave;
    }

    public Long getThreeClerkNumLave() {
        return threeClerkNumLave;
    }

    public void setThreeClerkNumLave(Long threeClerkNumLave) {
        this.threeClerkNumLave = threeClerkNumLave;
    }

    public Long getFourClerkNumLave() {
        return fourClerkNumLave;
    }

    public void setFourClerkNumLave(Long fourClerkNumLave) {
        this.fourClerkNumLave = fourClerkNumLave;
    }

    public Long getOneResearcherDraftingNum() {
        return oneResearcherDraftingNum;
    }

    public void setOneResearcherDraftingNum(Long oneResearcherDraftingNum) {
        this.oneResearcherDraftingNum = oneResearcherDraftingNum;
    }

    public Long getTowResearcherDraftingNum() {
        return towResearcherDraftingNum;
    }

    public void setTowResearcherDraftingNum(Long towResearcherDraftingNum) {
        this.towResearcherDraftingNum = towResearcherDraftingNum;
    }

    public Long getThreeResearcherDraftingNum() {
        return threeResearcherDraftingNum;
    }

    public void setThreeResearcherDraftingNum(Long threeResearcherDraftingNum) {
        this.threeResearcherDraftingNum = threeResearcherDraftingNum;
    }

    public Long getFourResearcherDraftingNum() {
        return fourResearcherDraftingNum;
    }

    public void setFourResearcherDraftingNum(Long fourResearcherDraftingNum) {
        this.fourResearcherDraftingNum = fourResearcherDraftingNum;
    }

    public Long getOneClerkDraftingNum() {
        return oneClerkDraftingNum;
    }

    public void setOneClerkDraftingNum(Long oneClerkDraftingNum) {
        this.oneClerkDraftingNum = oneClerkDraftingNum;
    }

    public Long getTowClerkDraftingNum() {
        return towClerkDraftingNum;
    }

    public void setTowClerkDraftingNum(Long towClerkDraftingNum) {
        this.towClerkDraftingNum = towClerkDraftingNum;
    }

    public Long getThreeClerkDraftingNum() {
        return threeClerkDraftingNum;
    }

    public void setThreeClerkDraftingNum(Long threeClerkDraftingNum) {
        this.threeClerkDraftingNum = threeClerkDraftingNum;
    }

    public Long getFourClerkDraftingNum() {
        return fourClerkDraftingNum;
    }

    public void setFourClerkDraftingNum(Long fourClerkDraftingNum) {
        this.fourClerkDraftingNum = fourClerkDraftingNum;
    }
}
