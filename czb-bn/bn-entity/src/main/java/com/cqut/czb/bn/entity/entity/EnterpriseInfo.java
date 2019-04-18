package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class EnterpriseInfo {
    private String enterpriseInfoId;

    private String enterpriseName;

    private String legalPerson;

    private String contactInfo;

    private Integer isDeleted;

    private String userId;

    private String orgCode;

    private Date ctreateAt;

    private Date updateAt;

    public String getEnterpriseInfoId() {
        return enterpriseInfoId;
    }

    public void setEnterpriseInfoId(String enterpriseInfoId) {
        this.enterpriseInfoId = enterpriseInfoId == null ? null : enterpriseInfoId.trim();
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName == null ? null : enterpriseName.trim();
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo == null ? null : contactInfo.trim();
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public Date getCtreateAt() {
        return ctreateAt;
    }

    public void setCtreateAt(Date ctreateAt) {
        this.ctreateAt = ctreateAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}