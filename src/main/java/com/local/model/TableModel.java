package com.local.model;

import lombok.Data;

@Data
public class TableModel {

    private String prop;
    private String label;

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
