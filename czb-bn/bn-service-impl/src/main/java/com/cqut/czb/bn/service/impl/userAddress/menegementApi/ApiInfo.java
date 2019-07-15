package com.cqut.czb.bn.service.impl.userAddress.menegementApi;

public class ApiInfo {

    private String apiId;//接口id

    private String api_url ;//接口路径

    private String apiName;//接口名

    private String apiRemark;//接口备注

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getApi_url() {
        return api_url;
    }

    public void setApi_url(String api_url) {
        this.api_url = api_url;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiRemark() {
        return apiRemark;
    }

    public void setApiRemark(String apiRemark) {
        this.apiRemark = apiRemark;
    }
}
