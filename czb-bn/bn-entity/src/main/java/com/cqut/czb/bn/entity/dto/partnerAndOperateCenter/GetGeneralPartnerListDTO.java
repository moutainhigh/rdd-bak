package com.cqut.czb.bn.entity.dto.partnerAndOperateCenter;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class GetGeneralPartnerListDTO {

    //普通合伙人ID
    private String generalPartnerId;

    //普通合伙人电话
    private String generalPartnerPhoneNumber;

    //普通合伙人姓名
    private String generalPartnerName;

    //区域
    private String area;

    //注册时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd hh:mm:ss")
    private Date registrationTime;

    //推荐人电话
    private String recommenderPhoneNumber;

    //推荐人姓名
    private String recommenderName;

    //推广普通用户人数
    private Integer promotionNumbers;

    //未结算金额
    private double unsettledAmount;

    //累计收入
    private double accumulatedIncome;

    //已结算金额
    private double settledAmount;

    public String getGeneralPartnerId() {
        return generalPartnerId;
    }

    public void setGeneralPartnerId(String generalPartnerId) {
        this.generalPartnerId = generalPartnerId;
    }

    public String getGeneralPartnerPhoneNumber() {
        return generalPartnerPhoneNumber;
    }

    public void setGeneralPartnerPhoneNumber(String generalPartnerPhoneNumber) {
        this.generalPartnerPhoneNumber = generalPartnerPhoneNumber;
    }

    public String getGeneralPartnerName() {
        return generalPartnerName;
    }

    public void setGeneralPartnerName(String generalPartnerName) {
        this.generalPartnerName = generalPartnerName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getRecommenderPhoneNumber() {
        return recommenderPhoneNumber;
    }

    public void setRecommenderPhoneNumber(String recommenderPhoneNumber) {
        this.recommenderPhoneNumber = recommenderPhoneNumber;
    }

    public String getRecommenderName() {
        return recommenderName;
    }

    public void setRecommenderName(String recommenderName) {
        this.recommenderName = recommenderName;
    }

    public Integer getPromotionNumbers() {
        return promotionNumbers;
    }

    public void setPromotionNumbers(Integer promotionNumbers) {
        this.promotionNumbers = promotionNumbers;
    }

    public double getUnsettledAmount() {
        return unsettledAmount;
    }

    public void setUnsettledAmount(double unsettledAmount) {
        this.unsettledAmount = unsettledAmount;
    }

    public double getAccumulatedIncome() {
        return accumulatedIncome;
    }

    public void setAccumulatedIncome(double accumulatedIncome) {
        this.accumulatedIncome = accumulatedIncome;
    }

    public double getSettledAmount() {
        return settledAmount;
    }

    public void setSettledAmount(double settledAmount) {
        this.settledAmount = settledAmount;
    }
}
