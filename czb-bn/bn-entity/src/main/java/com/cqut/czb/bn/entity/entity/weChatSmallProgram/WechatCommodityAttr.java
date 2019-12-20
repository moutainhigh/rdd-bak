package com.cqut.czb.bn.entity.entity.weChatSmallProgram;

import java.util.Date;

public class WechatCommodityAttr {
    private String commodityAttrId;

    private Double extraFyMoney;

    private String commodityId;

    private String attributeId;

    private Double extraSaleMoney;

    private String fileId;

    private Integer attrOrder;

    private Date createAt;

    private Date updateAt;

    public String getCommodityAttrId() {
        return commodityAttrId;
    }

    public void setCommodityAttrId(String commodityAttrId) {
        this.commodityAttrId = commodityAttrId == null ? null : commodityAttrId.trim();
    }

    public Double getExtraFyMoney() {
        return extraFyMoney;
    }

    public void setExtraFyMoney(Double extraFyMoney) {
        this.extraFyMoney = extraFyMoney;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId == null ? null : commodityId.trim();
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId == null ? null : attributeId.trim();
    }

    public Double getExtraSaleMoney() {
        return extraSaleMoney;
    }

    public void setExtraSaleMoney(Double extraSaleMoney) {
        this.extraSaleMoney = extraSaleMoney;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public Integer getAttrOrder() {
        return attrOrder;
    }

    public void setAttrOrder(Integer attrOrder) {
        this.attrOrder = attrOrder;
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