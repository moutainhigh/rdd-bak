package com.cqut.czb.bn.entity.dto.equityPayment;

import java.util.Date;

public class EquityPaymentAreaClothingDTO {
    private String areaId;

    private String gameName;

    private String areaName;

    private Date createAt;

    private Date updateAt;

    private String productCode;

    private String districtServiceName;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDistrictServiceName() {
        return districtServiceName;
    }

    public void setDistrictServiceName(String districtServiceName) {
        this.districtServiceName = districtServiceName;
    }
}
