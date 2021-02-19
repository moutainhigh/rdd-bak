package com.cqut.czb.bn.entity.dto.integral;

import java.util.Date;

public class IntegralInfoDTO {
    private String integralInfoId;

    private String userId;

    private String userAccount;

    private Integer gotTotal;

    private Integer getConsumeTotal;

    private Integer getOfferTotal;

    private Integer currentTotal;

    private Date createAt;

    private Date updateAt;

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

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public Integer getGotTotal() {
        return gotTotal;
    }

    public void setGotTotal(Integer gotTotal) {
        this.gotTotal = gotTotal;
    }

    public Integer getGetConsumeTotal() {
        return getConsumeTotal;
    }

    public void setGetConsumeTotal(Integer getConsumeTotal) {
        this.getConsumeTotal = getConsumeTotal;
    }

    public Integer getGetOfferTotal() {
        return getOfferTotal;
    }

    public void setGetOfferTotal(Integer getOfferTotal) {
        this.getOfferTotal = getOfferTotal;
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
}
