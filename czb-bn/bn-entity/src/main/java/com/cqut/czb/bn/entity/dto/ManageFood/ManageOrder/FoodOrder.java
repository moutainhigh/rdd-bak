package com.cqut.czb.bn.entity.dto.ManageFood.ManageOrder;

import com.cqut.czb.bn.entity.dto.ManageFood.Food;
import com.cqut.czb.bn.entity.dto.ManageFood.SetInfo;

import java.util.Date;
import java.util.List;

public class FoodOrder {
    private String orderId;

    private String userId;

    private String shopId;

    private Double actualPrice;

    private String thirdOrder;

    private Integer payStatus;

    private Integer peyMethod;

    private Integer diningStatus;

    private String remark;

    private Date createAt;

    private Date updateAt;

    private String dishId;

    private String dishName;

    private Food food;

    private List<SetInfo> sets;

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getThirdOrder() {
        return thirdOrder;
    }

    public void setThirdOrder(String thirdOrder) {
        this.thirdOrder = thirdOrder;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getPeyMethod() {
        return peyMethod;
    }

    public void setPeyMethod(Integer peyMethod) {
        this.peyMethod = peyMethod;
    }

    public Integer getDiningStatus() {
        return diningStatus;
    }

    public void setDiningStatus(Integer diningStatus) {
        this.diningStatus = diningStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public List<SetInfo> getSets() {
        return sets;
    }

    public void setSets(List<SetInfo> sets) {
        this.sets = sets;
    }
}
