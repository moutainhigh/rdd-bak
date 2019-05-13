package com.cqut.czb.bn.entity.dto.rentCar.personContractSigned;

public class CarNumAndRent {
    // 车牌号
    private String carNum;

    // 租金
    private String rent;

    // 身份证号
    private String personId;

    // 是否使用
    private int ifUsed;

    public int getIfUsed() {
        return ifUsed;
    }

    public void setIfUsed(int ifUsed) {
        this.ifUsed = ifUsed;
    }

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
