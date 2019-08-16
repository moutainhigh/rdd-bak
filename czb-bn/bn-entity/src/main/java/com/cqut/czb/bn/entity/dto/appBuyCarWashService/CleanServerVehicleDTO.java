package com.cqut.czb.bn.entity.dto.appBuyCarWashService;

public class CleanServerVehicleDTO {

    //服务车辆ID（自动生成）
    private String vehicleId;

    //车主(用户)ID——发起订单用户
    private String userId;

    //车主名
    private String userName="小明";

    //车牌号
    private String licenseNumber="13131bbb";

    //车辆颜色
    private String vehicleColor="黄色";

    //车型
    private int vehicleType;

    //车系
    private String vehicleSeries="比亚迪m6";

    //服务地点
    private String serviceLocation="东城";

    //电话号码
    private String phone;




    //服务商品号
    private String serverId="1";

    //服务名
    private String serverName="轿车清洗";

    //服务类型
    private String serverType="1";

    //服务定价
    private Double serverPrice=200.0;

    //车辆类型
    private int serverVehicleType=1;




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

    public int getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(int vehicleType) {
        this.vehicleType = vehicleType;
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

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public Double getServerPrice() {
        return serverPrice;
    }

    public void setServerPrice(Double serverPrice) {
        this.serverPrice = serverPrice;
    }

    public int getServerVehicleType() {
        return serverVehicleType;
    }

    public void setServerVehicleType(int serverVehicleType) {
        this.serverVehicleType = serverVehicleType;
    }
}
