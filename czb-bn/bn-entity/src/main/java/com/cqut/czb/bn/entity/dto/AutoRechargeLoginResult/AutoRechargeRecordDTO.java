package com.cqut.czb.bn.entity.dto.AutoRechargeLoginResult;

import com.cqut.czb.bn.entity.entity.autoRecharge.AutoRechargeRecord;

public class AutoRechargeRecordDTO extends AutoRechargeRecord {
    private String userAccount;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}
