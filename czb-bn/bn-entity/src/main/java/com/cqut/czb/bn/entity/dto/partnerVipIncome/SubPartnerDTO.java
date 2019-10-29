package com.cqut.czb.bn.entity.dto.partnerVipIncome;

import java.util.Date;

public class SubPartnerDTO {
    private String userId;

    private String subUserId;

    private Date createAt;  //成为子级时间


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubUserId() {
        return subUserId;
    }

    public void setSubUserId(String subUserId) {
        this.subUserId = subUserId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
