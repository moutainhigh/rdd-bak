package com.cqut.czb.bn.entity.entity.directChargingSystem;

import java.util.Date;

public class UserCardRelation {
    private String recordId;

    private String userId;

    private String sinopecPetrolNum;

    private String petrolchinaPetrolNum;

    private Date createAt;

    private Date updateAt;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getSinopecPetrolNum() {
        return sinopecPetrolNum;
    }

    public void setSinopecPetrolNum(String sinopecPetrolNum) {
        this.sinopecPetrolNum = sinopecPetrolNum == null ? null : sinopecPetrolNum.trim();
    }

    public String getPetrolchinaPetrolNum() {
        return petrolchinaPetrolNum;
    }

    public void setPetrolchinaPetrolNum(String petrolchinaPetrolNum) {
        this.petrolchinaPetrolNum = petrolchinaPetrolNum == null ? null : petrolchinaPetrolNum.trim();
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