package com.cqut.czb.bn.entity.dto.OfflineRecharge;

public class IncomeInfo {
    private String infoId;

    private String userId;

    private double offlineRechargeBalance;

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getOfflineRechargeBalance() {
        return offlineRechargeBalance;
    }

    public void setOfflineRechargeBalance(double offlineRechargeBalance) {
        this.offlineRechargeBalance = offlineRechargeBalance;
    }
}
