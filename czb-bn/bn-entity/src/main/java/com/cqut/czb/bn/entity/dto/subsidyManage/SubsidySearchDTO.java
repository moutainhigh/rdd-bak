package com.cqut.czb.bn.entity.dto.subsidyManage;

/**
 * auth: 谭深化
 * time: 2019-07-11
 */

public class SubsidySearchDTO {
    // 账号
    private String account;

    // 补贴月份
    private String subsidyMonth;

    // 补贴时间
    private String subsidyTime;

    // 合伙人类型
    private Integer partnerType;

    public Integer getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(Integer partnerType) {
        this.partnerType = partnerType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
}
