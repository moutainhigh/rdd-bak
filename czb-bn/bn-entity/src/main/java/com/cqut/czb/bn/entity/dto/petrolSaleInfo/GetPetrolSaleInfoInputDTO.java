package com.cqut.czb.bn.entity.dto.petrolSaleInfo;

import com.cqut.czb.bn.entity.dto.PageDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class GetPetrolSaleInfoInputDTO extends PageDTO {
    private String petrolNum;//油卡号
    private String petrolKind;//油卡种类 0 国通，1 中石油，2 中石化
    private String petrolDenomination;//油卡面额
    private String owner;//购买者电话
    private String area;//油卡所属区域
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date transactionTime;//交易时间
    private String paymentMethod; //0 佣金购买，1 支付宝，2 微信，3 自己开发的方案，4 合同打款
    private String petrolPrice;

    public String getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(String petrolPrice) {
        this.petrolPrice = petrolPrice;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    public String getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(String petrolNum) {
        this.petrolNum = petrolNum;
    }

    public String getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(String petrolKind) {
        this.petrolKind = petrolKind;
    }

    public String getPetrolDenomination() {
        return petrolDenomination;
    }

    public void setPetrolDenomination(String petrolDenomination) {
        this.petrolDenomination = petrolDenomination;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}