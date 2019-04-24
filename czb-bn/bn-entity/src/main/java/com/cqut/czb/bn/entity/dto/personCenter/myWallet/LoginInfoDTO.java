package com.cqut.czb.bn.entity.dto.personCenter.myWallet;

public class LoginInfoDTO {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 角色类型
     */
    private String roleType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
