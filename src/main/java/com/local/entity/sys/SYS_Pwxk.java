package com.local.entity.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@ApiModel("考核信息表")//用在模型类上，对模型类做注释；
@Table("sys_pwxk")
@Comment("考核信息表")
@Data
public class SYS_Pwxk implements Serializable {
    @Name
    @ApiModelProperty("职务id")//用在属性上，对属性做注释
    @Comment("职务id")//定义脚本中添加comment属性来添加注释
    @Column("id")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String id;

    @ApiModelProperty("职务名称")
    @Comment("职务名称")
    @Column("name")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String name;

    @ApiModelProperty("职务名称")
    @Comment("职务名称")
    @Column("code")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String code;

    @ApiModelProperty("任职时间")
    @Comment("任职时间")
    @Column("createDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ColDefine(type = ColType.DATETIME)
    private Date createDate;

    @ApiModelProperty("职务名称")
    @Comment("职务名称")
    @Column("ornum")
    @ColDefine(type = ColType.INT, width = 4)
    private String ornum;
    @ApiModelProperty("职务名称")
    @Comment("职务名称")
    @Column("isCode")
    @ColDefine(type = ColType.INT, width = 4)
    private String isCode;
}
