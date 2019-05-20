package com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned;

import javax.validation.constraints.NotNull;

public class CompanySignedInfoDTO {
    // 合同记录id
    @NotNull(message = "合同记录id不能为空")
    private String contractId;

    // 合同开始时间
    @NotNull(message = "合同开始时间")
    private String startTime;

    // 企业合同开户银行
    @NotNull(message = "企业合同开户银行")
    private String bankDeposit;

    // 企业合同银行卡
    @NotNull(message = "企业合同银行卡")
    private String bankAccount;

    // 企业合同开户银行姓名
    @NotNull(message = "企业合同开户银行姓名")
    private String bankName;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getBankDeposit() {
        return bankDeposit;
    }

    public void setBankDeposit(String bankDeposit) {
        this.bankDeposit = bankDeposit;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
