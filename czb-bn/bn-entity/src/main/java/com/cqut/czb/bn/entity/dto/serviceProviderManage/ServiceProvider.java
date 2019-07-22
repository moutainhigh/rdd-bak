package com.cqut.czb.bn.entity.dto.serviceProviderManage;

public class ServiceProvider {
    //服务商id
    private String serviceId;

    // 服务商名称
    private String serviceName;

    // 联系人
    private String servicePeopleName;

    // 联系电话
    private String phone;

    // 店铺地址
    private String serviceAddress;

    // 历史交易总额
    private Double amountHistory;

    // 当月交易总额
    private Double amountMonth;

    // 上一次结算时间
    private String lastTime;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePeopleName() {
        return servicePeopleName;
    }

    public void setServicePeopleName(String servicePeopleName) {
        this.servicePeopleName = servicePeopleName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public Double getAmountHistory() {
        return amountHistory;
    }

    public void setAmountHistory(Double amountHistory) {
        this.amountHistory = amountHistory;
    }

    public Double getAmountMonth() {
        return amountMonth;
    }

    public void setAmountMonth(Double amountMonth) {
        this.amountMonth = amountMonth;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
