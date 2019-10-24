package com.cqut.czb.bn.entity.dto.partnerVipIncome;

import java.util.Date;

public class PartnerVipIncomeDTO {
    private String partnerVipIncomeId;

    private String partnerId;

    private String userAccount;

    private String userName;

    private Integer partnerType;

    private Integer isSettle;

    private Integer vipAddCount;

    private Date startTime;

    private Date endTime;

    private Double vipAddIncome;

    private String settleIds;  //批量结算id串

    private Date createAt;

    private Date updateAt;

    public String getPartnerVipIncomeId() {
        return partnerVipIncomeId;
    }

    public void setPartnerVipIncomeId(String partnerVipIncomeId) {
        this.partnerVipIncomeId = partnerVipIncomeId == null ? null : partnerVipIncomeId.trim();
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId == null ? null : partnerId.trim();
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(Integer partnerType) {
        this.partnerType = partnerType;
    }

    public Integer getIsSettle() {
        return isSettle;
    }

    public void setIsSettle(Integer isSettle) {
        this.isSettle = isSettle;
    }

    public Integer getVipAddCount() {
        return vipAddCount;
    }

    public void setVipAddCount(Integer vipAddCount) {
        this.vipAddCount = vipAddCount;
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

    public Double getVipAddIncome() {
        return vipAddIncome;
    }

    public void setVipAddIncome(Double vipAddIncome) {
        this.vipAddIncome = vipAddIncome;
    }

    public String getSettleIds() {
        return settleIds;
    }

    public void setSettleIds(String settleIds) {
        this.settleIds = settleIds;
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