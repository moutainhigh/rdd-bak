package com.cqut.czb.bn.entity.dto.rentCar.personContractSigned;

import javax.validation.constraints.NotNull;

public class PersonBankInfo {

    /**
     * 合同记录id
     */
    @NotNull(message = "合同记录id不能为空")
    private String contractId;

    // 开户银行
    @NotNull(message = "开户银行不能为空")
    private String bankDeposit;

    // 银行账号
    @NotNull(message = "银行账号不能为空")
    private String bankAccount;

    // 账户姓名
    @NotNull(message = "账户姓名不能为空")
    private String bankName;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
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
