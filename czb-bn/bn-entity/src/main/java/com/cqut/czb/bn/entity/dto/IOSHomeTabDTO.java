package com.cqut.czb.bn.entity.dto;

/**
 * @Description
 * @auther nihao
 * @create 2019-08-26 10:10
 */
public class IOSHomeTabDTO {
    private String tabName;

    private String image;

    private String selectedImage;

    private String identifierName;

    public String getIdentifierName() {
        return identifierName;
    }

    public void setIdentifierName(String identifierName) {
        this.identifierName = identifierName;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(String selectedImage) {
        this.selectedImage = selectedImage;
    }
}