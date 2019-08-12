package com.cqut.czb.bn.entity.entity.vehicleService;

import java.util.Date;

public class CzbServerCoupon {
    private String couponId;

    private Date destroyTime;

    private String couponStandard;

    private String ownerId;

    private Byte status;

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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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