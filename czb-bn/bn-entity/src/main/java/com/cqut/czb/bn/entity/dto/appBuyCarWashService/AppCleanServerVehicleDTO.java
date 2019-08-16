package com.cqut.czb.bn.entity.dto.appBuyCarWashService;

import java.util.Date;

public class AppCleanServerVehicleDTO {
    private int count;

    private String vehicleId;

    private String userId;

    private String userName;

    private String licenseNumber;

    private String vehicleColor;

    private Byte vehicleType;

    private String vehicleSeries;

    private Date createAt;

    private Date updateAt;

    private String serviceLocation;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId == null ? null : vehicleId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber == null ? null : licenseNumber.trim();
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor == null ? null : vehicleColor.trim();
    }

    public Byte getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Byte vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleSeries() {
        return vehicleSeries;
    }

    public void setVehicleSeries(String vehicleSeries) {
        this.vehicleSeries = vehicleSeries == null ? null : vehicleSeries.trim();
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

    public String getServiceLocation() {
        return serviceLocation;
    }

    public void setServiceLocation(String serviceLocation) {
        this.serviceLocation = serviceLocation == null ? null : serviceLocation.trim();
    }
}
