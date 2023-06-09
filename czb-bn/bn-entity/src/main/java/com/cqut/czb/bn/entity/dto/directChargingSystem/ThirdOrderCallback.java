package com.cqut.czb.bn.entity.dto.directChargingSystem;

public class ThirdOrderCallback {

    int code;

    String msg;

    String orderId;

    String sign;

    public ThirdOrderCallback() {
    }

    public ThirdOrderCallback(int code, String msg, String orderId) {
        this.code = code;
        this.msg = msg;
        this.orderId = orderId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "ThirdOrderCallback{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", orderId='" + orderId + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
