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
    private String birthplace;
    private String nationality;
    private String party;
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

}
