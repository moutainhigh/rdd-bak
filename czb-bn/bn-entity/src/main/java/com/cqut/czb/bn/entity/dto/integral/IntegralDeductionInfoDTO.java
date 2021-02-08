package com.cqut.czb.bn.entity.dto.integral;

import java.util.Date;

public class IntegralDeductionInfoDTO {
    private String integralDeductionInfoId;

    private Integer deductionType;

    private Double maxDeductionAmount;

    private String commodityId;

    private Date createAt;

    private Date updateAt;

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
