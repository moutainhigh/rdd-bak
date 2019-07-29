package com.cqut.czb.bn.entity.dto.infoSpread;

import java.util.Date;
import java.util.List;

public class PartnerDTO {

    private String userId;

    private Integer partner;

    private String userAccount;

    private Integer type;  //消费类型

    private String missionStartTime;

    private String missionEndTime;

    private Integer targetPromotionNumber;

    private Integer actualPromotionNumber;

    private Integer targetNewConsumer;

    private Integer actualNewConsumer;

    private List<PartnerDTO> childPartner;

    private List<PartnerDTO> partnerList;

    private String firstPartner;

    private String secondPartner;

    private String superior;   //推荐人

    private String monthTime;

    private Date nearTime;      //最近的消费时间

    private Double nextTotalMoney;  //直推消费总额

    private Double totalMoney;      //子级总消费

    private Integer totalCount;     //子级用户总数

    private Integer consumptionCount; //消费次数

    private Integer payMethod;  //支付方式

    private String thirdOrderId; //第三方订单号

    private String orderBy;  //排序属性

    private String order;    //排序方式

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getFirstPartner() {
        return firstPartner;
    }

    public void setFirstPartner(String firstPartner) {
        this.firstPartner = firstPartner;
    }

    public String getSecondPartner() {
        return secondPartner;
    }

    public void setSecondPartner(String secondPartner) {
        this.secondPartner = secondPartner;
    }

    public void setMonthTime(String monthTime) {
        this.monthTime = monthTime;
    }

    public String getSuperior() {
        return superior;
    }

    public void setSuperior(String superior) {
        this.superior = superior;
    }

    public Date getNearTime() {
        return nearTime;
    }

    public void setNearTime(Date nearTime) {
        this.nearTime = nearTime;
    }

    public Double getNextTotalMoney() {
        return nextTotalMoney;
    }

    public void setNextTotalMoney(Double nextTotalMoney) {
        this.nextTotalMoney = nextTotalMoney;
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

    public Integer getConsumptionCount() {
        return consumptionCount;
    }

    public void setConsumptionCount(Integer consumptionCount) {
        this.consumptionCount = consumptionCount;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getThirdOrderId() {
        return thirdOrderId;
    }

    public void setThirdOrderId(String thirdOrderId) {
        this.thirdOrderId = thirdOrderId;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
