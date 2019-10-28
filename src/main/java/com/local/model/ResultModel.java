package com.local.model;

import java.util.List;

public class ResultModel {
    private String id;
    private String name;
    private String label;
    private String value;
    private String key;
    private List<ResultModel> children;

    public List<ResultModel> getChildren() {
        return children;
    }
    public void setChildren(List<ResultModel> children) {
        this.children = children;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
