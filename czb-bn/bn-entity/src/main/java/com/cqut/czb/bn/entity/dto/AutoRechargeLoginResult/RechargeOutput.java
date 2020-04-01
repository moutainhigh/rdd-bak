package com.cqut.czb.bn.entity.dto.AutoRechargeLoginResult;

public class RechargeOutput {
    private String result;

    private String errorMsg;

    private String blance;

    private String batchNo;

    private String loyaltyBalance;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getBlance() {
        return blance;
    }

    public void setBlance(String blance) {
        this.blance = blance;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getLoyaltyBalance() {
        return loyaltyBalance;
    }

    public void setLoyaltyBalance(String loyaltyBalance) {
        this.loyaltyBalance = loyaltyBalance;
    }
}
