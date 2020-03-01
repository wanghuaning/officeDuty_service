package com.local.model;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class FormModel {

    private Integer oneYear=0;
    private Integer towYear=0;
    private Integer threeYear=0;
    private Integer fourYear=0;
    private Integer fiveYear=0;

    private Integer bianZhiNum=0;
    private Integer incumbent=0;//在职人数
    private Integer leavePeople=0;
    private Integer turnPeople=0;
    private Integer party =0;
    private Integer notParty=0;
    private Integer man2=0;
    private Integer woman2=0;
    private Integer man3=0;
    private Integer woman3=0;
    private Integer man4=0;
    private Integer woman4=0;
    private Integer man5=0;
    private Integer woman5=0;
    private Integer man6=0;
    private Integer woman6=0;
    private Integer sex=0;
    private Integer fuKe=0;
    private Integer zhengKe=0;
    private Integer fuChu=0;
    private Integer zhengChu=0;
    private Integer qita=0;

    private Integer oneYearYouXiu=0;
    private Integer towYearYouXiu=0;
    private Integer threeYearYouXiu=0;
    private Integer fourYearYouXiu=0;
    private Integer fiveYearYouXiu=0;
    private Integer oneYearHeGe=0;
    private Integer towYearHeGe=0;
    private Integer threeYearHeGe=0;
    private Integer fourYearHeGe=0;
    private Integer fiveYearHeGe=0;
    private Integer oneYearNotGe=0;
    private Integer towYearNotGe=0;
    private Integer threeYearNotGe=0;
    private Integer fourYearNotGe=0;
    private Integer fiveYearNotGe=0;
    private FormRankModel formRankModel;

    public Integer getBianZhiNum() {
        return bianZhiNum;
    }

    public void setBianZhiNum(Integer bianZhiNum) {
        this.bianZhiNum = bianZhiNum;
    }

    public Integer getOneYear() {
        return oneYear;
    }

    public void setOneYear(Integer oneYear) {
        this.oneYear = oneYear;
    }

    public Integer getTowYear() {
        return towYear;
    }

    public void setTowYear(Integer towYear) {
        this.towYear = towYear;
    }

    public Integer getThreeYear() {
        return threeYear;
    }

    public void setThreeYear(Integer threeYear) {
        this.threeYear = threeYear;
    }

    public Integer getFourYear() {
        return fourYear;
    }

    public void setFourYear(Integer fourYear) {
        this.fourYear = fourYear;
    }

    public Integer getFiveYear() {
        return fiveYear;
    }

    public void setFiveYear(Integer fiveYear) {
        this.fiveYear = fiveYear;
    }

    public Integer getIncumbent() {
        return incumbent;
    }

    public void setIncumbent(Integer incumbent) {
        this.incumbent = incumbent;
    }

    public Integer getLeavePeople() {
        return leavePeople;
    }

    public void setLeavePeople(Integer leavePeople) {
        this.leavePeople = leavePeople;
    }

    public Integer getTurnPeople() {
        return turnPeople;
    }

    public void setTurnPeople(Integer turnPeople) {
        this.turnPeople = turnPeople;
    }

    public Integer getParty() {
        return party;
    }

    public void setParty(Integer party) {
        this.party = party;
    }

    public Integer getNotParty() {
        return notParty;
    }

    public void setNotParty(Integer notParty) {
        this.notParty = notParty;
    }

    public Integer getMan2() {
        return man2;
    }

    public void setMan2(Integer man2) {
        this.man2 = man2;
    }

    public Integer getWoman2() {
        return woman2;
    }

    public void setWoman2(Integer woman2) {
        this.woman2 = woman2;
    }

    public Integer getMan3() {
        return man3;
    }

    public void setMan3(Integer man3) {
        this.man3 = man3;
    }

    public Integer getWoman3() {
        return woman3;
    }

    public void setWoman3(Integer woman3) {
        this.woman3 = woman3;
    }

    public Integer getMan4() {
        return man4;
    }

    public void setMan4(Integer man4) {
        this.man4 = man4;
    }

    public Integer getWoman4() {
        return woman4;
    }

    public void setWoman4(Integer woman4) {
        this.woman4 = woman4;
    }

    public Integer getMan5() {
        return man5;
    }

    public void setMan5(Integer man5) {
        this.man5 = man5;
    }

    public Integer getWoman5() {
        return woman5;
    }

    public void setWoman5(Integer woman5) {
        this.woman5 = woman5;
    }

    public Integer getMan6() {
        return man6;
    }

    public void setMan6(Integer man6) {
        this.man6 = man6;
    }

    public Integer getWoman6() {
        return woman6;
    }

    public void setWoman6(Integer woman6) {
        this.woman6 = woman6;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getFuKe() {
        return fuKe;
    }

    public void setFuKe(Integer fuKe) {
        this.fuKe = fuKe;
    }

    public Integer getZhengKe() {
        return zhengKe;
    }

    public void setZhengKe(Integer zhengKe) {
        this.zhengKe = zhengKe;
    }

    public Integer getFuChu() {
        return fuChu;
    }

    public void setFuChu(Integer fuChu) {
        this.fuChu = fuChu;
    }

    public Integer getZhengChu() {
        return zhengChu;
    }

    public void setZhengChu(Integer zhengChu) {
        this.zhengChu = zhengChu;
    }

    public Integer getQita() {
        return qita;
    }

    public void setQita(Integer qita) {
        this.qita = qita;
    }

    public Integer getOneYearYouXiu() {
        return oneYearYouXiu;
    }

    public void setOneYearYouXiu(Integer oneYearYouXiu) {
        this.oneYearYouXiu = oneYearYouXiu;
    }

    public Integer getTowYearYouXiu() {
        return towYearYouXiu;
    }

    public void setTowYearYouXiu(Integer towYearYouXiu) {
        this.towYearYouXiu = towYearYouXiu;
    }

    public Integer getThreeYearYouXiu() {
        return threeYearYouXiu;
    }

    public void setThreeYearYouXiu(Integer threeYearYouXiu) {
        this.threeYearYouXiu = threeYearYouXiu;
    }

    public Integer getFourYearYouXiu() {
        return fourYearYouXiu;
    }

    public void setFourYearYouXiu(Integer fourYearYouXiu) {
        this.fourYearYouXiu = fourYearYouXiu;
    }

    public Integer getFiveYearYouXiu() {
        return fiveYearYouXiu;
    }

    public void setFiveYearYouXiu(Integer fiveYearYouXiu) {
        this.fiveYearYouXiu = fiveYearYouXiu;
    }

    public Integer getOneYearHeGe() {
        return oneYearHeGe;
    }

    public void setOneYearHeGe(Integer oneYearHeGe) {
        this.oneYearHeGe = oneYearHeGe;
    }

    public Integer getTowYearHeGe() {
        return towYearHeGe;
    }

    public void setTowYearHeGe(Integer towYearHeGe) {
        this.towYearHeGe = towYearHeGe;
    }

    public Integer getThreeYearHeGe() {
        return threeYearHeGe;
    }

    public void setThreeYearHeGe(Integer threeYearHeGe) {
        this.threeYearHeGe = threeYearHeGe;
    }

    public Integer getFourYearHeGe() {
        return fourYearHeGe;
    }

    public void setFourYearHeGe(Integer fourYearHeGe) {
        this.fourYearHeGe = fourYearHeGe;
    }

    public Integer getFiveYearHeGe() {
        return fiveYearHeGe;
    }

    public void setFiveYearHeGe(Integer fiveYearHeGe) {
        this.fiveYearHeGe = fiveYearHeGe;
    }

    public Integer getOneYearNotGe() {
        return oneYearNotGe;
    }

    public void setOneYearNotGe(Integer oneYearNotGe) {
        this.oneYearNotGe = oneYearNotGe;
    }

    public Integer getTowYearNotGe() {
        return towYearNotGe;
    }

    public void setTowYearNotGe(Integer towYearNotGe) {
        this.towYearNotGe = towYearNotGe;
    }

    public Integer getThreeYearNotGe() {
        return threeYearNotGe;
    }

    public void setThreeYearNotGe(Integer threeYearNotGe) {
        this.threeYearNotGe = threeYearNotGe;
    }

    public Integer getFourYearNotGe() {
        return fourYearNotGe;
    }

    public void setFourYearNotGe(Integer fourYearNotGe) {
        this.fourYearNotGe = fourYearNotGe;
    }

    public Integer getFiveYearNotGe() {
        return fiveYearNotGe;
    }

    public void setFiveYearNotGe(Integer fiveYearNotGe) {
        this.fiveYearNotGe = fiveYearNotGe;
    }

    public FormRankModel getFormRankModel() {
        return formRankModel;
    }

    public void setFormRankModel(FormRankModel formRankModel) {
        this.formRankModel = formRankModel;
    }
}
