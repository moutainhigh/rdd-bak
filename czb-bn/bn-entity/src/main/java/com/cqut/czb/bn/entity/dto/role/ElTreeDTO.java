package com.cqut.czb.bn.entity.dto.role;

import java.util.List;

public class ElTreeDTO {

    private String label;

    private String id;

    private String value;

    private String parentId;

    private List<ElTreeDTO> children;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ElTreeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<ElTreeDTO> children) {
        this.children = children;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
