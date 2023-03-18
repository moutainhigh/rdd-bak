package com.cqut.czb.bn.entity.dto.directChargingSystem;

public class YFCallBack {
    String userid;
    String order_number;
    String out_trade_num;
    Integer state;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getOut_trade_num() {
        return out_trade_num;
    }

    public void setOut_trade_num(String out_trade_num) {
        this.out_trade_num = out_trade_num;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "YFCallBack{" +
                "userid='" + userid + '\'' +
                ", order_number='" + order_number + '\'' +
                ", out_trade_num='" + out_trade_num + '\'' +
                ", state=" + state +
                '}';
    }
}
