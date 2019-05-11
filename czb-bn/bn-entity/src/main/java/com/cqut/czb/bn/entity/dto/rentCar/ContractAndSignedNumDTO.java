package com.cqut.czb.bn.entity.dto.rentCar;

public class ContractAndSignedNumDTO {
    /**
     * 企业第三方合同编号
     */
    private String carNum;

    /**
     * 个人已签订数
     */
    private String signedCarNum;

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getSignedCarNum() {
        return signedCarNum;
    }

    public void setSignedCarNum(String signedCarNum) {
        this.signedCarNum = signedCarNum;
    }
}
