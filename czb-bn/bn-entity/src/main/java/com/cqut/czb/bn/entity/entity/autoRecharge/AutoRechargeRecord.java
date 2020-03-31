package com.cqut.czb.bn.entity.entity.autoRecharge;

import java.util.Date;

public class AutoRechargeRecord {
    private String autoRechargeRecordId;

    private String petrolNum;

    private Double rechargeAmount;

    private Double price;

    private Integer status;

    private String message;

    private Date rechargeTime;

    private Date orderTime;

    private Date createAt;

    private Date updateAt;

    public String getAutoRechargeRecordId() {
        return autoRechargeRecordId;
    }

    public void setAutoRechargeRecordId(String autoRechargeRecordId) {
        this.autoRechargeRecordId = autoRechargeRecordId == null ? null : autoRechargeRecordId.trim();
    }

    public String getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(String petrolNum) {
        this.petrolNum = petrolNum == null ? null : petrolNum.trim();
    }

    public Double getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(Double rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
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
}