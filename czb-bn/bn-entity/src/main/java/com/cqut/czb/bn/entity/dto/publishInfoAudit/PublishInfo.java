package com.cqut.czb.bn.entity.dto.publishInfoAudit;

public class PublishInfo {
    // 商品id
    private String infoId;

    // 商品名
    private String infoName;

    // 商品内容
    private String content;

    // 商品图片
    private String img;

    // 发布时间
    private String publishTime;

    // 商品价格
    private Double price;

    // 是否审核
    private Integer isExamine;

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getIsExamine() {
        return isExamine;
    }

    public void setIsExamine(Integer isExamine) {
        this.isExamine = isExamine;
    }
}
