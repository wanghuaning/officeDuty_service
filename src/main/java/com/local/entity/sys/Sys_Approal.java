package com.local.entity.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.local.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@ApiModel("职数使用信息表")//用在模型类上，对模型类做注释；
@Table("sys_approal")
@Comment("职数使用信息表")
@Data
public class Sys_Approal {

  @Name
  @ApiModelProperty("主键Id")
  @Comment("主键Id")
  @Column("id")
  @ColDefine(type = ColType.VARCHAR,width = 64)
  private String id;

  @ApiModelProperty("单位Id")
  @Comment("单位Id")
  @Column("unit_Id")
  @ColDefine(type = ColType.VARCHAR,width = 64)
  private String unitId;


  @ApiModelProperty("创建日期")
  @Comment("创建日期")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Column("create_Time")
  @ColDefine(type = ColType.DATETIME)
  private Date createTime;

  private String createTimeStr;

  @Default(value = "0")
  @ApiModelProperty("1：已审批 0：未审批")
  @Comment("1：已审批 0：未审批")
  @Column("flag")
  @ColDefine(type = ColType.VARCHAR, width = 2)
  private String flag;

  @ApiModelProperty("单位名称")
  @Comment("单位名称")
  @Column("unit_Name")
  @ColDefine(type = ColType.VARCHAR,width = 64)
  private String unitName;

  @ApiModelProperty("机构性质")
  @Comment("机构性质")
  @Column("unit_Type")
  @ColDefine(type = ColType.VARCHAR,width = 64)
  private String unitType;

  @ApiModelProperty("机构规格")
  @Comment("机构规格")
  @Column("level")
  @ColDefine(type = ColType.VARCHAR,width = 64)
  private String level;

