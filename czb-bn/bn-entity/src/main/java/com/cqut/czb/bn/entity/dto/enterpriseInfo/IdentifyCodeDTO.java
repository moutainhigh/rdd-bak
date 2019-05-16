package com.cqut.czb.bn.entity.dto.enterpriseInfo;

public class IdentifyCodeDTO {

    String identifyCode; //认证码

    String personName;

    String personIdCard;

    String carLicense;

    Integer isSigned;

    String planName;  //套餐名

    String rentBackMoney;  //返租金额

    public String getIdentifyCode() {
        return identifyCode;
    }

    public void setIdentifyCode(String identifyCode) {
        this.identifyCode = identifyCode;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonIdCard() {
        return personIdCard;
    }

    public void setPersonIdCard(String personIdCard) {
        this.personIdCard = personIdCard;
    }

    public String getCarLicense() {
        return carLicense;
    }

    public void setCarLicense(String carLicense) {
        this.carLicense = carLicense;
    }

    public Integer getIsSigned() {
        return isSigned;
    }

    public void setIsSigned(Integer isSigned) {
        this.isSigned = isSigned;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getRentBackMoney() {
        return rentBackMoney;
    }

    public void setRentBackMoney(String rentBackMoney) {
        this.rentBackMoney = rentBackMoney;
    }
}
