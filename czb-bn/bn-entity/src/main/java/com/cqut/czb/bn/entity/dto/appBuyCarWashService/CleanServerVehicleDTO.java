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

    //车型
    private int vehicleType;

    //车系
    private String vehicleSeries;

    //服务商品号
    private String service_id;

    //服务名
    private String serverName;

    //服务类型
    private String serverType;

    //服务定价
    private Double serverPrice;

    //会员价(新增)
    private Double vipPrice;

    //服务说明
    private String serverExplain;

    //折扣
    private Double serverDiscount;

    //车辆类型
    private int serverVehicleType;

    public String getVehicleId() {
        return vehicleId;
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

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
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

    public Double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Double vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getServerExplain() {
        return serverExplain;
    }

    public void setServerExplain(String serverExplain) {
        this.serverExplain = serverExplain;
    }

    public Double getServerDiscount() {
        return serverDiscount;
    }

    public void setServerDiscount(Double serverDiscount) {
        this.serverDiscount = serverDiscount;
    }

    public int getServerVehicleType() {
        return serverVehicleType;
    }

    public void setServerVehicleType(int serverVehicleType) {
        this.serverVehicleType = serverVehicleType;
    }
}
