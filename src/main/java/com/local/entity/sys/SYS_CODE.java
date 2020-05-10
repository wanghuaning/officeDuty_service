package com.local.entity.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.nutz.dao.entity.annotation.*;

import javax.persistence.GeneratedValue;
import java.io.Serializable;

@ApiModel("系统字典表")//用在模型类上，对模型类做注释；
@Table("SYS_CODE")
@Comment("系统字典表")
public class SYS_CODE implements Serializable {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "increment")
    @GeneratedValue(generator = "idGenerator")
    @ApiModelProperty("ID")//用在属性上，对属性做注释
    @Comment("id")//定义脚本中添加comment属性来添加注释
    private Integer id;

    @ApiModelProperty("名称")
    @Comment("名称")
    @Column("code_name")
    @ColDefine(type = ColType.VARCHAR,width = 128)
    private String codeName;

    @ApiModelProperty("父Id")
    @Comment("父Id")
    @Column("parent_id")
    @ColDefine(type = ColType.INT)
    private Integer parentId;

    @ApiModelProperty("排序")
    @Comment("排序")
    @Column("code_order")
    @ColDefine(type = ColType.INT)
    private Integer codeOrder;

    @ApiModelProperty("0:启用，1：删除")
    @Comment("0:启用，1：删除")
    @Column("delete_flag")
    @ColDefine(type = ColType.VARCHAR,width = 1)
    private String deleteFlag;

    private String value;

    private String label;

    private String text;

    public String getText() {
        return codeName;
    }

    public void setText(String text) {
        this.text = codeName;
    }

    public String getValue() {
        return codeName;
    }

    public void setValue(String value) {
        this.value = codeName;
    }

    public String getLabel() {
        return codeName;
    }

    public void setLabel(String label) {
        this.label = codeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getCodeOrder() {
        return codeOrder;
    }

    public void setCodeOrder(Integer codeOrder) {
        this.codeOrder = codeOrder;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
