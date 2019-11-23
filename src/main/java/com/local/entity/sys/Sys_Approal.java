package com.local.entity.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.local.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel("职数使用信息表")//用在模型类上，对模型类做注释；
@Table("sys_approal")
@Comment("职数使用信息表")
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

  @Default(value = "0")
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

  private String dataFlag;

  public String getDataFlag() {
    return dataFlag;
  }

  public void setDataFlag(String dataFlag) {
    this.dataFlag = dataFlag;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getCreateTimeStr() {
    return DateUtil.dateToString(createTime);
  }

  public void setCreateTimeStr(String createTimeStr) {
    this.createTimeStr = createTimeStr;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }


  public String getUnitName() {
    return unitName;
  }

  public void setUnitName(String unitName) {
    this.unitName = unitName;
  }


  public String getUnitType() {
    return unitType;
  }

  public void setUnitType(String unitType) {
    this.unitType = unitType;
  }


  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }


  public String getOfficialNum() {
    return officialNum;
  }

  public void setOfficialNum(String officialNum) {
    this.officialNum = officialNum;
  }


  public String getOneTowResearcherNum() {
    return oneTowResearcherNum;
  }

  public void setOneTowResearcherNum(String oneTowResearcherNum) {
    this.oneTowResearcherNum = oneTowResearcherNum;
  }


  public String getOneResearcherNum() {
    return oneResearcherNum;
  }

  public void setOneResearcherNum(String oneResearcherNum) {
    this.oneResearcherNum = oneResearcherNum;
  }


  public String getTowResearcherNum() {
    return towResearcherNum;
  }

  public void setTowResearcherNum(String towResearcherNum) {
    this.towResearcherNum = towResearcherNum;
  }


  public String getThreeFourResearcherNum() {
    return threeFourResearcherNum;
  }

  public void setThreeFourResearcherNum(String threeFourResearcherNum) {
    this.threeFourResearcherNum = threeFourResearcherNum;
  }


  public String getThreeResearcherNum() {
    return threeResearcherNum;
  }

  public void setThreeResearcherNum(String threeResearcherNum) {
    this.threeResearcherNum = threeResearcherNum;
  }


  public String getFourResearcherNum() {
    return fourResearcherNum;
  }

  public void setFourResearcherNum(String fourResearcherNum) {
    this.fourResearcherNum = fourResearcherNum;
  }


  public String getResearcherTotal() {
    return researcherTotal;
  }

  public void setResearcherTotal(String researcherTotal) {
    this.researcherTotal = researcherTotal;
  }


  public String getOneTowClerkNum() {
    return oneTowClerkNum;
  }

  public void setOneTowClerkNum(String oneTowClerkNum) {
    this.oneTowClerkNum = oneTowClerkNum;
  }


  public String getOneClerkNum() {
    return oneClerkNum;
  }

  public void setOneClerkNum(String oneClerkNum) {
    this.oneClerkNum = oneClerkNum;
  }


  public String getTowClerkNum() {
    return towClerkNum;
  }

  public void setTowClerkNum(String towClerkNum) {
    this.towClerkNum = towClerkNum;
  }


  public String getThreeFourClerkNum() {
    return threeFourClerkNum;
  }

  public void setThreeFourClerkNum(String threeFourClerkNum) {
    this.threeFourClerkNum = threeFourClerkNum;
  }


  public String getThreeClerkNum() {
    return threeClerkNum;
  }

  public void setThreeClerkNum(String threeClerkNum) {
    this.threeClerkNum = threeClerkNum;
  }


  public String getFourClerkNum() {
    return fourClerkNum;
  }

  public void setFourClerkNum(String fourClerkNum) {
    this.fourClerkNum = fourClerkNum;
  }


  public String getClerkTotal() {
    return clerkTotal;
  }

  public void setClerkTotal(String clerkTotal) {
    this.clerkTotal = clerkTotal;
  }


  public String getOneClerkUserNum() {
    return oneClerkUserNum;
  }

  public void setOneClerkUserNum(String oneClerkUserNum) {
    this.oneClerkUserNum = oneClerkUserNum;
  }


  public String getTowClerkUserNum() {
    return towClerkUserNum;
  }

  public void setTowClerkUserNum(String towClerkUserNum) {
    this.towClerkUserNum = towClerkUserNum;
  }


  public String getThreeClerkUserNum() {
    return threeClerkUserNum;
  }

  public void setThreeClerkUserNum(String threeClerkUserNum) {
    this.threeClerkUserNum = threeClerkUserNum;
  }


  public String getFourClerkUserNum() {
    return fourClerkUserNum;
  }

  public void setFourClerkUserNum(String fourClerkUserNum) {
    this.fourClerkUserNum = fourClerkUserNum;
  }


  public String getUserTotal() {
    return userTotal;
  }

  public void setUserTotal(String userTotal) {
    this.userTotal = userTotal;
  }


  public String getOneClerkVacancyNum() {
    return oneClerkVacancyNum;
  }

  public void setOneClerkVacancyNum(String oneClerkVacancyNum) {
    this.oneClerkVacancyNum = oneClerkVacancyNum;
  }


  public String getTowClerkVacancyNum() {
    return towClerkVacancyNum;
  }

  public void setTowClerkVacancyNum(String towClerkVacancyNum) {
    this.towClerkVacancyNum = towClerkVacancyNum;
  }


  public String getThreeClerkVacancyNum() {
    return threeClerkVacancyNum;
  }

  public void setThreeClerkVacancyNum(String threeClerkVacancyNum) {
    this.threeClerkVacancyNum = threeClerkVacancyNum;
  }


  public String getFourClerkVacancyNum() {
    return fourClerkVacancyNum;
  }

  public void setFourClerkVacancyNum(String fourClerkVacancyNum) {
    this.fourClerkVacancyNum = fourClerkVacancyNum;
  }


  public String getVacancyTotal() {
    return vacancyTotal;
  }

  public void setVacancyTotal(String vacancyTotal) {
    this.vacancyTotal = vacancyTotal;
  }


  public String getOneClerkDraftingNum() {
    return oneClerkDraftingNum;
  }

  public void setOneClerkDraftingNum(String oneClerkDraftingNum) {
    this.oneClerkDraftingNum = oneClerkDraftingNum;
  }


  public String getTowClerkDraftingNum() {
    return towClerkDraftingNum;
  }

  public void setTowClerkDraftingNum(String towClerkDraftingNum) {
    this.towClerkDraftingNum = towClerkDraftingNum;
  }


  public String getThreeClerkDraftingNum() {
    return threeClerkDraftingNum;
  }

  public void setThreeClerkDraftingNum(String threeClerkDraftingNum) {
    this.threeClerkDraftingNum = threeClerkDraftingNum;
  }


  public String getFourClerkDraftingNum() {
    return fourClerkDraftingNum;
  }

  public void setFourClerkDraftingNum(String fourClerkDraftingNum) {
    this.fourClerkDraftingNum = fourClerkDraftingNum;
  }


  public String getDrafting() {
    return drafting;
  }

  public void setDrafting(String drafting) {
    this.drafting = drafting;
  }

}
