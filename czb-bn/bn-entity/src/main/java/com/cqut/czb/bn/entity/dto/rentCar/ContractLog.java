package com.cqut.czb.bn.entity.dto.rentCar;

/**
 * 合同记录表实体
 */
public class ContractLog {
    /**
     * 合同记录id
     */
    private String recordId;

    // 父级合同记录id
    private String fatherRecordId;

    // 个人租金
    private double rent;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 合同开始时间
     */
    private String startTime;

    /**
     * 合同结束时间
     */
    private String endTime;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public String getFatherRecordId() {
        return fatherRecordId;
    }

    public void setFatherRecordId(String fatherRecordId) {
        this.fatherRecordId = fatherRecordId;
    }
}
