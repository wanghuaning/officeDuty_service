package com.local.model;

import lombok.Data;

@Data
public class ApproalModel {
    private String unitName;
    private String unitType;//机构性质
    private String level;//机构规格
    private String officialNum;//行政编制数
    private String oneTowResearcherNum;//一级和二级调研员职数
    private String oneResearcherNum;//一级调研员职数
    private String towResearcherNum;//二级调研员职数
    private String threeFourResearcherNum;//三级和四级调研员职数
    private String threeResearcherNum;//三级调研员职数
    private String fourResearcherNum;//四级调研员职数
    private String researcherTotal;//调研员合计
    private String oneTowClerkNum;//一级和二级主任科员职数
    private String oneClerkNum;//一级主任科员职数
    private String towClerkNum;//二级主任科员职数
    private String threeFourClerkNum;//三级和四级主任科员职数
    private String threeClerkNum;//三级主任科员职数
    private String fourClerkNum;//四级主任科员职数
    private String clerkTotal;//主任科员合计

    private String oneClerkUserNum;//一级主任科员职数使用
    private String towClerkUserNum;//二级主任科员职数使用
    private String threeClerkUserNum;//三级主任科员职数使用
    private String fourClerkUserNum;//四级主任科员职数使用
    private String userTotal;//使用合计

    private String oneClerkVacancyNum;//一级主任科员职数空缺
    private String towClerkVacancyNum;//二级主任科员职数空缺
    private String threeClerkVacancyNum;//三级主任科员职数空缺
    private String fourClerkVacancyNum;//四级主任科员职数空缺
    private String vacancyTotal;//空缺合计

    private String oneClerkDraftingNum;//一级主任科员职数拟定
    private String towClerkDraftingNum;//二级主任科员职数拟定
    private String threeClerkDraftingNum;//三级主任科员职数拟定
    private String fourClerkDraftingNum;//四级主任科员职数拟定
    private String drafting;//拟定合计
}
