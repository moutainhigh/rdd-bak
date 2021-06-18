package com.cqut.czb.bn.entity.dto.directCustomers;

public class DirectCustomersDto {
    private String phoneno;
    private Double cardnum;
    private String ordersn;
    private String appId;
    private String appSecret;
    private String sign;
    private String gasUserid;
    private String gasMobile;

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public Double getCardnum() {
        return cardnum;
    }

    public void setCardnum(Double cardnum) {
        this.cardnum = cardnum;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

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
}
