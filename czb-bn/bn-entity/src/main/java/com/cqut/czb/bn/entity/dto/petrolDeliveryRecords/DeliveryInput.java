package com.cqut.czb.bn.entity.dto.petrolDeliveryRecords;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DeliveryInput {

    private String recordId;

    private String petrolNum;

    private Integer petrolKind;

    private Integer deliveryState;

    private String deliveryNum;

    private String contactNumber;

    private String deliveryCompany;
//    //京东物流查询专用
//    private String customerName;

    private String ids;

    private String startTime;

    private String endTime;

    public Integer getIsSpecialPetrol() {
        return isSpecialPetrol;
    }

    public void setIsSpecialPetrol(Integer isSpecialPetrol) {
        this.isSpecialPetrol = isSpecialPetrol;
    }
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date startTime;
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date endTime;

    private Integer isSpecialPetrol; // 普通特殊油卡类型

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(String petrolNum) {
        this.petrolNum = petrolNum;
    }

    public Integer getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(Integer petrolKind) {
        this.petrolKind = petrolKind;
    }

    public Integer getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(Integer deliveryState) {
        this.deliveryState = deliveryState;
    }

    public String getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(String deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

//    public String getCustomerName() {
//        return customerName;
//    }

//    public void setCustomerName(String customerName) {
//        this.customerName = customerName;
//    }
//
//    public String getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(String startTime) {
//        this.startTime = startTime;
//    }
//
//    public String getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(String endTime) {
//        this.endTime = endTime;
//    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
