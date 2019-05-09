package com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned;

public class CarsPersonsResultDTO {
    // 子级合同id
    private String contractId;

    // 车牌号
    private String carLicense;

    // 身份证号
    private String personId;

    // 油卡类型
    private String petrolType;

    // 套餐
    private String taoCan;

    // 姓名
    private String name;

    public CarsPersonsResultDTO() {
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

    public String getPetrolType() {
        return petrolType;
    }

    public void setPetrolType(String petrolType) {
        this.petrolType = petrolType;
    }

    public String getTaoCan() {
        return taoCan;
    }

    public void setTaoCan(String taoCan) {
        this.taoCan = taoCan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
