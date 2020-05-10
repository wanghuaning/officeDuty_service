package com.local.model;

import lombok.Data;

import java.util.List;

@Data
public class RegModel {
    private String unitId;
    private String peopleNums;//编制数
    private String hdzhengke;//核定正科
    private String hdfuke;//核定副科
    private String xianyouzhengke;//现有正科
    private String xianyoufuke;//现有副科
    private String xianyouganbu;//现有实名制干部
    private String hezhunoneTowClerkNum;//核准一级和二级主任科员
    private String hezhunthreeFourClerkNum;//核准三级和四级主任科员
    private String xianyouoneTowClerkNum;//现有一级和二级主任科员合计
    private String xianyouoneClerkNum;//现有一级主任科员
    private String xianyoutowClerkNum;//现有二级主任科员
    private String xianyouOneTowJunZhuanNum;//现有一级和二级主任科员首次套转不占职数军转干部数
    private String xianyouthreeFourClerkNum;//现有三级和四级主任科员合计
    private String xianyouThreeClerkNum;//现有三级主任科员
    private String xianyouFourClerkNum;//现有四级主任科员
    private String xianyouThreeFourJunZhuanNum;//现有三级和四级主任科员首次套转不占职数军转干部数
    private String nijinshengzhengke;//拟晋升正科
    private String nijinshengfuke;//拟晋升副科
    private String nijinshengganbu;//拟晋升干部
    private String nijinshengoneClerkNum;//拟晋升一级主任科员
    private String nijinshengtowClerkNum;//拟晋升二级主任科员
    private String nijinshengThreeClerkNum;//拟晋升三级主任科员
    private String nijinshengFourClerkNum;//拟晋升四级主任科员
    private String nijinshengJianZhioneClerkNum;//拟晋升兼职一级主任科员
    private String nijinshengJiantowClerkNum;//拟晋升兼职二级主任科员
    private String nijinshengJianThreeClerkNum;//拟晋升兼职三级主任科员
    private String zhengkeGaitowClerkNum;//正科改任二级主任科员
    private String fukeGaiFourClerkNum;//副科改任四级主任科员
    private String tiaozhengzhengke;//调整后正科
    private String tiaozhengfuke;//调整后副科
    private String tiaozhengganbu;//调整后干部
    private String tiaozhengoneTowClerkNum;//调整后一级和二级主任科员合计
    private String tiaozhengoneClerkNum;//调整后一级主任科员
    private String tiaozhengtowClerkNum;//调整后二级主任科员
    private String tiaozhengOneTowJunZhuanNum;//调整后一级和二级主任科员首次套转不占职数军转干部数
    private String tiaozhenghreeFourClerkNum;//调整后三级和四级主任科员合计
    private String tiaozhengThreeClerkNum;//调整后三级主任科员
    private String tiaozhengFourClerkNum;//调整后四级主任科员
    private String tiaozhengThreeFourJunZhuanNum;//调整后三级和四级主任科员首次套转不占职数军转干部数
    private List<RankModel> rankModels;//上报审核备案人员名单
    private String contact;// 联系人
    private String contactNumber; // 联系电话
    private String nowDateStr; // 填表日期
    private String year="";
    private String month="";
    private String day="";
    private String peopleName="";
    private String peopleNum="";
    private String unitName;
    private String rankProcessId="";//职数表Id
    private List<String> peopleIds;
    private String hasNiRen="否";//是否存在拟任职级
    private String explanation="";//
    private String detail="";//备注

    public List<String> getPeopleIds() {
        return peopleIds;
    }

