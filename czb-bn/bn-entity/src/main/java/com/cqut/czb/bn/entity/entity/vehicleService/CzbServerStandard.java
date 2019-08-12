package com.cqut.czb.bn.entity.entity.vehicleService;

import java.util.Date;

public class CzbServerStandard {
    private String serverId;

    private String serverName;

    private String serverType;

    private Double serverPrice;

    private String serverExplain;

    private Float serverDiscount;

    private Byte serverVehicleType;

    private Date createAt;

    private Date updateAt;

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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}