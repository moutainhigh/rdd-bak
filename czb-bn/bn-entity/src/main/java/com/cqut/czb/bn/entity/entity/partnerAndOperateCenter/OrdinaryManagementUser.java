package com.cqut.czb.bn.entity.entity.partnerAndOperateCenter;

import java.util.List;

/**
 * @ClassName: OrdinaryManagementUser
 * @Author: Iriya720
 * @Date: 2019/12/2
 * @Description:
 * @version: v1.0
 */
public class OrdinaryManagementUser {
    private Integer total;
    List<GeneralPartnerUser> generalPartnerUsers;

    public OrdinaryManagementUser(int total, List<GeneralPartnerUser> users) {
        this.total = total;
        this.generalPartnerUsers = users;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<GeneralPartnerUser> getGeneralPartnerUsers() {
        return generalPartnerUsers;
    }

    public void setGeneralPartnerUsers(List<GeneralPartnerUser> generalPartnerUsers) {
        this.generalPartnerUsers = generalPartnerUsers;
    }
}
