package com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned;

public class CompanySignedPersonal {
    // 姓名
    private String name;

    // 车牌号
    private String carNum;

    // 套餐id
    private String taoCanId;

    // 身份证号
    private String personId;

    // 油卡类型
    private  Integer petrolType;

    // 合同记录id
    private String contractId;

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaoCanId() {
        return taoCanId;
    }

    public void setTaoCanId(String taoCanId) {
        this.taoCanId = taoCanId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Integer getPetrolType() {
        return petrolType;
    }

    public void setPetrolType(Integer petrolType) {
        this.petrolType = petrolType;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
}
