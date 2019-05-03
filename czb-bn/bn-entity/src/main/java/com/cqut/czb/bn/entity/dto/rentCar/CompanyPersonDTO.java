package com.cqut.czb.bn.entity.dto.rentCar;

public class CompanyPersonDTO {
    /**
     * 车主姓名
     */
    private String name;

    /**
     * 车牌号
     */
    private String carNum;

    /**
     * 套餐
     */
    private Integer service;

    /**
     * 身份证号
     */
    private String personId;

    /**
     * 油卡类型
     */
    private Integer petrolType;

    public CompanyPersonDTO(){

    }

    public CompanyPersonDTO(String name, String carNum, Integer service, String personId, Integer petrolType) {
        this.name = name;
        this.carNum = carNum;
        this.service = service;
        this.personId = personId;
        this.petrolType = petrolType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public Integer getService() {
        return service;
    }

    public void setService(Integer service) {
        this.service = service;
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
}
