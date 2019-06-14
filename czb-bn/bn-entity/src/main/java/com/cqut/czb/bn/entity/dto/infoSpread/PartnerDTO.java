package com.cqut.czb.bn.entity.dto.infoSpread;

import java.util.Date;
import java.util.List;

public class PartnerDTO {

    private String userId;

    private Integer partner;

    private String userAccount;

    private String missionStartTime;

    private String missionEndTime;

    private Integer targetPromotionNumber;

    private Integer actualPromotionNumber;

    private Integer targetNewConsumer;

    private Integer actualNewConsumer;

    private List<PartnerDTO> childPartner;

    private List<PartnerDTO> partnerList;

    private String monthTime;

    private Date nearTime;      //最近的消费时间

    private Double totalMoney;      //子级总消费

    private Integer totalCount;     //子级用户总数

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getPartner() {
        return partner;
    }

    public void setPartner(Integer partner) {
        this.partner = partner;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getMissionStartTime() {
        return missionStartTime;
    }

    public void setMissionStartTime(String missionStartTime) {
        this.missionStartTime = missionStartTime;
    }

    public String getMissionEndTime() {
        return missionEndTime;
    }

    public void setMissionEndTime(String missionEndTime) {
        this.missionEndTime = missionEndTime;
    }

    public Integer getTargetPromotionNumber() {
        return targetPromotionNumber;
    }

    public void setTargetPromotionNumber(Integer targetPromotionNumber) {
        this.targetPromotionNumber = targetPromotionNumber;
    }

    public Integer getActualPromotionNumber() {
        return actualPromotionNumber;
    }

    public void setActualPromotionNumber(Integer actualPromotionNumber) {
        this.actualPromotionNumber = actualPromotionNumber;
    }

    public Integer getTargetNewConsumer() {
        return targetNewConsumer;
    }

    public void setTargetNewConsumer(Integer targetNewConsumer) {
        this.targetNewConsumer = targetNewConsumer;
    }

    public Integer getActualNewConsumer() {
        return actualNewConsumer;
    }

    public void setActualNewConsumer(Integer actualNewConsumer) {
        this.actualNewConsumer = actualNewConsumer;
    }

    public List<PartnerDTO> getChildPartner() {
        return childPartner;
    }

    public void setChildPartner(List<PartnerDTO> childPartner) {
        this.childPartner = childPartner;
    }

    public String getMonthTime() {
        return monthTime;
    }

    public List<PartnerDTO> getPartnerList() {
        return partnerList;
    }

    public void setPartnerList(List<PartnerDTO> partnerList) {
        this.partnerList = partnerList;
    }

    public void setMonthTime(String monthTime) {
        this.monthTime = monthTime;
    }

    public Date getNearTime() {
        return nearTime;
    }

    public void setNearTime(Date nearTime) {
        this.nearTime = nearTime;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
