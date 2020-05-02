package com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.util;

public class BackDTO {
    private String Code;

    private String Msg;

    private String OrderID;

    private String UnitPrice;

    private String ReturnOrderID;

    private String TradingID;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getReturnOrderID() {
        return ReturnOrderID;
    }

    public void setReturnOrderID(String returnOrderID) {
        ReturnOrderID = returnOrderID;
    }

    public String getTradingID() {
        return TradingID;
    }

    public void setTradingID(String tradingID) {
        TradingID = tradingID;
    }
}
