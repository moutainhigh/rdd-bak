package com.cqut.czb.bn.entity.entity.vehicleService;

import java.util.Date;

public class VehicleCleanOrder {
    private String serverOrderId;

    private String userId;

    private String riderId;

    private String thirdOrder;

    private Byte payStatus;

    private Double actualPrice;

    private String vehicleId;

    private Byte processStatus;

    private Date cancelTime;

    private String cancelPersonId;

    private String cancelReason;

    private Date createAt;

    private Date updateAt;

    private String serverId;

    public String getServerOrderId() {
        return serverOrderId;
    }

    public void setServerOrderId(String serverOrderId) {
        this.serverOrderId = serverOrderId == null ? null : serverOrderId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId == null ? null : riderId.trim();
    }

    public String getThirdOrder() {
        return thirdOrder;
    }

    public void setThirdOrder(String thirdOrder) {
        this.thirdOrder = thirdOrder == null ? null : thirdOrder.trim();
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
        this.vehicleId = vehicleId == null ? null : vehicleId.trim();
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
        this.cancelPersonId = cancelPersonId == null ? null : cancelPersonId.trim();
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason == null ? null : cancelReason.trim();
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
        this.serverId = serverId == null ? null : serverId.trim();
    }
}