package com.cqut.czb.bn.entity.dto.rentCar;

public class SignerData {
    // 合同id
    private Long id;

    // 合同状态
    private String status;

    // 合同状态码
    private Integer statusCode;

    // 合同标题
    private String title;

    public SignerData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
