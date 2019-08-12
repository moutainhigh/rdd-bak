package com.cqut.czb.bn.entity.entity.vehicleService;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CouponStandard {
    private String standardId;

    private String standardType;

    private Double standardValue;

    private Integer continueDays;

    private String standardExplain;

    private Date createAt;

    private Date updateAt;

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getStandardType() {
        return standardType;
    }

    public void setStandardType(String standardType) {
        this.standardType = standardType;
    }

    public Double getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(Double standardValue) {
        this.standardValue = standardValue;
    }

    public Integer getContinueDays() {
        return continueDays;
    }

    public void setContinueDays(Integer continueDays) {
        this.continueDays = continueDays;
    }

    public String getStandardExplain() {
        return standardExplain;
    }

    public void setStandardExplain(String standardExplain) {
        this.standardExplain = standardExplain;
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