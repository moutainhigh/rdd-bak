package com.cqut.czb.auth.service;

import com.cqut.czb.bn.entity.entity.User;

public interface UserDetailService {
    Boolean register(User user);
    Boolean checkAccount(User user);

    /**
     * 修改密码
     * @param user
     * @return
     */
    Boolean changePW(User user);
}
