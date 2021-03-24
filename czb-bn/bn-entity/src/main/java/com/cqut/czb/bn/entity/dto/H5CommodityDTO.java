package com.cqut.czb.bn.entity.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Liyan
 */
public class H5CommodityDTO extends PageDTO {
    private String commodityId;
    private String commodityTitle;
    private Integer isDelete;
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date createAt;
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date updateAt;
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date startTime;
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date endTime;
    private Integer maxDeductionAmount;
    private Double salePrice;
    private Integer deductionType;

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityTitle() {
        return commodityTitle;
    }

    public void setCommodityTitle(String commodityTitle) {
        this.commodityTitle = commodityTitle;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getMaxDeductionAmount() {
        return maxDeductionAmount;
    }

    public void setMaxDeductionAmount(Integer maxDeductionAmount) {
        this.maxDeductionAmount = maxDeductionAmount;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getDeductionType() {
        return deductionType;
    }

    public void setDeductionType(Integer deductionType) {
        this.deductionType = deductionType;
    }
}
