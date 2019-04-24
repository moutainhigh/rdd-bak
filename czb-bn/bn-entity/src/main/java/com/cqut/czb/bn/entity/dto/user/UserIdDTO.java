package com.cqut.czb.bn.entity.dto.user;

import javax.validation.constraints.NotNull;

/**
 * UserIdDTO 用户IdDTO
 * 设计者:   曹渝
 * 更新日期: 2018/4/24
 */
public class UserIdDTO {

    @NotNull(message = "用户id不能为空")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
