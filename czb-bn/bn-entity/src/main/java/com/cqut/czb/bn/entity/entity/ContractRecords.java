package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class ContractRecords {
    private String recordId;

    private Date signedTime;

    private String userId;

    private Date contractStartTime;

    private Date contractEndTime;

    private Integer contractState;

    private Integer contractType;

    private Double rent;

    private String thirdContractNum;

    private Double gotTotalMoney;

    private String judgeUserId;

    private Date judgeTime;

    private Double payTotalMoney;

    private Date createAt;

    private Date updateAt;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public Date getSignedTime() {
        return signedTime;
    }

    public void setSignedTime(Date signedTime) {
        this.signedTime = signedTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(Date contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    public Date getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(Date contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

    public Integer getContractState() {
        return contractState;
    }

    public void setContractState(Integer contractState) {
        this.contractState = contractState;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public String getThirdContractNum() {
        return thirdContractNum;
    }

    public void setThirdContractNum(String thirdContractNum) {
        this.thirdContractNum = thirdContractNum == null ? null : thirdContractNum.trim();
    }

    public Double getGotTotalMoney() {
        return gotTotalMoney;
    }

    public void setGotTotalMoney(Double gotTotalMoney) {
        this.gotTotalMoney = gotTotalMoney;
    }

    public String getJudgeUserId() {
        return judgeUserId;
    }

    public void setJudgeUserId(String judgeUserId) {
        this.judgeUserId = judgeUserId == null ? null : judgeUserId.trim();
    }

    public Date getJudgeTime() {
        return judgeTime;
    }

    public void setJudgeTime(Date judgeTime) {
        this.judgeTime = judgeTime;
    }

    public Double getPayTotalMoney() {
        return payTotalMoney;
    }

    public void setPayTotalMoney(Double payTotalMoney) {
        this.payTotalMoney = payTotalMoney;
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