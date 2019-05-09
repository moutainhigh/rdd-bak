package com.cqut.czb.bn.entity.dto.rentCar;

public class SignerData {
    // 合同id
    private Integer id;

    // 合同状态
    private String status;

    // 合同状态码
    private String statusCode;

    // 合同标题
    private String title;

    public SignerData() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
