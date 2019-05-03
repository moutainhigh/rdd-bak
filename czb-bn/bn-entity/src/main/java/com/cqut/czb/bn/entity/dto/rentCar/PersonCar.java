package com.cqut.czb.bn.entity.dto.rentCar;

/**
 * 人员车辆服务表实体
 */
public class PersonCar {
    /**
     * 人员车辆服务表id
     */
    private String personCarId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String personId;

    /**
     * 车辆型号
     */
    private String carType;

    /**
     * 车牌
     */
    private String carNum;

    /**
     * 所属合同记录
     */
    private String contractId;

    /**
     * 套餐id
     */
    private Integer serviceId;

    /**
     * 油卡类型
     */
    private Integer petrolType;

    /**
     * 认证码
     */
    private String identityCode;

    public String getPersonCarId() {
        return personCarId;
    }

    public void setPersonCarId(String personCarId) {
        this.personCarId = personCarId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getPetrolType() {
        return petrolType;
    }

    public void setPetrolType(Integer petrolType) {
        this.petrolType = petrolType;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }
}
