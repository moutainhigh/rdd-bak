package com.cqut.czb.bn.entity.dto.appBuyCarWashService;

public class CleanServerVehicleDTO {

    //服务车辆ID（自动生成）
    private String vehicleId;

    //车主(用户)ID——发起订单用户
    private String userId;

    //车主名
    private String userName;

    //车牌号
    private String licenseNumber;

    //车辆颜色
    private String vehicleColor;

    //车系
    private String vehicleSeries;

    //服务地点
    private String serviceLocation;

    //电话号码
    private String phone;

    //优惠劵id
    private String couponId;

    //服务商品号
    private String serverId;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getServiceLocation() {
        return serviceLocation;
    }

    public void setServiceLocation(String serviceLocation) {
        this.serviceLocation = serviceLocation;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleSeries() {
        return vehicleSeries;
    }

    public void setVehicleSeries(String vehicleSeries) {
        this.vehicleSeries = vehicleSeries;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

}
