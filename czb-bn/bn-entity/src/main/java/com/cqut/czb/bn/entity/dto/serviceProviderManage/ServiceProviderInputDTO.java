package com.cqut.czb.bn.entity.dto.serviceProviderManage;

public class ServiceProviderInputDTO {
    // 服务商id
    private String serviceId;

    // 店铺名称
    private String serviceName;

    // 联系人
    private String servicePeopleName;

    // 结算时间
    private String resultTime;

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

    public String getResultTime() {
        return resultTime;
    }

    public void setResultTime(String resultTime) {
        this.resultTime = resultTime;
    }
}
