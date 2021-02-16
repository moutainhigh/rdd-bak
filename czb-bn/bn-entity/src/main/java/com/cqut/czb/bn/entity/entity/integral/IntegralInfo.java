package com.cqut.czb.bn.entity.entity.integral;

import java.util.Date;

public class IntegralInfo {
    private String integralInfoId;

    private String userId;

    private Integer gotTotal;

    private Integer currentTotal;

    private Date createAt;

    private Date updateAt;

    // CAS版本控制
    private Date oldUpdateAt;

    public String getIntegralInfoId() {
        return integralInfoId;
    }

    public void setIntegralInfoId(String integralInfoId) {
        this.integralInfoId = integralInfoId == null ? null : integralInfoId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getGotTotal() {
        return gotTotal;
    }

    public void setGotTotal(Integer gotTotal) {
        this.gotTotal = gotTotal;
    }

    public Integer getCurrentTotal() {
        return currentTotal;
    }

    public void setCurrentTotal(Integer currentTotal) {
        this.currentTotal = currentTotal;
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

    public Date getOldUpdateAt() {
        return oldUpdateAt;
    }

    public void setOldUpdateAt(Date oldUpdateAt) {
        this.oldUpdateAt = oldUpdateAt;
    }
}