    public void setPeopleIds(List<String> peopleIds) {
        this.peopleIds = peopleIds;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPeopleNums() {
        return peopleNums;
    }

    public void setPeopleNums(String peopleNums) {
        this.peopleNums = peopleNums;
    }

    public String getHdzhengke() {
        return hdzhengke;
    }

    public void setHdzhengke(String hdzhengke) {
        this.hdzhengke = hdzhengke;
    }

    public String getHdfuke() {
        return hdfuke;
    }

    public void setHdfuke(String hdfuke) {
        this.hdfuke = hdfuke;
    }

    public String getXianyouzhengke() {
        return xianyouzhengke;
    }

    public void setXianyouzhengke(String xianyouzhengke) {
        this.xianyouzhengke = xianyouzhengke;
    }

    public String getXianyoufuke() {
        return xianyoufuke;
    }

    public void setXianyoufuke(String xianyoufuke) {
        this.xianyoufuke = xianyoufuke;
    }

    public String getXianyouganbu() {
        return xianyouganbu;
    }

    public void setXianyouganbu(String xianyouganbu) {
        this.xianyouganbu = xianyouganbu;
    }

    public String getHezhunoneTowClerkNum() {
        return hezhunoneTowClerkNum;
    }

    public void setHezhunoneTowClerkNum(String hezhunoneTowClerkNum) {
        this.hezhunoneTowClerkNum = hezhunoneTowClerkNum;
    }

    public String getHezhunthreeFourClerkNum() {
        return hezhunthreeFourClerkNum;
    }

    public void setHezhunthreeFourClerkNum(String hezhunthreeFourClerkNum) {
        this.hezhunthreeFourClerkNum = hezhunthreeFourClerkNum;
    }

    public String getXianyouoneTowClerkNum() {
        return xianyouoneTowClerkNum;
    }

    public void setXianyouoneTowClerkNum(String xianyouoneTowClerkNum) {
        this.xianyouoneTowClerkNum = xianyouoneTowClerkNum;
    }

    public String getXianyouoneClerkNum() {
        return xianyouoneClerkNum;
    }

    public void setXianyouoneClerkNum(String xianyouoneClerkNum) {
        this.xianyouoneClerkNum = xianyouoneClerkNum;
    }

    public String getXianyoutowClerkNum() {
        return xianyoutowClerkNum;
    }

    public void setXianyoutowClerkNum(String xianyoutowClerkNum) {
        this.xianyoutowClerkNum = xianyoutowClerkNum;
    }

    public String getXianyouOneTowJunZhuanNum() {
        return xianyouOneTowJunZhuanNum;
    }

    public void setXianyouOneTowJunZhuanNum(String xianyouOneTowJunZhuanNum) {
        this.xianyouOneTowJunZhuanNum = xianyouOneTowJunZhuanNum;
    }

    public String getXianyouthreeFourClerkNum() {
        return xianyouthreeFourClerkNum;
    }

    public void setXianyouthreeFourClerkNum(String xianyouthreeFourClerkNum) {
        this.xianyouthreeFourClerkNum = xianyouthreeFourClerkNum;
    }

    public String getXianyouThreeClerkNum() {
        return xianyouThreeClerkNum;
    }

    public void setXianyouThreeClerkNum(String xianyouThreeClerkNum) {
        this.xianyouThreeClerkNum = xianyouThreeClerkNum;
    }

    public String getXianyouFourClerkNum() {
        return xianyouFourClerkNum;
    }

    public void setXianyouFourClerkNum(String xianyouFourClerkNum) {
        this.xianyouFourClerkNum = xianyouFourClerkNum;
    }

    public String getXianyouThreeFourJunZhuanNum() {
        return xianyouThreeFourJunZhuanNum;
    }

    public void setXianyouThreeFourJunZhuanNum(String xianyouThreeFourJunZhuanNum) {
        this.xianyouThreeFourJunZhuanNum = xianyouThreeFourJunZhuanNum;
    }

    public String getNijinshengzhengke() {
        return nijinshengzhengke;
    }

    public void setNijinshengzhengke(String nijinshengzhengke) {
        this.nijinshengzhengke = nijinshengzhengke;
    }

    public String getNijinshengfuke() {
        return nijinshengfuke;
    }

    public void setNijinshengfuke(String nijinshengfuke) {
        this.nijinshengfuke = nijinshengfuke;
    }

    public String getNijinshengganbu() {
        return nijinshengganbu;
    }

    public void setNijinshengganbu(String nijinshengganbu) {
        this.nijinshengganbu = nijinshengganbu;
    }

    public String getNijinshengoneClerkNum() {
        return nijinshengoneClerkNum;
    }

    public void setNijinshengoneClerkNum(String nijinshengoneClerkNum) {
        this.nijinshengoneClerkNum = nijinshengoneClerkNum;
    }

    public String getNijinshengtowClerkNum() {
        return nijinshengtowClerkNum;
    }

    public void setNijinshengtowClerkNum(String nijinshengtowClerkNum) {
        this.nijinshengtowClerkNum = nijinshengtowClerkNum;
    }

    public String getNijinshengThreeClerkNum() {
        return nijinshengThreeClerkNum;
    }

    public void setNijinshengThreeClerkNum(String nijinshengThreeClerkNum) {
        this.nijinshengThreeClerkNum = nijinshengThreeClerkNum;
    }

    public String getNijinshengFourClerkNum() {
        return nijinshengFourClerkNum;
    }

    public void setNijinshengFourClerkNum(String nijinshengFourClerkNum) {
        this.nijinshengFourClerkNum = nijinshengFourClerkNum;
    }

    public String getNijinshengJianZhioneClerkNum() {
        return nijinshengJianZhioneClerkNum;
    }

    public void setNijinshengJianZhioneClerkNum(String nijinshengJianZhioneClerkNum) {
        this.nijinshengJianZhioneClerkNum = nijinshengJianZhioneClerkNum;
    }

    public String getNijinshengJiantowClerkNum() {
        return nijinshengJiantowClerkNum;
    }

    public void setNijinshengJiantowClerkNum(String nijinshengJiantowClerkNum) {
        this.nijinshengJiantowClerkNum = nijinshengJiantowClerkNum;
    }

    public String getNijinshengJianThreeClerkNum() {
        return nijinshengJianThreeClerkNum;
    }

    public void setNijinshengJianThreeClerkNum(String nijinshengJianThreeClerkNum) {
        this.nijinshengJianThreeClerkNum = nijinshengJianThreeClerkNum;
    }

    public String getZhengkeGaitowClerkNum() {
        return zhengkeGaitowClerkNum;
    }

    public void setZhengkeGaitowClerkNum(String zhengkeGaitowClerkNum) {
        this.zhengkeGaitowClerkNum = zhengkeGaitowClerkNum;
    }

    public String getFukeGaiFourClerkNum() {
        return fukeGaiFourClerkNum;
    }

    public void setFukeGaiFourClerkNum(String fukeGaiFourClerkNum) {
        this.fukeGaiFourClerkNum = fukeGaiFourClerkNum;
    }

    public String getTiaozhengzhengke() {
        return tiaozhengzhengke;
    }

    public void setTiaozhengzhengke(String tiaozhengzhengke) {
        this.tiaozhengzhengke = tiaozhengzhengke;
    }

    public String getTiaozhengfuke() {
        return tiaozhengfuke;
    }

    public void setTiaozhengfuke(String tiaozhengfuke) {
        this.tiaozhengfuke = tiaozhengfuke;
    }

    public String getTiaozhengganbu() {
        return tiaozhengganbu;
    }

    public void setTiaozhengganbu(String tiaozhengganbu) {
        this.tiaozhengganbu = tiaozhengganbu;
    }

    public String getTiaozhengoneTowClerkNum() {
        return tiaozhengoneTowClerkNum;
    }

    public void setTiaozhengoneTowClerkNum(String tiaozhengoneTowClerkNum) {
        this.tiaozhengoneTowClerkNum = tiaozhengoneTowClerkNum;
    }

    public String getTiaozhengoneClerkNum() {
        return tiaozhengoneClerkNum;
    }

    public void setTiaozhengoneClerkNum(String tiaozhengoneClerkNum) {
        this.tiaozhengoneClerkNum = tiaozhengoneClerkNum;
    }

    public String getTiaozhengtowClerkNum() {
        return tiaozhengtowClerkNum;
    }

    public void setTiaozhengtowClerkNum(String tiaozhengtowClerkNum) {
        this.tiaozhengtowClerkNum = tiaozhengtowClerkNum;
    }

    public String getTiaozhengOneTowJunZhuanNum() {
        return tiaozhengOneTowJunZhuanNum;
    }

    public void setTiaozhengOneTowJunZhuanNum(String tiaozhengOneTowJunZhuanNum) {
        this.tiaozhengOneTowJunZhuanNum = tiaozhengOneTowJunZhuanNum;
    }

    public String getTiaozhenghreeFourClerkNum() {
        return tiaozhenghreeFourClerkNum;
    }

    public void setTiaozhenghreeFourClerkNum(String tiaozhenghreeFourClerkNum) {
        this.tiaozhenghreeFourClerkNum = tiaozhenghreeFourClerkNum;
    }

    public String getTiaozhengThreeClerkNum() {
        return tiaozhengThreeClerkNum;
    }

    public void setTiaozhengThreeClerkNum(String tiaozhengThreeClerkNum) {
        this.tiaozhengThreeClerkNum = tiaozhengThreeClerkNum;
    }

    public String getTiaozhengFourClerkNum() {
        return tiaozhengFourClerkNum;
    }

    public void setTiaozhengFourClerkNum(String tiaozhengFourClerkNum) {
        this.tiaozhengFourClerkNum = tiaozhengFourClerkNum;
    }

    public String getTiaozhengThreeFourJunZhuanNum() {
        return tiaozhengThreeFourJunZhuanNum;
    }

    public void setTiaozhengThreeFourJunZhuanNum(String tiaozhengThreeFourJunZhuanNum) {
        this.tiaozhengThreeFourJunZhuanNum = tiaozhengThreeFourJunZhuanNum;
    }

    public List<RankModel> getRankModels() {
        return rankModels;
    }

    public void setRankModels(List<RankModel> rankModels) {
        this.rankModels = rankModels;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getNowDateStr() {
        return nowDateStr;
    }

    public void setNowDateStr(String nowDateStr) {
        this.nowDateStr = nowDateStr;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(String peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
