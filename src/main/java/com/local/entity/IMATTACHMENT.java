package com.local.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

@ApiModel("附件信息表")
@Table("IM_ATTACHMENT")
@Comment("附件信息表")
public class IMATTACHMENT implements Serializable {

    @Name
    @Prev(els = {@EL("uuid")})
    @ApiModelProperty("主键ID")
    @Comment("主键ID")
    @Column("SNO")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String SNO;

    @ApiModelProperty("业务ID")
    @Comment("业务ID")
    @Column("ID")
    @ColDefine(type = ColType.VARCHAR,width = 80)
    private String ID;

    @ApiModelProperty("保存名称")
    @Comment("保存名称")
    @Column("SAVEFILE")
    @ColDefine(type = ColType.VARCHAR,width = 128)
    private String SAVEFILE;

    @ApiModelProperty("文件名称")
    @Comment("文件名称")
    @Column("FILENAME")
    @ColDefine(type = ColType.VARCHAR,width = 128)
    private String FILENAME;

    @ApiModelProperty("文件大小")
    @Comment("文件大小")
    @Column("FILESIZE")
    @ColDefine(type = ColType.INT)
    private Integer FILESIZE;

    @ApiModelProperty("扩展名")
    @Comment("扩展名")
    @Column("EXTENDNAME")
    @ColDefine(type = ColType.VARCHAR,width = 128)
    private String EXTENDNAME;

    @ApiModelProperty("附件类型")
    @Comment("附件类型")
    @Column("FILE_TYPE")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String FILE_TYPE;

    @ApiModelProperty("上传人")
    @Comment("上传人")
    @Column("UPLOAD_PERSON")
    @ColDefine(type = ColType.VARCHAR,width = 64)
    private String UPLOAD_PERSON;

    @ApiModelProperty("上传时间")
    @Comment("上传时间")
    @Column("UPLOAD_DATE")
    @ColDefine(type = ColType.DATE)
    private Date UPLOAD_DATE;

    public String getSNO() {
        return SNO;
    }

    public void setSNO(String SNO) {
        this.SNO = SNO;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSAVEFILE() {
        return SAVEFILE;
    }

    public void setSAVEFILE(String SAVEFILE) {
        this.SAVEFILE = SAVEFILE;
    }

    public String getFILENAME() {
        return FILENAME;
    }

    public void setFILENAME(String FILENAME) {
        this.FILENAME = FILENAME;
    }

    public Integer getFILESIZE() {
        return FILESIZE;
    }

    public void setFILESIZE(Integer FILESIZE) {
        this.FILESIZE = FILESIZE;
    }

    public String getEXTENDNAME() {
        return EXTENDNAME;
    }

    public void setEXTENDNAME(String EXTENDNAME) {
        this.EXTENDNAME = EXTENDNAME;
    }

    public String getFILE_TYPE() {
        return FILE_TYPE;
    }

    public void setFILE_TYPE(String FILE_TYPE) {
        this.FILE_TYPE = FILE_TYPE;
    }

    public String getUPLOAD_PERSON() {
        return UPLOAD_PERSON;
    }

    public void setUPLOAD_PERSON(String UPLOAD_PERSON) {
        this.UPLOAD_PERSON = UPLOAD_PERSON;
    }

    public Date getUPLOAD_DATE() {
        return UPLOAD_DATE;
    }

    public void setUPLOAD_DATE(Date UPLOAD_DATE) {
        this.UPLOAD_DATE = UPLOAD_DATE;
    }
}
