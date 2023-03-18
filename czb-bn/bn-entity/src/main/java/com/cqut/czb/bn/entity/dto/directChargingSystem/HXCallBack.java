package com.cqut.czb.bn.entity.dto.directChargingSystem;

public class HXCallBack {
    String status;
    String partner_order_no;
    String account;
    String amount;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPartner_order_no() {
        return partner_order_no;
    }

    public void setPartner_order_no(String partner_order_no) {
        this.partner_order_no = partner_order_no;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "HXCallBack{" +
                "status='" + status + '\'' +
                ", partner_order_no='" + partner_order_no + '\'' +
                ", account='" + account + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
