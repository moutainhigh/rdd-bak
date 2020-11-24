package com.cqut.czb.bn.entity.dto.withoutCard;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PetrolWithoutCardDto extends PageDTO {
    private String petrolId;

    private String petrolNum;

    private String petrolPsw;

    private Double availableIntegral;

    private Double pointsToBeLoaded;

    private Double petrolBalance;

    private Double reserveFund;

    private String area;

    private String lastTransactionArea;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lastTransactionTime;

    private Double accumulativeTotal;

    private Date startTime;

    private Date endTime;

    public String getPetrolId() {
        return petrolId;
    }

    public void setPetrolId(String petrolId) {
        this.petrolId = petrolId;
    }

    public String getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(String petrolNum) {
        this.petrolNum = petrolNum;
    }

    public String getPetrolPsw() {
        return petrolPsw;
    }

    public void setPetrolPsw(String petrolPsw) {
        this.petrolPsw = petrolPsw;
    }

    public Double getAvailableIntegral() {
        return availableIntegral;
    }

    public void setAvailableIntegral(Double availableIntegral) {
        this.availableIntegral = availableIntegral;
    }

    public Double getPointsToBeLoaded() {
        return pointsToBeLoaded;
    }

    public void setPointsToBeLoaded(Double pointsToBeLoaded) {
        this.pointsToBeLoaded = pointsToBeLoaded;
    }

    public Double getPetrolBalance() {
        return petrolBalance;
    }

    public void setPetrolBalance(Double petrolBalance) {
        this.petrolBalance = petrolBalance;
    }

    public Double getReserveFund() {
        return reserveFund;
    }

    public void setReserveFund(Double reserveFund) {
        this.reserveFund = reserveFund;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLastTransactionArea() {
        return lastTransactionArea;
    }

    public void setLastTransactionArea(String lastTransactionArea) {
        this.lastTransactionArea = lastTransactionArea;
    }

    public Date getLastTransactionTime() {
        return lastTransactionTime;
    }

    public void setLastTransactionTime(Date lastTransactionTime) {
        this.lastTransactionTime = lastTransactionTime;
    }

    public Double getAccumulativeTotal() {
        return accumulativeTotal;
    }

    public void setAccumulativeTotal(Double accumulativeTotal) {
        this.accumulativeTotal = accumulativeTotal;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
