package com.cqut.czb.bn.entity.dto.petrolDeliveryRecords;

/**
 * @Description
 * @auther nihao
 * @create 2019-10-22 17:43
 */
public class DeliveryMessageDTO {
    private String userAccount;

    private String petrolNum;

    private String deliveryNum;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(String petrolNum) {
        this.petrolNum = petrolNum;
    }

    public String getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(String deliveryNum) {
        this.deliveryNum = deliveryNum;
    }
}