package com.cqut.czb.bn.entity.entity.food;

import java.util.Date;

public class DishRelationship {
    private String dishRelationshipId;

    private String setMealId;

    private String singleProductId;

    private Date createAt;

    private Date updateAt;

    public String getDishRelationshipId() {
        return dishRelationshipId;
    }

    public void setDishRelationshipId(String dishRelationshipId) {
        this.dishRelationshipId = dishRelationshipId == null ? null : dishRelationshipId.trim();
    }

    public String getSetMealId() {
        return setMealId;
    }

    public void setSetMealId(String setMealId) {
        this.setMealId = setMealId == null ? null : setMealId.trim();
    }

    public String getSingleProductId() {
        return singleProductId;
    }

    public void setSingleProductId(String singleProductId) {
        this.singleProductId = singleProductId == null ? null : singleProductId.trim();
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