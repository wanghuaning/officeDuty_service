package com.local.model;

import lombok.Data;

@Data
public class FormRankModel {

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

    private Long oneResearcherNumLave=0l;//剩余一级调研员职数
    private Long towResearcherNumLave=0l;//剩余二级调研员职数
    private Long threeResearcherNumLave=0l;//剩余三级调研员职数
    private Long fourResearcherNumLave=0l;//剩余四级调研员职数
    private Long oneClerkNumLave=0l;//剩余一级主任科员职数
    private Long towClerkNumLave=0l;//剩余二级主任科员职数
    private Long threeClerkNumLave=0l;//剩余三级主任科员职数
    private Long fourClerkNumLave=0l;//剩余四级主任科员职数

    private Long oneResearcherDraftingNum=0l;//拟使用一级调研员职数
    private Long towResearcherDraftingNum=0l;//拟使用二级调研员职数
    private Long threeResearcherDraftingNum=0l;//拟使用三级调研员职数
    private Long fourResearcherDraftingNum=0l;//拟使用四级调研员职数
    private Long oneClerkDraftingNum=0l;//一级主任科员职数拟定
    private Long towClerkDraftingNum=0l;//二级主任科员职数拟定
    private Long threeClerkDraftingNum=0l;//三级主任科员职数拟定
    private Long fourClerkDraftingNum=0l;//四级主任科员职数拟定
}