  @Default(value = "0")
  @ApiModelProperty("行政编制数")
  @Comment("行政编制数")
  @Column("official_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String officialNum;

  @Default(value = "0")
  @ApiModelProperty("一级和二级调研员职数")
  @Comment("一级和二级调研员职数")
  @Column("one_Tow_Researcher_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String oneTowResearcherNum="0";

  @Default(value = "0")
  @ApiModelProperty("一级调研员职数")
  @Comment("一级调研员职数")
  @Column("one_Researcher_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String oneResearcherNum="0";

  @Default(value = "0")
  @ApiModelProperty("二级调研员职数")
  @Comment("二级调研员职数")
  @Column("tow_Researcher_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String towResearcherNum="0";

  @Default(value = "0")
  @ApiModelProperty("三级和四级调研员职数")
  @Comment("三级和四级调研员职数")
  @Column("three_FourResearcher_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String threeFourResearcherNum="0";

  @Default(value = "0")
  @ApiModelProperty("三级调研员职数")
  @Comment("三级调研员职数")
  @Column("three_Researcher_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String threeResearcherNum="0";

  @Default(value = "0")
  @ApiModelProperty("四级调研员职数")
  @Comment("四级调研员职数")
  @Column("four_Researcher_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String fourResearcherNum="0";

  @Default(value = "0")
  @ApiModelProperty("调研员合计")
  @Comment("调研员合计")
  @Column("researcher_Total")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String researcherTotal="0";

  @Default(value = "0")
  @ApiModelProperty("一级和二级主任科员职数")
  @Comment("一级和二级主任科员职数")
  @Column("one_Tow_Clerk_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String oneTowClerkNum="0";

  @Default(value = "0")
  @ApiModelProperty("一级主任科员职数")
  @Comment("一级主任科员职数")
  @Column("one_Clerk_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String oneClerkNum="0";

  @Default(value = "0")
  @ApiModelProperty("二级主任科员职数")
  @Comment("二级主任科员职数")
  @Column("tow_Clerk_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String towClerkNum="0";

  @Default(value = "0")
  @ApiModelProperty("三级和四级主任科员职数")
  @Comment("三级和四级主任科员职数")
  @Column("three_Four_Clerk_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String threeFourClerkNum="0";

  @Default(value = "0")
  @ApiModelProperty("三级主任科员职数")
  @Comment("三级主任科员职数")
  @Column("three_Clerk_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String threeClerkNum="0";

  @Default(value = "0")
  @ApiModelProperty("四级主任科员职数")
  @Comment("四级主任科员职数")
  @Column("four_Clerk_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String fourClerkNum="0";

  @Default(value = "0")
  @ApiModelProperty("主任科员合计")
  @Comment("主任科员合计")
  @Column("clerk_Total")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String clerkTotal="0";

  @Default(value = "0")
  @ApiModelProperty("一级调研员职数使用")
  @Comment("一级调研员职数使用")
  @Column("one_Researcher_User_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String oneResearcherUserNum="0";

  @Default(value = "0")
  @ApiModelProperty("二级调研员职数使用")
  @Comment("二级调研员职数使用")
  @Column("tow_Researcher_User_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String towResearcherUserNum="0";

  @Default(value = "0")
  @ApiModelProperty("三级调研员职数使用")
  @Comment("三级调研员职数使用")
  @Column("three_Researcher_User_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String threeResearcherUserNum="0";

  @Default(value = "0")
  @ApiModelProperty("四级调研员职数使用")
  @Comment("四级调研员职数使用")
  @Column("four_Researcher_User_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String fourResearcherUserNum="0";

  @Default(value = "0")
  @ApiModelProperty("一级主任科员职数使用")
  @Comment("一级主任科员职数使用")
  @Column("one_Clerk_User_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String oneClerkUserNum="0";

  @Default(value = "0")
  @ApiModelProperty("二级主任科员职数使用")
  @Comment("二级主任科员职数使用")
  @Column("tow_Clerk_User_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String towClerkUserNum="0";

  @Default(value = "0")
  @ApiModelProperty("三级主任科员职数使用")
  @Comment("三级主任科员职数使用")
  @Column("three_Clerk_User_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String threeClerkUserNum="0";

  @Default(value = "0")
  @ApiModelProperty("四级主任科员职数使用")
  @Comment("四级主任科员职数使用")
  @Column("four_Clerk_User_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String fourClerkUserNum="0";

  @Default(value = "0")
  @ApiModelProperty("使用合计")
  @Comment("使用合计")
  @Column("user_Total")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String userTotal="0";

  @Default(value = "0")
  @ApiModelProperty("一级主任科员职数空缺")
  @Comment("一级主任科员职数空缺")
  @Column("one_Clerk_Vacancy_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String oneClerkVacancyNum;

  @Default(value = "0")
  @ApiModelProperty("二级主任科员职数空缺")
  @Comment("二级主任科员职数空缺")
  @Column("tow_Clerk_Vacancy_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String towClerkVacancyNum="0";

  @Default(value = "0")
  @ApiModelProperty("三级主任科员职数空缺")
  @Comment("三级主任科员职数空缺")
  @Column("three_Clerk_Vacancy_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String threeClerkVacancyNum="0";

  @ApiModelProperty("四级主任科员职数空缺")
  @Comment("四级主任科员职数空缺")
  @Column("four_Clerk_Vacancy_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String fourClerkVacancyNum="0";

  @Default(value = "0")
  @ApiModelProperty("空缺合计")
  @Comment("空缺合计")
  @Column("vacancy_Total")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String vacancyTotal="0";

  @Default(value = "0")
  @ApiModelProperty("一级调研员职数拟定")
  @Comment("一级调研员职数拟定")
  @Column("one_Researcher_Drafting_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String oneResearcherDraftingNum="0";

  @Default(value = "0")
  @ApiModelProperty("二级调研员职数拟定")
  @Comment("二级调研员职数拟定")
  @Column("tow_Researcher_Drafting_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String towResearcherDraftingNum="0";

  @Default(value = "0")
  @ApiModelProperty("三级调研员职数拟定")
  @Comment("三级调研员职数拟定")
  @Column("three_Researcher_Drafting_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String threeResearcherDraftingNum="0";

  @Default(value = "0")
  @ApiModelProperty("四级调研员职数拟定")
  @Comment("四级调研员职数拟定")
  @Column("four_Researcher_Drafting_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String fourResearcherDraftingNum="0";

  @Default(value = "0")
  @ApiModelProperty("一级主任科员职数拟定")
  @Comment("一级主任科员职数拟定")
  @Column("one_Clerk_Drafting_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String oneClerkDraftingNum="0";

  @Default(value = "0")
  @ApiModelProperty("二级主任科员职数拟定")
  @Comment("二级主任科员职数拟定")
  @Column("tow_Clerk_Drafting_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String towClerkDraftingNum="0";

  @Default(value = "0")
  @ApiModelProperty("三级主任科员职数拟定")
  @Comment("三级主任科员职数拟定")
  @Column("three_Clerk_Drafting_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String threeClerkDraftingNum="0";

  @Default(value = "0")
  @ApiModelProperty("四级主任科员职数拟定")
  @Comment("四级主任科员职数拟定")
  @Column("four_Clerk_Drafting_Num")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String fourClerkDraftingNum="0";

  @Default(value = "0")
  @ApiModelProperty("拟定合计")
  @Comment("拟定合计")
  @Column("drafting")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String drafting="0";

  @Default(value = "0")
  @ApiModelProperty("一级调研员职数拟定备注")
  @Comment("一级调研员职数拟定备注")
  @Column("one_Researcher_Drafting_Num_Detail")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String oneResearcherDraftingNumDetail="0";

  @Default(value = "0")
  @ApiModelProperty("二级调研员职数拟定备注")
  @Comment("二级调研员职数拟定备注")
  @Column("tow_Researcher_Drafting_Num_Detail")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String towResearcherDraftingNumDetail="0";

  @Default(value = "0")
  @ApiModelProperty("三级调研员职数拟定备注")
  @Comment("三级调研员职数拟定备注")
  @Column("three_Researcher_Drafting_Num_Detail")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String threeResearcherDraftingNumDetail="0";

  @Default(value = "0")
  @ApiModelProperty("四级调研员职数拟定备注")
  @Comment("四级调研员职数拟定备注")
  @Column("four_Researcher_Drafting_Num_Detail")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String fourResearcherDraftingNumDetail="0";

  @Default(value = "0")
  @ApiModelProperty("一级主任科员职数拟定备注")
  @Comment("一级主任科员职数拟定备注")
  @Column("one_Clerk_Drafting_Num_Detail")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String oneClerkDraftingNumDetail="0";

  @Default(value = "0")
  @ApiModelProperty("二级主任科员职数拟定备注")
  @Comment("二级主任科员职数拟定备注")
  @Column("tow_Clerk_Drafting_Num_Detail")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String towClerkDraftingNumDetail="0";

  @Default(value = "0")
  @ApiModelProperty("三级主任科员职数拟定备注")
  @Comment("三级主任科员职数拟定备注")
  @Column("three_Clerk_Drafting_Num_Detail")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String threeClerkDraftingNumDetail="0";

  @Default(value = "0")
  @ApiModelProperty("四级主任科员职数拟定备注")
  @Comment("四级主任科员职数拟定备注")
  @Column("four_Clerk_Drafting_Num_Detail")
  @ColDefine(type = ColType.VARCHAR,width = 8)
  private String fourClerkDraftingNumDetail="0";
  private String dataFlag;

  private List<Sys_Approal> children;

  public String getCreateTimeStr() {
    return DateUtil.dateToString(createTime);
  }

  public void setCreateTimeStr(String createTimeStr) {
    this.createTimeStr = createTimeStr;
  }
}
