package com.cqut.czb.bn.api.controller.test.model;

import java.util.Date;

/**
 * @author Liyan
 */
public class VideoChargeDTO {
    private String orderId;
    private String account;
    private String productCode;
    private Integer buyNum;
    private Integer IsCallBack;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public Integer getIsCallBack() {
        return IsCallBack;
    }

    public void setIsCallBack(Integer isCallBack) {
        IsCallBack = isCallBack;
    }
}
