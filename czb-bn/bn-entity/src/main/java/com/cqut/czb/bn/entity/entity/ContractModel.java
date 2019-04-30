package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class ContractModel {
    private String modelId;

    private String yunModelId;

    private String modelName;

    private Date createAt;

    private Date updateAt;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId == null ? null : modelId.trim();
    }

    public String getYunModelId() {
        return yunModelId;
    }

    public void setYunModelId(String yunModelId) {
        this.yunModelId = yunModelId == null ? null : yunModelId.trim();
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName == null ? null : modelName.trim();
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