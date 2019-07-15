package com.cqut.czb.bn.entity.entity.subsidyManage;

/**
 * auth：谭深化
 * module：后台管理，补贴管理模块
 * time：2019-07-14
 */

public class SubsidyMission {
    // 补贴任务id
    private String missionId;

    // 补贴比例
    private Double subsidyRate;

    // 补贴状态
    private Integer state;

    // 补贴月份
    private String subsidyMonth;

    // 补贴时间
    private String subsidyTime;

    // 补贴对象
    private Integer subsidyType;

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public Double getSubsidyRate() {
        return subsidyRate;
    }

    public void setSubsidyRate(Double subsidyRate) {
        this.subsidyRate = subsidyRate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSubsidyMonth() {
        return subsidyMonth;
    }

    public void setSubsidyMonth(String subsidyMonth) {
        this.subsidyMonth = subsidyMonth;
    }

    public String getSubsidyTime() {
        return subsidyTime;
    }

    public void setSubsidyTime(String subsidyTime) {
        this.subsidyTime = subsidyTime;
    }

    public Integer getSubsidyType() {
        return subsidyType;
    }

    public void setSubsidyType(Integer subsidyType) {
        this.subsidyType = subsidyType;
    }
}
