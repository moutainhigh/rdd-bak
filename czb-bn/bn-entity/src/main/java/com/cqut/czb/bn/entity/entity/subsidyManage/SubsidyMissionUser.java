package com.cqut.czb.bn.entity.entity.subsidyManage;

public class SubsidyMissionUser {
    // 关系id
    private String relationId;

    // 补贴任务id
    private String missionId;

    // 补贴用户id
    private String userId;

    //  补贴金额
    private Double amount;

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
