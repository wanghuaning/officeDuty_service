package com.local.model;

import lombok.Data;

@Data
public class ApproalModel {
    private String id;
    private String unitName;
    private String unitType;//机构性质
    private String level;//机构规格
    private String officialNum;//行政编制数
    private String oneTowResearcherNum = "0";//一级和二级调研员职数
    private String oneResearcherNum= "0";//一级调研员职数
    private String towResearcherNum= "0";//二级调研员职数
    private String threeFourResearcherNum= "0";//三级和四级调研员职数
    private String threeResearcherNum= "0";//三级调研员职数
    private String fourResearcherNum= "0";//四级调研员职数
    private String researcherTotal= "0";//调研员合计
    private String oneTowClerkNum= "0";//一级和二级主任科员职数
    private String oneClerkNum= "0";//一级主任科员职数
    private String towClerkNum= "0";//二级主任科员职数
    private String threeFourClerkNum= "0";//三级和四级主任科员职数
    private String threeClerkNum= "0";//三级主任科员职数
    private String fourClerkNum= "0";//四级主任科员职数
    private String clerkTotal= "0";//主任科员合计

    private String oneClerkUserNum= "0";//一级主任科员职数使用
    private String towClerkUserNum= "0";//二级主任科员职数使用
    private String threeClerkUserNum= "0";//三级主任科员职数使用
    private String fourClerkUserNum= "0";//四级主任科员职数使用
    private String userTotal= "0";//使用合计

    private String oneClerkVacancyNum= "0";//一级主任科员职数空缺
    private String towClerkVacancyNum= "0";//二级主任科员职数空缺
    private String threeClerkVacancyNum= "0";//三级主任科员职数空缺
    private String fourClerkVacancyNum= "0";//四级主任科员职数空缺
    private String vacancyTotal= "0";//空缺合计

    private String oneClerkDraftingNum= "0";//一级主任科员职数拟定
    private String towClerkDraftingNum= "0";//二级主任科员职数拟定
    private String threeClerkDraftingNum= "0";//三级主任科员职数拟定
    private String fourClerkDraftingNum= "0";//四级主任科员职数拟定
    private String drafting= "0";//拟定合计
}
