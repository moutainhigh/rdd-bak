package com.cqut.czb.bn.entity.entity.vehicleService;

import com.cqut.czb.bn.util.string.StringUtil;

import java.util.Date;

public class ServerStandard {
    private String serverId;

    private String serverName;

    private String serverType;

    private Double serverPrice;

    private Double vipPrice;

    private String serverExplain;

    private Float serverDiscount;

    private Byte serverVehicleType;

    private Date createAt;

    private Date updateAt;

    ServerStandard() {
        this.setServerId(StringUtil.createId());
        this.setCreateAt(new Date());
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId == null ? null : serverId.trim();
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType == null ? null : serverType.trim();
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
        this.serverExplain = serverExplain == null ? null : serverExplain.trim();
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