package com.local.model;

import lombok.Data;

import java.util.Date;

@Data
public class RankModel {
    private Integer order;
    private String name;
    private String idcard;
    private Date birthday;
    private String sex;
    private String nationality;
    private Date workday;
    private String education;
    private String democracy;
    private String nowRank;
    private String newRank;
    private Date newRankTime;
    private String detail;
}
