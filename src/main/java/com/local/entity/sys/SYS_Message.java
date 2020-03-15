package com.local.entity.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;

@ApiModel("通知信息表")//用在模型类上，对模型类做注释；
@Table("sys_message")
@Comment("通知信息表")
public class SYS_Message implements Serializable {
    @Name
    @ApiModelProperty("id")//用在属性上，对属性做注释
    @Comment("id")//定义脚本中添加comment属性来添加注释
    @Column("id")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    private String id;

    @ApiModelProperty("标题")
    @Comment("标题")
    @Column("name")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String name;

    @ApiModelProperty("消息")
    @Comment("消息")
    @Column("param")
    @ColDefine(type = ColType.TEXT)
    private String param;

    @ApiModelProperty("排序")
    @Comment("排序")
    @Column("order_Num")
    @ColDefine(type = ColType.INT, width = 6)
    private Integer orderNum;

    @ApiModelProperty("状态：0：在用；1:停用")
    @Comment("状态：0：在用；1:停用")
    @Column("states")
    @ColDefine(type = ColType.VARCHAR, width = 8)
    private String states;

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }
}
