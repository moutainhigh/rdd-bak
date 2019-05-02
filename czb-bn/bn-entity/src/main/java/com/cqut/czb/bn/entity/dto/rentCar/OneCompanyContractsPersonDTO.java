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
     * 租金
     */
    private double rent;

    /**
     * 是否签约
     */
    private Integer isSign;

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

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public Integer getIsSign() {
        return isSign;
    }

    public void setIsSign(Integer isSign) {
        this.isSign = isSign;
    }
}
