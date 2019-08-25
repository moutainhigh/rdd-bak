package com.cqut.czb.bn.entity.dto.vehicleService;

import java.util.Date;

public class VehicleOrderManageDTO {
    // 订单id
    private String serverOrderId;
    // 用户id
    private String userId;
    // 骑手id
    private String riderId;
    // 第三方订单
    private String thirdOrder;
    // 支付状态
    private Byte payStatus;
    // 实际价格
    private Double actualPrice;
    // 车辆id
    private String vehicleId;
    // 服务状态
    private Byte processStatus;
    // 取消时间
    private Date cancelTime;
    // 取消人id
    private String cancelPersonId;
    // 取消原因
    private String cancelReason;
    // 创建时间
    private Date createAt;
    // 更新时间
    private Date updateAt;
    // 洗车服务id
    private String serverId;

    // 车主电话
    private String phone;

    // 客户姓氏
    private String firstName;

    // 车牌号
    private String carNum;

    // 车型
    private String carType;

    // 车系
    private String carXi;

    // 车身颜色
    private String color;

    // 骑手电话
    private String riderPhone;

    // 骑手名
    private String riderName;

    // 服务名称
    private String serverName;

    // 服务地点
    private String location;

    // 支付方式
    private Integer payMethod;

    VehicleOrderManageDTO() {}

    public String getLocation() {
        return location;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getServerOrderId() {
        return serverOrderId;
    }

    public void setServerOrderId(String serverOrderId) {
        this.serverOrderId = serverOrderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public String getThirdOrder() {
        return thirdOrder;
    }

    public void setThirdOrder(String thirdOrder) {
        this.thirdOrder = thirdOrder;
    }

    public Byte getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
    }

    public Double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Byte getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Byte processStatus) {
        this.processStatus = processStatus;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getCancelPersonId() {
        return cancelPersonId;
    }

    public void setCancelPersonId(String cancelPersonId) {
        this.cancelPersonId = cancelPersonId;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
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

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarXi() {
        return carXi;
    }

    public void setCarXi(String carXi) {
        this.carXi = carXi;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRiderPhone() {
        return riderPhone;
    }

    public void setRiderPhone(String riderPhone) {
        this.riderPhone = riderPhone;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
