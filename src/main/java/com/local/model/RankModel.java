package com.local.model;

import lombok.Data;

import java.util.Date;

@Data
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

}
