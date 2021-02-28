package com.cqut.czb.bn.entity.dto.equityPayment;

public class CategoryAndTypeDTO {

    private String categoryId;

    private String categoryName;

    private String categoryIsHot;

    private String typeId;

    private String typeName;

    private String typeIsHot;

    private String pic;

    private String src;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCategoryIsHot() {
        return categoryIsHot;
    }

    public void setCategoryIsHot(String categoryIsHot) {
        this.categoryIsHot = categoryIsHot;
    }

    public String getTypeIsHot() {
        return typeIsHot;
    }

    public void setTypeIsHot(String typeIsHot) {
        this.typeIsHot = typeIsHot;
    }

    public String getSave_path() {
        return pic;
    }

    public void setSave_path(String pic) {
        this.pic = pic;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
