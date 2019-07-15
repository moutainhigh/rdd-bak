package com.cqut.czb.bn.service.impl.userAddress.menegementApi;

/**
 * 添加apiUrl
 */
public class ApiDTO {
    private String apiId;
    private String requestUrl;
    private String requestType;
    private String controllerName;
    private String requestMethodName;
    private Class<?>[] methodParamTypes;

    public ApiDTO(String requestUrl, String requestType, String controllerName, String requestMethodName, Class<?>[] methodParamTypes) {
        this.requestUrl = requestUrl;
        this.requestType = requestType;
        this.controllerName = controllerName;
        this.requestMethodName = requestMethodName;
        this.methodParamTypes = methodParamTypes;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getRequestMethodName() {
        return requestMethodName;
    }

    public void setRequestMethodName(String requestMethodName) {
        this.requestMethodName = requestMethodName;
    }

    public Class<?>[] getMethodParamTypes() {
        return methodParamTypes;
    }

    public void setMethodParamTypes(Class<?>[] methodParamTypes) {
        this.methodParamTypes = methodParamTypes;
    }
}
