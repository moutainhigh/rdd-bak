package com.cqut.czb.bn.entity.dto.directChargingSystem;

import java.util.Arrays;

public class SelectOrderDto {
    private String[] orderId;

    private Integer state;

    private String up;

    @Override
    public String toString() {
        return "SelectOrderDto{" +
                "orderId=" + Arrays.toString(orderId) +
                ", state=" + state +
                '}';
    }

    public String[] getOrderId() {
        return orderId;
    }

    public void setOrderId(String[] orderId) {
        this.orderId = orderId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUp() {
        return up;
    }

    public void setUp(String up) {
        this.up = up;
    }
}
