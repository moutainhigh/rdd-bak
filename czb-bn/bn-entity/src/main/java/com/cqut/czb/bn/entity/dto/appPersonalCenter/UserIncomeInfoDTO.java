package com.cqut.czb.bn.entity.dto.appPersonalCenter;

import java.util.Date;

/**
 * 创建人：陈德强
 * 创建时间：21019/4/22
 * 作用：个人收入信息
 */
public class UserIncomeInfoDTO {

    private String infoId;

    private String userId;

    private Double fanyongIncome;

    private Double shareIncome;

    private Double payTotalRent;

    private Double gotTotalRent;

    private Double otherIncome;

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getFanyongIncome() {
        return fanyongIncome;
    }

    public void setFanyongIncome(Double fanyongIncome) {
        this.fanyongIncome = fanyongIncome;
    }

    public Double getShareIncome() {
        return shareIncome;
    }

    public void setShareIncome(Double shareIncome) {
        this.shareIncome = shareIncome;
    }

    public Double getPayTotalRent() {
        return payTotalRent;
    }

    public void setPayTotalRent(Double payTotalRent) {
        this.payTotalRent = payTotalRent;
    }

    public Double getGotTotalRent() {
        return gotTotalRent;
    }

    public void setGotTotalRent(Double gotTotalRent) {
        this.gotTotalRent = gotTotalRent;
    }

    public Double getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(Double otherIncome) {
        this.otherIncome = otherIncome;
    }
}
