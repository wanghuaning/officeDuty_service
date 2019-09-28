package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;


@ApiModel("企业附件管理")
@Table("TT_SYS_ATTACH_MANAGE")
@Comment("企业附件管理")
public class TTSYSATTACHMANAGE implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("主键")
    @Comment("主键")
    @Column("ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String ID;

    @ApiModelProperty("文件名")
    @Comment("文件名")
    @Column("FILE_NAME")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String FILE_NAME;

    @ApiModelProperty("上传日期")
    @Comment("上传日期")
    @Column("UP_DATE")
    @ColDefine(type = ColType.DATE)
    private Date UP_DATE;

    @ApiModelProperty("文件类型")
    @Comment("文件类型")
    @Column("FILE_TYPE")
    @ColDefine(type = ColType.VARCHAR,width = 50)
    private String FILE_TYPE;

    @ApiModelProperty("上传单位名称")
    @Comment("上传单位名称")
    @Column("UP_COMPANY")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String UP_COMPANY;

    @ApiModelProperty("上传单位实例ID")
    @Comment("上传单位实例ID")
    @Column("UP_COMPANY_INSTANCE_ID")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String UP_COMPANY_INSTANCE_ID;

    @ApiModelProperty("年份")
    @Comment("年份")
    @Column("FILE_YEAR")
    @ColDefine(type = ColType.VARCHAR,width = 50)
    private String FILE_YEAR;

    @ApiModelProperty("文件地址")
    @Comment("文件地址")
    @Column("FILE_URL")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String FILE_URL;

    @ApiModelProperty("上传单位许可证")
    @Comment("上传单位许可证")
    @Column("UP_COMPANY_LICENSE_NO")
    @ColDefine(type = ColType.VARCHAR,width = 255)
    private String UP_COMPANY_LICENSE_NO;

    @ApiModelProperty("")
    @Comment("")
    @Column("ATTACH_TYPE_CODE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String ATTACH_TYPE_CODE;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public Date getUP_DATE() {
        return UP_DATE;
    }

    public void setUP_DATE(Date UP_DATE) {
        this.UP_DATE = UP_DATE;
    }

    public String getFILE_TYPE() {
        return FILE_TYPE;
    }

    public void setFILE_TYPE(String FILE_TYPE) {
        this.FILE_TYPE = FILE_TYPE;
    }

    public String getUP_COMPANY() {
        return UP_COMPANY;
    }

    public void setUP_COMPANY(String UP_COMPANY) {
        this.UP_COMPANY = UP_COMPANY;
    }

    public String getUP_COMPANY_INSTANCE_ID() {
        return UP_COMPANY_INSTANCE_ID;
    }

    public void setUP_COMPANY_INSTANCE_ID(String UP_COMPANY_INSTANCE_ID) {
        this.UP_COMPANY_INSTANCE_ID = UP_COMPANY_INSTANCE_ID;
    }

    public String getFILE_YEAR() {
        return FILE_YEAR;
    }

    public void setFILE_YEAR(String FILE_YEAR) {
        this.FILE_YEAR = FILE_YEAR;
    }

    public String getFILE_URL() {
        return FILE_URL;
    }

    public void setFILE_URL(String FILE_URL) {
        this.FILE_URL = FILE_URL;
    }

    public String getUP_COMPANY_LICENSE_NO() {
        return UP_COMPANY_LICENSE_NO;
    }

    public void setUP_COMPANY_LICENSE_NO(String UP_COMPANY_LICENSE_NO) {
        this.UP_COMPANY_LICENSE_NO = UP_COMPANY_LICENSE_NO;
    }

    public String getATTACH_TYPE_CODE() {
        return ATTACH_TYPE_CODE;
    }

    public void setATTACH_TYPE_CODE(String ATTACH_TYPE_CODE) {
        this.ATTACH_TYPE_CODE = ATTACH_TYPE_CODE;
    }
}
