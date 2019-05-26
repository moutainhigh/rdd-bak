package com.cqut.czb.bn.entity.dto.rentCar;

import javax.validation.constraints.NotNull;

public class PersonSignedInputInfo {
    /**
     * 认证码
     */
    @NotNull(message = "认证码不能为空")
    private String identifyCode;

    /**
     * 身份证
     */
    @NotNull(message = "身份证号码不能为空")
    private String personId;

    // 开户银行
    @NotNull(message = "开户银行不能为空")
    private String bankDeposit;

    // 银行账号
    @NotNull(message = "银行账号不能为空")
    private String bankAccount;

    // 账户姓名
    @NotNull(message = "账户姓名不能为空")
    private String bankName;

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

    public String getIdentifyCode() {
        return identifyCode;
    }

    public void setIdentifyCode(String identifyCode) {
        this.identifyCode = identifyCode;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
