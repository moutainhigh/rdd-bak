package com.cqut.czb.bn.entity.entity.weChatSmallProgram;

import java.util.Date;

public class WechatNorms {
    private String normsId;

    private Double originPrice;

    private String normsType;

    private String normsContent;

    private Double salePrice;

    private String fileId;

    private Integer orderNum;

    private Date createAt;

    private Date updateAt;

    public String getNormsId() {
        return normsId;
    }

    public void setNormsId(String normsId) {
        this.normsId = normsId == null ? null : normsId.trim();
    }

    public Double getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Double originPrice) {
        this.originPrice = originPrice;
    }

    public String getNormsType() {
        return normsType;
    }

    public void setNormsType(String normsType) {
        this.normsType = normsType == null ? null : normsType.trim();
    }

    public String getNormsContent() {
        return normsContent;
    }

    public void setNormsContent(String normsContent) {
        this.normsContent = normsContent == null ? null : normsContent.trim();
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}