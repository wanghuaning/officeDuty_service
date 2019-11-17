package com.local.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UnitModel {
    private String id;
    private String code;//组织机构编码
    private String name;//单位名称
    private String parentId;//父单位ID
    private String area;//所在地区
    private String affiliation;//隶属关系
    private String category;//所属序列
    private String level;//机构级别
    private String parentName;//上级单位名称
    private String detail;//备注
    private String contact;//联系人
    private String contactNumber;//联系电话
    private String referOfficialDocument;//参照公务员法管理审批文号
    private Date referOfficialDate;//参照公务员法管理审批时间
    private Integer officialNum;//行政编制数
    private Integer referOfficialNum;//事业编制数（参公）
    private Integer officialRealNum;//行政实有数
    private Integer referOfficialRealNum;//事业实有数（参公）

    private Integer realNameNumTurn;//套转实名制管理
    private Integer militaryNumTurn;//套转军转干部
    private Integer rightPlaceNumTurn;//套转县处级正职领导职数
    private Integer deputyPlaceNumTurn;//套转县处级副职领导职数
    private Integer mainHallNumTurn;//套转乡科级正职领导职数
    private Integer deputyHallNumTurn;//套转乡科级副职领导职数
    private Integer oneTowResearcherNumTurn;//套转一级和二级调研员职数
    private Integer oneResearcherNumTurn;//套转一级调研员职数
    private Integer towResearcherNumTurn;//套转二级调研员职数
    private Integer threeFourResearcherNumTurn;//套转三级和四级调研员职数
    private Integer threeResearcherNumTurn;//套转三级调研员职数
    private Integer fourResearcherNumTurn;//套转四级调研员职数
    private Integer oneTowClerkNumTurn;//套转一级和二级主任科员职数
    private Integer oneClerkNumTurn;//套转一级主任科员职数
    private Integer towClerkNumTurn;//套转二级主任科员职数
    private Integer threeFourClerkNumTurn;//套转三级和四级主任科员职数
    private Integer threeClerkNumTurn;//套转三级主任科员职数
    private Integer fourClerkNumTurn;//套转四级主任科员职数
    private Integer oneClerkTurn;//套转一级科员
    private Integer towClerkTurn;//套转二级科员
    private Integer probationTurn;//套转试用期

    private Integer realNameNumNow;//现有实名制管理
    private Integer militaryNumNow;//现有军转干部
    private Integer rightPlaceNumNow;//现有县处级正职领导职数
    private Integer deputyPlaceNumNow;//现有县处级副职领导职数
    private Integer mainHallNumNow;//现有乡科级正职领导职数
    private Integer deputyHallNumNow;//现有乡科级副职领导职数
    private Integer oneTowResearcherNumNow;//现有一级和二级调研员职数
    private Integer oneResearcherNumNow;//现有一级调研员职数
    private Integer towResearcherNumNow;//现有二级调研员职数
    private Integer threeFourResearcherNumNow;//现有三级和四级调研员职数
    private Integer threeResearcherNumNow;//现有三级调研员职数
    private Integer fourResearcherNumNow;//现有四级调研员职数
    private Integer oneTowClerkNumNow;//现有一级和二级主任科员职数
    private Integer oneClerkNumNow;//现有一级主任科员职数
    private Integer towClerkNumNow;//现有二级主任科员职数
    private Integer threeFourClerkNumNow;//现有三级和四级主任科员职数
    private Integer threeClerkNumNow;//现有三级主任科员职数
    private Integer fourClerkNumNow;//现有四级主任科员职数
    private Integer oneClerkNow;//现有一级科员
    private Integer towClerkNow;//现有二级科员
    private Integer probationNow;//现有试用期

    private Long oneTowResearcherNumLave;//剩余一级和二级调研员职数
    private Long oneResearcherNumLave;//剩余一级调研员职数
    private Long towResearcherNumLave;//剩余二级调研员职数
    private Long threeFourResearcherNumLave;//剩余三级和四级调研员职数
    private Long threeResearcherNumLave;//剩余三级调研员职数
    private Long fourResearcherNumLave;//剩余四级调研员职数
    private Long oneTowClerkNumLave;//剩余一级和二级主任科员职数
    private Long oneClerkNumLave;//剩余一级主任科员职数
    private Long towClerkNumLave;//剩余二级主任科员职数
    private Long threeFourClerkNumLave;//剩余三级和四级主任科员职数
    private Long threeClerkNumLave;//剩余三级主任科员职数
    private Long fourClerkNumLave;//剩余四级主任科员职数

}
