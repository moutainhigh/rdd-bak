package com.cqut.czb.bn.entity.dto.enterpriseInfo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class EnterpriseInfoDTO {
    private String enterpriseInfoId;
    //企业名称
    private String enterpriseName;
    //企业法人
    private String legalPerson;
    //联系电话
    private String contactInfo;
    //是否删除   0没删   1删除
    private Integer isDeleted;
    //合同數
    private Integer totalContract;

    private String userId;
    //组织机构代码
    private String orgCode;
    //企业账号
    private String userAccount;
    //创建时间
    private Date createAt;
    //更新时间
    private Date updateAt;
    //开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    //结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

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

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public Integer getTotalContract() {
        return totalContract;
    }

    public void setTotalContract(Integer totalContract) {
        this.totalContract = totalContract;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
