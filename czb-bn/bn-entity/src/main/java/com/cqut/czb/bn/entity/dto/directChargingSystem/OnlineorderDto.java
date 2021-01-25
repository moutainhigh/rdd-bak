package com.cqut.czb.bn.entity.dto.directChargingSystem;

public class OnlineorderDto {

    private String gasUserid;

    private String gasMobile;

    private String ordersn;

    private String cardnum;

    private String appId;

    private String sign;

    public String getGasUserid() {
        return gasUserid;
    }

    public void setGasUserid(String gasUserid) {
        this.gasUserid = gasUserid;
    }

    public String getGasMobile() {
        return gasMobile;
    }

    public void setGasMobile(String gasMobile) {
        this.gasMobile = gasMobile;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
