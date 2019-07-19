package com.cqut.czb.bn.entity.dto.rentCar.personContractSigned;

public class PersonBankInfo {

    /**
     * 合同记录id
     */
    private String contractId;

    // 开户银行
    private String bankDeposit;

    // 银行账号
    private String bankAccount;

    // 账户姓名
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
