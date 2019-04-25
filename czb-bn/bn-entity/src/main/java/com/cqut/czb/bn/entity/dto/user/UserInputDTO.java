package com.cqut.czb.bn.entity.dto.user;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * UserInputDTO 用户输入DTO
 * 设计者:   曹渝
 * 更新日期: 2018/4/24
 */
public class UserInputDTO {

    private String userId;

    private String userAccount;

    private String userName;

    private Integer userType;

    private Integer userRank;
    /**
     * 注册时间
     * */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    private Date createStartTime;

    private Date createEndTime;

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserRank() {
        return userRank;
    }

    public void setUserRank(Integer userRank) {
        this.userRank = userRank;
    }

    public Date getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(Date createStartTime) {
        this.createStartTime = createStartTime;
    }

    public Date getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }
}
