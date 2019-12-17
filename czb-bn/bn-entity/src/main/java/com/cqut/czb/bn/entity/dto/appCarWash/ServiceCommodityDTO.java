package com.cqut.czb.bn.entity.dto.appCarWash;

public class ServiceCommodityDTO {

    private String serverId;

    private String serverName;

    private String serverType;

    private Double serverPrice;

    private Double vipPrice;

    private String serverExplain;

    private Float serverDiscount;

    private Byte serverVehicleType;

    private String filePath;

    private Double currentPrice;

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public Double getServerPrice() {
        return serverPrice;
    }

    public void setServerPrice(Double serverPrice) {
        this.serverPrice = serverPrice;
    }

    public Double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Double vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getServerExplain() {
        return serverExplain;
    }

    public void setServerExplain(String serverExplain) {
        this.serverExplain = serverExplain;
    }

    public Float getServerDiscount() {
        return serverDiscount;
    }

    public void setServerDiscount(Float serverDiscount) {
        this.serverDiscount = serverDiscount;
    }

    public Byte getServerVehicleType() {
        return serverVehicleType;
    }

    public void setServerVehicleType(Byte serverVehicleType) {
        this.serverVehicleType = serverVehicleType;
    }

}
