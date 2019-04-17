package com.cqut.czb.bn.entity.dto;

import com.cqut.czb.bn.entity.entity.Notify;

public class NotifyInsertInputDTO {
    private double money;
    private String peyType;
    private String sign;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getPeyType() {
        return peyType;
    }

    public void setPeyType(String peyType) {
        this.peyType = peyType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Notify getNotify(){
        Notify notify = new Notify();
        notify.setState(1);
        notify.setPayType(getPeyType());
        notify.setMoney(getMoney());
        notify.setNotifyId(System.currentTimeMillis()+"");
        return notify;
    }
}
