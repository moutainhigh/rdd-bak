package com.cqut.czb.bn.entity.dto.recommenderInvitee;

import java.util.List;

/**
 *  受邀请者DTO
 * */
public class InviteeDTO {

    private String userId;

    private String userAccount;

    private String userName;

    private Double amount;

    private List<InviteeDTO> inviteeDTOList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<InviteeDTO> getInviteeDTOList() {
        return inviteeDTOList;
    }

    public void setInviteeDTOList(List<InviteeDTO> inviteeDTOList) {
        this.inviteeDTOList = inviteeDTOList;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
