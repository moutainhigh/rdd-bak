package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class CarsPersons {
    private String personCarId;

    private String personName;

    private String personIdCard;

    private String carModel;

    private String carLicense;

    private String contractRecordId;

    private String planId;

    private Integer petrolType;

    private Integer isSigned;

    private Date createAt;

    private Date updateAt;

    private Date identifyCode;

    public String getPersonCarId() {
        return personCarId;
    }

    public void setPersonCarId(String personCarId) {
        this.personCarId = personCarId == null ? null : personCarId.trim();
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName == null ? null : personName.trim();
    }

    public String getPersonIdCard() {
        return personIdCard;
    }

    public void setPersonIdCard(String personIdCard) {
        this.personIdCard = personIdCard == null ? null : personIdCard.trim();
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel == null ? null : carModel.trim();
    }

    public String getCarLicense() {
        return carLicense;
    }

    public void setCarLicense(String carLicense) {
        this.carLicense = carLicense == null ? null : carLicense.trim();
    }

    public String getContractRecordId() {
        return contractRecordId;
    }

    public void setContractRecordId(String contractRecordId) {
        this.contractRecordId = contractRecordId == null ? null : contractRecordId.trim();
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId == null ? null : planId.trim();
    }

    public Integer getPetrolType() {
        return petrolType;
    }

    public void setPetrolType(Integer petrolType) {
        this.petrolType = petrolType;
    }

    public Integer getIsSigned() {
        return isSigned;
    }

    public void setIsSigned(Integer isSigned) {
        this.isSigned = isSigned;
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

    public Date getIdentifyCode() {
        return identifyCode;
    }

    public void setIdentifyCode(Date identifyCode) {
        this.identifyCode = identifyCode;
    }
}