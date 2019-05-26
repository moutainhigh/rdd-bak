package com.cqut.czb.bn.entity.dto.contractManagement;

import java.util.Date;

public class PersonalContractDetailDTO {

    private String contractId;

    private Date signedTime;

    private String signer;

    /**
     *  合同状态
     * */
    private Integer contractState; // 0: 未生效，1: 生效，2: 已失效

    /**
     *  签署状态
     * */
    private Integer signState; // 0: 未签署，1: 已签署

    private Date contractStartTime;

    private Date contractEndTime;

    private Double rent;

    private Double gotTotalMoney;

    private String bankUserName;

    private String bankOfDeposit;

    private String bankAccountNum;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Date getSignedTime() {
        return signedTime;
    }

    public void setSignedTime(Date signedTime) {
        this.signedTime = signedTime;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public Integer getContractState() {
        return contractState;
    }

    public void setContractState(Integer contractState) {
        this.contractState = contractState;
    }

    public Integer getSignState() {
        return signState;
    }

    public void setSignState(Integer signState) {
        this.signState = signState;
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

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public Double getGotTotalMoney() {
        return gotTotalMoney;
    }

    public void setGotTotalMoney(Double gotTotalMoney) {
        this.gotTotalMoney = gotTotalMoney;
    }

    public String getBankUserName() {
        return bankUserName;
    }

    public void setBankUserName(String bankUserName) {
        this.bankUserName = bankUserName;
    }

    public String getBankOfDeposit() {
        return bankOfDeposit;
    }

    public void setBankOfDeposit(String bankOfDeposit) {
        this.bankOfDeposit = bankOfDeposit;
    }

    public String getBankAccountNum() {
        return bankAccountNum;
    }

    public void setBankAccountNum(String bankAccountNum) {
        this.bankAccountNum = bankAccountNum;
    }
}
