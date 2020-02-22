package com.local.model;

import lombok.Data;

import java.util.Date;

/**
 * 任免表
 */
@Data
public class ReimbursementModel {
    private String name;
    private String sex;
    private String years;
    private String birthplace;//籍贯
    private String nationality;//民族
    private String party;
    private String partyTime;
    private String unitAndDuty;//工作单位及职务职级
    private String workday;
    private String fullTimeEducation;
    private String fullTimeSchool;
    private String workEducation;
    private String workSchool;
    private String dutyAndRank;//现职务层次和职级
    private String dutyAndRankTime;//任现职务职级时间
    private String level;
    private String superYears;//优秀年数
    private String competentYears;//称职年数
    private String notCompetentYears;
    private String intendedRank;//拟任职级
    private String convertYears;//年度考核折算年限
    private String deposeRank;//拟免职级

    private String nowDuty;//现  任  职  务
    private String niRenDuty;//拟  任  职  务
    private String niMianDuty;//拟  免  职  务


    public String getNowDuty() {
        return nowDuty;
    }

    public void setNowDuty(String nowDuty) {
        this.nowDuty = nowDuty;
    }

    public String getNiRenDuty() {
        return niRenDuty;
    }

    public void setNiRenDuty(String niRenDuty) {
        this.niRenDuty = niRenDuty;
    }

    public String getNiMianDuty() {
        return niMianDuty;
    }

    public void setNiMianDuty(String niMianDuty) {
        this.niMianDuty = niMianDuty;
    }

    public String getPartyTime() {
        return partyTime;
    }

    public void setPartyTime(String partyTime) {
        this.partyTime = partyTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getUnitAndDuty() {
        return unitAndDuty;
    }

    public void setUnitAndDuty(String unitAndDuty) {
        this.unitAndDuty = unitAndDuty;
    }

    public String getWorkday() {
        return workday;
    }

    public void setWorkday(String workday) {
        this.workday = workday;
    }

    public String getFullTimeEducation() {
        return fullTimeEducation;
    }

    public void setFullTimeEducation(String fullTimeEducation) {
        this.fullTimeEducation = fullTimeEducation;
    }

    public String getFullTimeSchool() {
        return fullTimeSchool;
    }

    public void setFullTimeSchool(String fullTimeSchool) {
        this.fullTimeSchool = fullTimeSchool;
    }

    public String getWorkEducation() {
        return workEducation;
    }

    public void setWorkEducation(String workEducation) {
        this.workEducation = workEducation;
    }

    public String getWorkSchool() {
        return workSchool;
    }

    public void setWorkSchool(String workSchool) {
        this.workSchool = workSchool;
    }

    public String getDutyAndRank() {
        return dutyAndRank;
    }

    public void setDutyAndRank(String dutyAndRank) {
        this.dutyAndRank = dutyAndRank;
    }

    public String getDutyAndRankTime() {
        return dutyAndRankTime;
    }

    public void setDutyAndRankTime(String dutyAndRankTime) {
        this.dutyAndRankTime = dutyAndRankTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSuperYears() {
        return superYears;
    }

    public void setSuperYears(String superYears) {
        this.superYears = superYears;
    }

    public String getCompetentYears() {
        return competentYears;
    }

    public void setCompetentYears(String competentYears) {
        this.competentYears = competentYears;
    }

    public String getNotCompetentYears() {
        return notCompetentYears;
    }

    public void setNotCompetentYears(String notCompetentYears) {
        this.notCompetentYears = notCompetentYears;
    }

    public String getIntendedRank() {
        return intendedRank;
    }

    public void setIntendedRank(String intendedRank) {
        this.intendedRank = intendedRank;
    }

    public String getConvertYears() {
        return convertYears;
    }

    public void setConvertYears(String convertYears) {
        this.convertYears = convertYears;
    }

    public String getDeposeRank() {
        return deposeRank;
    }

    public void setDeposeRank(String deposeRank) {
        this.deposeRank = deposeRank;
    }
}
