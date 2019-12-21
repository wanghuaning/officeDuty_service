package com.local.model;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class FormModel {
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
}
