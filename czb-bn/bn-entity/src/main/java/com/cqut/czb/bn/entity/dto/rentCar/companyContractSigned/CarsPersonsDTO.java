package com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned;

public class CarsPersonsDTO {
    // 子级合同id
    private String contractId;

    // 车牌号
    private String carLicense;

    // 身份证号
    private String personId;

    // 油卡类型
    private int petrolType;

    // 套餐id
    private String taoCanId;

    // 姓名
    private String name;

    // 开户银行
    private String bankDeposit;

    // 银行账号
    private String bankAccount;

    // 账户姓名
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

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getCarLicense() {
        return carLicense;
    }

    public void setCarLicense(String carLicense) {
        this.carLicense = carLicense;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public int getPetrolType() {
        return petrolType;
    }

    public void setPetrolType(int petrolType) {
        this.petrolType = petrolType;
    }

    public String getTaoCanId() {
        return taoCanId;
    }

    public void setTaoCanId(String taoCanId) {
        this.taoCanId = taoCanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
