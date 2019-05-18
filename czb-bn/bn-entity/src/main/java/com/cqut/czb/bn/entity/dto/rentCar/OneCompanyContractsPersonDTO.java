package com.cqut.czb.bn.entity.dto.rentCar;

public class OneCompanyContractsPersonDTO {
    /**
     * 个人姓名
     */
    private String personName;

    /**
     * 身份证号
     */
    private String carId;

    /**
     * 车牌号
     */
    private String license;

    /**
     * 这是套餐
     */
    private String rent;

    // 真实租金
    private String realRent;

    /**
     * 是否签约
     */
    private Integer isSign;

    // 员工认证码
    private String identifyCode;

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

    public String getIdentifyCode() {
        return identifyCode;
    }

    public void setIdentifyCode(String identifyCode) {
        this.identifyCode = identifyCode;
    }

    public String getRealRent() {
        return realRent;
    }

    public void setRealRent(String realRent) {
        this.realRent = realRent;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public Integer getIsSign() {
        return isSign;
    }

    public void setIsSign(Integer isSign) {
        this.isSign = isSign;
    }
}
