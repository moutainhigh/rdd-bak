package com.cqut.czb.bn.entity.dto.directChargingSystem;

public class JHCallBack {
    String orderId;
    String phone;
    String amount;
    String time;
    int status;
    String msg;

    @Override
    public String toString() {
        return "JHCallBack{" +
                "orderId='" + orderId + '\'' +
                ", phone='" + phone + '\'' +
                ", amount='" + amount + '\'' +
                ", time='" + time + '\'' +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
