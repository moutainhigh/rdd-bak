package com.cqut.czb.bn.entity.dto.vehicleService;

import java.util.Date;

public class ServerCouponDTO {
    private String couponId;

    private Date destroyTime;

    private String couponStandard;

    private String ownerId;

    private Integer status;

    private Double standardValue;

    private String standardExplain;

    private Date createAt;

    private Date updateAt;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public Date getDestroyTime() {
        return destroyTime;
    }

    public void setDestroyTime(Date destroyTime) {
        this.destroyTime = destroyTime;
    }

    public String getCouponStandard() {
        return couponStandard;
    }

    public void setCouponStandard(String couponStandard) {
        this.couponStandard = couponStandard;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(Double standardValue) {
        this.standardValue = standardValue;
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
