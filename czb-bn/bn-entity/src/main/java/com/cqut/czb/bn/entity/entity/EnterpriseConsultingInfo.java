package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class EnterpriseConsultingInfo {
    private String consultingId;

    private String enterpriseName;

    private String contactPhone;

    private Date createAt;

    private Date updateAt;

    private Integer isHandled;

    private String applicantId;

    private String applicantAccount;

    public String getConsultingId() {
        return consultingId;
    }

    public void setConsultingId(String consultingId) {
        this.consultingId = consultingId == null ? null : consultingId.trim();
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName == null ? null : enterpriseName.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
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

    public Integer getIsHandled() {
        return isHandled;
    }

    public void setIsHandled(Integer isHandled) {
        this.isHandled = isHandled;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId == null ? null : applicantId.trim();
    }

    public String getApplicantAccount() {
        return applicantAccount;
    }

    public void setApplicantAccount(String applicantAccount) {
        this.applicantAccount = applicantAccount == null ? null : applicantAccount.trim();
    }
}