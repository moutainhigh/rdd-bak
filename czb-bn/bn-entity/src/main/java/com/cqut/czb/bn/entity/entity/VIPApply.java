package com.cqut.czb.bn.entity.entity;

import com.cqut.czb.bn.entity.dto.PageDTO;

import java.util.Date;

public class VIPApply extends PageDTO {
    private String vipAccount;

    private String vipName;

    private Date createAt;

    private Date updateAt;

    private String recordId;

    private String remark;

    private Integer status;

    private String phoneNum;

    /**
     * get方法
     * @return
     */
    public String getVipAccount() {
        return vipAccount;
    }

    public String getVipName() {
        return vipName;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getRemark() {
        return remark;
    }

    public Integer getStatus() {
        return status;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * set方法
     * @param vipAccount
     */
    public void setVipAccount(String vipAccount) {
        this.vipAccount = vipAccount;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

}
