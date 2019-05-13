package com.cqut.czb.bn.entity.dto.rentCar.personContractSigned;

public class CarNumAndRent {
    // 车牌号
    private String carNum;

    // 租金
    private String rent;

    // 身份证号
    private String personId;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }
}
