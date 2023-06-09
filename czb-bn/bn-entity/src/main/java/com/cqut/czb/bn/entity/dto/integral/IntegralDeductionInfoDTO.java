package com.cqut.czb.bn.entity.dto.integral;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class IntegralDeductionInfoDTO {
    private String integralDeductionInfoId;

    private Integer deductionType;

    private Double maxDeductionAmount;

    private String commodityId;

    private String commodityAttrId;

    private String commodityTitle;

    private Date createAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;

    private String beginDate;

    private String overDate;

    private String attributeId;

    private String extraSaleMoney;

    private String salePrice;

    private String commodityAttr;

    public String getIntegralDeductionInfoId() {
        return integralDeductionInfoId;
    }

    public void setIntegralDeductionInfoId(String integralDeductionInfoId) {
        this.integralDeductionInfoId = integralDeductionInfoId == null ? null : integralDeductionInfoId.trim();
    }

    public Integer getDeductionType() {
        return deductionType;
    }

    public void setDeductionType(Integer deductionType) {
        this.deductionType = deductionType;
    }

    public Double getMaxDeductionAmount() {
        return maxDeductionAmount;
    }

    public void setMaxDeductionAmount(Double maxDeductionAmount) {
        this.maxDeductionAmount = maxDeductionAmount;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId == null ? null : commodityId.trim();
    }

    public String getCommodityAttrId() {
        return commodityAttrId;
    }

    public void setCommodityAttrId(String commodityAttrId) {
        this.commodityAttrId = commodityAttrId;
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

    public String getCommodityTitle() {
        return commodityTitle;
    }

    public void setCommodityTitle(String commodityTitle) {
        this.commodityTitle = commodityTitle;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getOverDate() {
        return overDate;
    }

    public void setOverDate(String overDate) {
        this.overDate = overDate;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getExtraSaleMoney() {
        return extraSaleMoney;
    }

    public void setExtraSaleMoney(String extraSaleMoney) {
        this.extraSaleMoney = extraSaleMoney;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getCommodityAttr() {
        return commodityAttr;
    }

    public void setCommodityAttr(String commodityAttr) {
        this.commodityAttr = commodityAttr;
    }
}
