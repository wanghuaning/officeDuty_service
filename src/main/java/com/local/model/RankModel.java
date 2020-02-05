package com.local.model;

import lombok.Data;

import java.util.Date;

public class RankModel {
    private String id;
    private Integer order;
    private String name;
    private String sex;
    private String education;
    private String birthday;
    private String workday;
    //现任职务职级
    private String renzhibumen;//任职部门名称
    private String nowDuty;
    private String tongzhiwudate;//同级职务任职时间
    private String nowRank;
    private String tongzhijiDate;//同级职级任职时间
    //拟任职务职级
    private String nirenbumen;//拟任职务职级任职部门名称
    private String nirenduty;//拟任职务职级职务名称
    private String nirenrank;//拟任职务职级职级名称

    //拟免职务职级
    private String nimianduty;//拟免职务职级职务名称
    private String nimianrank;//拟免职务职级职级名称

    private String kaoheyouxiu;//因考核优秀 缩短晋升职 级年限情况"
    private String junzhuanganbu;//军转干部、实名制管理干部

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getWorkday() {
        return workday;
    }

    public void setWorkday(String workday) {
        this.workday = workday;
    }

    public String getRenzhibumen() {
        return renzhibumen;
    }

    public void setRenzhibumen(String renzhibumen) {
        this.renzhibumen = renzhibumen;
    }

    public String getNowDuty() {
        return nowDuty;
    }

    public void setNowDuty(String nowDuty) {
        this.nowDuty = nowDuty;
    }

    public String getTongzhiwudate() {
        return tongzhiwudate;
    }

    public void setTongzhiwudate(String tongzhiwudate) {
        this.tongzhiwudate = tongzhiwudate;
    }

    public String getNowRank() {
        return nowRank;
    }

    public void setNowRank(String nowRank) {
        this.nowRank = nowRank;
    }

    public String getTongzhijiDate() {
        return tongzhijiDate;
    }

    public void setTongzhijiDate(String tongzhijiDate) {
        this.tongzhijiDate = tongzhijiDate;
    }

    public String getNirenbumen() {
        return nirenbumen;
    }

    public void setNirenbumen(String nirenbumen) {
        this.nirenbumen = nirenbumen;
    }

    public String getNirenduty() {
        return nirenduty;
    }

    public void setNirenduty(String nirenduty) {
        this.nirenduty = nirenduty;
    }

    public String getNirenrank() {
        return nirenrank;
    }

    public void setNirenrank(String nirenrank) {
        this.nirenrank = nirenrank;
    }

    public String getNimianduty() {
        return nimianduty;
    }

    public void setNimianduty(String nimianduty) {
        this.nimianduty = nimianduty;
    }

    public String getNimianrank() {
        return nimianrank;
    }

    public void setNimianrank(String nimianrank) {
        this.nimianrank = nimianrank;
    }

    public String getKaoheyouxiu() {
        return kaoheyouxiu;
    }

    public void setKaoheyouxiu(String kaoheyouxiu) {
        this.kaoheyouxiu = kaoheyouxiu;
    }

    public String getJunzhuanganbu() {
        return junzhuanganbu;
    }

    public void setJunzhuanganbu(String junzhuanganbu) {
        this.junzhuanganbu = junzhuanganbu;
    }
}
