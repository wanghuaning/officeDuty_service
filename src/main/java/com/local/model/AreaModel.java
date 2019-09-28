package com.local.model;

import java.util.List;

public class AreaModel {

    private String id;
    private String code;
    private String name;
    private List<AreaModel> children;

    public AreaModel() {
        this.id = id;
        this.code = code;
        this.name = name;
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AreaModel> getChildren() {
        return children;
    }

    public void setChildren(List<AreaModel> children) {
        this.children = children;
    }
}
