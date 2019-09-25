package com.cqut.czb.bn.entity.entity.food;

import java.util.Date;

public class OrderDishes {
    private String orderDishesId;

    private String orderId;

    private String dishId;

    private Integer dishType;

    private Date createAt;

    private Date updateAt;

    private Integer dishCount;

    public Integer getDishCount() {
        return dishCount;
    }

    public void setDishCount(Integer dishCount) {
        this.dishCount = dishCount;
    }

    public String getOrderDishesId() {
        return orderDishesId;
    }

    public void setOrderDishesId(String orderDishesId) {
        this.orderDishesId = orderDishesId == null ? null : orderDishesId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId == null ? null : dishId.trim();
    }

    public Integer getDishType() {
        return dishType;
    }

    public void setDishType(Integer dishType) {
        this.dishType = dishType;
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